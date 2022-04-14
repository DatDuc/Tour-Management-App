
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
import java.sql.Date;
import java.time.LocalDate;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import config.config;
import dao.DiaDanh_DAO;
import dao.NhanVien_DAO;
import entity.DiaDanh;
import entity.NhanVien;



public class ThongTinNhanVien extends JFrame implements ActionListener{
	String conf = config.conf;
	private JLabel lblMaNV, lblTenNV, lblCMND, lblDiaChi, lblEmail, lblNVL, lblSDT,lblGioiTinh;
	private JTextField txtMaNV, txtTenNV, txtEmail, txtDiaChi, txtSDT, txtCMND,txtNVL;
	private JButton btnLamMoi, btnLuu, btnThoat;
	private JPanel pnNorth;
	JRadioButton radNam, radNu,radDangLam,radNghi;
	NhanVien_DAO nhanVien_DAO;
	QuanLiNhanVien ui_ThongTinNhanVien;
	NhanVien nhanVien;
	boolean flag = true;

	public ThongTinNhanVien(NhanVien nv,boolean flag) throws MalformedURLException, RemoteException, NotBoundException  {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		nhanVien_DAO =  (NhanVien_DAO) Naming.lookup(conf+"/nhanVien_DAO");
		nhanVien = nv;		  
		ui_ThongTinNhanVien = new QuanLiNhanVien();

		setTitle("TTKH");
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);	
		setSize(600,400);
		setLocationRelativeTo(null);

		pnNorth = new JPanel() { };
		JLabel lblTieuDe = new JLabel("Thông Tin Nhân Viên");
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
		JPanel pnMaNV = new JPanel();
		lblMaNV = new JLabel("Mã nhân viên");
		txtMaNV = new JTextField(18);
		txtMaNV.setEditable(false);
		pnMaNV.add(lblMaNV);
		pnMaNV.add(txtMaNV);
		pnThongTin.add(pnMaNV);
		
			//Ten
		JPanel pnTenNV = new JPanel();
		lblTenNV = new JLabel("Tên nhân viên");
		txtTenNV = new JTextField(18);
		pnTenNV.add(lblTenNV);
		pnTenNV.add(txtTenNV);
		pnThongTin.add(pnTenNV);
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
		lblCMND = new JLabel("CMND");
		txtCMND = new JTextField(18);
		pnCMND.add(lblCMND);
		pnCMND.add(txtCMND);
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
	
		lblGioiTinh.setPreferredSize(lblSDT.getPreferredSize());
		radNam.setPreferredSize(lblSDT.getPreferredSize());
		radNu.setPreferredSize(lblSDT.getPreferredSize());
		
