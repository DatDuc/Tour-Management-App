package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import config.config;
import dao.Ve_DAO;
import entity.Tour;
import entity.Ve;
//class này dùng để làm chức năng xem chi tiết tour khi click vào nút "Chi tiết tour" trong mục đặt vé
public class XemChiTietTour extends JFrame{
	String conf = config.conf;
	private static Tour t;
	JLabel lblHinhAnh;
	private Ve_DAO ve_DAO;
	private ArrayList<Ve> listVe;
	public XemChiTietTour(Tour tour) throws RemoteException, MalformedURLException, NotBoundException {
		
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		
		setTitle("Chi tiết Tour");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setSize(700,700);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
		t= tour;
		ve_DAO = (Ve_DAO) Naming.lookup(conf+"/ve_DAO");
		listVe = ve_DAO.DanhSachVeTheoMaTour(t.getMaTour());
		
		lblHinhAnh = new JLabel("");
		JPanel pnHinhAnh = new JPanel();
		pnHinhAnh.add(lblHinhAnh);
		lblHinhAnh.setPreferredSize(new Dimension(700,280));
		//lblHinhAnh.setBackground(Color.BLACK);
		String path = t.getHinhAnh();
		lblHinhAnh.setIcon(ResizeImage(path));
		add(pnHinhAnh, BorderLayout.NORTH);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new GridLayout(2,1));
		
		JLabel lblTenTour = new JLabel(t.getTenTour());
		
		lblTenTour.setFont(new Font("Time New Roman",Font.BOLD+Font.ITALIC, 32));
		lblTenTour.setForeground(Color.BLUE);
		JPanel pnTenTour = new JPanel();
		pnTenTour.add(lblTenTour);
		
		JLabel lblGia = new JLabel("Giá tour:");
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JLabel lblGiaTour = new JLabel(String.valueOf(formatter.format(t.getGiaTour())+" VNĐ"));
		JPanel pnGia = new JPanel();
		pnGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnGia.add(lblGia);
		pnGia.add(lblGiaTour);
		JPanel pnGiaGrid = new JPanel();
		pnGiaGrid.setLayout(new GridLayout(1,2));
		pnGiaGrid.add(pnGia);
		//pnGiaGrid.add(lblGiaTour);

		JLabel lblSoNguoiToiDa = new JLabel("Số người tối đa:");
		JLabel lblSoNguoiTD = new JLabel(String.valueOf(t.getSoLuongNguoi()));//Khai báo để hiển thị số
		JPanel pnSoNguoiToiDa = new JPanel();
		pnSoNguoiToiDa.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnSoNguoiToiDa.add(lblSoNguoiToiDa);
		pnSoNguoiToiDa.add(lblSoNguoiTD);
		JLabel lblSoNguoi = new JLabel("Số vé đã đặt:"); //Khai báo để hiển thị chữ
		JLabel lblSoNguoiDaDat = new JLabel(String.valueOf(listVe.size()) );// khai báo để hiển thị số
		JPanel pnSoNguoiDaDat = new JPanel();
		pnSoNguoiDaDat.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnSoNguoiDaDat.add(lblSoNguoi);
		pnSoNguoiDaDat.add(lblSoNguoiDaDat);
		JPanel pnSoNguoi = new JPanel();
		pnSoNguoi.setLayout(new GridLayout(1,2));
		pnSoNguoi.add(pnSoNguoiDaDat);
		pnSoNguoi.add(pnSoNguoiToiDa);
		
		JLabel lblTDNgayKhoiHanh = new JLabel("Ngày khởi hành:"); // biến này viết tắt của "lbl Tiêu đề ngày khởi hành"
		JLabel lblTDNgayKetThuc = new JLabel("Ngày kết thúc:");// biến này viết tắt của "lbl Tiêu đề ngày kết thúc"
		JLabel lblNgayKH = new JLabel(String.valueOf(t.getNgayKhoiHanh().toString()));
		JLabel lblNgayKT = new JLabel(String.valueOf(t.getNgayKetThuc().toString()));
		JPanel pnNgayKH = new JPanel();
		pnNgayKH.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnNgayKH.add(lblTDNgayKhoiHanh);
		pnNgayKH.add(lblNgayKH);
		JPanel pnNgayKT = new JPanel();
		pnNgayKT.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnNgayKT.add(lblTDNgayKetThuc);
		pnNgayKT.add(lblNgayKT);
		JPanel pnNgay = new JPanel();
		pnNgay.setLayout(new GridLayout(1,2));
		pnNgay.add(pnNgayKH);
		pnNgay.add(pnNgayKT);
		
		JPanel pnThongTin = new JPanel();
		pnThongTin.setLayout(new GridLayout(3,1));
		pnCenter.add(pnTenTour);
		pnThongTin.add(pnGiaGrid);
		pnThongTin.add(pnSoNguoi);
		pnThongTin.add(pnNgay);
		
		JTextArea taMoTa = new JTextArea();
		//taMoTa.setPreferredSize(new Dimension(600,200));
		//taMoTa.setBackground(Color.BLUE);	
		//taMoTa.setTabSize(300);
		taMoTa.setEditable(false);
		taMoTa.setLineWrap(true);
		taMoTa.setText(t.getMoTa());
		//taMoTa.setBackground(Color.MAGENTA);
		taMoTa.setWrapStyleWord(true);
		JScrollPane scrMoTa = new JScrollPane(taMoTa);
		taMoTa.setFont(new Font("Time New Roman", Font.PLAIN, 22));
		JPanel pnMoTa = new JPanel();
		pnMoTa.setLayout(new BorderLayout());
		pnMoTa.add(scrMoTa, BorderLayout.CENTER);
		pnMoTa.setPreferredSize(new Dimension(700,200));
		
		pnCenter.add(pnThongTin);
		
		add(pnCenter, BorderLayout.CENTER);
		add(pnMoTa, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		

	}
	public ImageIcon ResizeImage(String ImagePath)
	 {
		 ImageIcon MyImage = new ImageIcon(ImagePath);
		 Image img = MyImage.getImage();
		 Image newImg = img.getScaledInstance(700, 280, Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImg);
	     return image;
	 }
}
