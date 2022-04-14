package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;


import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import config.config;
import dao.HuongDanVien_DAO;
import entity.HuongDanVien;

public class ThongTinHuongDanVien extends JFrame implements ActionListener{

	String conf = config.conf;
	private JLabel lblMaHDV, lblTenHDV, lblGioiTinh, lblSDT, lblEmail, lblCmnd, lblDiaChi, lblNVL, lblTinhTrang;
	private JTextField txtMaHDV, txtTenHDV, txtMess, txtSDT, txtEmail, txtCmnd, txtDiaChi, txtNVL;
	private JRadioButton radNam, radNu, radDangLam, radNghi;
	private JButton btnLamMoi, btnLuu, btnThoat;
	private JPanel pnNorth;
	private HuongDanVien_DAO huongDanVien_DAO;
	private QuanLiHuongDanVien quanlihdv;
	private HuongDanVien huongDanVien;
	boolean flag = true;

	public ThongTinHuongDanVien(HuongDanVien hdv, boolean flag)
			throws MalformedURLException, RemoteException, NotBoundException {
		SecurityManager securityManager = System.getSecurityManager();
		if (securityManager == null) {

			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());

		}
		huongDanVien_DAO = (HuongDanVien_DAO) Naming.lookup(conf + "/huongDanVien_DAO");

		huongDanVien = hdv;
		quanlihdv = new QuanLiHuongDanVien();

		setTitle("Thông Tin Hướng Dẫn Viên");
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);	
		setSize(600,400);
		setLocationRelativeTo(null);

		pnNorth = new JPanel() { };
		JLabel lblTieuDe = new JLabel("Thông Tin Hướng Dẫn Viên");
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
		
		JPanel pnMaHDV = new JPanel();
		lblMaHDV = new JLabel("Mã Hướng Dẫn Viên");
		txtMaHDV = new JTextField(18);
		txtMaHDV.setEditable(false);
		pnMaHDV.add(lblMaHDV);
		pnMaHDV.add(txtMaHDV);
		pnThongTin.add(pnMaHDV);
		
			//Ten
		JPanel pnTenHDV = new JPanel();
		lblTenHDV = new JLabel("Tên Hướng Dẫn Viên");
		txtTenHDV = new JTextField(18);
		pnTenHDV.add(lblTenHDV);
		pnTenHDV.add(txtTenHDV);
		pnThongTin.add(pnTenHDV);
		
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

		//NgayVaoLam
//		JPanel pnNVL = new JPanel();
//		lblNVL = new JLabel("Ngày vào làm");
//		txtNVL = new JTextField(18);
//		txtNVL.setEditable(false);
//		pnNVL.add(lblNVL);
//		pnNVL.add(txtNVL);
//		pnThongTin.add(pnNVL);
		
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
		
		//TÌNH TRANG
		JPanel pnTinhTrang = new JPanel();
		lblTinhTrang = new JLabel("Tình Trạng:");
		radDangLam = new JRadioButton("Đang Làm");
		radNghi = new JRadioButton("Nghỉ");
		ButtonGroup groupTinhTrang = new ButtonGroup();
		groupTinhTrang.add(radDangLam);
		groupTinhTrang.add(radNghi);
		pnTinhTrang.add(lblTinhTrang);
		pnTinhTrang.add(radDangLam);
		pnTinhTrang.add(radNghi);
		pnThongTin.add(pnTinhTrang);	
		
		JPanel pnMess = new JPanel();
		txtMess = new JTextField(32);
		txtMess.setEditable(false);
		txtMess.setBorder(null);
		txtMess.setForeground(Color.red);
		txtMess.setFont(new Font("Arial", Font.ITALIC, 12));
		pnMess.add(txtMess);
		pnThongTin.add(pnMess);
	
		
		lblMaHDV.setPreferredSize(lblTenHDV.getPreferredSize());
		lblEmail.setPreferredSize(lblTenHDV.getPreferredSize());
		lblSDT.setPreferredSize(lblTenHDV.getPreferredSize());
		lblCmnd.setPreferredSize(lblTenHDV.getPreferredSize());
		lblDiaChi.setPreferredSize(lblTenHDV.getPreferredSize());
//		lblNVL.setPreferredSize(lblTenHDV.getPreferredSize());
		radNam.setPreferredSize(radDangLam.getPreferredSize());

		
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
			dinhDangMaHDV();
			txtTenHDV.setText("");
			txtSDT.setText("");
			txtEmail.setText("");
			txtCmnd.setText("");
			txtDiaChi.setText("");
