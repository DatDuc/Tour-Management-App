package ui;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import entity.Tour;


public class TourTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] chuoi1 = {"maTour","tenTour","giaTour","soLuongNguoi","ngayKhoiHanh","ngayKetThuc","moTa","tinhTrang","hinhAnh"};
	private java.util.List<Tour> dsTour = new LinkedList<>();
	public TourTableModel(String[] chuoi1, java.util.List<Tour> dsTour) {
		this.chuoi1 = chuoi1;
		this.dsTour = dsTour;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsTour.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return chuoi1.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Tour tour = dsTour.get(rowIndex);
		switch (columnIndex) {
		case 0: return tour.getMaTour();
		case 1: return tour.getTenTour();
		case 2: return tour.getGiaTour();
		case 3: return tour.getSoLuongNguoi();
		case 4: return tour.getNgayKhoiHanh();
		case 5: return tour.getNgayKetThuc();
		case 6: return tour.getMoTa();
		case 7: return tour.isTinhTrang();
		case 8: return tour.getHinhAnh();
		}
		return tour;
	}
	//Tieu de cột cho Table
		@Override
		public String getColumnName(int column) {
			return chuoi1[column];
		}
}
