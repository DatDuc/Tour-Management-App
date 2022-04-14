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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import config.config;
import dao.DiaDanh_DAO;
import entity.DiaDanh;



public class ThongTinDiaDanh extends JFrame implements ActionListener{
	String conf = config.conf;
	private JLabel lblMaDiaDanh, lblTenDiaDanh, lblMoTa, lblTinhThanh;
	private JTextField txtMaDiaDanh, txtTenDiaDanh, txtMess;
	private JTextArea txtMoTa;
	private JComboBox cbxTinhThanh;
	private JButton btnLamMoi, btnLuu, btnThoat;
	private JPanel pnNorth;
	private DiaDanh_DAO diaDanh_DAO;
	private QuanLiDiaDanh qldd;
	private DiaDanh diadanh;
	boolean flag = true;

	public ThongTinDiaDanh(DiaDanh dd,boolean flag) throws MalformedURLException, RemoteException, NotBoundException  {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		diaDanh_DAO =  (DiaDanh_DAO) Naming.lookup(conf+"/diaDanh_DAO");
		diadanh = dd;		  
		qldd = new QuanLiDiaDanh();

		setTitle("TTKH");
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);	
		setSize(600,400);
		setLocationRelativeTo(null);

		pnNorth = new JPanel() { };
		JLabel lblTieuDe = new JLabel("Thông Tin Địa Danh");
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
			//Ma
		JPanel pnMaDiaDanh = new JPanel();
		lblMaDiaDanh = new JLabel("Mã địa danh");
		txtMaDiaDanh = new JTextField(40);
		txtMaDiaDanh.setEditable(false);
		pnMaDiaDanh.add(lblMaDiaDanh);
		pnMaDiaDanh.add(txtMaDiaDanh);
		pnThongTin.add(pnMaDiaDanh);
			//Ten
		JPanel pnTenDiaDanh = new JPanel();
		lblTenDiaDanh = new JLabel("Tên địa danh");
		txtTenDiaDanh = new JTextField(40);
		pnTenDiaDanh.add(lblTenDiaDanh);
		pnTenDiaDanh.add(txtTenDiaDanh);
		pnThongTin.add(pnTenDiaDanh);				
			//Mota
		JPanel pnMoTa = new JPanel();
		lblMoTa = new JLabel("Mô tả");
		txtMoTa = new JTextArea(10,40);
		txtMoTa.setWrapStyleWord(true);
		txtMoTa.setLineWrap(true);
		JScrollPane sc = new JScrollPane(txtMoTa);
		pnMoTa.add(lblMoTa);
		pnMoTa.add(sc);
		pnThongTin.add(pnMoTa);
			//TinhThanh
		JPanel pnTinhThanh = new JPanel();
		lblTinhThanh = new JLabel("Tỉnh thành");
		cbxTinhThanh = new JComboBox();
		cbxTinhThanh.setPreferredSize(new Dimension(400, 25));
		cbxTinhThanh.addItem("An Giang ");cbxTinhThanh.addItem("Bà rịa – Vũng tàu");cbxTinhThanh.addItem("Bắc Giang");
		cbxTinhThanh.addItem("Bắc Kạn");cbxTinhThanh.addItem("Bạc Liêu");cbxTinhThanh.addItem("Bắc Ninh");
		cbxTinhThanh.addItem("Bến Tre");cbxTinhThanh.addItem("Bình Định");cbxTinhThanh.addItem("Bình Dương");cbxTinhThanh.addItem("Bình Phước");
		cbxTinhThanh.addItem("Bình Thuận");cbxTinhThanh.addItem("Cà Mau");cbxTinhThanh.addItem("Cần Thơ");cbxTinhThanh.addItem("Cao Bằng ");
		cbxTinhThanh.addItem("Đà Nẵng");cbxTinhThanh.addItem("Đắk Lắk");cbxTinhThanh.addItem("Đắk Nông");cbxTinhThanh.addItem("Điện Biên");
		cbxTinhThanh.addItem("Đồng Nai");cbxTinhThanh.addItem("Đồng Tháp");cbxTinhThanh.addItem("Gia Lai");cbxTinhThanh.addItem("Hà Giang");
		cbxTinhThanh.addItem("Hà Nam");cbxTinhThanh.addItem("Hà Nội ");cbxTinhThanh.addItem("Hà Tĩnh");cbxTinhThanh.addItem("Hải Dương ");
		cbxTinhThanh.addItem("Hải Phòng");cbxTinhThanh.addItem("Hậu Giang");cbxTinhThanh.addItem("Hòa Bình");cbxTinhThanh.addItem("Hưng Yên");
		cbxTinhThanh.addItem("Khánh Hòa");cbxTinhThanh.addItem("Kiên Giang");cbxTinhThanh.addItem("Kon Tum");cbxTinhThanh.addItem("Lai Châu");
		cbxTinhThanh.addItem("Lâm Đồng");cbxTinhThanh.addItem("Lạng Sơn");cbxTinhThanh.addItem("Lào Cai");cbxTinhThanh.addItem("Long An");
		cbxTinhThanh.addItem("Nam Định");cbxTinhThanh.addItem("Nghệ An");cbxTinhThanh.addItem("Ninh Bình");cbxTinhThanh.addItem("Ninh Thuận");
		cbxTinhThanh.addItem("Phú Thọ");cbxTinhThanh.addItem("Phú Yên");cbxTinhThanh.addItem("Quảng Bình");cbxTinhThanh.addItem("Quảng Nam");
		cbxTinhThanh.addItem("Quảng Ngãi");cbxTinhThanh.addItem("Quảng Ninh");cbxTinhThanh.addItem("Quảng Trị");cbxTinhThanh.addItem("Sóc Trăng");
		cbxTinhThanh.addItem("Sơn La");cbxTinhThanh.addItem("Tây Ninh");cbxTinhThanh.addItem("Thái Bình");cbxTinhThanh.addItem("Thái Nguyên");
		cbxTinhThanh.addItem("Thanh Hóa");cbxTinhThanh.addItem("Thừa Thiên Huế");cbxTinhThanh.addItem("Tiền Giang");cbxTinhThanh.addItem("Thành phố Hồ Chí Minh");
		cbxTinhThanh.addItem("Trà Vinh");cbxTinhThanh.addItem("Tuyên Quang");cbxTinhThanh.addItem("Vĩnh Long");cbxTinhThanh.addItem("Vĩnh Phúc");
		cbxTinhThanh.addItem("Yên Bái");
		
