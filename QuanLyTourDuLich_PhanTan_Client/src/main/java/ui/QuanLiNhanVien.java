//package ui;
//import java.awt.BorderLayout;
//
//
//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.EventQueue;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.sql.SQLException;
//
//import javax.swing.BoxLayout;
//import javax.swing.ButtonGroup;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JScrollPane;
//import javax.swing.JSplitPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.table.DefaultTableModel;
//
//import dao.DAO_HuongDanVien;
//import dao.DAO_KhachHang;
//import dao.DAO_NhanVien;
//import connectDB.ConnectDB;
//public class UI_ThongTinNhanVien extends JPanel implements ActionListener{
//	DefaultTableModel modeltable;
//	JTable table;
//	JTextField txtTim;
//	JButton btnTim, btnThem, btnSua, btnLoad, btnThoat;
//	protected String[] chuoi;
//	UI_NhanVien ui;
//	public UI_ThongTinNhanVien() {
//		try {
//			ConnectDB.getInstance().connect();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		
//
//		setLayout(new BorderLayout());
//		
//		
//		JPanel pnNorth = new JPanel();
//		JLabel lblTieuDe = new JLabel("Quản Lý Nhân Viên");
//		Font font =new Font("Arial",Font.BOLD,25);
//		lblTieuDe.setFont(font);
//		lblTieuDe.setForeground(Color.RED);
//		//pnNorth.setBackground(Color.WHITE);
//		pnNorth.add(lblTieuDe);
//		add(pnNorth,BorderLayout.NORTH);
//		
//		
//		String[] chuoi = {"Mã nhân viên","Tên nhân viên","Số điện thoại","Email","CMND","Địa chỉ","Ngày vào làm"};
//		modeltable = new DefaultTableModel(chuoi,0);
//		table = new JTable(modeltable);
//		JScrollPane sc = new JScrollPane(table);
//		add(sc,BorderLayout.CENTER);
//		
//		//SOUTH
//		JPanel pnSouth = new JPanel();
//		pnSouth.setLayout(new BoxLayout(pnSouth, BoxLayout.Y_AXIS));
//		add(pnSouth,BorderLayout.SOUTH);
//	
//		JPanel pnLeft = new JPanel();
//		JPanel pnRight = new JPanel();
//		pnRight.setPreferredSize(new Dimension(400,0));
//		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnLeft,pnRight);
//		
//			//LEFT
//		//Nam - 2-6
//		JLabel lblTim = new JLabel("Tìm kiếm");
//		pnLeft.add(lblTim);
//		txtTim = new JTextField(10);
//		//btnTim = new JButton("Tìm kiếm");
//		pnLeft.add(txtTim);
//		//pnLeft.add(btnTim);
//			//RIGHT
//		btnThem = new JButton("Thêm");
//		btnThem.setIcon(new ImageIcon("Icon/add.png"));
//		btnSua = new JButton("Sửa");
//		btnSua.setIcon(new ImageIcon("Icon/sua.png"));
//		btnLoad = new JButton("Load");
//		btnLoad.setIcon(new ImageIcon("Icon/open1.png"));
//		btnThoat = new JButton("Thoát");
//		btnThoat.setIcon(new ImageIcon("Icon/thoat.png"));
//		pnRight.add(btnThem);
//		pnRight.add(btnSua);
//		pnRight.add(btnLoad);
//		pnRight.add(btnThoat);
//		pnSouth.add(sp);
//		
//		btnThem.addActionListener(this);
//		btnSua.addActionListener(this);
//		btnThoat.addActionListener(this);
//		btnLoad.addActionListener(this);
//		
//		try {
//			loadNV();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		txtTim.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				if(txtTim.getText().length()==0) {
//					try {
//						loadNV();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//				if(txtTim.getText().length()>0) {
//					try {
//						timNV();
//					} catch (SQLException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//				
//			}
//		});
//	}
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		Object o = e.getSource();
//		if(o.equals(btnThem)) {
//			
//			new UI_NhanVien().setVisible(true);
//		}
//		else if(o.equals(btnThoat)) {
//			setVisible(false);
//			dispose();
//		}
//		else if(o.equals(btnSua)) {
//			new UI_NhanVien().setVisible(true);
//		}
//		else if(o.equals(btnLoad)) {
//			DAO_NhanVien dao_nv = new DAO_NhanVien();
//			try {
//				modeltable= dao_nv.getAllNV();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			table.setModel(modeltable);
//			
//		}
//		
//	}
//	private void dispose() {
//		// TODO Auto-generated method stub
//		
//	}
//	private void loadNV() throws SQLException {
//		DAO_NhanVien dao_nv = new DAO_NhanVien();
//		modeltable= dao_nv.getAllNV();
//		table.setModel(modeltable);
//	}
//	private void timNV() throws SQLException{
//		DAO_NhanVien dao_nv= new DAO_NhanVien();
//		modeltable = dao_nv.timKiem("%"+txtTim.getText()+"%", "%"+txtTim.getText()+"%");
//		table.setModel(modeltable);
//	}
////	private boolean xoaNV() throws SQLException {
////		 DAO_KhachHang  dao_kh= new DAO_KhachHang();
////		if(dao_kh.xoaNV(ui.txtMaNV.getText()));
////			return true;
////	
////	}
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UI_ThongTinNhanVien frame = new UI_ThongTinNhanVien();
//					frame.setVisible(true);
//					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//	}
//	protected void setExtendedState(int maximizedBoth) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
//========================================================CỦA THÁI
//========================================================CỦA THÁI
//========================================================CỦA THÁI
//========================================================CỦA THÁI
//========================================================CỦA THÁI
//========================================================CỦA THÁI NÀY LÀ GIAO DIỆN, UINHAAN VIÊN MOI LA FORM
package ui;
//THÁI ĐÃ SỬA
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import config.config;
import dao.DiaDanh_DAO;
import dao.NhanVien_DAO;
import dao.Tour_DAO;
import entity.DiaDanh;
import entity.NhanVien;
import entity.Tour;



