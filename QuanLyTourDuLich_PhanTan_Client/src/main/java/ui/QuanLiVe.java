package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import config.config;
import dao.DiaDanh_DAO;
import dao.Tour_DAO;
import dao.Ve_DAO;
import entity.DiaDanh;

import entity.Tour;
import entity.Ve;

public class QuanLiVe extends JPanel implements ActionListener, MouseListener {
	String conf = config.conf;
	public static QuanLiVe qlVe;
	JTextField txtTim;
	JButton btnTim;
	JPanel pnCenter;
	DefaultMutableTreeNode rootDiaDanh;
	JTree tree;
	DefaultMutableTreeNode nodeTinhThanh;
	DefaultMutableTreeNode nodeDiaDanh;
	DefaultMutableTreeNode nodeTour;
	DefaultTableModel modeltable;
	DefaultTableModel modeltable1;
	public JTable table;
	JTable table1;
	JPanel pnTree;
	JPanel pnTab2;
	private DiaDanh_DAO diaDanh_DAO;
	private Tour_DAO tour_DAO;
	private Ve_DAO ve_DAO;
	List<DiaDanh> listDD;
	ArrayList<Tour> listTour;
	ArrayList<Ve> listVe;
	ArrayList<Tour> listTourKhaDung; // Danh sách tour có thể đặt
	Tour tour;
	DiaDanh diadanh = null;
	JScrollPane sc;
	DefaultTableCellDiaDanh cellDiaDanh;

	public QuanLiVe() throws MalformedURLException, RemoteException, NotBoundException {
		qlVe = this;

		diaDanh_DAO = (DiaDanh_DAO) Naming.lookup(conf+"/diaDanh_DAO");
		tour_DAO = (Tour_DAO) Naming.lookup(conf+"/tour_DAO");
		ve_DAO = (Ve_DAO) Naming.lookup(conf+"/ve_DAO");
		// Vinh
		listTour = tour_DAO.LayHetTour();
		listTourKhaDung = tour_DAO.DSTCoTheDatVe(LocalDate.now());
		//
		Border blackline = BorderFactory.createLineBorder(Color.gray);
		setBorder(blackline);
		setLayout(new BorderLayout());
		setBackground(new Color(230, 247, 255));

		JTabbedPane tab = new JTabbedPane();

		JPanel pnTab1 = new JPanel();
		pnTab1.setBackground(new Color(230, 247, 255));
		pnTab1.setLayout(new BorderLayout());
		pnTab2 = new JPanel();
		JScrollPane sp1 = new JScrollPane(pnTab1);

		tab.add(sp1, "Đặt vé");
		tab.add(pnTab2, "Quản lí vé");
		pnTab2.setBackground(Color.WHITE);
		add(tab, BorderLayout.CENTER);

		JPanel pnTim = new JPanel();
		pnTim.setBackground(new Color(230, 247, 255));
		JLabel lblTim = new JLabel("Tìm kiếm");
		lblTim.setIcon(new ImageIcon("Icon/find1.png"));
		txtTim = new JTextField(25);
		btnTim = new JButton("Tìm kiếm");
		pnTim.add(lblTim);
		pnTim.add(txtTim);
		pnTim.add(btnTim);
		pnTab1.add(pnTim, BorderLayout.NORTH);

		pnCenter = new JPanel();
		pnCenter.setLayout(new WrapLayout());
		pnCenter.setBackground(new Color(230, 247, 255));
		// Vinh
		TaiTourLen();
		//
		pnTab1.add(pnCenter, BorderLayout.CENTER);
		btnTim.addActionListener(this);

		// tab2
		pnTab2.setLayout(new BorderLayout());
		// UIManager.put("Tree.rendererFillBackground", false);
		pnTab2.setBackground(new Color(230, 247, 255));

		pnTree = new JPanel();
		// pnTree.setBackground(new Color(230, 247, 255));
		pnTree.setLayout(new BorderLayout());
		// WEST
		// NodeCha
		rootDiaDanh = new DefaultMutableTreeNode("Các địa danh");
		tree = new JTree(rootDiaDanh);
		// tree.setBackground(new Color(230, 247, 255));
		sc = new JScrollPane(tree);
		sc.setPreferredSize(new Dimension(200, 0));

		// TaoHinh
		ImageIcon leafIcon = new ImageIcon("Icon/start.png");
		ImageIcon openIcon = new ImageIcon("Icon/open2.png");
		ImageIcon closedIcon = new ImageIcon("Icon/close.png");
		if (leafIcon != null) {
			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
			renderer.setClosedIcon(closedIcon);
			renderer.setOpenIcon(openIcon);
			renderer.setLeafIcon(leafIcon);
			tree.setCellRenderer(renderer);
		}
		// NodeDiaDanh
		nodeDiaDanh = new DefaultMutableTreeNode("địa danh");
		listDD = diaDanh_DAO.getalltbDiaDanh();
		for (DiaDanh p1 : listDD) {
			nodeDiaDanh = new DefaultMutableTreeNode(p1);
			rootDiaDanh.add(nodeDiaDanh);

			// NodeTour
			diadanh = (DiaDanh) nodeDiaDanh.getUserObject();
			listTour = tour_DAO.getTourTheoMaDiaDanh(diadanh.getMaDiaDanh());
			for (Tour tour : listTour) {
				nodeTour = new DefaultMutableTreeNode(tour);
				nodeDiaDanh.add(nodeTour);
			}
		}
		tree.expandRow(0);// Thái - 2-6
		pnTree.add(sc, BorderLayout.WEST);
		// CENTER
		String[] chuoi = { "Mã vé", "Ngày đặt vé", "Tên tour", "Tên khách hàng", "Tên nhân viên" };
		modeltable = new DefaultTableModel(chuoi, 0);
		table = new JTable(modeltable);
		JScrollPane sc1 = new JScrollPane(table);

		pnTree.add(sc1, BorderLayout.CENTER);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sc, sc1);
		pnTree.add(split, BorderLayout.CENTER);
		pnTab2.add(pnTree, BorderLayout.CENTER);

