package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.NhanVien;

public interface NhanVien_DAO extends Remote{
	public NhanVien LayNhanVienTheoMa(String mnv) throws RemoteException;
	public boolean themNV(NhanVien nv) throws RemoteException;
	
	public boolean update(NhanVien nv) throws RemoteException;
	
	public List<NhanVien> getListMaNV() throws RemoteException;
	
	
	public List<NhanVien> timKiem(String cmnd) throws RemoteException;
	
	public int layMaNhanVienLonNhat()  throws RemoteException;
	
}