public class QuanLiNhanVien extends JPanel  implements ActionListener,MouseListener, KeyListener{
	String conf = config.conf;
	DefaultTableModel modeltable;
	JTable table;
	JButton  btnThem, btnSua, btnLoad, btnThoat;
	JTextField txtTimKiem;
	NhanVien_DAO nhanVien_DAO;
	List<NhanVien> listNV;
	NhanVien nhanVien;
	JPanel pnCenter;
	boolean flag;
	int row;
	DefaultTableCellDiaDanh cellDiaDanh;
	public QuanLiNhanVien() throws MalformedURLException, RemoteException, NotBoundException {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {

			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());

		}
		nhanVien_DAO =  (NhanVien_DAO) Naming.lookup(conf+"/nhanVien_DAO");
		Border backline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(backline);
		setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		pnNorth.setBackground(new Color(230, 247, 255));
		JLabel lblTieuDe = new JLabel("Quản lí nhân viên");
		Font font =new Font("Arial",Font.BOLD,25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(Color.RED);
		pnNorth.add(lblTieuDe);
		add(pnNorth,BorderLayout.NORTH);

		Font font2 =new Font("Arial",Font.PLAIN,17);
		JPanel pnBorderThongTin = new JPanel();
		pnBorderThongTin.setBackground(new Color(230, 247, 255));
		pnBorderThongTin.setLayout(new BoxLayout(pnBorderThongTin, BoxLayout.Y_AXIS)); 
		Border borderThongtin = BorderFactory.createLineBorder(new Color(51, 51, 0));
		TitledBorder borderTitleThongtin = new TitledBorder(borderThongtin, "Thông tin: ");
		borderTitleThongtin.setTitleColor(new Color(0, 102, 255));
		borderTitleThongtin.setTitleFont(font2);
		Font font3 =new Font("Arial",Font.PLAIN,15);
		String[] chuoi = {"Mã nhân viên","Tên nhân viên","CMND","Địa chỉ","email","Giới tính","Ngày vào làm"," Số điện thoại"};
		modeltable = new DefaultTableModel(chuoi,0);
		table = new JTable(modeltable);
		table.getTableHeader().setBackground(new Color(230, 247, 255));
		table.setFillsViewportHeight(true);
		table.setBackground(new Color(255, 255, 255));

		JScrollPane sc = new JScrollPane(table);
		sc.setBackground(new Color(230, 247, 255));
		table.getTableHeader().setFont(font3);
		sc.setBorder(borderTitleThongtin);
		add(sc,BorderLayout.CENTER);

		//SOUTH
		JPanel pnSouth = new JPanel();
		pnSouth.setBackground(new Color(230, 247, 255));
		pnSouth.setLayout(new BoxLayout(pnSouth, BoxLayout.Y_AXIS));
		pnSouth.setPreferredSize(new Dimension(200,70));
		add(pnSouth,BorderLayout.SOUTH);

		JPanel pnBorderTacVu = new JPanel();
		pnBorderTacVu.setBackground(new Color(230, 247, 255));
		pnBorderTacVu.setLayout(new BoxLayout(pnBorderTacVu, BoxLayout.Y_AXIS)); 
		Border borderTacVu = BorderFactory.createLineBorder(new Color(51, 51, 0));
		TitledBorder titleborderTacVu = new TitledBorder(borderTacVu, "Chọn tác vụ: ");
		titleborderTacVu.setTitleColor(new Color(0, 102, 255));
		titleborderTacVu.setTitleFont(font2);
		pnBorderTacVu.setBorder(titleborderTacVu);
		pnSouth.add(pnBorderTacVu);

		JPanel pnLeft = new JPanel();
		pnLeft.setBackground(new Color(230, 247, 255));
		JPanel pnRight = new JPanel();
		pnRight.setBackground(new Color(230, 247, 255));
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnLeft,pnRight);

