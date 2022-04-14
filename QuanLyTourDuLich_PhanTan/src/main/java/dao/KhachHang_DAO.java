package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.KhachHang;

public interface KhachHang_DAO extends Remote{
	public KhachHang LayKhachHangTheoCMND(String cmndNhap) throws RemoteException;
	
	public List<KhachHang> LayHetKhachHang() throws RemoteException;
	
	public int LayMaKHLonNhat() throws RemoteException;
	
	public boolean themKH(KhachHang kh)  throws RemoteException;
	
	public boolean update(KhachHang kh) throws RemoteException;
	
	public KhachHang getKhachHangbyId(String ma) throws RemoteException;
	 
	public boolean xoaNV(String maKH) throws RemoteException;
	
	public List<KhachHang> timKiem(String value) throws RemoteException;
	
}