		// SỰ KIỆN JTREE
		tree.addMouseListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnTim)) {
			ArrayList<Tour> tourTimDuoc = null;
			try {
				tourTimDuoc = tour_DAO.TimTour(txtTim.getText().toString().trim().toLowerCase(), false);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (tourTimDuoc.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy!");
			} else {
				try {
					TaiTourTimKiem(tourTimDuoc);
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
			}
		}

	}

	// Vinh - 26-5
	public void TaiTourLen() {
		try {
			pnCenter.removeAll();
			pnCenter.revalidate();
			listTourKhaDung = tour_DAO.DSTCoTheDatVe(LocalDate.now());
			for (Tour tour : listTourKhaDung) {
				JPanel pnItem = new TourTrongQuanLyVe(tour);
				pnCenter.add(pnItem);
			}
			pnCenter.revalidate();
			// JOptionPane.showMessageDialog(this, listTourKhaDung.size());
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, e2);
		}
	}

	// Vinh -28-5
	private void TaiTourTimKiem(ArrayList<Tour> tourTimDuoc) throws MalformedURLException, RemoteException, NotBoundException {
		pnCenter.removeAll();
		pnCenter.revalidate();
		for (Tour tour : tourTimDuoc) {
			JPanel pnItem = new TourTrongQuanLyVe(tour);
			pnCenter.add(pnItem);
			pnCenter.revalidate();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode nodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (nodeSelected != null && nodeSelected.getLevel() == 1) {
			pnTree.removeAll();
			pnTree.revalidate();
			String[] chuoi1 = { "Mã tour", "Tên tour", "Giá tour", "Ngày khởi hành", "Ngày kết thúc" };
			modeltable1 = new DefaultTableModel(chuoi1, 0);
			table1 = new JTable(modeltable1);
			table1.getColumnModel().getColumn(1).setPreferredWidth(250);

			DefaultTableCellDiaDanh cellDiaDanh = new DefaultTableCellDiaDanh();
			table1.getColumnModel().getColumn(4).setCellRenderer(cellDiaDanh.tableCellRenderer);
			table1.getColumnModel().getColumn(3).setCellRenderer(cellDiaDanh.tableCellRenderer);

			JScrollPane sc2 = new JScrollPane(table1);
			// sc.setPreferredSize(new Dimension(200, 0));
			pnTree.add(sc2, BorderLayout.CENTER);
			pnTree.add(sc, BorderLayout.WEST);
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sc, sc2);
			pnTree.add(split, BorderLayout.CENTER);
			pnTree.revalidate();
			pnTree.repaint();

			diadanh = (DiaDanh) nodeSelected.getUserObject();
			modeltable1.setRowCount(0);
			try {
				listTour = tour_DAO.getTourTheoMaDiaDanh(diadanh.getMaDiaDanh());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (Tour tour : listTour) {
				modeltable1.addRow(new Object[] { tour.getMaTour(), tour.getTenTour(), tour.getGiaTour(),
						tour.getNgayKhoiHanh(), tour.getNgayKetThuc() });
			}
		}
		if (nodeSelected != null && nodeSelected.getLevel() == 2) {
			pnTree.removeAll();
			pnTree.revalidate();
			String[] chuoi = { "Mã vé", "Ngày đặt vé", "Tên tour", "Tên khách hàng", "Tên nhân viên" };
			modeltable = new DefaultTableModel(chuoi, 0);
			table = new JTable(modeltable);
			table.getColumnModel().getColumn(2).setPreferredWidth(160);
			DefaultTableCellTour celltour = new DefaultTableCellTour();
			// celltour.getTableCellRendererComponent(table,
			// table.getColumnModel().getColumn(1), true, true, 10, 15);
			// table.getColumnModel().getColumn(1).setCellRenderer(new
			// DefaultTableCellTour());
			table.getColumnModel().getColumn(1).setCellRenderer(celltour.tableCellRenderer);
			table.setAutoCreateRowSorter(true);

			JScrollPane sc1 = new JScrollPane(table);
			pnTree.add(sc1, BorderLayout.CENTER);
			pnTree.add(sc, BorderLayout.WEST);
			// sc.setPreferredSize(new Dimension(200, 0));
			JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sc, sc1);
			pnTree.add(split, BorderLayout.CENTER);
			pnTree.revalidate();
			pnTree.repaint();

			tour = (Tour) nodeSelected.getUserObject();
			modeltable.setRowCount(0);
			try {
				listVe = ve_DAO.getVeTheoMaTour(tour.getMaTour());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (Ve ve : listVe) {
				modeltable.addRow(new Object[] { ve.getMaVe(), ve.getNgayDatVe(), ve.getTour().getTenTour(),
						ve.getKhachHang().getTenKH(), ve.getNhanVien().getTenNV() });
			}
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

}
