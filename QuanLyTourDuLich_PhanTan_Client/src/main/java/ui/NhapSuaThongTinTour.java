package ui;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.DiaDanh_DAO;
import dao.HuongDanVien_DAO;
import dao.LoaiTour_DAO;
import dao.Tour_DAO;
import dao.Ve_DAO;
import entity.DiaDanh;
import entity.HuongDanVien;
import entity.LoaiTour;
import entity.Tour;
import entity.Ve;

import com.toedter.calendar.JDateChooser;

import config.config;

public class NhapSuaThongTinTour extends JFrame implements ActionListener, ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String conf = config.conf;
	private static Tour t;
	JLabel lblHinhAnh;
	private Ve_DAO ve_DAO;
	private DiaDanh_DAO diaDanh_DAO;
	private LoaiTour_DAO loaiTour_DAO;
	private Tour_DAO tour_DAO;
	private HuongDanVien_DAO huongDanVien_DAO;
	
	private JButton btnChonAnh;
	private JComboBox cboDiaDanh;
	private JComboBox cboLoaiTour;
	private JTextField txtMaTour;
	private JTextField txtTenTour;
	private JTextField txtSoNguoi;
	private JTextField txtGiaVe;
	private List<DiaDanh> lstDiaDanh;
	private ArrayList<LoaiTour> lstLoaiTour;
	private ArrayList<Ve> listVe;
	private ArrayList<Tour> lstTour;
	private List<HuongDanVien> lstHuongDanVien;
	private String hinhAnh="";
	private JButton btnLuu;
	private JComboBox cboHuongDanVien;
	private int l;//để xét coi mở giao diện nào
	private JRadioButton radTrue;
	private JRadioButton radFalse;
	private JTextArea taMoTa;
	private JDateChooser dcNgayKH;
	private JDateChooser dcNgayKT;
	private JLabel lblTB;
	public NhapSuaThongTinTour(Tour tour, int loai) throws RemoteException { // Nếu 1 thì mở giao diện thêm, 0 thì giao diện sửa
		t= tour;
		l=loai;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setSize(700,700);
		setLocationRelativeTo(null);
		setVisible(true);
		SecurityManager securityManager = System.getSecurityManager();
		if (securityManager == null) {

			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());

		}
		
		
		try {
			ve_DAO = (Ve_DAO) Naming.lookup(conf+"/ve_DAO");
			diaDanh_DAO = (DiaDanh_DAO) Naming.lookup(conf+"/diaDanh_DAO");
			loaiTour_DAO = (LoaiTour_DAO) Naming.lookup(conf+"/loaiTour_DAO");
			tour_DAO = (Tour_DAO) Naming.lookup(conf+"/tour_DAO");
			huongDanVien_DAO = (HuongDanVien_DAO) Naming.lookup(conf+"/huongDanVien_DAO");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		lstDiaDanh = new ArrayList<DiaDanh>();
		lstLoaiTour = new ArrayList<LoaiTour>();
		listVe = ve_DAO.DanhSachVeTheoMaTour(t.getMaTour());
		lstTour = new ArrayList<Tour>();
		lstHuongDanVien = new ArrayList<HuongDanVien>();
		
		JLabel lblTieuDe = new JLabel("THÔNG TIN TOUR");
		lblTieuDe.setFont(new Font("Time New Roman",Font.BOLD,20));
		JPanel pnTieuDe = new JPanel();
		pnTieuDe.add(lblTieuDe);
		add(pnTieuDe,BorderLayout.NORTH);
		
		lblHinhAnh = new JLabel("");
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		JPanel pnHinhAnh = new JPanel();
		pnHinhAnh.setPreferredSize(new Dimension(320,210));
		pnHinhAnh.setLayout(new WrapLayout());
		btnChonAnh = new JButton("Chọn ảnh");
		btnLuu = new JButton("Lưu");
		pnHinhAnh.add(lblHinhAnh);
		JPanel pnChonAnhVaTB = new JPanel();
		pnChonAnhVaTB.setLayout(new GridLayout(2,1));
		pnHinhAnh.add(pnChonAnhVaTB);
		lblTB = new JLabel("");
		lblTB.setForeground(Color.RED);
		JPanel pnCA = new JPanel();
		pnCA.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnCA.add(btnChonAnh);
		pnChonAnhVaTB.add(pnCA);
		JPanel pnTB = new JPanel();
		pnTB.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnTB.add(lblTB);
		pnChonAnhVaTB.add(pnTB);
		
		lblHinhAnh.setPreferredSize(new Dimension(300,180));
		String path = t.getHinhAnh();
		
		if(path!=null)
			lblHinhAnh.setIcon(DoiKichThuocAnh(path));
		add(pnHinhAnh, BorderLayout.EAST);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BorderLayout());
		add(pnCenter, BorderLayout.CENTER);
		JPanel pnThongTin = new JPanel();
		pnThongTin.setLayout(new GridLayout(10,1));
		pnCenter.add(pnThongTin, BorderLayout.CENTER);
		
		JPanel pnMoTa = new JPanel();
		pnMoTa.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnMT = new JPanel();
		pnMT.setLayout(new BorderLayout());
		JLabel lblTDMoTa = new JLabel("Mô tả tour:");
		pnMT.add(lblTDMoTa, BorderLayout.NORTH);
		taMoTa = new JTextArea(8,27);
		taMoTa.setLineWrap(true);
		taMoTa.setWrapStyleWord(true);
		JScrollPane scrMoTa = new JScrollPane(taMoTa);
		pnMT.add(scrMoTa, BorderLayout.CENTER);
		pnMoTa.add(pnMT);
		pnCenter.add(pnMoTa, BorderLayout.SOUTH);
		
		JPanel pnDiaDanh = new JPanel();
		pnDiaDanh.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnLoaiTour = new JPanel();
		pnLoaiTour.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnMaTour = new JPanel();
		pnMaTour.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnTenTour = new JPanel();
		pnTenTour.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnHDV = new JPanel();
		pnHDV.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnNgayKH = new JPanel();
		pnNgayKH.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnNgayKT = new JPanel();
		pnNgayKT.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnSoNguoi = new JPanel();
		pnSoNguoi.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnGiaVe = new JPanel();
		pnGiaVe.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel pnTinhTrang = new JPanel();
		pnTinhTrang.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		JLabel lblTDDiaDanh = new JLabel("Địa danh:");
		JLabel lblTDLoaiTour = new JLabel("Loại tour:");
		JLabel lblTDMaTour = new JLabel("Mã tour:");
		JLabel lblTDTenTour = new JLabel("Tên tour:");
		JLabel lblTDHuongDanVien = new JLabel("Hướng dẫn viên:");
		JLabel lblTDNgayKH = new JLabel("Ngày khởi hành:");
		JLabel lblTDNgayKT = new JLabel("Ngày kết thúc:");
		JLabel lblTDSoNguoi = new JLabel("Số người:");
		JLabel lblTDGiaVe = new JLabel("Giá vé:");
		JLabel lblTDTinhTrang = new JLabel("Tình trạng:");
		
		lblTDDiaDanh.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDLoaiTour.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDMaTour.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDTenTour.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDNgayKH.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDNgayKT.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDSoNguoi.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDGiaVe.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDTinhTrang.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		//lblTDMoTa.setPreferredSize(lblTDHuongDanVien.getPreferredSize());
		lblTDMoTa.setBounds(lblTDHuongDanVien.getBounds());
		
		cboDiaDanh = new JComboBox();
		cboLoaiTour = new JComboBox();
		txtMaTour = new JTextField(18);
		txtMaTour.setEditable(false);
		txtTenTour = new JTextField(18);
		cboHuongDanVien = new JComboBox();
		radTrue = new JRadioButton("Có thể đi",true); 
		radFalse = new JRadioButton("Không thể đi");
		ButtonGroup group = new ButtonGroup();
		group.add(radTrue);group.add(radFalse);
		
		cboDiaDanh.setPreferredSize(txtMaTour.getPreferredSize());
		cboLoaiTour.setPreferredSize(txtMaTour.getPreferredSize());
		cboHuongDanVien.setPreferredSize(txtMaTour.getPreferredSize());
		
		
		dcNgayKH= new JDateChooser();
		dcNgayKH.setDateFormatString("dd-MM-yyyy");
		Date dkh = t.getNgayKhoiHanh();
		  if (dkh == null) {
		    try { 
				dkh = new SimpleDateFormat("dd-MM-yyyy").parse("30-05-2021");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		  dcNgayKH.setDate(dkh);
		dcNgayKH.setPreferredSize(txtMaTour.getPreferredSize());
		dcNgayKT = new JDateChooser();
		dcNgayKT.setDateFormatString("dd-MM-yyyy");
		Date dkt = t.getNgayKetThuc();
		if (dkt == null) {
		    try {
				dkt = new SimpleDateFormat("dd-MM-yyyy").parse("10-06-2021");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dcNgayKT.setDate(dkt);
		dcNgayKT.setPreferredSize(txtMaTour.getPreferredSize());
		txtSoNguoi = new JTextField(18);
		txtGiaVe = new JTextField(18);
		
		pnDiaDanh.add(lblTDDiaDanh);
		pnDiaDanh.add(cboDiaDanh);
		pnLoaiTour.add(lblTDLoaiTour);
		pnLoaiTour.add(cboLoaiTour);
		pnMaTour.add(lblTDMaTour);
		pnMaTour.add(txtMaTour);
		pnTenTour.add(lblTDTenTour);
		pnTenTour.add(txtTenTour);
		pnHDV.add(lblTDHuongDanVien);
		pnHDV.add(cboHuongDanVien);
		pnNgayKH.add(lblTDNgayKH);
		pnNgayKH.add(dcNgayKH);
		pnNgayKT.add(lblTDNgayKT);
		pnNgayKT.add(dcNgayKT);
		pnSoNguoi.add(lblTDSoNguoi);
		pnSoNguoi.add(txtSoNguoi);
		pnGiaVe.add(lblTDGiaVe);
		pnGiaVe.add(txtGiaVe);
		pnTinhTrang.add(lblTDTinhTrang);
		pnTinhTrang.add(radTrue);
		pnTinhTrang.add(radFalse);
				
		pnThongTin.add(pnDiaDanh);
		pnThongTin.add(pnLoaiTour);
		pnThongTin.add(pnMaTour);
		pnThongTin.add(pnTenTour);
		pnThongTin.add(pnHDV);
		pnThongTin.add(pnNgayKH);
		pnThongTin.add(pnNgayKT);
		pnThongTin.add(pnSoNguoi);
		pnThongTin.add(pnGiaVe);
		pnThongTin.add(pnTinhTrang);
		pnThongTin.revalidate();
		pnCenter.revalidate();
		
		JPanel pnChucNang = new JPanel();
		pnChucNang.add(btnLuu);
		add(pnChucNang, BorderLayout.SOUTH);
		
		LayDanhSachDiaDanh();
		ThemVaoCboDiaDanh();
		LayDanhSachLoaiTour();
		ThemVaoCboLoaiTour();
		LayDanhSachTour();
		LayDanhSachHuongDanVien();
		ThemVaoCboHDV();
		ThietLapThongBao();
		
		btnChonAnh.addActionListener(this);
		btnLuu.addActionListener(this);
		cboLoaiTour.addItemListener(this);
		cboDiaDanh.addItemListener(this);
		
		if(l==0)
		{
			DuaThongTinTourVaoComponent();
			btnLuu.setVisible(true);
		}
		else if(l==2)
		{
			DuaThongTinTourVaoComponent();
			btnLuu.setVisible(false);
		}
		else
		{
			btnLuu.setVisible(true);
			DinhDangMaTour();
			DinhDangTenTour();
		}
			
	}

	public static void main(String[] args) {
		
	}
	public ImageIcon DoiKichThuocAnh(String ImagePath)
	 {
		 ImageIcon MyImage = new ImageIcon(ImagePath);
		 Image img = MyImage.getImage();
		 Image newImg = img.getScaledInstance(290, 150, Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImg);
	     return image;
	 }
	
	private void LayDanhSachDiaDanh() throws RemoteException {
		lstDiaDanh = diaDanh_DAO.getalltbDiaDanh();
	}
	
	private void ThemVaoCboDiaDanh() throws RemoteException
	{
		for (DiaDanh d : diaDanh_DAO.getalltbDiaDanh()) {
			cboDiaDanh.addItem(d.getMaDiaDanh()+"-"+d.getTenDiaDanh());
		}
	}
	
	private void LayDanhSachLoaiTour() throws RemoteException {
		lstLoaiTour = loaiTour_DAO.layHetLoaiTour();
	}
	private void ThemVaoCboLoaiTour() {
		for (LoaiTour loaiTour : lstLoaiTour) {
			cboLoaiTour.addItem(loaiTour.getMaLoaiTour()+"-"+loaiTour.getTenLoaiTour());
		}
	}
	private void LayDanhSachHuongDanVien() {
		try {
			lstHuongDanVien = huongDanVien_DAO.LayToanBoHuongDanVien();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void ThemVaoCboHDV() {
		try {
			lstHuongDanVien = huongDanVien_DAO.LayToanBoHuongDanVien();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (HuongDanVien danVien : lstHuongDanVien) {
			if(danVien.isTinhTrang())
				cboHuongDanVien.addItem(danVien.getMaHuongDanVien()+"-"+danVien.getTenHuongDanVien());
		}
	}
	
	private void LayDanhSachTour() {
		// TODO Auto-generated method stub
		try {
			lstTour = tour_DAO.LayHetTour();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 
	private String DinhDanhLaiNguonAnh(String a) {
		 String b= "";
		 for(int i =0;i<a.length();i++)
		 {
			 char c = a.charAt(i);
			 b+=c;
			 if(c=='\\'){
				 b+="\\";
			 }
		 }
		 
		 return b;
	 }
	 
	private String XuLyLayTenAnh(String nguon) {
		 StringBuilder str = new StringBuilder(nguon);
		 str = str.reverse();
		 String tenAnh="";
		 for(int i =0;i<str.length();i++) {
			 if(str.charAt(i)=='\\')
				 break;
			 else
				 tenAnh+=str.charAt(i);
		 }
		 str=new StringBuilder(tenAnh);
		 return str.reverse().toString();
	 }
	 
	private void LuuAnh() {
		 FileInputStream in;
			try {
				in = new FileInputStream(DinhDanhLaiNguonAnh(hinhAnh));
				System.out.println(XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
				FileOutputStream ou = new FileOutputStream("HinhAnhTour\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
				BufferedInputStream bin = new BufferedInputStream(in);
				BufferedOutputStream bou = new BufferedOutputStream(ou);
				int b= 0;
				while(b!=-1) {
					try {
						b = bin.read();
						bou.write(b);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				try {
					bin.close();
					bou.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("Copy thành công");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	 }
	 
	private void DinhDangMaTour() throws RemoteException {

		String maT="";
		maT+="T";
		
		maT+=cboLoaiTour.getSelectedItem().toString().substring(0,2);
		if(String.valueOf(tour_DAO.LayMaTourLonNhat()).length()==2)
			maT+="0";
		maT+=String.valueOf(tour_DAO.LayMaTourLonNhat()+1);
		txtMaTour.setText(maT);
	}
	private void DinhDangMaTourDeSua() {
		String maT="";
		maT+="T";
		
		maT+=cboLoaiTour.getSelectedItem().toString().substring(0,2);
		
		maT+=String.valueOf(t.getMaTour().substring(3));
		txtMaTour.setText(maT);
	}
	private void DinhDangTenTour() throws RemoteException {
		String tenT="";
		tenT+="Tour ";
		tenT+=cboLoaiTour.getSelectedItem().toString().substring(3)+" ";
		tenT+=cboDiaDanh.getSelectedItem().toString().substring(7)+" ";
		if(String.valueOf(tour_DAO.LayMaTourLonNhat()).length()==2)
			tenT+="0";
		tenT+=String.valueOf(tour_DAO.LayMaTourLonNhat()+1);
		txtTenTour.setText(tenT);
	}
	private void DinhDangTenTourDeSua() {
		String tenT="";
		tenT+="Tour ";
		tenT+=cboLoaiTour.getSelectedItem().toString().substring(3)+" ";
		tenT+=cboDiaDanh.getSelectedItem().toString().substring(7)+" ";
		tenT+=String.valueOf(t.getMaTour().substring(3));
		txtTenTour.setText(tenT);
	}
	private void DuaThongTinTourVaoComponent() {
		for (DiaDanh dd : lstDiaDanh) {
			if(t.getDiaDanh().getMaDiaDanh().equals(dd.getMaDiaDanh())) {
				cboDiaDanh.setSelectedItem(dd.getMaDiaDanh()+"-"+dd.getTenDiaDanh());
			}
		}
		for(LoaiTour lt : lstLoaiTour)
		{
			if(t.getLoaiTour().getMaLoaiTour().equals(lt.getMaLoaiTour())) {
				cboLoaiTour.setSelectedItem(lt.getMaLoaiTour()+"-"+lt.getTenLoaiTour());
			}
		}
		for (HuongDanVien hdv : lstHuongDanVien) {
			if(t.getHuongDanVien().getMaHuongDanVien().equals(hdv.getMaHuongDanVien())) {
				cboHuongDanVien.setSelectedItem(hdv.getMaHuongDanVien()+"-"+hdv.getTenHuongDanVien());
			}
		}
		txtMaTour.setText(t.getMaTour());
		txtTenTour.setText(t.getTenTour());
		txtSoNguoi.setText(String.valueOf(t.getSoLuongNguoi()) );
		txtGiaVe.setText(String.valueOf(t.getGiaTour()));
		taMoTa.setText(t.getMoTa());
		hinhAnh = t.getHinhAnh();
	}
	private void ThietLapThongBao() throws RemoteException {
		listVe = ve_DAO.DanhSachVeTheoMaTour(t.getMaTour());
		if(listVe.size()>0) {
			lblTB.setText("Tour này đã đặt vé, không thể sửa loại tour và địa danh");
			cboDiaDanh.setEnabled(false);
			cboLoaiTour.setEnabled(false);
		}
		else
		{
			lblTB.setText("");
			cboDiaDanh.setEnabled(true);
			cboLoaiTour.setEnabled(true);
		}
			
	}
	private boolean KiemTraNhapSoNguyen(String nhap) {
		try {
			int a = Integer.parseInt(nhap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	private boolean KiemTraNhapSoThuc(String nhap) {
		try {
			float  a = Float.parseFloat(nhap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean KiemTraNhapLieu() throws NumberFormatException, HeadlessException, RemoteException {
		if(txtTenTour.getText().trim().length()==0) {
			txtTenTour.requestFocus();
			JOptionPane.showMessageDialog(this, "Tên tour không đc trống");
			return false;
		}
		else if(txtTenTour.getText().length()>50) {
			txtTenTour.requestFocus();
			txtTenTour.selectAll();
			JOptionPane.showMessageDialog(this, "Tên tour quá dài !");
			return false;
		}
		
		
		try {
			SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
			String ngayKH= "", ngayKT="";
			ngayKH = dcn.format(dcNgayKH.getDate() );
			ngayKT = dcn.format(dcNgayKT.getDate());
			LocalDate ngayHienTai = LocalDate.now();
			LocalDate nkh = LocalDate.parse(ngayKH);
			LocalDate nkt = LocalDate.parse(ngayKT);
			
			if(ngayHienTai.compareTo(nkh)>0) {
				JOptionPane.showMessageDialog(this, "Ngày khởi hành phải sau ngày hiện tại !");
				return false;
			}
			if(nkh.compareTo(nkt)>0) {
				JOptionPane.showMessageDialog(this, "Ngày khởi hành phải trước ngày kết thúc !");
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi ngày, vui lòng chọn ngày chính xác !");
			return false;
		}
	    //kiểm tra ngày của hướng dẫn viên
		try {
			SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
			String ngayKH = dcn.format(dcNgayKH.getDate());
			java.sql.Date nkh =java.sql.Date.valueOf(ngayKH);
			String ngayKT = dcn.format(dcNgayKT.getDate());
			java.sql.Date nkt = java.sql.Date.valueOf(ngayKT);
			Tour tourTrung = new Tour();
			
			long khoangCach = nkt.getTime() - nkh.getTime();

			long khoangCachNgay = khoangCach / (24 * 60 * 60 * 1000);
			
			if(khoangCachNgay>100)
			{
				JOptionPane.showMessageDialog(this, "Ngày khởi hành và ngày kết thúc cách nhau "+khoangCachNgay+" ngày ! Quá lớn !");
				return false;
			}
			
			tourTrung = tour_DAO.KiemTraCoTheChoHuongDanVien(txtMaTour.getText(),cboHuongDanVien.getSelectedItem().toString().substring(0,6),nkh,nkt);
			if(tourTrung!=null) {
				JOptionPane.showMessageDialog(this, "Hướng dẫn viên này sẽ bị trùng lịch !"
						+"\n"+"Tên tour trùng là: "+tourTrung.getTenTour()
						+"\n"+"Có mã tour là: "+tourTrung.getMaTour()
						+"\n"+"Có ngày khởi hành: "+tourTrung.getNgayKhoiHanh().toString()
						+"\n"+"Có ngày kết thúc: "+tourTrung.getNgayKetThuc().toString());
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Lỗi ngày nha");
			return false;
		}
		
		
		if(txtSoNguoi.getText().trim().length()==0) {
			txtSoNguoi.requestFocus();
			txtSoNguoi.selectAll();
			JOptionPane.showMessageDialog(this,"Số người không được để trống !");
			return false;
		}
		else if(!KiemTraNhapSoNguyen(txtSoNguoi.getText()) ) {
			txtSoNguoi.requestFocus();
			txtSoNguoi.selectAll();
			JOptionPane.showMessageDialog(this,"Số người phải là số nguyên !");
			return false;
		}
		else if(Integer.parseInt(txtSoNguoi.getText())>100&& txtSoNguoi.getText().length()>=3)
		{
			txtSoNguoi.requestFocus();
			txtSoNguoi.selectAll();
			JOptionPane.showMessageDialog(this,"Số người quá lớn");
			return false;
		}
		else if(Integer.parseInt(txtSoNguoi.getText())<0) {
			txtSoNguoi.requestFocus();
			txtSoNguoi.selectAll();
			JOptionPane.showMessageDialog(this,"Số người phải lớn hơn 0");
			return false;
		}
		else if(ve_DAO.DanhSachVeTheoMaTour(t.getMaTour()).size()>Integer.parseInt(txtSoNguoi.getText())) {
			txtSoNguoi.requestFocus();
			txtSoNguoi.selectAll();
			JOptionPane.showMessageDialog(this,"Số vé đã đặt lớn hơn số người tối đa mà bạn đang sửa !");
			return false;
		}
		
		else if(txtGiaVe.getText().trim().length()==0) {
			txtGiaVe.requestFocus();
			txtGiaVe.selectAll();
			JOptionPane.showMessageDialog(this,"Giá vé không được để trống");
			return false;
		}
		else if(!KiemTraNhapSoThuc(txtGiaVe.getText())) {
			txtGiaVe.requestFocus();
			txtGiaVe.selectAll();
			JOptionPane.showMessageDialog(this,"Giá vé phải là số thực");
			return false;
		}
		else if(Float.parseFloat(txtGiaVe.getText())<0) {
			txtGiaVe.requestFocus();
			txtGiaVe.selectAll();
			JOptionPane.showMessageDialog(this,"Giá vé phải là số thực > 0");
			return false;
		}
		else if(txtGiaVe.getText().length()>9&&Float.parseFloat(txtGiaVe.getText())>999999999) {
			txtGiaVe.requestFocus();
			txtGiaVe.selectAll();
			JOptionPane.showMessageDialog(this,"Giá vé quá lớn !");
			return false;
		}
		
		if(taMoTa.getText().length()==0)
			taMoTa.setText("Chưa có mô tả !");
		if(hinhAnh.length()==0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh !");
			return false;
		}
		return true;
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(btnChonAnh)) {
			try {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
				file.addChoosableFileFilter(filter);
				file.setFileFilter(filter);
				int result = file.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					hinhAnh = selectedFile.getAbsolutePath();
					lblHinhAnh.setIcon(DoiKichThuocAnh(hinhAnh));

				}
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh !");
			}
			
		}
		else if(obj.equals(btnLuu)) {
			if(l==1) { // 1 là thêm
					try {
						if(KiemTraNhapLieu()) {
							java.sql.Date nkh = null ;
							java.sql.Date nkt = null ;
							try {
								SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
							    String ngayKH = dcn.format(dcNgayKH.getDate() );
								String ngayKT = dcn.format(dcNgayKT.getDate());
								nkh = java.sql.Date.valueOf(ngayKH);
								nkt =java.sql.Date.valueOf(ngayKT);
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(this, "Lỗi ngày, vui lòng chọn lại chính xác!");
							}
							
							
							
							HuongDanVien hdv= new HuongDanVien(cboHuongDanVien.getSelectedItem().toString().substring(0,6));
							DiaDanh diaD = new DiaDanh(cboDiaDanh.getSelectedItem().toString().substring(0,6));
							LoaiTour lTour = new LoaiTour(cboLoaiTour.getSelectedItem().toString().substring(0,2));
							Tour tourThem =null;
							try {
								tourThem = new Tour(txtMaTour.getText(), txtTenTour.getText(),Float.parseFloat(txtGiaVe.getText()) ,
										Integer.parseInt(txtSoNguoi.getText()), nkh, nkt, taMoTa.getText(), true,
										"HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
								tourThem.setHuongDanVien(hdv);
								tourThem.setDiaDanh(diaD);
								tourThem.setLoaiTour(lTour);
							} catch (Exception e2) {
								System.out.println(e2);
								JOptionPane.showMessageDialog(this, "Không thể tạo tour");
							}
							
							try {
								tour_DAO.ThemTour(tourThem);
								LuuAnh();
								JOptionPane.showMessageDialog(this, "Thêm tour thành công !");
//								tour_DAO.GuiEmail(tourThem,cboDiaDanh.getSelectedItem().toString().substring(7),
//										cboLoaiTour.getSelectedItem().toString().substring(3));
//								JOptionPane.showMessageDialog(this, "Đã gửi mail !");
								QuanLiTour.qlTour.TaiTourLen();
								QuanLiVe.qlVe.TaiTourLen();
								this.dispose();
							} catch (Exception e2) {
								e2.printStackTrace();
								JOptionPane.showMessageDialog(this, "Lỗi, không thể thêm tour!");
								
							}
						}
					} catch (NumberFormatException | HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			else if(l==0) { // Sửa thông tin
				try {
					if(KiemTraNhapLieu()) {
						if(!t.getMaTour().equals(txtMaTour.getText())) { // Nếu khác thì phải new 1 tour mới, r xóa tour cũ
							try {
								
								java.sql.Date nkh = null ;
								java.sql.Date nkt = null ;
								try {
									SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
								    String ngayKH = dcn.format(dcNgayKH.getDate() );
									String ngayKT = dcn.format(dcNgayKT.getDate());
									nkh = java.sql.Date.valueOf(ngayKH);
									nkt =java.sql.Date.valueOf(ngayKT);
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(this, "Lỗi ngày, vui lòng chọn lại chính xác!");
								}
								
								
								
								HuongDanVien hdv= new HuongDanVien(cboHuongDanVien.getSelectedItem().toString().substring(0,6));
								DiaDanh diaD = new DiaDanh(cboDiaDanh.getSelectedItem().toString().substring(0,6));
								LoaiTour lTour = new LoaiTour(cboLoaiTour.getSelectedItem().toString().substring(0,2));
								Tour tourThem =null;
								try {
									if(t.getHinhAnh().equals("HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)))) // Vinh - 2-6
									{
										JOptionPane.showMessageDialog(this,DinhDanhLaiNguonAnh("HinhAnhTour\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(t.getHinhAnh()))+"VINH"));
										tourThem = new Tour(txtMaTour.getText(), txtTenTour.getText(),Float.parseFloat(txtGiaVe.getText()) ,
												Integer.parseInt(txtSoNguoi.getText()), nkh, nkt, taMoTa.getText(), true,
												"HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(t.getHinhAnh())));
										tourThem.setHuongDanVien(hdv);
										tourThem.setDiaDanh(diaD);
										tourThem.setLoaiTour(lTour);
									}
									else
									{
										JOptionPane.showMessageDialog(this,DinhDanhLaiNguonAnh( t.getHinhAnh())+"THÀNH"+"\n"+"HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
										tourThem = new Tour(txtMaTour.getText(), txtTenTour.getText(),Float.parseFloat(txtGiaVe.getText()) ,
												Integer.parseInt(txtSoNguoi.getText()), nkh, nkt, taMoTa.getText(), true,
												"HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));	
										tourThem.setHuongDanVien(hdv);
										tourThem.setDiaDanh(diaD);
										tourThem.setLoaiTour(lTour);
									}
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(this, "Không thể tạo tour");
								}
								
								try {
									tour_DAO.ThemTour(tourThem);
									
									try {
										tour_DAO.XoaTour(t.getMaTour());
									} catch (Exception e2) {
										JOptionPane.showMessageDialog(this, "Lỗi trong khi xóa tour cũ !");
									}
									if(!tourThem.getHinhAnh().equals(t.getHinhAnh()))
										LuuAnh();
									JOptionPane.showMessageDialog(this, "Sửa tour thành công !");
									QuanLiTour.qlTour.TaiTourLen();
									QuanLiVe.qlVe.TaiTourLen();
									this.dispose();// Vinh - 2-6
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(this, "Lỗi, không thể sửa tour!");
								}
								
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(this, "Lỗi! không thể sửa tour.");
							}
						}
						
						else {//Nếu giống thì sửa bình thường
							java.sql.Date nkh = null ;
							java.sql.Date nkt = null ;
							try {
								SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
							    String ngayKH = dcn.format(dcNgayKH.getDate() );
								String ngayKT = dcn.format(dcNgayKT.getDate());
								nkh = java.sql.Date.valueOf(ngayKH);
								nkt =java.sql.Date.valueOf(ngayKT);
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(this, "Lỗi ngày, vui lòng chọn lại chính xác!");
							}
										
							HuongDanVien hdv= new HuongDanVien(cboHuongDanVien.getSelectedItem().toString().substring(0,6));
							DiaDanh diaD = new DiaDanh(cboDiaDanh.getSelectedItem().toString().substring(0,6));
							LoaiTour lTour = new LoaiTour(cboLoaiTour.getSelectedItem().toString().substring(0,2));
							Tour tourSua =null;
							try {
								if(DinhDanhLaiNguonAnh(t.getHinhAnh()).equals("HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh))))
								{
									tourSua = new Tour(txtMaTour.getText(), txtTenTour.getText(),Float.parseFloat(txtGiaVe.getText()) ,
											Integer.parseInt(txtSoNguoi.getText()), nkh, nkt, taMoTa.getText(), true,
											"HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(t.getHinhAnh())));
									tourSua.setHuongDanVien(hdv);
									tourSua.setDiaDanh(diaD);
									tourSua.setLoaiTour(lTour);
									
								}
								else
								{
									tourSua = new Tour(txtMaTour.getText(), txtTenTour.getText(),Float.parseFloat(txtGiaVe.getText()) ,
											Integer.parseInt(txtSoNguoi.getText()), nkh, nkt, taMoTa.getText(), true,
											"HinhAnhTour\\\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
									tourSua.setHuongDanVien(hdv);
									tourSua.setDiaDanh(diaD);
									tourSua.setLoaiTour(lTour);
								}
								
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(this, "Không thể tạo tour để sửa !");
							}
							
							try {
								tour_DAO.SuaTour(tourSua);
								if(!tourSua.getHinhAnh().equals(t.getHinhAnh()))
									LuuAnh();
								JOptionPane.showMessageDialog(this, "Sửa tour thành công !");
								QuanLiTour.qlTour.TaiTourLen();
								QuanLiVe.qlVe.TaiTourLen();
								this.dispose();
							} catch (Exception e2) {
								JOptionPane.showMessageDialog(this, "Lỗi, không thể sửa tour!");
							}
						}
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(l==1) {//1 là thêm
			if(obj.equals(cboLoaiTour)&&e.getStateChange()==ItemEvent.SELECTED) {
				try {
					DinhDangMaTour();
					DinhDangTenTour();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			else if(obj.equals(cboDiaDanh)&&e.getStateChange()==ItemEvent.SELECTED) {
				try {
					DinhDangTenTour();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		else if(l==0) {// 0 là sửa
			if(obj.equals(cboLoaiTour)&&e.getStateChange()==ItemEvent.SELECTED) {
				DinhDangMaTourDeSua();
				DinhDangTenTourDeSua();
			}
			else if(obj.equals(cboDiaDanh)&&e.getStateChange()==ItemEvent.SELECTED) {
				DinhDangTenTourDeSua();
			}
		}
	}
}