		//LEFT
		JLabel lblTimKiem = new JLabel("Tìm kiếm: ");
		lblTimKiem.setForeground(Color.BLACK);
		lblTimKiem.setIcon(new ImageIcon("Icon/search2.png"));		
		txtTimKiem = new JTextField(18);
		TextPrompt tp7 = new TextPrompt("Nhập tên nhân viên hoặc CMND", txtTimKiem);
		tp7.setForeground(new Color(46, 46, 31));
		tp7.changeAlpha(0.9f);
		tp7.changeStyle(Font.ITALIC);
		txtTimKiem.setPreferredSize(new Dimension(100, 32));

		pnLeft.add(lblTimKiem);
		pnLeft.add(txtTimKiem);
		//RIGHT
		Font font1 =new Font("Arial",Font.PLAIN,17);
		btnThem = new JButton("Thêm");
		btnThem.setIcon(new ImageIcon("Icon/them1.png"));
		btnThem.setForeground(Color.BLACK);
		btnThem.setBackground(new Color(230, 247, 255));
		btnThem.setPreferredSize(new Dimension(120,32));
		btnThem.setFont(font1);
		//btnThem.setBackground(Color.cyan);				
		btnSua = new JButton("Sửa");
		btnSua.setIcon(new ImageIcon("Icon/sua.png"));
		btnSua.setForeground(Color.BLACK);
		btnSua.setBackground(new Color(230, 247, 255));
		btnSua.setPreferredSize(new Dimension(120,32));
		btnSua.setFont(font1);
		//btnSua.setBackground(Color.cyan);					//Ä�Ãƒ Sá»¬A
		btnLoad = new JButton("Hiển thị lại thông tin");
		btnLoad.setIcon(new ImageIcon("Icon/load.png"));
		btnLoad.setForeground(Color.BLACK);
		btnLoad.setBackground(new Color(230, 247, 255));
		btnLoad.setPreferredSize(new Dimension(210,32));
		btnLoad.setFont(font1);
		//btnLoad.setBackground(Color.cyan);				
		btnThoat = new JButton("Thoát");
		btnThoat.setIcon(new ImageIcon("Icon/thoat1.png"));
		btnThoat.setBackground(new Color(230, 247, 255));
		btnThoat.setForeground(Color.BLACK);
		btnThoat.setPreferredSize(new Dimension(120,32));
		//btnThoat.setBackground(Color.cyan);				
		pnRight.add(btnThem);
		pnRight.add(btnSua);
		pnRight.add(btnLoad);
		pnRight.add(btnThoat);
		pnBorderTacVu.add(sp);

		//JSplitPane sp1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,sc,pnSouth);
		//add(sp1,BorderLayout.CENTER);

		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		txtTimKiem.addActionListener(this);
		btnLoad.addActionListener(this);
		btnThoat.addActionListener(this);