		pnTinhThanh.add(lblTinhThanh);
		pnTinhThanh.add(cbxTinhThanh);
		pnThongTin.add(pnTinhThanh);
		
		JPanel pnMess = new JPanel();
		txtMess = new JTextField(32);
		txtMess.setEditable(false);
		txtMess.setBorder(null);
		txtMess.setForeground(Color.red);
		txtMess.setFont(new Font("Arial", Font.ITALIC, 12));
		pnMess.add(txtMess);
		pnThongTin.add(pnMess);
		
			
		lblMaDiaDanh.setPreferredSize(lblTenDiaDanh.getPreferredSize());
		lblMoTa.setPreferredSize(lblTenDiaDanh.getPreferredSize());
		lblTinhThanh.setPreferredSize(lblTenDiaDanh.getPreferredSize());
		
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
			dinhDangMaDiaDanh();
			txtTenDiaDanh.setText("");
			txtMoTa.setText("");
			cbxTinhThanh.setSelectedItem("An Giang");
		}
		else {
			txtMaDiaDanh.setText(diadanh.getMaDiaDanh());
			txtTenDiaDanh.setText(diadanh.getTenDiaDanh());
			txtMoTa.setText(diadanh.getMoTa());
			cbxTinhThanh.setSelectedItem(diadanh.getTinhThanh());
		}
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(flag == true) {
					if(validData()) {
						String maDiaDanh = txtMaDiaDanh.getText().trim();
						String tenDiaDanh = txtTenDiaDanh.getText().trim();
						String moTa = txtMoTa.getText().trim();
						String tinhThanh = (String) cbxTinhThanh.getSelectedItem();
						diadanh = new DiaDanh(maDiaDanh,tenDiaDanh,moTa,tinhThanh);
						try {
							if(diaDanh_DAO.themDiaDanh(diadanh))
							{
								JOptionPane.showMessageDialog(null , "Thêm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
								qldd.modeltable.addRow(new Object[] {
										maDiaDanh,tenDiaDanh,moTa,tinhThanh
								});
							}
						} catch (HeadlessException | RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						dispose();
					}			
				}
				else if (flag == false ) {
					if(qldd.row>=0) {
						String maDiaDanh = txtMaDiaDanh.getText().toString();
						String tenDiaDanh = txtTenDiaDanh.getText().toString();
						String moTa = txtMoTa.getText().toString();
						String tinhThanh = (String) cbxTinhThanh.getSelectedItem();
						diadanh = new DiaDanh(maDiaDanh,tenDiaDanh,moTa,tinhThanh);
						try {
							if(diaDanh_DAO.update(diadanh)) {
								qldd.table.setValueAt(txtTenDiaDanh.getText(), qldd.row, 1);
								qldd.table.setValueAt(txtMoTa.getText(),qldd.row,2);
								qldd.table.setValueAt(cbxTinhThanh.getSelectedItem().toString(), qldd.row, 3);	
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null , "Sửa thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
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
			txtTenDiaDanh.setText("");
			txtMoTa.setText("");
			txtMaDiaDanh.requestFocus();
		}
		else if (o.equals(btnThoat)) {
			int kt = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không","Thông báo",JOptionPane.YES_NO_OPTION);
			if(kt == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}
	private void showMessage(String message, JTextField txt) {
		txt.requestFocus();
		txtMess.setText(message);
	}
	private boolean validData() {
		String tenDiaDanh = txtTenDiaDanh.getText().trim();
		if(!(tenDiaDanh.length() > 0))
		{
			showMessage("Error: Tên địa danh không được rỗng", txtTenDiaDanh);
			return false;
		}
		return true;
	}
	private void dinhDangMaDiaDanh() throws RemoteException {
		String maDD = "";
		maDD += "DD";
		if(String.valueOf(diaDanh_DAO.layMaDiaDanhLonNhat()).length() == 2) {
			maDD += "00";
		}
		else if(String.valueOf(diaDanh_DAO.layMaDiaDanhLonNhat()).length() == 3) {
			maDD += "0";
		}
		else if(String.valueOf(diaDanh_DAO.layMaDiaDanhLonNhat()).length() == 1) {
			maDD += "";
		}
		maDD += String.valueOf(diaDanh_DAO.layMaDiaDanhLonNhat()+1);
		txtMaDiaDanh.setText(maDD);
	}
}
