package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import config.config;
import dao.DiaDanh_DAO;
import dao.KhachHang_DAO;
import entity.DiaDanh;
import entity.KhachHang;

public class ThongTinKhachHang extends JFrame implements ActionListener {
	String conf = config.conf;
	private JLabel lblMaKhachHang, lblTenKhachHang, lblGioiTinh, lblSDT, lblEmail, lblCmnd, lblDiaChi;
	private JTextField txtMaKhachHang, txtTenKhachHang, txtMess, txtSDT, txtEmail, txtCmnd, txtDiaChi;
	private JRadioButton radNam, radNu;
	private JButton btnLamMoi, btnLuu, btnThoat;
	private JPanel pnNorth;
	private KhachHang_DAO khachHang_DAO;
	private QuanLiKhachHang quanLiKhachHang;
	private KhachHang khachHang;
	boolean flag = true;

	public ThongTinKhachHang(KhachHang kh, boolean flag)
			throws MalformedURLException, RemoteException, NotBoundException {
		SecurityManager securityManager = System.getSecurityManager();
		if (securityManager == null) {

			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());

		}
		khachHang_DAO = (KhachHang_DAO) Naming.lookup(conf + "/khachHang_DAO");

		khachHang = kh;
		quanLiKhachHang = new QuanLiKhachHang();

