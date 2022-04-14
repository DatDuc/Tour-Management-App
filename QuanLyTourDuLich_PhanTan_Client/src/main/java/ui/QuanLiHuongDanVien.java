package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import config.config;
import dao.HuongDanVien_DAO;
import dao.KhachHang_DAO;
import entity.DiaDanh;
import entity.HuongDanVien;
import entity.KhachHang;

public class QuanLiHuongDanVien extends JPanel  implements ActionListener,MouseListener, KeyListener{

	String conf = config.conf;
	DefaultTableModel modeltable;
	JTable table;
	JButton  btnThem, btnSua, btnLoad, btnThoat;
	JTextField txtTimkiem;
//	KhachHang_DAO khachHang_DAO;
	HuongDanVien_DAO huongDanVien_DAO;
//	List<KhachHang> listKH;
	List<HuongDanVien> listHDV;
//	KhachHang khachHang;
	HuongDanVien huongDanVien;
	JPanel pnCenter;
	boolean flag;
	int row;
	DefaultTableCellDiaDanh cellDiaDanh;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public QuanLiHuongDanVien() throws MalformedURLException, RemoteException, NotBoundException {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		huongDanVien_DAO = (HuongDanVien_DAO) Naming.lookup(conf+"/huongDanVien_DAO");
		
		Border backline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(backline);
		setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		pnNorth.setBackground(new Color(230, 247, 255));
		JLabel lblTieuDe = new JLabel("Quản Lý Hướng Dẫn Viên");
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
		String[] chuoi = {"Mã HDV","Tên HDV","Số điện thoại","Email","CMND","Địa chỉ","Giới tính", "Ngày vào làm", "Tình Trạng"};
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
		txtTimkiem = new JTextField(18);
		TextPrompt tp7 = new TextPrompt("Nhập mã hoặc tên HDV", txtTimkiem);
		tp7.setForeground(new Color(46, 46, 31));
		tp7.changeAlpha(0.5f);
		tp7.changeStyle(Font.ITALIC);
		txtTimkiem.setPreferredSize(new Dimension(100, 32));
		pnLeft.add(lblTimKiem);
		pnLeft.add(txtTimkiem);
			//RIGHT
		Font font1 =new Font("Arial",Font.PLAIN,17);
		btnThem = new JButton("Thêm");
		btnThem.setIcon(new ImageIcon("Icon/them1.png"));
		btnThem.setForeground(Color.BLACK);
		btnThem.setBackground(new Color(230, 247, 255));
		btnThem.setPreferredSize(new Dimension(120,32));
		btnThem.setFont(font1);
		//btnThem.setBackground(Color.cyan);				//ĐÃ SỬA
		btnSua = new JButton("Sửa");
		btnSua.setIcon(new ImageIcon("Icon/sua.png"));
		btnSua.setForeground(Color.BLACK);
		btnSua.setBackground(new Color(230, 247, 255));
		btnSua.setPreferredSize(new Dimension(120,32));
		btnSua.setFont(font1);
		//btnSua.setBackground(Color.cyan);					//ĐÃ SỬA
		btnLoad = new JButton("Hiển thị lại thông tin");
		btnLoad.setIcon(new ImageIcon("Icon/load.png"));
		btnLoad.setForeground(Color.BLACK);
		btnLoad.setBackground(new Color(230, 247, 255));
		btnLoad.setPreferredSize(new Dimension(210,32));
		btnLoad.setFont(font1);
		//btnLoad.setBackground(Color.cyan);				//ĐÃ SỬA
		btnThoat = new JButton("Thoát");
		btnThoat.setIcon(new ImageIcon("Icon/thoat1.png"));
		btnThoat.setBackground(new Color(230, 247, 255));
		btnThoat.setForeground(Color.BLACK);
		btnThoat.setPreferredSize(new Dimension(120,32));
		//btnThoat.setBackground(Color.cyan);				//ĐÃ SỬA
		pnRight.add(btnThem);
		pnRight.add(btnSua);
		pnRight.add(btnLoad);
		pnRight.add(btnThoat);
		pnBorderTacVu.add(sp);
		
		
		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnLoad.addActionListener(this);
		btnThoat.addActionListener(this);
		txtTimkiem.addActionListener(this);
		
		huongDanVien = new HuongDanVien();
		table.addMouseListener(this);
		
		modeltable.setRowCount(0);
		listHDV	= huongDanVien_DAO.LayToanBoHuongDanVien();
		for(HuongDanVien hdv : listHDV) {
			modeltable.addRow(new Object[] {
					hdv.getMaHuongDanVien(), hdv.getTenHuongDanVien(), hdv.getSoDT(), hdv.getEmail(), hdv.getCmnd(), 
					hdv.getDiaChi(), hdv.isGioiTinh() ? "Nam" : "Nữ", df.format(hdv.getNgayVaoLam()), hdv.isTinhTrang() ? "Đang Làm" : "Đã Nghĩ"
			});
		}
		txtTimkiem.addKeyListener(this);
	}
	
	
	public void hienThiThongTin() throws RemoteException {
		modeltable.setRowCount(0);
		table.removeAll();
		listHDV	= huongDanVien_DAO.LayToanBoHuongDanVien();
		for(HuongDanVien hdv : listHDV) {
			modeltable.addRow(new Object[] {
					hdv.getMaHuongDanVien(), hdv.getTenHuongDanVien(), hdv.getSoDT(), hdv.getEmail(), hdv.getCmnd(), 
					hdv.getDiaChi(), hdv.isGioiTinh() ? "Nam" : "Nữ", df.format(hdv.getNgayVaoLam()), hdv.isTinhTrang() ? "Đang Làm" : "Đã Nghĩ"
			});
		}
		JOptionPane.showMessageDialog(this, "Hiển thị thành công");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnThem)) {
			btnThem.setBackground(new Color(153, 255, 153));				//ĐÃ SỬA
			btnThem.setForeground(Color.BLACK);
			btnLoad.setBackground(null);				//ĐÃ SỬA
			btnSua.setBackground(null);					//ĐÃ SỬA
			btnThoat.setBackground(null);
			try {
				new ThongTinHuongDanVien(huongDanVien, true).setVisible(true);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				e1.printStackTrace();
			}
			
		}
		else if(o.equals(btnSua)) {
			btnSua.setBackground(new Color(153, 255, 153));			//ĐÃ SỬA
			btnLoad.setBackground(null);				//ĐÃ SỬA
			btnThem.setBackground(null);				//ĐÃ SỬA
			btnThoat.setBackground(null);
			try {
				new ThongTinHuongDanVien(huongDanVien, false).setVisible(true);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				e1.printStackTrace();
			}
			
		}
		else if(o.equals(btnLoad)) {
			btnLoad.setBackground(new Color(153, 255, 153));				//ĐÃ SỬA
			btnSua.setBackground(null);					//ĐÃ SỬA
			btnThem.setBackground(null);				//ĐÃ SỬA
			btnThoat.setBackground(null);
			try {
				hienThiThongTin();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}		
			btnLoad.setBackground(null);				//ĐÃ SỬA
			btnSua.setBackground(null);					//ĐÃ SỬA
			btnThem.setBackground(null);				//ĐÃ SỬA
			btnThoat.setBackground(null);
		}
		else if(o.equals(btnThoat)) {
			btnThoat.setBackground(new Color(153, 255, 153));
			int kt = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không","Thông báo",JOptionPane.YES_NO_OPTION);
			if(kt == JOptionPane.YES_OPTION) {				//ĐÃ SỬA
				System.exit(0);
			}
		}
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		row = table.getSelectedRow();
		huongDanVien.setMaHuongDanVien(table.getValueAt(row, 0).toString());
		huongDanVien.setTenHuongDanVien(table.getValueAt(row, 1).toString());
		huongDanVien.setSoDT(table.getValueAt(row, 2).toString());
		huongDanVien.setEmail(table.getValueAt(row, 3).toString());
		huongDanVien.setCmnd(table.getValueAt(row, 4).toString());
		huongDanVien.setDiaChi(table.getValueAt(row, 5).toString());
		if(table.getValueAt(row, 6).toString().equals("Nam")) {
			huongDanVien.setGioiTinh(true);
		}else {
			huongDanVien.setGioiTinh(false);
		}
//		huongDanVien.setNgayVaoLam(new Date(table.getValueAt(row, 7)));
//		huongDanVien.setNgayVaoLam((Date) table.getValueAt(row, 7));
		if(table.getValueAt(row, 8).toString().equals("Đang Làm")) {
			huongDanVien.setTinhTrang(true);
		}else {
			huongDanVien.setTinhTrang(false);
		}
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
			listHDV = huongDanVien_DAO.timKiem(txtTimkiem.getText());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		for(HuongDanVien hdv : listHDV) {
			modeltable.addRow(new Object[] {
					hdv.getMaHuongDanVien(), hdv.getTenHuongDanVien(), hdv.getSoDT(), hdv.getEmail(), hdv.getCmnd(), 
					hdv.getDiaChi(), hdv.isGioiTinh() ? "Nam" : "Nữ", df.format(hdv.getNgayVaoLam()), hdv.isTinhTrang() ? "Đang Làm" : "Đã Nghĩ"
			});	
		}
		
	}

}