		//		NhanVien_DAO nhanVien_DAO;
		//		List<NhanVien> listNV;
		//		NhanVien nhanVien;

		nhanVien = new NhanVien();
		table.addMouseListener(this);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		modeltable.setRowCount(0);
		listNV	= nhanVien_DAO.getListMaNV();
		for(NhanVien nv : listNV) {
			modeltable.addRow(new Object[] {
					nv.getMaNV(),nv.getTenNV(),nv.getCmnd(),nv.getDiaChi(), nv.getEmail(),nv.isGioiTinh()?"Nam":"Nữ",
							sdf.format(nv.getNgayVaoLam()),nv.getSoDT()
			});
		}
		cellDiaDanh = new DefaultTableCellDiaDanh();
		txtTimKiem.addKeyListener(this);

	}
	public void hienThiThongTin() throws RemoteException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		modeltable.setRowCount(0);
		table.removeAll();
		listNV	= nhanVien_DAO.getListMaNV();
		for(NhanVien nv : listNV) {
			modeltable.addRow(new Object[] {
					nv.getMaNV(),nv.getTenNV(),nv.getCmnd(),nv.getDiaChi(), nv.getEmail(),nv.isGioiTinh()?"Nam":"Nữ",
							sdf.format(nv.getNgayVaoLam()),nv.getSoDT()
			});
		}
		JOptionPane.showMessageDialog(this, "Hiển thị thông tin thành công");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			btnThem.setBackground(new Color(153, 255, 153));				
			btnThem.setForeground(Color.BLACK);
			btnLoad.setBackground(null);				
			btnSua.setBackground(null);					
			btnThoat.setBackground(null);
			try {
				new ThongTinNhanVien(nhanVien,true).setVisible(true);
			} catch (MalformedURLException | RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		else if(o.equals(btnSua)) {
			btnSua.setBackground(new Color(153, 255, 153));			
			btnLoad.setBackground(null);				
			btnThem.setBackground(null);				
			btnThoat.setBackground(null);
			try {
				new ThongTinNhanVien(nhanVien,false).setVisible(true);
			} catch (MalformedURLException | RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		else if(o.equals(btnLoad)) {
			btnLoad.setBackground(new Color(153, 255, 153));				
			btnSua.setBackground(null);					
			btnThem.setBackground(null);				
			btnThoat.setBackground(null);
			try {
				hienThiThongTin();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			btnLoad.setBackground(null);				
			btnSua.setBackground(null);					
			btnThem.setBackground(null);				
			btnThoat.setBackground(null);
		}
		else if(o.equals(btnThoat)) 
		{
			btnThoat.setBackground(new Color(153, 255, 153));
			int kt = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không?","Thông báo",JOptionPane.YES_NO_OPTION);
			if(kt == JOptionPane.YES_OPTION) {				
				System.exit(0);
			}
		}

	}
	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		row = table.getSelectedRow();
		nhanVien.setMaNV(table.getValueAt(row, 0).toString());
		nhanVien.setTenNV(table.getValueAt(row, 1).toString());
		nhanVien.setCmnd(table.getValueAt(row, 2).toString());
		nhanVien.setDiaChi(table.getValueAt(row, 3).toString());
		nhanVien.setEmail(table.getValueAt(row, 4).toString());
		nhanVien.setGioiTinh(layGioiTinh());
		//nhanVien.setNgayVaoLam((Date) table.getValueAt(row, 6));
		nhanVien.setSoDT(table.getValueAt(row, 7).toString());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public boolean layGioiTinh() {
		if(table.getValueAt(row, 5).toString().equals("Nam")) {
			return true;
		}
		return false;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		modeltable.setRowCount(0);
		table.removeAll();
		try {
			listNV	= nhanVien_DAO.timKiem(txtTimKiem.getText());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(NhanVien nv : listNV) {
			modeltable.addRow(new Object[] {
					nv.getMaNV(),nv.getTenNV(),nv.getCmnd(),nv.getDiaChi(), nv.getEmail(),nv.isGioiTinh()?"Nam":"Nữ",nv.getNgayVaoLam(),nv.getSoDT()
			});
		}
		
	}
}
















