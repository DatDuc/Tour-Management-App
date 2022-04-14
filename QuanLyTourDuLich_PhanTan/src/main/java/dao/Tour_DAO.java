package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Tour;

public interface Tour_DAO extends Remote{
	public ArrayList<Tour> getalltbTour() throws RemoteException; 
	public ArrayList<Tour> getTourTheoMa(String maTour1) throws RemoteException;
	public ArrayList<Tour> DSTCoTheDatVe(LocalDate ngayHienTai) throws RemoteException;
	public boolean ThemTour(Tour tour) throws RemoteException;
	public boolean SuaTour(Tour tour) throws RemoteException;
	public int LayMaTourLonNhat() throws RemoteException;
	public Tour KiemTraCoTheChoHuongDanVien(String maTourMoi,String maHDV, Date ngayKH, Date ngayKT) throws RemoteException;
	public Tour LayTourTheoMaTour(String mt) throws RemoteException;
	public boolean XoaTour(String maTour) throws RemoteException;
	public ArrayList<String> tachChuoiTim(String chuoiTim) throws RemoteException;
	public ArrayList<Tour> TimTour(String nhapTour, boolean loai) throws RemoteException;
	
	public ArrayList<Tour> getTourTheoMaDiaDanh(String maDiaDanh) throws RemoteException;
	
	public ArrayList<Tour> LayHetTour() throws RemoteException;
	
	public void GuiEmail(Tour tour, String diaDanh, String loaiTour) throws RemoteException;
	
}
