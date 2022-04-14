package ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import config.config;
import dao.DiaDanh_DAO;
import dao.NhanVien_DAO;
import entity.NhanVien;

public class ChucNang extends JFrame implements ActionListener,MouseListener{
	String conf = config.conf;
	
	private JPanel pnWest;
	private JButton btnQLKH;
	private JButton btnQLNV;
	private JButton btnThongKe;
	private JButton btnQLTour;
	private JButton btnQLVe;
	private JButton btnQLDiaDanh;
	private JButton btnChucNang;
	private JButton btnHDV;
	private JButton btnDangXuat;
	JLabel nButton1,nButton2,nButton3,nButton4, nButton5, nButton6,nButton7,nButton8, nButton9;
	JPanel pnCenter;
	JPanel pnCC;
	JPanel pnQLKH;
	JPanel pnQLTOUR;
	JPanel pnQLNV;
	JPanel pnQLThongKe;
	JPanel pnQLVe;
	JPanel pnQLDiaDanh;
	JPanel pnHDV;
	JPanel pnDangXuat;
	boolean flag = true;
	ThongTinDiaDanh ttdd;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	NhanVien_DAO nhanVien_DAO;
	QuanLiNhanVien nv = new QuanLiNhanVien();
	private JPanel pnTrangChu;
	private JLabel lblLogo;
	private JButton btnTrangChu;
	
	public static String maNhanVien;
	
