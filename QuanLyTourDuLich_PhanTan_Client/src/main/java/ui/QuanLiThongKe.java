package ui;
//THÁI ĐÃ SỬA
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;


import dao.ThongKe_DAO;

import dao.Tour_DAO;
import entity.Tour;
import entity.Ve;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.toedter.calendar.JDateChooser;

import config.config;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class QuanLiThongKe extends JPanel implements ActionListener {
	String conf = config.conf;
	private ThongKe_DAO thongKe_DAO;

	private JDateChooser jdbd;
	private JDateChooser jdkt;
	JButton btnthongke;
	String tuNgay;
	String denNgay;
	int i = 1;
	DefaultTableModel modeltable;
	JLabel lblSoLuongVe, lblTongDoanhThu, lblSoLuongTour, lblSoLuongKhachHang;
	JButton btnQuy1, btnQuy2, btnQuy3, btnQuy4;
	JComboBox<String> cbxChonNam;
	JTable table;
	List<Tour> listTour;
	Map<Tour, Double> loadListTourMap;
	Map<Tour, Double> loadListTourMapQuy;
	JLabel  txtTitleTopTour;
	JTextPane  txtTitleSoLuongVe, txtTitleTongDoanhThu, txtTittleSoLuongTour , txtTittleSoLuongKhachHang;	//SoLuongVe, TongDoanhThu, SoLuongTour, SoLuongKhachHang
	public QuanLiThongKe() throws Exception {


		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {

			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());

		}
		thongKe_DAO = (ThongKe_DAO) Naming.lookup(conf+"/thongKe_DAO");

		setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		JLabel lblTieuDe = new JLabel("Quản lí thống kê");
		Font font = new Font("Arial", Font.BOLD, 25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(Color.RED);
		pnNorth.add(lblTieuDe);
		add(pnNorth, BorderLayout.NORTH);

		JPanel pnWest = new JPanel();
		pnWest.setLayout(new BoxLayout(pnWest, BoxLayout.Y_AXIS));
		pnWest.setPreferredSize(new Dimension(420, 1000));
		String[] chuoi = {"Top","Tên Tour","Tổng Doanh Thu"};

		modeltable = new DefaultTableModel(chuoi,0){
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; 
			}
		};;
		table = new JTable(modeltable) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(245, 245, 240));

				return c;
			}
		};
		table.setEnabled(true);

		table.getTableHeader().setBackground(new Color(230, 247, 255));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setForeground(Color.BLACK);
		Border borderHeader = BorderFactory.createLineBorder(new Color(230, 247, 255));
		table.getTableHeader().setBorder(borderHeader);

		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
		midRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.getColumn("Top").setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(0).setMaxWidth(50);

		table.getColumnModel().getColumn(1).setMaxWidth(350);
		table.getColumnModel().getColumn(2).setMaxWidth(180);
		table.getColumn("Tổng Doanh Thu").setCellRenderer(midRenderer);
		table.setRowHeight(30);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		//		table.setShowHorizontalLines(false);
		//		table.setShowVerticalLines(false);
		table.setShowGrid(false);


		JScrollPane sc = new JScrollPane(table);
		sc.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.black));
		txtTitleTopTour = new JLabel();
		txtTitleTopTour.setFont(new Font("Arial", Font.PLAIN, 16));

		txtTitleTopTour.setText("Top tour đã bán");
		//		txtTitleTopSP.setForeground(Color.WHITE);
		//		StyledDocument documentStyle0 = txtTitleTopSP.getStyledDocument();
		//		SimpleAttributeSet centerAttribute0 = new SimpleAttributeSet();
		//		StyleConstants.setAlignment(centerAttribute0, StyleConstants.ALIGN_CENTER);
		//		documentStyle0.setParagraphAttributes(0, documentStyle0.getLength(), centerAttribute0, false);

		pnWest.add(txtTitleTopTour);
		pnWest.add(Box.createVerticalStrut(20));
		pnWest.add(sc);
		add(pnWest,BorderLayout.WEST);
		//Ket Thuc WEST
		JPanel pncen = new JPanel();
		pncen.setLayout(new BorderLayout());
		add(pncen,BorderLayout.CENTER);

		JPanel pnRightNorth = new JPanel();
		pnRightNorth.setLayout(new BoxLayout(pnRightNorth, BoxLayout.Y_AXIS));
		pncen.add(pnRightNorth);

		JPanel pnhang1= new JPanel();
		pnhang1.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTuNgay = new JLabel("Từ ngày:    ");
		lblTuNgay.setFont(new Font("Arial", Font.BOLD, 14));
		pnhang1.add(lblTuNgay);
		pnhang1.add(jdbd = new JDateChooser());
		jdbd.setDateFormatString("dd/MM/yyyy");
		jdbd.setFont(new Font("Arial", Font.BOLD, 14));
		jdbd.setPreferredSize( new Dimension(200,25) );
		pnhang1.add(Box.createHorizontalStrut(20));	
		JLabel lblDenNgay = new JLabel("Đến ngày:    ");
		lblDenNgay.setFont(new Font("Arial", Font.BOLD, 14));
		pnhang1.add(lblDenNgay);
		pnhang1.add(jdkt = new JDateChooser());

		jdkt.setDateFormatString("dd/MM/yyyy");
		jdkt.setFont(new Font("Arial", Font.BOLD, 14));

		jdkt.setPreferredSize( new Dimension(200,25) );
		pnhang1.add(Box.createHorizontalStrut(30));
		btnthongke = new JButton("Thống kê");
		btnthongke.setBackground(new Color(230, 247, 255));
		btnthongke.setForeground(Color.BLACK);
		pnhang1.add(btnthongke);
		pnRightNorth.add(pnhang1);

		LocalDateTime localDate = LocalDateTime.now();
		int year = localDate.getYear();
		JPanel pnhang2= new JPanel();
		pnhang2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblChonNam = new JLabel("Chọn năm:");
		//lblChonNam.setFont(fontLbl);
		cbxChonNam = new JComboBox();
		cbxChonNam.setEditable(true);
		for (int i =  year; i >= 2015; i--) {
			cbxChonNam.addItem(String.valueOf(i));
		}
		cbxChonNam.setPreferredSize(new Dimension(75,20));
		pnhang2.add(lblChonNam);
		pnhang2.add(cbxChonNam);
		pnhang2.add(Box.createHorizontalStrut(20));
		btnQuy1 = new JButton("Thống Kê Quý I");	
		btnQuy2 = new JButton("Thống Kê Quý II");
		btnQuy3 = new JButton("Thống Kê Quý III");
		btnQuy4 = new JButton("Thống Kê Quý IV");
		pnhang2.add(btnQuy1);
		btnQuy1.setBackground(new Color(230, 247, 255));
		btnQuy1.setForeground(Color.BLACK);
		pnhang2.add(Box.createHorizontalStrut(15));
		pnhang2.add(btnQuy2);
		btnQuy2.setBackground(new Color(230, 247, 255));
		btnQuy2.setForeground(Color.BLACK);
		pnhang2.add(Box.createHorizontalStrut(15));
		pnhang2.add(btnQuy3);
		btnQuy3.setBackground(new Color(230, 247, 255));
		btnQuy3.setForeground(Color.BLACK);
		pnhang2.add(Box.createHorizontalStrut(15));
		pnhang2.add(btnQuy4);
		btnQuy4.setBackground(new Color(230, 247, 255));
		btnQuy4.setForeground(Color.BLACK);
		pnRightNorth.add(pnhang2);

		pncen.add(pnRightNorth,BorderLayout.NORTH);

		//Ã” thá»‘ng kÃª
		JPanel pnCenNorth = new JPanel();
		pnCenNorth.setLayout(new BoxLayout(pnCenNorth, BoxLayout.Y_AXIS));
		pncen.add(pnCenNorth);
		pnCenNorth.add(Box.createVerticalStrut(20));

		JPanel pnHang3= new JPanel();
		pnHang3.setLayout(new FlowLayout(FlowLayout.CENTER));	

		//========================================== Ô 1

		txtTitleSoLuongVe = new JTextPane();
		txtTitleSoLuongVe.setFont(new Font("Arial", Font.BOLD, 16));
		txtTitleSoLuongVe.setText("Tổng số lượng vẽ đã bán");
		txtTitleSoLuongVe.setForeground(Color.WHITE);
		StyledDocument documentStyle1 = txtTitleSoLuongVe.getStyledDocument();
		SimpleAttributeSet centerAttribute1 = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute1, StyleConstants.ALIGN_CENTER);
		documentStyle1.setParagraphAttributes(0, documentStyle1.getLength(), centerAttribute1, false);
		//		lblNgaySoLuongHoaDon = new JLabel("");
		lblSoLuongVe = new JLabel("? vé");
		lblSoLuongVe.setForeground(Color.WHITE);
		lblSoLuongVe.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnDoanhThuQuy_SoTienQuy = new JPanel();	
		pnDoanhThuQuy_SoTienQuy.setPreferredSize(new Dimension(300,250));
		pnDoanhThuQuy_SoTienQuy.setLayout(new GridLayout(2,1));


		txtTitleSoLuongVe.setBackground(new Color(179, 179, 0));
		JPanel pnSoTienQuy = new JPanel();
		pnSoTienQuy.setBackground(new Color(179, 179, 0));
		pnSoTienQuy.add(lblSoLuongVe);

		pnDoanhThuQuy_SoTienQuy.add(txtTitleSoLuongVe);
		pnDoanhThuQuy_SoTienQuy.add(pnSoTienQuy);

		//========================================== Ô 2
		txtTitleTongDoanhThu = new JTextPane();
		txtTitleTongDoanhThu.setText("Tổng doanh thu ");
		txtTitleTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));	
		txtTitleTongDoanhThu.setForeground(Color.WHITE);
		StyledDocument documentStyle2 = txtTitleTongDoanhThu.getStyledDocument();
		SimpleAttributeSet centerAttribute2 = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute2, StyleConstants.ALIGN_CENTER);
		documentStyle2.setParagraphAttributes(0, documentStyle2.getLength(), centerAttribute2, false);
		txtTitleTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));

		lblTongDoanhThu = new JLabel("? VND");
		lblTongDoanhThu.setForeground(Color.WHITE);
		lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnDoanhThuTuNgay_SoTienNgay = new JPanel();
		pnDoanhThuTuNgay_SoTienNgay.setPreferredSize(new Dimension(300,250));
		pnDoanhThuTuNgay_SoTienNgay.setLayout(new GridLayout(2,1));

		txtTitleTongDoanhThu.setBackground(new Color(51, 133, 255));
		JPanel pnSoTienNgay = new JPanel();
		pnSoTienNgay.setBackground(new Color(51, 133, 255));
		pnSoTienNgay.add(lblTongDoanhThu);

		pnDoanhThuTuNgay_SoTienNgay.add(txtTitleTongDoanhThu);
		pnDoanhThuTuNgay_SoTienNgay.add(pnSoTienNgay);

		pnHang3.add(pnDoanhThuQuy_SoTienQuy);
		pnHang3.add(Box.createHorizontalStrut(40));
		pnHang3.add(pnDoanhThuTuNgay_SoTienNgay);

		JPanel pnHang4= new JPanel();
		pnHang4.setLayout(new FlowLayout(FlowLayout.CENTER));	

		//========================================== Ô 3
		txtTittleSoLuongTour = new JTextPane();
		txtTittleSoLuongTour.setText("Số lượng tour đã bán");
		txtTittleSoLuongTour.setForeground(Color.WHITE);
		txtTittleSoLuongTour.setFont(new Font("Arial", Font.BOLD, 16));	
		StyledDocument documentStyle = txtTittleSoLuongTour.getStyledDocument();
		SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
		documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

		lblSoLuongTour = new JLabel("? tour");
		lblSoLuongTour.setForeground(Color.WHITE);
		lblSoLuongTour.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnSanPhamQuy_SoLuongQuy = new JPanel();	
		pnSanPhamQuy_SoLuongQuy.setPreferredSize(new Dimension(300,250));
		pnSanPhamQuy_SoLuongQuy.setLayout(new GridLayout(2,1));

		txtTittleSoLuongTour.setBackground(new Color(255, 51, 51));
		JPanel pnSoLuongQuy = new JPanel();
		pnSoLuongQuy.setBackground(new Color(255, 51, 51));
		pnSoLuongQuy.add(lblSoLuongTour);

		pnSanPhamQuy_SoLuongQuy.add(txtTittleSoLuongTour);
		pnSanPhamQuy_SoLuongQuy.add(pnSoLuongQuy);

		//==========================================  Ô 4
		txtTittleSoLuongKhachHang = new JTextPane();
		txtTittleSoLuongKhachHang.setText("Tổng số lượng khách hàng");
		txtTittleSoLuongKhachHang.setFont(new Font("Arial", Font.BOLD, 16));	
		StyledDocument documentStyle4 = txtTittleSoLuongKhachHang.getStyledDocument();
		SimpleAttributeSet centerAttribute4 = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute4, StyleConstants.ALIGN_CENTER);
		documentStyle4.setParagraphAttributes(0, documentStyle4.getLength(), centerAttribute4, false);


		lblSoLuongKhachHang = new JLabel("? khách hàng");
		lblSoLuongKhachHang.setForeground(Color.WHITE);
		lblSoLuongKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnSoLuongTuNgay_SoLuongSPNgay = new JPanel();
		pnSoLuongTuNgay_SoLuongSPNgay.setPreferredSize(new Dimension(300,250));
		pnSoLuongTuNgay_SoLuongSPNgay.setLayout(new GridLayout(2,1));
		txtTittleSoLuongKhachHang.setForeground(Color.WHITE);
		txtTittleSoLuongKhachHang.setBackground(new Color(0, 179, 60));
		JPanel pnSoLuongSPNgay = new JPanel();
		pnSoLuongSPNgay.setBackground(new Color(0, 179, 60));
		pnSoLuongSPNgay.add(lblSoLuongKhachHang);

		pnSoLuongTuNgay_SoLuongSPNgay.add(txtTittleSoLuongKhachHang);
		pnSoLuongTuNgay_SoLuongSPNgay.add(pnSoLuongSPNgay);

		pnHang4.add(pnSanPhamQuy_SoLuongQuy);
		pnHang4.add(Box.createHorizontalStrut(40));
		pnHang4.add(pnSoLuongTuNgay_SoLuongSPNgay);

		pnCenNorth.add(pnHang3);
		pnCenNorth.add(pnHang4);
		pncen.add(pnCenNorth,BorderLayout.CENTER);

		btnthongke.addActionListener(this);
		btnQuy1.addActionListener(this);
		btnQuy2.addActionListener(this);
		btnQuy3.addActionListener(this);
		btnQuy4.addActionListener(this);
		cbxChonNam.addActionListener(this);

	}
	private boolean KiemTraNhapLieu() {
		try {
			SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
			String ngayBD= "", ngayKT="";
			ngayBD = dcn.format(jdbd.getDate() );
			ngayKT = dcn.format(jdkt.getDate());
			LocalDate ngayHienTai = LocalDate.now();
			LocalDate nbd = LocalDate.parse(ngayBD);
			LocalDate nkt = LocalDate.parse(ngayKT);

			if(nbd.compareTo(nkt)>0) {
				JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải bé hơn ngày kết thúc!");
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày cần thống kê !");
			return false;
		}
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent e) {	//txtTitleSoLuongVe, txtTitleTongDoanhThu, txtTittleSoLuongTour , txtTittleSoLuongKhachHang;
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnthongke)) {
			if(KiemTraNhapLieu()) {
				DecimalFormat formatter = new DecimalFormat("###,###,###,###");
				SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfIn = new SimpleDateFormat("dd-MM-yyyy");
				tuNgay = "";
				denNgay = "";
				tuNgay = sdfSQL.format(jdbd.getDate());
				denNgay= sdfSQL.format(jdkt.getDate());

				txtTitleSoLuongVe.setText("Tổng số lượng vẽ đã bán từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
				try {
					lblSoLuongVe.setText(String.valueOf(thongKe_DAO.tongSoLuongVeTheoNgayChon(tuNgay,denNgay)) +" vé");
				} catch (RemoteException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}

				txtTitleTongDoanhThu.setText("Tổng doanh thu từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
				try {
					lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.TongDTtheoNgayChon(tuNgay,denNgay))) +" VND");
				} catch (RemoteException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					loadListTourMap = thongKe_DAO.thongKeTheoNgay(tuNgay, denNgay);
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				i = 0;
				loadListTourMap.forEach((a,b) -> {
					i++;
				});
					
				
				txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành từ ngày"+sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
				lblSoLuongTour.setText(String.valueOf(i +" tour"));
				//
				//				txtTittleSoLuongKhachHang.setText("Tá»•ng sá»‘ lÆ°á»£ng khÃ¡ch hÃ ng tá»« ngÃ y " +sdfIn.format(jdbd.getDate()) + " Ä‘áº¿n ngÃ y " +sdfIn.format(jdkt.getDate()));
				//				lblSoLuongKhachHang.setText(String.valueOf(formatter.format(tk_dao.DoanhThuTheoNgayChon(tuNgay,denNgay))) +" khÃ¡ch hÃ ng");

				txtTitleTopTour.setText("Top tour đã bán từ ngày "+sdfIn.format(jdbd.getDate())+" đến ngày "+sdfIn.format(jdkt.getDate()));
				try {
					loadTK();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if(o.equals(btnQuy4)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");
			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý IV "+ (String) cbxChonNam.getSelectedItem());
			try {
				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12") +" vé"));
			} catch (RemoteException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý IV năm " + (String) cbxChonNam.getSelectedItem());
			try {
				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12")) +" VND"));
			} catch (RemoteException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			try {
				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"10","11","12");
			} catch (RemoteException e2) {
				e2.printStackTrace();
			}
			i = 0;
			loadListTourMapQuy.forEach((a,b) -> {
				i++;
			});
			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý IV năm " + (String) cbxChonNam.getSelectedItem());
			lblSoLuongTour.setText(String.valueOf(i +" tour"));
			//
			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ IV " +  (String) cbxChonNam.getSelectedItem());
			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12"))+" VND"));

			txtTitleTopTour.setText("Top sản phẩm đã bán trong quý IV năm " + (String) cbxChonNam.getSelectedItem());		

			try {
				loadTKTheoQuy4("10","11","12");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnQuy3)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý IV "+ (String) cbxChonNam.getSelectedItem());
			try {
				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"7","8","9") +" vé"));
			} catch (RemoteException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý III năm " + (String) cbxChonNam.getSelectedItem());
			try {
				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"7","8","9")) +" VND"));
			} catch (RemoteException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			};

			try {
				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"7","8","9");
			} catch (RemoteException e2) {
				e2.printStackTrace();
			}
			i = 0;
			loadListTourMapQuy.forEach((a,b) -> {
				i++;
			});
			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý III năm " + (String) cbxChonNam.getSelectedItem());
			lblSoLuongTour.setText(String.valueOf(i +" tour"));
			//
			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ III " +  (String) cbxChonNam.getSelectedItem());
			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"7","8","9"))+" VND"));
			//
			txtTitleTopTour.setText("Top sản phẩm đã bán trong quý III năm " + (String) cbxChonNam.getSelectedItem());		

			try {
				loadTKTheoQuy4("7","8","9");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnQuy2)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý IV "+ (String) cbxChonNam.getSelectedItem());
			try {
				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6") +" vé"));
			} catch (RemoteException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý II năm" + (String) cbxChonNam.getSelectedItem());
			try {
				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6")) +" VND"));
			} catch (RemoteException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			};

			try {
				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"4","5","6");
			} catch (RemoteException e2) {
				e2.printStackTrace();
			}
			i = 0;
			loadListTourMapQuy.forEach((a,b) -> {
				i++;
			});
			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý II năm " + (String) cbxChonNam.getSelectedItem());
			lblSoLuongTour.setText(String.valueOf(i +" tour"));
			//
			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ II " +  (String) cbxChonNam.getSelectedItem());
			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6"))+" VND"));
			//
			txtTitleTopTour.setText("Top sản phẩm đã bán trong quý II năm " + (String) cbxChonNam.getSelectedItem());
			//
			try {
				loadTKTheoQuy4("4","5","6");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(o.equals(btnQuy1)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý I "+ (String) cbxChonNam.getSelectedItem());
			try {
				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3") +" vé"));
			} catch (RemoteException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý I năm " + (String) cbxChonNam.getSelectedItem());
			try {
				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3")) +" VND"));
			} catch (RemoteException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			};

			try {
				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"1","2","3");
				//									listTour = thongKe_DAO.thongKeTheoNgay(tuNgay,denNgay);
			} catch (RemoteException e2) {
				e2.printStackTrace();
			}
			i = 0;
			loadListTourMapQuy.forEach((a,b) -> {
				i++;
			});
			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý I năm " + (String) cbxChonNam.getSelectedItem());
			lblSoLuongTour.setText(String.valueOf(i +" tour"));
			//
			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ I " +  (String) cbxChonNam.getSelectedItem());
			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3"))+" VND"));
			//
			//			txtTitleTopSP.setText("Top sáº£n pháº©m Ä‘Ã£ bÃ¡n trong quÃ½ I nÄƒm " + (String) cbxChonNam.getSelectedItem());
			try {
				loadTKTheoQuy4("1","2","3");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}


	public void loadTK() throws SQLException, RemoteException {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		modeltable.setRowCount(0);
		table.removeAll();
		i =1;
		loadListTourMap = thongKe_DAO.thongKeTheoNgay(tuNgay, denNgay);
		loadListTourMap.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.forEach(data -> {
			modeltable.addRow(new Object[] {i++,data.getKey().getTenTour(),formatter.format(data.getValue())+ "VND"});
		});

	}
	public void loadTKTheoQuy4(String thang1, String thang2, String thang3) throws SQLException, RemoteException {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		modeltable.setRowCount(0);
		table.removeAll();
		i =1;
		loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),thang1, thang2, thang3);
		System.out.println(loadListTourMapQuy);
		loadListTourMapQuy.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		.forEach(data -> {
			modeltable.addRow(new Object[] {i++,data.getKey().getTenTour(),formatter.format(data.getValue())+ "VND"});
		});
	}
}