		lblMaNV.setPreferredSize(lblSDT.getPreferredSize());
		lblEmail.setPreferredSize(lblSDT.getPreferredSize());
		lblSDT.setPreferredSize(lblSDT.getPreferredSize());
		lblCMND.setPreferredSize(lblSDT.getPreferredSize());
		lblDiaChi.setPreferredSize(lblSDT.getPreferredSize());
		//lblNVL.setPreferredSize(lblSDT.getPreferredSize());
		lblGioiTinh.setPreferredSize(lblSDT.getPreferredSize());
		

		
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
			dinhDangMaNhanVien();
			txtTenNV.setText("");
			txtEmail.setText("");
			txtDiaChi.setText("");
			txtSDT.setText("");
			txtCMND.setText("");
			//txtNVL.setText("");
		}
		else {
			txtMaNV.setText(nhanVien.getMaNV());
			txtTenNV.setText(nhanVien.getTenNV());
			txtEmail.setText(nhanVien.getEmail());
			txtDiaChi.setText(nhanVien.getDiaChi());
			txtSDT.setText(nhanVien.getSoDT());
			txtCMND.setText(nhanVien.getCmnd());
			//txtNVL.setText(String.valueOf(nhanVien.getNgayVaoLam()));
			if(nhanVien.isGioiTinh() == true) {
				radNam.setSelected(true);
			}
			else {
				radNu.setSelected(true);
			}

			
		}
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(flag == true) {
					if(validData()) {
						int row = ui_ThongTinNhanVien.table.getSelectedRow();
						String maNV= txtMaNV.getText();
						String tenNV= txtTenNV.getText();
						String email = txtEmail.getText();
						String diaChi = txtDiaChi.getText();
						String soDT = txtSDT.getText();
						String cmnd = txtCMND.getText();
						Date ngayVaoLam =Date.valueOf(LocalDate.now());
						boolean gioiTinh = false;
						if(radNam.isSelected())
							gioiTinh = true;
						nhanVien = new NhanVien(maNV, tenNV,email, diaChi, soDT, cmnd,ngayVaoLam,gioiTinh, true);
						try {
							if(nhanVien_DAO.themNV(nhanVien))
							{
								JOptionPane.showMessageDialog(null , "Thêm thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
								ui_ThongTinNhanVien.modeltable.addRow(new Object[] {
										nv.getMaNV(),nv.getTenNV(),nv.getCmnd(),nv.getDiaChi(), nv.getEmail(),nv.isGioiTinh(),nv.getNgayVaoLam(),nv.getSoDT()
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
					if(ui_ThongTinNhanVien.row>=0) {
						String maNV= txtMaNV.getText().toString();
						String tenNV= txtTenNV.getText().toString();
						String email = txtEmail.getText().toString();
						String diaChi = txtDiaChi.getText().toString();
						String soDT = txtSDT.getText().toString();
						String cmnd = txtCMND.getText().toString();
						Date ngayVaoLam =Date.valueOf(LocalDate.now());
						boolean gioiTinh = false;
						if(radNam.isSelected())
							gioiTinh = true;
						nhanVien = new NhanVien(maNV, tenNV,email, diaChi, soDT, cmnd,ngayVaoLam,gioiTinh, true);
						try {
							if(nhanVien_DAO.update(nhanVien)) {
	
								ui_ThongTinNhanVien.table.setValueAt(txtTenNV.getText(),ui_ThongTinNhanVien.row,1);
								ui_ThongTinNhanVien.table.setValueAt(txtCMND.getText().toString(), ui_ThongTinNhanVien.row, 2);	
								ui_ThongTinNhanVien.table.setValueAt(txtDiaChi.getText(), ui_ThongTinNhanVien.row, 3);
								ui_ThongTinNhanVien.table.setValueAt(txtEmail.getText(),ui_ThongTinNhanVien.row,4);
								ui_ThongTinNhanVien.table.setValueAt(gioiTinh, ui_ThongTinNhanVien.row, 5);	
								ui_ThongTinNhanVien.table.setValueAt(ngayVaoLam, ui_ThongTinNhanVien.row, 6);
								ui_ThongTinNhanVien.table.setValueAt(txtSDT.getText(),ui_ThongTinNhanVien.row,7);
													
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
			txtMaNV.setText("");
			txtTenNV.setText("");
			txtDiaChi.setText("");
			txtSDT.setText("");
			txtCMND.setText("");
			txtMaNV.requestFocus();
		}
		else if (o.equals(btnThoat)) {
			int kt = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không","Thông báo",JOptionPane.YES_NO_OPTION);
			if(kt == JOptionPane.YES_OPTION) {
				dispose();
			}
		}
	}

	private boolean validData() {
		String tenDiaDanh = txtTenNV.getText().trim();
		if(!(tenDiaDanh.length() > 0))
		{
			JOptionPane.showMessageDialog(this, "Tên nhân viên không được rỗng");
			return false;
		}
		return true;
	}
	private void dinhDangMaNhanVien() throws RemoteException {
		String maNV = "";
		maNV += "NV";
		System.out.println(nhanVien_DAO.layMaNhanVienLonNhat());
		System.out.println(String.valueOf(nhanVien_DAO.layMaNhanVienLonNhat()).length());
		if(nhanVien_DAO.layMaNhanVienLonNhat() == 9) {
			maNV += "00";
		}
		else {
			if(String.valueOf(nhanVien_DAO.layMaNhanVienLonNhat()).length() == 1) {
				maNV += "000";
			}
			else if(String.valueOf(nhanVien_DAO.layMaNhanVienLonNhat()).length() == 2) {
				maNV += "00";
			}
			else if(String.valueOf(nhanVien_DAO.layMaNhanVienLonNhat()).length() == 3) {
				maNV += "0";
			}
		}
	
		maNV += String.valueOf(nhanVien_DAO.layMaNhanVienLonNhat()+1);
		txtMaNV.setText(maNV);
	}
}