	public ChucNang(String maNV) throws Exception {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
	//public ChucNang() {
		setTitle("Chức năng");
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		setSize(1200,700);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		maNhanVien = maNV;
		nhanVien_DAO = (NhanVien_DAO) Naming.lookup(conf+"/nhanVien_DAO");
		pnQLKH= new JPanel();
		pnQLKH = new QuanLiKhachHang();
		pnQLTOUR = new JPanel();
		pnQLTOUR = new QuanLiTour();
		pnQLNV = new JPanel();
		pnQLNV = new QuanLiNhanVien();
		pnQLThongKe = new JPanel();
		pnQLThongKe = new QuanLiThongKe();
		pnQLVe = new JPanel();
		pnQLVe = new QuanLiVe();
		pnQLDiaDanh = new JPanel();
		pnQLDiaDanh = new QuanLiDiaDanh();
		pnHDV = new JPanel();
		pnHDV = new QuanLiHuongDanVien();
		pnTrangChu = new JPanel();
		pnTrangChu = new TrangChu();
		
		//North
		JPanel pnNorth = new JPanel();
		
		pnNorth.setLayout(new BorderLayout());
		pnNorth.setBackground(Color.cyan);
		
		JPanel pnTieuDe = new JPanel();
		pnTieuDe.setBackground(new Color(204, 255, 255));
		pnTieuDe.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTieuDe = new JLabel("Quản Lý Tour Du Lịch");
		Font font =new Font("Arial",Font.BOLD,25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(Color.RED);
		pnTieuDe.add(lblTieuDe);
		
		JPanel pnTTNhanVien = new JPanel();
		pnTTNhanVien.setBackground(new Color(204, 255, 255));
		pnTTNhanVien.setLayout(new GridLayout(2,1));
		JPanel pnMaNV = new JPanel();
		pnMaNV.setBackground(new Color(204, 255, 255));
		JLabel lblMaNV = new JLabel("Mã Nhân Viên: ");
		txtMaNV = new JTextField(10);
		txtMaNV.setText(maNhanVien);
		txtMaNV.setEditable(false);
		txtMaNV.setOpaque(false);
		pnMaNV.add(lblMaNV);
		pnMaNV.add(txtMaNV);
		JPanel pnTenNV = new JPanel();
		pnTenNV.setBackground(new Color(204, 255, 255));
		JLabel lblTenNV = new JLabel("Tên Nhân Viên: ");
		txtTenNV = new JTextField(10);
		txtTenNV.setText(nhanVien_DAO.LayNhanVienTheoMa(maNhanVien).getTenNV().toString());
		txtTenNV.setOpaque(false);
		txtTenNV.setEditable(false);
		pnTenNV.add(lblTenNV);
		pnTenNV.add(txtTenNV);
		
		lblMaNV.setPreferredSize(lblTenNV.getPreferredSize());
		pnTTNhanVien.add(pnMaNV);
		pnTTNhanVien.add(pnTenNV);
		pnTTNhanVien.setBackground(Color.CYAN);
		pnNorth.add(pnTTNhanVien,BorderLayout.EAST);
		pnNorth.add(pnTieuDe,BorderLayout.CENTER);
		pnNorth.add(pnTieuDe,BorderLayout.CENTER);
		add(pnNorth,BorderLayout.NORTH);
	
		
		//Center
		pnCenter = new JPanel();
		pnCenter.setLayout(new BorderLayout());
		pnCC = new JPanel();
		pnCenter.add(pnTrangChu);
		add(pnCenter, BorderLayout.CENTER);
		
		//WEST
		pnWest = new JPanel();
		pnWest.setBackground(new Color(204, 255, 255));
		pnWest.setLayout(new BorderLayout());
		JLabel iconLabel = new JLabel(new ImageIcon("Icon/menu.png"));
		btnChucNang = new JButton("");
		btnChucNang.setLayout(new BorderLayout());
		btnChucNang.add(iconLabel,BorderLayout.CENTER);//Vinh 29-5
		btnChucNang.setPreferredSize(new Dimension(50,50));
		btnChucNang.setBackground(new Color(204, 255, 255));
		btnChucNang.setBorderPainted(false);
		btnChucNang.setFocusPainted(false);
		btnChucNang.setContentAreaFilled(false);
		btnChucNang.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		lblLogo = new JLabel(ResizeImage("Icon/sun.png"));
		lblLogo.setPreferredSize(new Dimension(140,50));
		JPanel pnChucNang = new JPanel();
		pnChucNang.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnChucNang.setPreferredSize(pnTTNhanVien.getPreferredSize());
		pnChucNang.add(btnChucNang);
		pnChucNang.add(lblLogo);
		pnChucNang.setBackground(new Color(204, 255, 255));
		pnNorth.add(pnChucNang,BorderLayout.WEST);
		
		JLabel iconLabel1 = new JLabel(new ImageIcon("Icon/qlkh.png"));
		nButton1 = new JLabel("Quản Lý Khách Hàng", SwingConstants.CENTER);
		btnQLKH = new JButton("");
		btnQLKH.setLayout(new BorderLayout());
		btnQLKH.add(iconLabel1,BorderLayout.WEST);
		btnQLKH.add(nButton1,BorderLayout.CENTER);
		
		JLabel iconLabel2 = new JLabel(new ImageIcon("Icon/qlnv2.png"));
		nButton2 = new JLabel("Quản Lý Nhân Viên", SwingConstants.CENTER);
		btnQLNV = new JButton("");
		btnQLNV.setLayout(new BorderLayout());
		btnQLNV.add(iconLabel2,BorderLayout.WEST);
		btnQLNV.add(nButton2,BorderLayout.CENTER);
		
		JLabel iconLabel3 = new JLabel(new ImageIcon("Icon/thongke.png"));
		nButton3 = new JLabel("Quản Lý Thống Kê", SwingConstants.CENTER);
		btnThongKe = new JButton("");
		btnThongKe.setLayout(new BorderLayout());
		btnThongKe.add(iconLabel3,BorderLayout.WEST);
		btnThongKe.add(nButton3,BorderLayout.CENTER);
		
		
		JLabel iconLabel4 = new JLabel(new ImageIcon("Icon/qltour.png"));
		nButton4 = new JLabel("Quản Lý Tour", SwingConstants.CENTER);
		btnQLTour = new JButton("");
		btnQLTour.setLayout(new BorderLayout());
		btnQLTour.add(iconLabel4,BorderLayout.WEST);
		btnQLTour.add(nButton4,BorderLayout.CENTER);
		
		JLabel iconLabel5 = new JLabel(new ImageIcon("Icon/ticket1.png"));
		nButton5 = new JLabel("Đặt vé", SwingConstants.CENTER);
		btnQLVe = new JButton("");
		btnQLVe.setLayout(new BorderLayout());
		btnQLVe.add(iconLabel5,BorderLayout.WEST);
		btnQLVe.add(nButton5,BorderLayout.CENTER);
		
		JLabel iconLabel6 = new JLabel(new ImageIcon("Icon/QLDD5.png"));
		nButton6 = new JLabel("Quản Lý Địa Danh", SwingConstants.CENTER);
		btnQLDiaDanh = new JButton("");
		btnQLDiaDanh.setLayout(new BorderLayout());
		btnQLDiaDanh.add(iconLabel6,BorderLayout.WEST);
		btnQLDiaDanh.add(nButton6,BorderLayout.CENTER);
		
		
		//Nam
		JLabel iconLabel7 = new JLabel(new ImageIcon("Icon/User-icon.png"));
		nButton7 = new JLabel("Quản Lý HDV", SwingConstants.CENTER);
		btnHDV = new JButton("");
		btnHDV.setLayout(new BorderLayout());
		btnHDV.add(iconLabel7,BorderLayout.WEST);
		btnHDV.add(nButton7,BorderLayout.CENTER);
		
		//Nam
		JLabel iconLabel8 = new JLabel(new ImageIcon("Icon/log-out.png"));
		nButton8 = new JLabel("Đăng Xuất", SwingConstants.CENTER);
		nButton8.setForeground(Color.WHITE);
		btnDangXuat = new JButton("");
		btnDangXuat.setLayout(new BorderLayout());
		btnDangXuat.add(iconLabel8,BorderLayout.WEST);
		btnDangXuat.add(nButton8,BorderLayout.CENTER);
		//ThÃ¡i
		JLabel iconLabel9 = new JLabel(new ImageIcon("Icon/house.png"));
		nButton9 = new JLabel("Trang Chủ", SwingConstants.CENTER);
		btnTrangChu = new JButton("");
		btnTrangChu.setLayout(new BorderLayout());
		btnTrangChu.add(iconLabel9,BorderLayout.WEST);
		btnTrangChu.add(nButton9,BorderLayout.CENTER);
		
		
		
		btnChucNang.setBackground(Color.WHITE);
		btnQLKH.setBackground(Color.WHITE);
		btnQLNV.setBackground(Color.WHITE);
		btnQLTour.setBackground(Color.WHITE);
		btnThongKe.setBackground(Color.WHITE);
		btnQLVe.setBackground(Color.WHITE);
		btnQLDiaDanh.setBackground(Color.WHITE);
		//ThÃ¡i
		btnTrangChu.setBackground(Color.WHITE);
		//Nam
		btnHDV.setBackground(Color.WHITE);
		btnDangXuat.setBackground(new Color(255, 51, 0));
		JPanel pnCN = new JPanel();
		pnCN.setBackground(new Color(204, 255, 255));
		pnCN.add(btnTrangChu);
		pnCN.add(btnQLVe);
		pnCN.add(btnQLTour);
		pnCN.add(btnQLKH);
		pnCN.add(btnQLNV);
		//Nam
		pnCN.add(btnHDV);
		pnCN.add(btnQLDiaDanh);
		pnCN.add(btnThongKe);
		//Nam
		JPanel pnDX = new JPanel();
		pnDX.add(btnDangXuat);
		pnDX.setBackground(new Color(204, 255, 255));
		pnWest.add(pnCN, BorderLayout.CENTER);
		pnWest.add(pnDX,BorderLayout.SOUTH);
		pnWest.setPreferredSize(new Dimension(200,400));
		btnQLKH.setPreferredSize(new Dimension(180,50));
		btnQLNV.setPreferredSize(btnQLKH.getPreferredSize());
		btnThongKe.setPreferredSize(btnQLKH.getPreferredSize());
		btnQLTour.setPreferredSize(btnQLKH.getPreferredSize());
		btnQLVe.setPreferredSize(btnQLKH.getPreferredSize());
		btnTrangChu.setPreferredSize(btnQLKH.getPreferredSize());
		btnQLDiaDanh.setPreferredSize(btnQLKH.getPreferredSize());
		//Nam
		btnHDV.setPreferredSize(btnQLKH.getPreferredSize());
		btnDangXuat.setPreferredSize(btnQLKH.getPreferredSize());
		add(pnWest,BorderLayout.WEST);
		
		btnChucNang.addActionListener(this);
		btnQLKH.addActionListener(this);
		btnQLTour.addActionListener(this);
		btnQLNV.addActionListener(this);
		btnThongKe.addActionListener(this);
		btnQLVe.addActionListener(this);
		btnQLDiaDanh.addActionListener(this);
		btnTrangChu.addActionListener(this);
		//Nam
		
		btnHDV.addActionListener(this);
		btnDangXuat.addActionListener(this);
		
		
		//btnChucNang.addMouseListener(this);
		btnQLKH.addMouseListener(this);
		btnQLTour.addMouseListener(this);
		btnQLNV.addMouseListener(this);
		btnThongKe.addMouseListener(this);
		btnQLVe.addMouseListener(this);
		btnQLDiaDanh.addMouseListener(this);
		btnTrangChu.addMouseListener(this);
		lblLogo.addMouseListener(this);
		//Nam
		btnHDV.addMouseListener(this);
	}

	public static void main(String[] args) throws Exception {
		new ChucNang(maNhanVien).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		//Nam
		if(object.equals(btnQLKH)) {
			//Vinh
			btnQLKH.setBackground(new Color(153, 255, 153));
			btnQLDiaDanh.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLNV.setBackground(Color.WHITE);
			btnQLTour.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnTrangChu.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnQLKH,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
			
		}
		
		else if(object.equals(btnQLTour)) {
			//Vinh
			btnQLTour.setBackground(new Color(153, 255, 153));
			btnQLKH.setBackground(Color.WHITE);
			btnQLDiaDanh.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLNV.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnTrangChu.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnQLTOUR,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		//Nam
		else if(object.equals(btnQLNV)) {
			//Vinh
			btnQLNV.setBackground(new Color(153, 255, 153));
			btnQLTour.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLDiaDanh.setBackground(Color.WHITE);
			btnQLKH.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnTrangChu.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnQLNV,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		else if(object.equals(btnChucNang)) {
			if (flag == true) {
				pnWest.setPreferredSize(new Dimension(68,400));
				btnQLKH.remove(nButton1);
				btnQLNV.remove(nButton2);
				btnThongKe.remove(nButton3);
				btnQLTour.remove(nButton4);
				btnQLVe.remove(nButton5);
				btnQLDiaDanh.remove(nButton6);
				btnHDV.remove(nButton7);
				btnDangXuat.remove(nButton8);
				btnTrangChu.remove(nButton9);
				btnQLKH.setPreferredSize(new Dimension(60,50));
				btnQLTour.setPreferredSize(new Dimension(60,50));
				btnQLNV.setPreferredSize(new Dimension(60,50));
				btnThongKe.setPreferredSize(new Dimension(60,50));
				btnQLVe.setPreferredSize(new Dimension(60,50));
				btnQLDiaDanh.setPreferredSize(new Dimension(60,50));
				btnHDV.setPreferredSize(new Dimension(60,50));
				btnDangXuat.setPreferredSize(new Dimension(60,50));
				btnTrangChu.setPreferredSize(new Dimension(60,50));
				pnWest.revalidate();
				flag = false; 
			}
			else if(flag == false){
				pnWest.setPreferredSize(new Dimension(200,400));
				btnQLKH.add(nButton1, BorderLayout.CENTER);
				btnQLNV.add(nButton2,BorderLayout.CENTER);
				btnThongKe.add(nButton3,BorderLayout.CENTER);
				btnQLTour.add(nButton4,BorderLayout.CENTER);
				btnQLVe.add(nButton5,BorderLayout.CENTER);
				btnQLDiaDanh.add(nButton6,BorderLayout.CENTER); // Vinh -29-5
				btnHDV.add(nButton7,BorderLayout.CENTER);
				btnDangXuat.add(nButton8, BorderLayout.CENTER);
				btnTrangChu.add(nButton9,BorderLayout.CENTER);
				btnQLKH.setPreferredSize(new Dimension(180,50));
				btnQLTour.setPreferredSize(new Dimension(180,50));
				btnQLNV.setPreferredSize(new Dimension(180,50));
				btnThongKe.setPreferredSize(new Dimension(180,50));
				btnQLVe.setPreferredSize(new Dimension(180,50));
				btnQLDiaDanh.setPreferredSize(new Dimension(180,50));
				btnHDV.setPreferredSize(new Dimension(180,50));
				btnDangXuat.setPreferredSize(new Dimension(180,50));
				btnTrangChu.setPreferredSize(new Dimension(180,50));
				pnWest.revalidate();
				flag = true;
			}
		}
		else if(object.equals(btnThongKe)) {
			//Vinh
			btnThongKe.setBackground(new Color(153, 255, 153));
			btnQLNV.setBackground(Color.WHITE);
			btnQLTour.setBackground(Color.WHITE);
			btnQLDiaDanh.setBackground(Color.WHITE);
			btnQLKH.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnTrangChu.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnQLThongKe,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		else if(object.equals(btnQLVe)) {
			//Vinh
			btnQLVe.setBackground(new Color(153, 255, 153));
			btnQLNV.setBackground(Color.WHITE);
			btnQLTour.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLDiaDanh.setBackground(Color.WHITE);
			btnQLKH.setBackground(Color.WHITE);
			btnTrangChu.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnQLVe,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		else if(object.equals(btnQLDiaDanh)) {
			//Vinh
			btnQLDiaDanh.setBackground(new Color(153, 255, 153));
			btnQLNV.setBackground(Color.WHITE);
			btnQLTour.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLKH.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnTrangChu.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnQLDiaDanh,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		//Vinh
		else if(object.equals(btnTrangChu)) {
			btnTrangChu.setBackground(new Color(153, 255, 153));
			btnQLNV.setBackground(Color.WHITE);
			btnQLTour.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLKH.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnQLDiaDanh.setBackground(Color.WHITE);
			btnHDV.setBackground(Color.WHITE);
			
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnTrangChu,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		//Nam
		else if(object.equals(btnHDV)) {
			btnHDV.setBackground(new Color(153, 255, 153));
			btnQLNV.setBackground(Color.WHITE);
			btnQLTour.setBackground(Color.WHITE);
			btnThongKe.setBackground(Color.WHITE);
			btnQLKH.setBackground(Color.WHITE);
			btnQLVe.setBackground(Color.WHITE);
			btnQLDiaDanh.setBackground(Color.WHITE);
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnHDV,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();

		}
		//Nam
		else if(object.equals(btnDangXuat)) {
			
			JFrame f= new JFrame();
			int hoi=JOptionPane.showConfirmDialog(f, "Bạn có chắc muốn đăng xuất?","Chý Ý",JOptionPane.YES_NO_OPTION);
			if(hoi==JOptionPane.YES_OPTION) {
				try {
					DangNhap dn = null;
					try {
						dn = new DangNhap();
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
					dn.setVisible(true);
					dispose();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//Vinh
		Object obj = e.getSource();
		 if(obj.equals(lblLogo)) {
			 btnTrangChu.setBackground(Color.CYAN);
				btnQLNV.setBackground(Color.WHITE);
				btnQLTour.setBackground(Color.WHITE);
				btnThongKe.setBackground(Color.WHITE);
				btnQLKH.setBackground(Color.WHITE);
				btnQLVe.setBackground(Color.WHITE);
				btnQLDiaDanh.setBackground(Color.WHITE);
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.add(pnTrangChu,BorderLayout.CENTER);
			pnCenter.revalidate();
			pnCenter.repaint();
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
		Object obj = e.getSource();
		if(flag==true)
		{
			if(obj.equals(btnQLKH)) {
				btnQLKH.setPreferredSize(new Dimension(200,70));
				btnQLKH.revalidate();
			}
			else if(obj.equals(btnQLTour)) {
				btnQLTour.setPreferredSize(new Dimension(200,70));
				btnQLTour.revalidate();
			}
			else if(obj.equals(btnThongKe)) {
				btnThongKe.setPreferredSize(new Dimension(200,70));
				btnThongKe.revalidate();
			}
			else if(obj.equals(btnQLNV)) {
				btnQLNV.setPreferredSize(new Dimension(200,70));
				btnQLNV.revalidate();
			}
			else if(obj.equals(btnQLVe)) {
				btnQLVe.setPreferredSize(new Dimension(200,70));
				btnQLVe.revalidate();
			}
			else if(obj.equals(btnQLDiaDanh)) {
				btnQLDiaDanh.setPreferredSize(new Dimension(200,70));
				btnQLDiaDanh.revalidate();
			}
			//Vinh
			else if(obj.equals(btnTrangChu)) {
				btnTrangChu.setPreferredSize(new Dimension(200,70));
				btnTrangChu.revalidate();
			}
			//Nam
			else if(obj.equals(btnHDV)) {
				btnHDV.setPreferredSize(new Dimension(200,70));
				btnQLVe.revalidate();
			}
		}
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(flag==true) {
			if(obj.equals(btnQLKH)) {
				btnQLKH.setPreferredSize(new Dimension(180,50));
				btnQLKH.revalidate();
			}
			else if(obj.equals(btnQLTour)) {
				btnQLTour.setPreferredSize(new Dimension(180,50));
				btnQLTour.revalidate();
			}
			else if(obj.equals(btnThongKe)) {
				btnThongKe.setPreferredSize(new Dimension(180,50));
				btnThongKe.revalidate();
			}
			else if(obj.equals(btnQLNV)) {
				btnQLNV.setPreferredSize(new Dimension(180,50));
				btnQLNV.revalidate();
			}
			else if(obj.equals(btnQLVe)) {
				btnQLVe.setPreferredSize(new Dimension(180,50));
				btnQLVe.revalidate();
			}
			else if(obj.equals(btnQLDiaDanh)) {
				btnQLDiaDanh.setPreferredSize(new Dimension(180,50));
				btnQLDiaDanh.revalidate();
			}
			//Vinh
			else if(obj.equals(btnTrangChu)) {
				btnTrangChu.setPreferredSize(new Dimension(180,50));
				btnTrangChu.revalidate();
			}
			//Nam
			else if(obj.equals(btnHDV)) {
				btnHDV.setPreferredSize(new Dimension(180,50));
				btnQLVe.revalidate();
			}
		}
		
	}
	//Nam
//	private void loadNV() throws SQLException {
//		NhanVien_DAO nhanVien_DAO = (NhanVien_DAO) Naming.lookup("rmi://DESKTOP-O44ASRI:9999/nhanVien_DAO");
//		nv.modeltable= nhanVien_DAO.getAllNV();
//		nv.table.setModel(nv.modeltable);
//	}
//	private void loadKH() throws SQLException {
//		DAO_KhachHang dao_kh = new DAO_KhachHang();
//		kh.modeltable= dao_kh.getAllKH();
//		kh.table.setModel(kh.modeltable);
//	}
//	private void loadHDV() throws SQLException {
//		DAO_HuongDanVien dao_hdv = new DAO_HuongDanVien();
//		hdv.modeltable= dao_hdv.getAllHDV();
//		hdv.table.setModel(hdv.modeltable);
//	}
	public ImageIcon ResizeImage(String ImagePath)
	 {
		 ImageIcon MyImage = new ImageIcon(ImagePath);
		 Image img = MyImage.getImage();
		 Image newImg = img.getScaledInstance(140, 120, Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImg);
	     return image;
	 }
}