//=================================CỦA VINH
//package ui;
////THÁI ĐÃ SỬA
//import java.awt.BorderLayout;
//import java.awt.Button;
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.rmi.Naming;
//import java.rmi.RemoteException;
//import java.sql.Statement;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import javax.swing.BorderFactory;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTabbedPane;
//import javax.swing.JTable;
//
//
//import dao.ThongKe_DAO;
//
//import dao.Tour_DAO;
//import entity.Tour;
//import entity.Ve;
//
//import javax.swing.JTextField;
//import javax.swing.JTextPane;
//import javax.swing.border.Border;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyledDocument;
//
//import com.toedter.calendar.JDateChooser;
//
//import config.config;
//
//import javax.swing.JScrollBar;
//import javax.swing.JScrollPane;
//
//public class QuanLiThongKe extends JPanel implements ActionListener {
//	String conf = config.conf;
//	private ThongKe_DAO thongKe_DAO;
//
//	private JDateChooser jdbd;
//	private JDateChooser jdkt;
//	JButton btnthongke;
//	String tuNgay;
//	String denNgay;
//	int i = 1;
//	DefaultTableModel modeltable;
//	JLabel lblSoLuongVe, lblTongDoanhThu, lblSoLuongTour, lblSoLuongKhachHang;
//	JButton btnQuy1, btnQuy2, btnQuy3, btnQuy4;
//	JComboBox<String> cbxChonNam;
//	JTable table;
//	List<Tour> listTour;
//	Map<Tour, Double> loadListTourMap;
//	Map<Tour, Double> loadListTourMapQuy;
//	JLabel  txtTitleTopTour;
//	JTextPane  txtTitleSoLuongVe, txtTitleTongDoanhThu, txtTittleSoLuongTour , txtTittleSoLuongKhachHang;	//SoLuongVe, TongDoanhThu, SoLuongTour, SoLuongKhachHang
//	public QuanLiThongKe() throws Exception {
//
//
//		SecurityManager securityManager = System.getSecurityManager();
//		if(securityManager == null) {
//
//			System.setProperty("java.security.policy", "policy/policy.policy");
//			System.setSecurityManager(new SecurityManager());
//
//		}
//		thongKe_DAO = (ThongKe_DAO) Naming.lookup(conf+"/thongKe_DAO");
//
//		setLayout(new BorderLayout());
//		JPanel pnNorth = new JPanel();
//		JLabel lblTieuDe = new JLabel("Quản lí thống kê");
//		Font font = new Font("Arial", Font.BOLD, 25);
//		lblTieuDe.setFont(font);
//		lblTieuDe.setForeground(Color.RED);
//		pnNorth.add(lblTieuDe);
//		add(pnNorth, BorderLayout.NORTH);
//
//		JPanel pnWest = new JPanel();
//		pnWest.setLayout(new BoxLayout(pnWest, BoxLayout.Y_AXIS));
//		pnWest.setPreferredSize(new Dimension(425, 700));			//của vinh
//		String[] chuoi = {"Top","Tên Tour","Tổng Doanh Thu"};
//
//		modeltable = new DefaultTableModel(chuoi,0){
//			@Override
//			public boolean isCellEditable(int i, int i1) {
//				return false; 
//			}
//		};;
//		table = new JTable(modeltable) {
//			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
//			{
//				Component c = super.prepareRenderer(renderer, row, column);
//
//				//  Alternate row color
//
//				if (!isRowSelected(row))
//					c.setBackground(row % 2 == 0 ? getBackground() : new Color(245, 245, 240));
//
//				return c;
//			}
//		};
//		table.setEnabled(true);
//
//		table.getTableHeader().setBackground(new Color(230, 247, 255));
//		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
//		table.getTableHeader().setForeground(Color.BLACK);
//		Border borderHeader = BorderFactory.createLineBorder(new Color(230, 247, 255));
//		table.getTableHeader().setBorder(borderHeader);
//
//		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
//		midRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//		table.getColumn("Top").setCellRenderer(midRenderer);
//		table.getColumnModel().getColumn(0).setMaxWidth(50);
//
//		table.getColumnModel().getColumn(1).setMaxWidth(350);
//		table.getColumnModel().getColumn(2).setMaxWidth(180);
//		table.getColumn("Tổng Doanh Thu").setCellRenderer(midRenderer);
//		table.setRowHeight(30);
//		table.setFont(new Font("Arial", Font.PLAIN, 13));
//		//		table.setShowHorizontalLines(false);
//		//		table.setShowVerticalLines(false);
//		table.setShowGrid(false);
//
//
//		JScrollPane sc = new JScrollPane(table);
//		sc.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.black));
//		txtTitleTopTour = new JLabel();
//		txtTitleTopTour.setFont(new Font("Arial", Font.PLAIN, 16));
//
//		txtTitleTopTour.setText("Top tour đã bán");
//		//		txtTitleTopSP.setForeground(Color.WHITE);
//		//		StyledDocument documentStyle0 = txtTitleTopSP.getStyledDocument();
//		//		SimpleAttributeSet centerAttribute0 = new SimpleAttributeSet();
//		//		StyleConstants.setAlignment(centerAttribute0, StyleConstants.ALIGN_CENTER);
//		//		documentStyle0.setParagraphAttributes(0, documentStyle0.getLength(), centerAttribute0, false);
//
//		pnWest.add(txtTitleTopTour);
//		pnWest.add(Box.createVerticalStrut(20));
//		pnWest.add(sc);
//		add(pnWest,BorderLayout.WEST);
//		//Ket Thuc WEST
//		JPanel pncen = new JPanel();
//		pncen.setLayout(new BorderLayout());
//		add(pncen,BorderLayout.CENTER);
//
//		JPanel pnRightNorth = new JPanel();
//		pnRightNorth.setLayout(new BoxLayout(pnRightNorth, BoxLayout.Y_AXIS));
//		pncen.add(pnRightNorth);
//
//		JPanel pnhang1= new JPanel();
//		pnhang1.setLayout(new FlowLayout(FlowLayout.CENTER));
//		JLabel lblTuNgay = new JLabel("Từ ngày:    ");
//		lblTuNgay.setFont(new Font("Arial", Font.BOLD, 14));
//		pnhang1.add(lblTuNgay);
//		pnhang1.add(jdbd = new JDateChooser());
//		jdbd.setDateFormatString("dd/MM/yyyy");
//		jdbd.setFont(new Font("Arial", Font.BOLD, 14));
//		jdbd.setPreferredSize( new Dimension(200,25) );
//		pnhang1.add(Box.createHorizontalStrut(20));	
//		JLabel lblDenNgay = new JLabel("Đến ngày:    ");
//		lblDenNgay.setFont(new Font("Arial", Font.BOLD, 14));
//		pnhang1.add(lblDenNgay);
//		pnhang1.add(jdkt = new JDateChooser());
//
//		jdkt.setDateFormatString("dd/MM/yyyy");
//		jdkt.setFont(new Font("Arial", Font.BOLD, 14));
//
//		jdkt.setPreferredSize( new Dimension(200,25) );
//		pnhang1.add(Box.createHorizontalStrut(30));
//		btnthongke = new JButton("Thống kê");
//		btnthongke.setBackground(new Color(230, 247, 255));
//		btnthongke.setForeground(Color.BLACK);
//		pnhang1.add(btnthongke);
//		pnRightNorth.add(pnhang1);
//
//		LocalDateTime localDate = LocalDateTime.now();
//		int year = localDate.getYear();
//		JPanel pnhang2= new JPanel();
//		pnhang2.setLayout(new FlowLayout(FlowLayout.LEFT));
//		JLabel lblChonNam = new JLabel("Chọn năm:");
//		//lblChonNam.setFont(fontLbl);
//		cbxChonNam = new JComboBox();
//		cbxChonNam.setEditable(true);
//		for (int i =  year; i >= 2015; i--) {
//			cbxChonNam.addItem(String.valueOf(i));
//		}
//		cbxChonNam.setPreferredSize(new Dimension(75,20));
//		pnhang2.add(lblChonNam);
//		pnhang2.add(cbxChonNam);
//		pnhang2.add(Box.createHorizontalStrut(20));
//		btnQuy1 = new JButton("Thống Kê Quý I");	
//		btnQuy2 = new JButton("Thống Kê Quý II");
//		btnQuy3 = new JButton("Thống Kê Quý III");
//		btnQuy4 = new JButton("Thống Kê Quý IV");
//		pnhang2.add(btnQuy1);
//		btnQuy1.setBackground(new Color(230, 247, 255));
//		btnQuy1.setForeground(Color.BLACK);
//		pnhang2.add(Box.createHorizontalStrut(15));
//		pnhang2.add(btnQuy2);
//		btnQuy2.setBackground(new Color(230, 247, 255));
//		btnQuy2.setForeground(Color.BLACK);
//		pnhang2.add(Box.createHorizontalStrut(15));
//		pnhang2.add(btnQuy3);
//		btnQuy3.setBackground(new Color(230, 247, 255));
//		btnQuy3.setForeground(Color.BLACK);
//		pnhang2.add(Box.createHorizontalStrut(15));
//		pnhang2.add(btnQuy4);
//		btnQuy4.setBackground(new Color(230, 247, 255));
//		btnQuy4.setForeground(Color.BLACK);
//		pnRightNorth.add(pnhang2);
//
//		pncen.add(pnRightNorth,BorderLayout.NORTH);
//
//		JPanel pnCenNorth = new JPanel();
//		pnCenNorth.setLayout(new BoxLayout(pnCenNorth, BoxLayout.Y_AXIS));
//		pncen.add(pnCenNorth);
//		pnCenNorth.add(Box.createVerticalStrut(20));
//
//		JPanel pnHang3= new JPanel();
//		pnHang3.setLayout(new FlowLayout(FlowLayout.CENTER));	
//
//		//========================================== Ô 1
//
//		txtTitleSoLuongVe = new JTextPane();
//		txtTitleSoLuongVe.setFont(new Font("Arial", Font.BOLD, 16));
//		txtTitleSoLuongVe.setText("Tổng số lượng vẽ đã bán");
//		txtTitleSoLuongVe.setForeground(Color.WHITE);
//		StyledDocument documentStyle1 = txtTitleSoLuongVe.getStyledDocument();
//		SimpleAttributeSet centerAttribute1 = new SimpleAttributeSet();
//		StyleConstants.setAlignment(centerAttribute1, StyleConstants.ALIGN_CENTER);
//		documentStyle1.setParagraphAttributes(0, documentStyle1.getLength(), centerAttribute1, false);
//		//		lblNgaySoLuongHoaDon = new JLabel("");
//		lblSoLuongVe = new JLabel("? vé");
//		lblSoLuongVe.setForeground(Color.WHITE);
//		lblSoLuongVe.setFont(new Font("Arial", Font.BOLD, 16));
//		JPanel pnDoanhThuQuy_SoTienQuy = new JPanel();	
//		pnDoanhThuQuy_SoTienQuy.setPreferredSize(new Dimension(300,250));
//		pnDoanhThuQuy_SoTienQuy.setLayout(new GridLayout(2,1));
//
//
//		txtTitleSoLuongVe.setBackground(new Color(179, 179, 0));
//		JPanel pnSoTienQuy = new JPanel();
//		pnSoTienQuy.setBackground(new Color(179, 179, 0));
//		pnSoTienQuy.add(lblSoLuongVe);
//
//		pnDoanhThuQuy_SoTienQuy.add(txtTitleSoLuongVe);
//		pnDoanhThuQuy_SoTienQuy.add(pnSoTienQuy);
//
//		//========================================== Ô 2
//		txtTitleTongDoanhThu = new JTextPane();
//		txtTitleTongDoanhThu.setText("Tổng doanh thu ");
//		txtTitleTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));	
//		txtTitleTongDoanhThu.setForeground(Color.WHITE);
//		StyledDocument documentStyle2 = txtTitleTongDoanhThu.getStyledDocument();
//		SimpleAttributeSet centerAttribute2 = new SimpleAttributeSet();
//		StyleConstants.setAlignment(centerAttribute2, StyleConstants.ALIGN_CENTER);
//		documentStyle2.setParagraphAttributes(0, documentStyle2.getLength(), centerAttribute2, false);
//		txtTitleTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));
//
//		lblTongDoanhThu = new JLabel("? VND");
//		lblTongDoanhThu.setForeground(Color.WHITE);
//		lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));
//		JPanel pnDoanhThuTuNgay_SoTienNgay = new JPanel();
//		pnDoanhThuTuNgay_SoTienNgay.setPreferredSize(new Dimension(300,250));
//		pnDoanhThuTuNgay_SoTienNgay.setLayout(new GridLayout(2,1));
//
//		txtTitleTongDoanhThu.setBackground(new Color(51, 133, 255));
//		JPanel pnSoTienNgay = new JPanel();
//		pnSoTienNgay.setBackground(new Color(51, 133, 255));
//		pnSoTienNgay.add(lblTongDoanhThu);
//
//		pnDoanhThuTuNgay_SoTienNgay.add(txtTitleTongDoanhThu);
//		pnDoanhThuTuNgay_SoTienNgay.add(pnSoTienNgay);
//
//		pnHang3.add(pnDoanhThuQuy_SoTienQuy);
//		pnHang3.add(Box.createHorizontalStrut(40));
//		pnHang3.add(pnDoanhThuTuNgay_SoTienNgay);
//
//		JPanel pnHang4= new JPanel();
//		pnHang4.setLayout(new FlowLayout(FlowLayout.CENTER));	
//
//		//========================================== Ô 3
//		txtTittleSoLuongTour = new JTextPane();
//		txtTittleSoLuongTour.setText("Số lượng tour đã bán");
//		txtTittleSoLuongTour.setForeground(Color.WHITE);
//		txtTittleSoLuongTour.setFont(new Font("Arial", Font.BOLD, 16));	
//		StyledDocument documentStyle = txtTittleSoLuongTour.getStyledDocument();
//		SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
//		StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
//		documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
//
//		lblSoLuongTour = new JLabel("? tour");
//		lblSoLuongTour.setForeground(Color.WHITE);
//		lblSoLuongTour.setFont(new Font("Arial", Font.BOLD, 16));
//		JPanel pnSanPhamQuy_SoLuongQuy = new JPanel();	
//		pnSanPhamQuy_SoLuongQuy.setPreferredSize(new Dimension(300,250));
//		pnSanPhamQuy_SoLuongQuy.setLayout(new GridLayout(2,1));
//
//		txtTittleSoLuongTour.setBackground(new Color(255, 51, 51));
//		JPanel pnSoLuongQuy = new JPanel();
//		pnSoLuongQuy.setBackground(new Color(255, 51, 51));
//		pnSoLuongQuy.add(lblSoLuongTour);
//
//		pnSanPhamQuy_SoLuongQuy.add(txtTittleSoLuongTour);
//		pnSanPhamQuy_SoLuongQuy.add(pnSoLuongQuy);
//
//		//==========================================  Ô 4
//		txtTittleSoLuongKhachHang = new JTextPane();
//		txtTittleSoLuongKhachHang.setText("Tổng số lượng khách hàng");
//		txtTittleSoLuongKhachHang.setFont(new Font("Arial", Font.BOLD, 16));	
//		StyledDocument documentStyle4 = txtTittleSoLuongKhachHang.getStyledDocument();
//		SimpleAttributeSet centerAttribute4 = new SimpleAttributeSet();
//		StyleConstants.setAlignment(centerAttribute4, StyleConstants.ALIGN_CENTER);
//		documentStyle4.setParagraphAttributes(0, documentStyle4.getLength(), centerAttribute4, false);
//
//
//		lblSoLuongKhachHang = new JLabel("? khách hàng");
//		lblSoLuongKhachHang.setForeground(Color.WHITE);
//		lblSoLuongKhachHang.setFont(new Font("Arial", Font.BOLD, 16));
//		JPanel pnSoLuongTuNgay_SoLuongSPNgay = new JPanel();
//		pnSoLuongTuNgay_SoLuongSPNgay.setPreferredSize(new Dimension(300,250));
//		pnSoLuongTuNgay_SoLuongSPNgay.setLayout(new GridLayout(2,1));
//		txtTittleSoLuongKhachHang.setForeground(Color.WHITE);
//		txtTittleSoLuongKhachHang.setBackground(new Color(0, 179, 60));
//		JPanel pnSoLuongSPNgay = new JPanel();
//		pnSoLuongSPNgay.setBackground(new Color(0, 179, 60));
//		pnSoLuongSPNgay.add(lblSoLuongKhachHang);
//
//		pnSoLuongTuNgay_SoLuongSPNgay.add(txtTittleSoLuongKhachHang);
//		pnSoLuongTuNgay_SoLuongSPNgay.add(pnSoLuongSPNgay);
//
//		pnHang4.add(pnSanPhamQuy_SoLuongQuy);
//		pnHang4.add(Box.createHorizontalStrut(40));
//		pnHang4.add(pnSoLuongTuNgay_SoLuongSPNgay);
//
//		pnCenNorth.add(pnHang3);
//		pnCenNorth.add(pnHang4);
//		pncen.add(pnCenNorth,BorderLayout.CENTER);
//
//		btnthongke.addActionListener(this);
//		btnQuy1.addActionListener(this);
//		btnQuy2.addActionListener(this);
//		btnQuy3.addActionListener(this);
//		btnQuy4.addActionListener(this);
//		cbxChonNam.addActionListener(this);
//
//	}
//	private boolean KiemTraNhapLieu() {
//		try {
//			SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
//			String ngayBD= "", ngayKT="";
//			ngayBD = dcn.format(jdbd.getDate() );
//			ngayKT = dcn.format(jdkt.getDate());
//			LocalDate ngayHienTai = LocalDate.now();
//			LocalDate nbd = LocalDate.parse(ngayBD);
//			LocalDate nkt = LocalDate.parse(ngayKT);
//
//			if(nbd.compareTo(nkt)>0) {
//				JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải bé hơn ngày kết thúc!");
//				return false;
//			}
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày cần thống kê !");
//			return false;
//		}
//		return true;
//	}
//	@Override
//	public void actionPerformed(ActionEvent e) {	//txtTitleSoLuongVe, txtTitleTongDoanhThu, txtTittleSoLuongTour , txtTittleSoLuongKhachHang;
//		// TODO Auto-generated method stub
//		Object o = e.getSource();
//		if(o.equals(btnthongke)) {
//			if(KiemTraNhapLieu()) {
//				DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//				SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
//				SimpleDateFormat sdfIn = new SimpleDateFormat("dd-MM-yyyy");
//				tuNgay = "";
//				denNgay = "";
//				tuNgay = sdfSQL.format(jdbd.getDate());
//				denNgay= sdfSQL.format(jdkt.getDate());
//
//				txtTitleSoLuongVe.setText("Tổng số lượng vẽ đã bán từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
//				try {
//					lblSoLuongVe.setText(String.valueOf(thongKe_DAO.tongSoLuongVeTheoNgayChon(tuNgay,denNgay)) +" vé");
//				} catch (RemoteException e4) {
//					// TODO Auto-generated catch block
//					e4.printStackTrace();
//				}
//
//				txtTitleTongDoanhThu.setText("Tổng doanh thu từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
//				try {
//					lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.TongDTtheoNgayChon(tuNgay,denNgay))) +" VND");
//				} catch (RemoteException e3) {
//					// TODO Auto-generated catch block
//					e3.printStackTrace();
//				}
//				try {
//					loadListTourMap = thongKe_DAO.thongKeTheoNgay(tuNgay, denNgay);
//				} catch (RemoteException e2) {
//					e2.printStackTrace();
//				}
//				i = 0;
//				loadListTourMap.forEach((a,b) -> {
//					i++;
//				});
//					
//				
//				txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành từ ngày"+sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
//				lblSoLuongTour.setText(String.valueOf(i +" tour"));
//				//
//				//				txtTittleSoLuongKhachHang.setText("Tá»•ng sá»‘ lÆ°á»£ng khÃ¡ch hÃ ng tá»« ngÃ y " +sdfIn.format(jdbd.getDate()) + " Ä‘áº¿n ngÃ y " +sdfIn.format(jdkt.getDate()));
//				//				lblSoLuongKhachHang.setText(String.valueOf(formatter.format(tk_dao.DoanhThuTheoNgayChon(tuNgay,denNgay))) +" khÃ¡ch hÃ ng");
//
//				txtTitleTopTour.setText("Top tour đã bán từ ngày "+sdfIn.format(jdbd.getDate())+" đến ngày "+sdfIn.format(jdkt.getDate()));
//				try {
//					loadTK();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (RemoteException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		}
//		else if(o.equals(btnQuy4)) {
//			DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý IV "+ (String) cbxChonNam.getSelectedItem());
//			try {
//				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12") +" vé"));
//			} catch (RemoteException e4) {
//				// TODO Auto-generated catch block
//				e4.printStackTrace();
//			}
//
//			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý IV năm " + (String) cbxChonNam.getSelectedItem());
//			try {
//				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12")) +" VND"));
//			} catch (RemoteException e3) {
//				// TODO Auto-generated catch block
//				e3.printStackTrace();
//			}
//
//			try {
//				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"10","11","12");
//			} catch (RemoteException e2) {
//				e2.printStackTrace();
//			}
//			i = 0;
//			loadListTourMapQuy.forEach((a,b) -> {
//				i++;
//			});
//			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý IV năm " + (String) cbxChonNam.getSelectedItem());
//			lblSoLuongTour.setText(String.valueOf(i +" tour"));
//			//
//			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ IV " +  (String) cbxChonNam.getSelectedItem());
//			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12"))+" VND"));
//
//			txtTitleTopTour.setText("Top sản phẩm đã bán trong quý IV năm " + (String) cbxChonNam.getSelectedItem());		
//
//			try {
//				loadTKTheoQuy4("10","11","12");
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (RemoteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		else if(o.equals(btnQuy3)) {
//			DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//
//			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý IV "+ (String) cbxChonNam.getSelectedItem());
//			try {
//				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"7","8","9") +" vé"));
//			} catch (RemoteException e4) {
//				// TODO Auto-generated catch block
//				e4.printStackTrace();
//			}
//
//			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý III năm " + (String) cbxChonNam.getSelectedItem());
//			try {
//				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"7","8","9")) +" VND"));
//			} catch (RemoteException e3) {
//				// TODO Auto-generated catch block
//				e3.printStackTrace();
//			};
//
//			try {
//				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"7","8","9");
//			} catch (RemoteException e2) {
//				e2.printStackTrace();
//			}
//			i = 0;
//			loadListTourMapQuy.forEach((a,b) -> {
//				i++;
//			});
//			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý III năm " + (String) cbxChonNam.getSelectedItem());
//			lblSoLuongTour.setText(String.valueOf(i +" tour"));
//			//
//			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ III " +  (String) cbxChonNam.getSelectedItem());
//			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"7","8","9"))+" VND"));
//			//
//			txtTitleTopTour.setText("Top sản phẩm đã bán trong quý III năm " + (String) cbxChonNam.getSelectedItem());		
//
//			try {
//				loadTKTheoQuy4("7","8","9");
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (RemoteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		else if(o.equals(btnQuy2)) {
//			DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//
//			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý IV "+ (String) cbxChonNam.getSelectedItem());
//			try {
//				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6") +" vé"));
//			} catch (RemoteException e4) {
//				// TODO Auto-generated catch block
//				e4.printStackTrace();
//			}
//
//			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý II năm" + (String) cbxChonNam.getSelectedItem());
//			try {
//				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6")) +" VND"));
//			} catch (RemoteException e3) {
//				// TODO Auto-generated catch block
//				e3.printStackTrace();
//			};
//
//			try {
//				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"4","5","6");
//			} catch (RemoteException e2) {
//				e2.printStackTrace();
//			}
//			i = 0;
//			loadListTourMapQuy.forEach((a,b) -> {
//				i++;
//			});
//			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý II năm " + (String) cbxChonNam.getSelectedItem());
//			lblSoLuongTour.setText(String.valueOf(i +" tour"));
//			//
//			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ II " +  (String) cbxChonNam.getSelectedItem());
//			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6"))+" VND"));
//			//
//			txtTitleTopTour.setText("Top sản phẩm đã bán trong quý II năm " + (String) cbxChonNam.getSelectedItem());
//			//
//			try {
//				loadTKTheoQuy4("4","5","6");
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (RemoteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		else if(o.equals(btnQuy1)) {
//			DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//
//			txtTitleSoLuongVe.setText("Tổng số lượng vé đã bán trong quý I "+ (String) cbxChonNam.getSelectedItem());
//			try {
//				lblSoLuongVe.setText(String.valueOf(thongKe_DAO.SoLuongVeTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3") +" vé"));
//			} catch (RemoteException e4) {
//				// TODO Auto-generated catch block
//				e4.printStackTrace();
//			}
//
//			txtTitleTongDoanhThu.setText("Tổng doanh thu trong quý I năm " + (String) cbxChonNam.getSelectedItem());
//			try {
//				lblTongDoanhThu.setText(String.valueOf(formatter.format(thongKe_DAO.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3")) +" VND"));
//			} catch (RemoteException e3) {
//				// TODO Auto-generated catch block
//				e3.printStackTrace();
//			};
//
//			try {
//				loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),"1","2","3");
//				//									listTour = thongKe_DAO.thongKeTheoNgay(tuNgay,denNgay);
//			} catch (RemoteException e2) {
//				e2.printStackTrace();
//			}
//			i = 0;
//			loadListTourMapQuy.forEach((a,b) -> {
//				i++;
//			});
//			txtTittleSoLuongTour.setText("Số lượng tour đã hoàn thành trong quý I năm " + (String) cbxChonNam.getSelectedItem());
//			lblSoLuongTour.setText(String.valueOf(i +" tour"));
//			//
//			//			txtTitleDoanhThu.setText("Tá»•ng doanh thu trong quÃ½ I " +  (String) cbxChonNam.getSelectedItem());
//			//			lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3"))+" VND"));
//			//
//			//			txtTitleTopSP.setText("Top sáº£n pháº©m Ä‘Ã£ bÃ¡n trong quÃ½ I nÄƒm " + (String) cbxChonNam.getSelectedItem());
//			try {
//				loadTKTheoQuy4("1","2","3");
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (RemoteException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//	}
//
//
//	public void loadTK() throws SQLException, RemoteException {
//		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//		modeltable.setRowCount(0);
//		table.removeAll();
//		i =1;
//		loadListTourMap = thongKe_DAO.thongKeTheoNgay(tuNgay, denNgay);
//		loadListTourMap.entrySet().stream()
//		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		.forEach(data -> {
//			modeltable.addRow(new Object[] {i++,data.getKey().getTenTour(),formatter.format(data.getValue())+ "VND"});
//		});
//
//	}
//	public void loadTKTheoQuy4(String thang1, String thang2, String thang3) throws SQLException, RemoteException {
//		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
//		modeltable.setRowCount(0);
//		table.removeAll();
//		i =1;
//		loadListTourMapQuy = thongKe_DAO.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),thang1, thang2, thang3);
//		System.out.println(loadListTourMapQuy);
//		loadListTourMapQuy.entrySet().stream()
//		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//		.forEach(data -> {
//			modeltable.addRow(new Object[] {i++,data.getKey().getTenTour(),formatter.format(data.getValue())+ "VND"});
//		});
//	}
//}
//





