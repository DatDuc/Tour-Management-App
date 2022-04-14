package ui;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import config.config;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class DangNhap extends JFrame implements KeyListener{
	String conf = config.conf;
	private static final long serialVersionUID = 1L;
	private JLabel lblAnh;
	public static JButton btndangNhap;
	public static JTextField txtuser;
	private JPasswordField txtpass;
	private TaiKhoan_DAO taiKhoan_DAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SecurityManager securityManager = System.getSecurityManager();
		if (securityManager == null) {

			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());

		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws InterruptedException
	 * @throws NotBoundException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
//	
	public DangNhap() throws InterruptedException, MalformedURLException, RemoteException, NotBoundException {
		// setIconImage(Toolkit.getDefaultToolkit().getImage(DangNhap.class.getResource("/img/secrecy-icon.png")));
		taiKhoan_DAO = (TaiKhoan_DAO) Naming.lookup(conf + "/taiKhoan_DAO");

		setLayout(new BorderLayout());
		setTitle("DN");
		setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		JPanel pnNorth = new JPanel();
		lblAnh = new JLabel("");

		lblAnh.setIcon(new ImageIcon("Icon/User-Administrator-Red-icon.png"));
		pnNorth.add(lblAnh);
		add(pnNorth, BorderLayout.NORTH);
		pnNorth = new JPanel() {
		};

		lblAnh = new JLabel("");

		lblAnh.setIcon(new ImageIcon("Icon/User-Administrator-Red-icon.png"));
		pnNorth.add(lblAnh);
		add(pnNorth, BorderLayout.NORTH);

		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		JPanel pnThongTin = new JPanel();

		pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.Y_AXIS));
		pnCenter.add(pnThongTin);

		add(pnCenter, BorderLayout.CENTER);

		JPanel pnAo = new JPanel();
		pnThongTin.add(pnAo);

		JPanel pnMaNV = new JPanel();
		JLabel lblmaNV = new JLabel("Mã nhân viên");
		lblmaNV.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnMaNV.add(lblmaNV);
		pnThongTin.add(pnMaNV);

		JPanel pnUser = new JPanel();
		JLabel imageuser = new JLabel("");
		imageuser.setIcon(new ImageIcon("Icon/user.PNG"));

		txtuser = new JTextField(17);
		txtuser.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnUser.add(imageuser);
		pnUser.add(txtuser);

		pnThongTin.add(pnUser);

		JPanel pnMK = new JPanel();
		JLabel lblMK = new JLabel("Mật khẩu");
		lblMK.setFont(new Font("Tahoma", Font.BOLD, 25));
		pnMK.add(lblMK);
		pnThongTin.add(pnMK);

		JPanel pnpass = new JPanel();
		JLabel imagepass = new JLabel("");
		imagepass.setIcon(new ImageIcon("Icon/pass.PNG"));

		txtpass = new JPasswordField(10);
		txtpass.setFont(new Font("Tahoma", Font.BOLD, 25));

		pnpass.add(imagepass);
		pnpass.add(txtpass);
		pnThongTin.add(pnpass);


		txtuser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtuser.setBackground(Color.WHITE);

		txtuser.setBorder(null);
		pnUser.add(txtuser);

		JPanel pnDN = new JPanel();
		btndangNhap = new JButton("Đăng nhập");
		pnDN.add(btndangNhap);
		pnThongTin.add(pnDN);
		btndangNhap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = txtuser.getText();
				@SuppressWarnings("deprecation")
				String password = txtpass.getText();
				TaiKhoan tk = null;
				try {
					tk = taiKhoan_DAO.Login(username, password);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				if (tk != null) {
					ChucNang cn = null;
					try {
						cn = new ChucNang(txtuser.getText().trim());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					cn.setVisible(true);
					close();
					System.out.println(tk);
				} else {
					JOptionPane.showMessageDialog(null, "Tài khoản hoặc Mật khẩu không hợp lệ", "Wrong !", JOptionPane.ERROR_MESSAGE,
							null);
				}
			}
		});

		btndangNhap.setForeground(SystemColor.inactiveCaptionBorder);
		btndangNhap.setBackground(new Color(0, 153, 255));
		btndangNhap.setFont(new Font("Tahoma", Font.BOLD, 25));
		btndangNhap.setBounds(155, 469, 202, 49);


		txtpass.setBorder(null);
		txtuser.setText("NV0001");
		txtpass.setText("123456");

		pnpass.add(txtpass);
		txtuser.setText("NV0001");
		txtpass.setText("123456");


		txtuser.setPreferredSize(txtpass.getPreferredSize());
		lblmaNV.setPreferredSize(txtpass.getPreferredSize());
		lblMK.setPreferredSize(txtpass.getPreferredSize());
		
		txtuser.addKeyListener(this);
		txtpass.addKeyListener(this);
		


	}

	private void close() {
		WindowEvent winClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosing);
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

		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			String username = txtuser.getText();
			@SuppressWarnings("deprecation")
			String password = txtpass.getText();
			TaiKhoan tk = null;
			try {
				tk = taiKhoan_DAO.Login(username, password);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if (tk != null) {
				ChucNang cn = null;
				try {
					cn = new ChucNang(txtuser.getText().trim());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				cn.setVisible(true);
				close();
				System.out.println(tk);
			} else {
				JOptionPane.showMessageDialog(null, "Tài khoản hoặc Mật khẩu không hợp lệ", "Wrong !", JOptionPane.ERROR_MESSAGE,
						null);
			}
			btndangNhap.setForeground(SystemColor.inactiveCaptionBorder);
			btndangNhap.setBackground(new Color(0, 153, 255));
			btndangNhap.setFont(new Font("Tahoma", Font.BOLD, 25));
			btndangNhap.setBounds(155, 469, 202, 49);
		}
	}

}