//			txtNVL.setText("");
			radNam.setSelected(false);
			radNu.setSelected(false);
			radDangLam.setSelected(false);
			radNghi.setSelected(false);
		} else {
			txtMaHDV.setText(huongDanVien.getMaHuongDanVien());
			txtTenHDV.setText(huongDanVien.getTenHuongDanVien());
			txtSDT.setText(huongDanVien.getSoDT());
			txtCmnd.setText(huongDanVien.getCmnd());
			txtEmail.setText(huongDanVien.getEmail());
			txtDiaChi.setText(huongDanVien.getDiaChi());
//			txtNVL.setText(huongDanVien.getNgayVaoLam().toString());
			if(huongDanVien.isGioiTinh() == true) {
				radNam.setSelected(true);
			}else {
				radNu.setSelected(true);
			}
			if(huongDanVien.isTinhTrang() == true) {
				radDangLam.setSelected(true);
			}else {
				radNghi.setSelected(true);
			}
		}
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag == true) {
					if (validData()) {
						String maHDV = txtMaHDV.getText().trim();
						String tenHDV = txtTenHDV.getText().trim();
						String SDTHDV = txtSDT.getText().trim();
						String emailHDV = txtEmail.getText().trim();
						String diaChiHDV = txtDiaChi.getText().trim();
						String cmndHDV = txtCmnd.getText().trim();
						boolean gioiTinhHDV = false, tinhTrang = false;
						if(radNam.isSelected()) {
							gioiTinhHDV = true;
						}else if(radNu.isSelected()) {
							gioiTinhHDV = false;
						}
						if(radDangLam.isSelected()) {
							tinhTrang = true;
						}else if(radNghi.isSelected()){
							tinhTrang = false;
						}
						Date ngayVaoLam = Date.valueOf(LocalDate.now());
						huongDanVien = new HuongDanVien(maHDV, tenHDV, emailHDV, diaChiHDV, SDTHDV, ngayVaoLam, cmndHDV, gioiTinhHDV, tinhTrang);
						try {
							if (huongDanVien_DAO.themHDV(huongDanVien)) {
								JOptionPane.showMessageDialog(null, "Thêm thành công", "Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
								quanlihdv.modeltable.addRow(new Object[] {maHDV, tenHDV, emailHDV, diaChiHDV, SDTHDV, ngayVaoLam, cmndHDV, gioiTinhHDV, tinhTrang});
							}
						} catch (HeadlessException | RemoteException e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				} else if (flag == false) {
					if (quanlihdv.row >= 0) {
						String maHDV = txtMaHDV.getText().trim();
						String tenHDV = txtTenHDV.getText().trim();
						String SDTHDV = txtSDT.getText().trim();
						String emailHDV = txtEmail.getText().trim();
						String diaChiHDV = txtDiaChi.getText().trim();
						String cmndHDV = txtCmnd.getText().trim();
						boolean gioiTinhHDV = false;
						boolean tinhTrang = false;
						if(radNam.isSelected()) {
							gioiTinhHDV = true;
						}else {
							gioiTinhHDV = false;
						}
						if(radDangLam.isSelected()) {
							tinhTrang = true;
						}else if(radNghi.isSelected()){
							tinhTrang = false;
						}
						Date ngayVaoLam = Date.valueOf(LocalDate.now());
						huongDanVien = new HuongDanVien(maHDV, tenHDV, emailHDV, diaChiHDV, SDTHDV, ngayVaoLam, cmndHDV, gioiTinhHDV, tinhTrang);
						try {
							if (huongDanVien_DAO.updateHDV(huongDanVien)) {
								quanlihdv.table.setValueAt(txtTenHDV.getText(), quanlihdv.row, 1);
								quanlihdv.table.setValueAt(txtSDT.getText(), quanlihdv.row, 2);
								quanlihdv.table.setValueAt(txtEmail.getText(), quanlihdv.row, 3);
								quanlihdv.table.setValueAt(txtCmnd.getText(), quanlihdv.row, 4);
								quanlihdv.table.setValueAt(txtDiaChi.getText(), quanlihdv.row, 5);
								quanlihdv.table.setValueAt(gioiTinhHDV, quanlihdv.row, 6);
								quanlihdv.table.setValueAt(ngayVaoLam, quanlihdv.row, 7);
								quanlihdv.table.setValueAt(tinhTrang, quanlihdv.row, 8);
							}
						} catch (RemoteException e1) {
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
		Object o = e.getSource();
		if (o.equals(btnLamMoi)) {
			txtTenHDV.setText("");
			txtSDT.setText("");
			txtEmail.setText("");
			txtCmnd.setText("");
			txtDiaChi.setText("");
			txtMaHDV.requestFocus();
			radNam.setSelected(false);
			radNu.setSelected(false);
			radDangLam.setSelected(false);
			radNghi.setSelected(false);
			txtNVL.setText("");
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
		String tenHDV = txtTenHDV.getText().trim();
		String cmndHDV = txtCmnd.getText().trim();
		if (!(tenHDV.length() > 0)) {
			showMessage("Error: Tên Hướng Dẫn Viên không được rỗng", txtTenHDV);
			return false;
		}else if(!(cmndHDV.length() > 0)) {
			showMessage("Error: Chứng minh nhân dân không được để trống", txtCmnd);
			return false;
		}
		return true;
	}

	private void dinhDangMaHDV() throws RemoteException {
		String maHDV = "";
		maHDV += "HDV";
		if (String.valueOf(huongDanVien_DAO.LayMaHDVLonNhat()).length() == 2) {
			maHDV += "0";
		} else if (String.valueOf(huongDanVien_DAO.LayMaHDVLonNhat()).length() == 3) {
			maHDV += "";
		} else if (String.valueOf(huongDanVien_DAO.LayMaHDVLonNhat()).length() == 1) {
			maHDV += "00";
		}
		maHDV += String.valueOf(huongDanVien_DAO.LayMaHDVLonNhat() + 1);
		txtMaHDV.setText(maHDV);
	}
}
