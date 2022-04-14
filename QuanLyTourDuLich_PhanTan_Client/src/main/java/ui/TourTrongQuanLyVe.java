package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import config.config;
import dao.Tour_DAO;
import dao.Ve_DAO;
import entity.DiaDanh;
import entity.Tour;
import entity.Ve;
// Class này dùng để hiển thị tour trong mục quản lý vé
public class TourTrongQuanLyVe extends JPanel implements ActionListener{
	String conf = config.conf;
	private JPanel pnCC;
	private Tour_DAO tour_DAO;
	private Ve_DAO ve_DAO;
	private Tour t;
	ArrayList<Ve> listVe;
	JButton btnDatTour,btnXem;
	private JLabel lblHinhAnh;
	private JLabel lblSoVeCon;
	public TourTrongQuanLyVe(Tour tour) throws MalformedURLException, RemoteException, NotBoundException {
		
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		
		setBackground(Color.BLUE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,300));
		
		
		t= tour;
		ve_DAO = (Ve_DAO) Naming.lookup(conf+"/ve_DAO");
		listVe = ve_DAO.DanhSachVeTheoMaTour(t.getMaTour());
		//Vinh - 22 - 5
		JLabel lblTenTour = new JLabel(t.getTenTour());
		JPanel pnTenTour = new JPanel();
		pnTenTour.add(lblTenTour);
		pnTenTour.setLayout(new FlowLayout(FlowLayout.LEFT));
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JLabel lblGia = new JLabel(String.valueOf(formatter.format(t.getGiaTour())+" VNĐ"));
		JPanel pnGiaTour = new JPanel();
		pnGiaTour.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnGiaTour.add(new JLabel("Giá vé: "));
		pnGiaTour.add(lblGia);
		lblSoVeCon = new JLabel("    Số vé còn: "+(t.getSoLuongNguoi()-listVe.size())+" vé");
		
		lblSoVeCon.setForeground(Color.RED);
		pnGiaTour.add(lblSoVeCon);	
		JLabel lblTDNgayKH = new JLabel("Ngày khởi hành:");//Tiêu đề ngày khởi hành
		JLabel lblNgayKhoiHanh = new JLabel(String.valueOf(t.getNgayKhoiHanh().toString()));
		JPanel pnNgayKH = new JPanel();
		pnNgayKH.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnNgayKH.add(lblTDNgayKH);
		pnNgayKH.add(lblNgayKhoiHanh);
		
		JLabel lblTDNgayKT = new JLabel("Ngày kết thúc:");//Tiêu đề ngày khởi hành
		lblTDNgayKT.setPreferredSize(lblTDNgayKH.getPreferredSize());
		JLabel lblNgayKetThuc = new JLabel(String.valueOf(t.getNgayKetThuc().toString()));
		JPanel pnNgayKT = new JPanel();
		pnNgayKT.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnNgayKT.add(lblTDNgayKT);
		pnNgayKT.add(lblNgayKetThuc);
		
		JPanel pnButton = new JPanel();
		pnButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnButton.setBackground(new Color(230, 247, 255));
		btnDatTour = new JButton("Đặt vé");
		btnXem = new JButton("Xem Chi tiết tour");
		pnButton.add(btnDatTour);pnButton.add(btnXem);
		pnCC = new JPanel();
		pnCC.setLayout(new GridLayout(4, 1));
		pnCC.add(pnTenTour);
		pnCC.add(pnGiaTour);
		pnCC.add(pnNgayKH);
		pnCC.add(pnNgayKT);
		pnTenTour.setBackground(new Color(230, 247, 255));
		pnGiaTour.setBackground(new Color(230, 247, 255));
		pnNgayKH.setBackground(new Color(230, 247, 255));
		pnNgayKT.setBackground(new Color(230, 247, 255));
		
		lblHinhAnh = new JLabel("");
		JPanel pnHinhAnh = new JPanel();
		pnHinhAnh.setBackground(new Color(230, 247, 255));
		pnHinhAnh.add(lblHinhAnh);
		String path = t.getHinhAnh();
		lblHinhAnh.setIcon(ResizeImage(path));
		
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new GridLayout(2,1));
		pnCenter.add(pnHinhAnh);
		pnCenter.add(pnCC);
		
		add(pnCenter, BorderLayout.CENTER);
		add(pnButton,BorderLayout.SOUTH);
		
		btnDatTour.addActionListener(this);
		btnXem.addActionListener(this);
		//
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//Vinh - 20-5
		Object obj = e.getSource();
		if(obj.equals(btnDatTour))
		{
			LocalDate ngayHienTai = LocalDate.now();
			if(ngayHienTai.compareTo(t.getNgayKetThuc().toLocalDate())>0)
			{
				JOptionPane.showMessageDialog(this, "Tour không thể đăng ký vì đã kết thúc");
				return ;
			}
			if(ngayHienTai.compareTo(t.getNgayKhoiHanh().toLocalDate())>0)
			{
				JOptionPane.showMessageDialog(this, "Tour không thể đăng ký vì đã khởi hành");
				return;
			}
			else if(listVe.size()>=t.getSoLuongNguoi()) {
				JOptionPane.showMessageDialog(this, "Tour không thể đăng ký vì đã đủ số lượng người");
				return;
			} else
				try {
					new DatVe(t);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
		else if(obj.equals(btnXem)) {
			try {
				new XemChiTietTour(t);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public ImageIcon ResizeImage(String ImagePath)
	 {
		 ImageIcon MyImage = new ImageIcon(ImagePath);
		 Image img = MyImage.getImage();
		 Image newImg = img.getScaledInstance(290, 150, Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImg);
	     return image;
	 }
	//
}