		setTitle("Thông Tin Khách Hàng");
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);	
		setSize(600,400);
		setLocationRelativeTo(null);

		pnNorth = new JPanel() { };
		JLabel lblTieuDe = new JLabel("Thông Tin Khách Hàng");
		Font font =new Font("Arial",Font.BOLD,15);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(Color.RED);
		pnNorth.add(lblTieuDe);
		add(pnNorth,BorderLayout.NORTH);
		
		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		JPanel pnThongTin = new JPanel();
		JPanel pnChucNang = new JPanel();
		pnChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.Y_AXIS));
		pnCenter.add(pnThongTin);
		pnCenter.add(pnChucNang);
		add(pnCenter,BorderLayout.CENTER);
		
		JPanel pnMaKH = new JPanel();
		lblMaKhachHang = new JLabel("Mã khách hàng");
		txtMaKhachHang = new JTextField(18);
		txtMaKhachHang.setEditable(false);
		pnMaKH.add(lblMaKhachHang);
		pnMaKH.add(txtMaKhachHang);
		pnThongTin.add(pnMaKH);
		
			//Ten
		JPanel pnTenKH = new JPanel();
		lblTenKhachHang = new JLabel("Tên khách hàng");
		txtTenKhachHang = new JTextField(18);
		pnTenKH.add(lblTenKhachHang);
		pnTenKH.add(txtTenKhachHang);
		pnThongTin.add(pnTenKH);
		//Email
		JPanel pnEmail = new JPanel();
		lblEmail = new JLabel("Email");
		txtEmail = new JTextField(18);
		pnEmail.add(lblEmail);
		pnEmail.add(txtEmail);
		pnThongTin.add(pnEmail);
		//DiaChi
		JPanel pnDiaCHi = new JPanel();
		lblDiaChi = new JLabel("Địa Chỉ");
		txtDiaChi = new JTextField(18);
		pnDiaCHi.add(lblDiaChi);
		pnDiaCHi.add(txtDiaChi);
		pnThongTin.add(pnDiaCHi);
		
		//SDT
		JPanel pnSDT = new JPanel();
		lblSDT = new JLabel("Số Điện Thoại");
		txtSDT = new JTextField(18);
		pnSDT.add(lblSDT);
		pnSDT.add(txtSDT);
		pnThongTin.add(pnSDT);
		
		//CMND
		JPanel pnCMND = new JPanel();
		lblCmnd = new JLabel("CMND");
		txtCmnd = new JTextField(18);
		pnCMND.add(lblCmnd);
		pnCMND.add(txtCmnd);
		pnThongTin.add(pnCMND);

		
		//GioiTinh
		JPanel pnGioiTinh = new JPanel();
		lblGioiTinh = new JLabel("Giới Tính:");
		radNam = new JRadioButton("Nam");
		radNu = new JRadioButton("Nữ");
		ButtonGroup groupGioitinh = new ButtonGroup();
		groupGioitinh.add(radNam);
		groupGioitinh.add(radNu);
		pnGioiTinh.add(lblGioiTinh);
		pnGioiTinh.add(radNam);pnGioiTinh.add(radNu);
		pnThongTin.add(pnGioiTinh);	
		
		JPanel pnMess = new JPanel();
		txtMess = new JTextField(32);
		txtMess.setEditable(false);
		txtMess.setBorder(null);
		txtMess.setForeground(Color.red);
		txtMess.setFont(new Font("Arial", Font.ITALIC, 12));
		pnMess.add(txtMess);
		pnThongTin.add(pnMess);
	
		lblGioiTinh.setPreferredSize(lblTenKhachHang.getPreferredSize());
		radNam.setPreferredSize(lblTenKhachHang.getPreferredSize());
		radNu.setPreferredSize(lblTenKhachHang.getPreferredSize());
		
		lblMaKhachHang.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblEmail.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblSDT.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblCmnd.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblDiaChi.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblGioiTinh.setPreferredSize(lblTenKhachHang.getPreferredSize());
		

		
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setIcon(new ImageIcon("Icon/xoarong.png"));
		btnLuu = new JButton("Lưu");
		btnLuu.setIcon(new ImageIcon("Icon/save.png"));
		btnThoat = new JButton("Thoát");
		btnThoat.setIcon(new ImageIcon("Icon/thoat.png"));
		
		pnChucNang.add(btnLamMoi);
		pnChucNang.add(btnLuu);
		pnChucNang.add(btnThoat);
		
		btnLuu.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnThoat.addActionListener(this);

		if (flag == true) {
			dinhDangMaKhacHang();
			txtTenKhachHang.setText("");
			txtSDT.setText("");
			txtEmail.setText("");
			txtCmnd.setText("");
			txtDiaChi.setText("");
			radNam.setSelected(false);
			radNu.setSelected(false);
		} else {
			txtMaKhachHang.setText(khachHang.getMaKH());
			txtTenKhachHang.setText(khachHang.getTenKH());
			txtSDT.setText(khachHang.getSoDT());
			txtCmnd.setText(khachHang.getCmnd());
			txtEmail.setText(khachHang.getEmail());
			txtDiaChi.setText(khachHang.getDiaChi());
			if(khachHang.isGioiTinh() == true) {
				radNam.setSelected(true);
			}else {
				radNu.setSelected(true);
			}
		}
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (flag == true) {
					if (validData()) {
						String maKhachHang = txtMaKhachHang.getText().trim();
						String tenKhachHang = txtTenKhachHang.getText().trim();
						String SDTKhachHang = txtSDT.getText().trim();
						String emailKhachHang = txtEmail.getText().trim();
						String diaChiKhachhang = txtDiaChi.getText().trim();
						String cmndKhachHang = txtCmnd.getText().trim();
						boolean gioiTinhKhachHang;
						if(radNam.isSelected()) {
							gioiTinhKhachHang = true;
						}else {
							gioiTinhKhachHang = false;
						}
						khachHang = new KhachHang(maKhachHang, tenKhachHang, emailKhachHang, diaChiKhachhang, SDTKhachHang, cmndKhachHang, gioiTinhKhachHang);
						try {
							if (khachHang_DAO.themKH(khachHang)) {
								JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
								quanLiKhachHang.modeltable.addRow(new Object[] { maKhachHang, tenKhachHang, emailKhachHang, diaChiKhachhang, SDTKhachHang, cmndKhachHang, gioiTinhKhachHang });
							}
						} catch (HeadlessException | RemoteException e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				} else if (flag == false) {
					if (quanLiKhachHang.row >= 0) {
						String maKhachHang = txtMaKhachHang.getText().trim();
						String tenKhachHang = txtTenKhachHang.getText().trim();
						String SDTKhachHang = txtSDT.getText().trim();
						String emailKhachHang = txtEmail.getText().trim();
						String diaChiKhachhang = txtDiaChi.getText().trim();
						String cmndKhachHang = txtCmnd.getText().trim();
						boolean gioiTinhKhachHang;
						if(radNam.isSelected()) {
							gioiTinhKhachHang = true;
						}else {
							gioiTinhKhachHang = false;
						}
						khachHang = new KhachHang(maKhachHang, tenKhachHang, emailKhachHang, diaChiKhachhang, SDTKhachHang, cmndKhachHang, gioiTinhKhachHang);
						try {
							if (khachHang_DAO.update(khachHang)) {
								quanLiKhachHang.table.setValueAt(txtTenKhachHang.getText(), quanLiKhachHang.row, 1);
								quanLiKhachHang.table.setValueAt(txtEmail.getText(), quanLiKhachHang.row, 2);
								quanLiKhachHang.table.setValueAt(txtDiaChi.getText(), quanLiKhachHang.row, 3);
								quanLiKhachHang.table.setValueAt(txtSDT.getText(), quanLiKhachHang.row, 4);
								quanLiKhachHang.table.setValueAt(txtCmnd.getText(), quanLiKhachHang.row, 5);
								quanLiKhachHang.table.setValueAt(gioiTinhKhachHang, quanLiKhachHang.row, 6);
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null, "Sửa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});
	}

	public static void main(String[] args) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnLamMoi)) {
			txtTenKhachHang.setText("");
			txtSDT.setText("");
			txtEmail.setText("");
			txtCmnd.setText("");
			txtDiaChi.setText("");
			txtMaKhachHang.requestFocus();
			radNam.setSelected(false);
			radNu.setSelected(false);
		} else if (o.equals(btnThoat)) {
			int kt = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (kt == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}

	private void showMessage(String message, JTextField txt) {
		txt.requestFocus();
		txtMess.setText(message);
	}

	private boolean validData() {
		String tenKhachHang = txtTenKhachHang.getText().trim();
		String cmndKH = txtCmnd.getText().trim();
		if (!(tenKhachHang.length() > 0)) {
			showMessage("Error: Tên khách hàng không được rỗng", txtTenKhachHang);
			return false;
		}else if(!(cmndKH.length() > 0)) {
			showMessage("Error: Chứng minh nhân dân không được rỗng", txtCmnd);
			return false;
		}
		return true;
	}

	private void dinhDangMaKhacHang() throws RemoteException {
		String maKH = "";
		maKH += "KH";
		if (String.valueOf(khachHang_DAO.LayMaKHLonNhat()).length() == 2) {
			maKH += "00";
		} else if (String.valueOf(khachHang_DAO.LayMaKHLonNhat()).length() == 3) {
			maKH += "0";
		} else if (String.valueOf(khachHang_DAO.LayMaKHLonNhat()).length() == 1) {
			maKH += "000";
		}
		maKH += String.valueOf(khachHang_DAO.LayMaKHLonNhat() + 1);
		txtMaKhachHang.setText(maKH);
	}
}
