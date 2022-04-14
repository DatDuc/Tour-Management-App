package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.HuongDanVien;

public interface HuongDanVien_DAO extends Remote{
	
	public List<HuongDanVien> LayToanBoHuongDanVien() throws RemoteException;
	
	public boolean themHDV(HuongDanVien hdv) throws RemoteException;
	
	public boolean updateHDV(HuongDanVien hdv) throws RemoteException;
	
	public HuongDanVien getHuongDanVienHDId(String ma) throws RemoteException;
	
	public boolean xoaHDV(String maNV) throws RemoteException;
	
	public List<HuongDanVien> timKiem(String value) throws RemoteException;
	
	public int LayMaHDVLonNhat() throws RemoteException;
	
	
}
