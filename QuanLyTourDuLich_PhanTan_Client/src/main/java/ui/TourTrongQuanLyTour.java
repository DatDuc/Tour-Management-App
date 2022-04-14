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
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.DiaDanh;
import entity.Tour;
// Class này dùng để hiển thị tour trong mục quản lý vé
public class TourTrongQuanLyTour extends JPanel implements ActionListener{
	private JPanel pnCC;
	private Tour t;
	JButton btnSua;
	private JLabel lblHinhAnh;
	private JLabel lblThongBao;
	public TourTrongQuanLyTour(Tour tour) {
		setBackground(Color.BLUE);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300,300));
		
		t= tour;
		//Vinh - 22 - 5
		JLabel lblTenTour = new JLabel(t.getTenTour());
		JPanel pnTenTour = new JPanel();
		pnTenTour.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnTenTour.add(lblTenTour);
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JLabel lblGia = new JLabel(String.valueOf(formatter.format(t.getGiaTour())+" VNĐ"));
		JPanel pnGia = new JPanel();
		pnGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnGia.add(new JLabel("Giá vé:"));
		pnGia.add(lblGia);
		lblThongBao = new JLabel("");
		lblThongBao.setFont(new Font("Time New Roman", Font.ITALIC,13));
		lblThongBao.setForeground(Color.RED);
		pnGia.add(lblThongBao);
		
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
		pnButton.setLayout(new BorderLayout());
		//btnDatTour = new JButton("Đặt tour");
		btnSua = new JButton("Sửa");
		pnButton.add(btnSua, BorderLayout.CENTER);
		pnCC = new JPanel();
		pnCC.setLayout(new GridLayout(4, 1));
		//pnCC.setBackground(new Color(230, 247, 255));
		pnCC.add(pnTenTour);
		pnCC.add(pnGia);
		pnCC.add(pnNgayKH);
		pnCC.add(pnNgayKT);

		pnTenTour.setBackground(new Color(230, 247, 255));
		pnGia.setBackground(new Color(230, 247, 255));
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
		
		ThietLapThongBao();
		
		btnSua.addActionListener(this);
		//
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//Vinh - 20-5
		Object obj = e.getSource();
		
		if(obj.equals(btnSua)) {
			if(lblThongBao.getText().length()>0) {
				try {
					new NhapSuaThongTinTour(t, 2);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//2 là mở giao diện để xem thông tin tour
			} else
				try {
					new NhapSuaThongTinTour(t, 0);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} //0 là mở giao diện để sửa thông tin tour
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

	private void ThietLapThongBao() {
		LocalDate ngayHT = LocalDate.now();
		LocalDate ngayKH = LocalDate.parse(t.getNgayKhoiHanh().toString());
		LocalDate ngayKT = LocalDate.parse(t.getNgayKetThuc().toString());
		//Date ngayKH = t.getNgayKhoiHanh();
		if(ngayHT.compareTo(ngayKH)>0) {
			lblThongBao.setText("       *Tour đã bắt đầu");
		}
		if(ngayHT.compareTo(ngayKT)>0)
		{
			lblThongBao.setText("       *Tour đã kết thúc");
		}
	}
}
