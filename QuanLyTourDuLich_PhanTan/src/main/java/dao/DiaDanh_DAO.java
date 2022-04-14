package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.DiaDanh;

public interface DiaDanh_DAO extends Remote{
public List<DiaDanh> getalltbDiaDanh() throws RemoteException;
	
	public int layMaDiaDanhLonNhat()  throws RemoteException;
	
	public boolean themDiaDanh(DiaDanh diadanh) throws RemoteException;
	
	public boolean update(DiaDanh diadanh) throws RemoteException;
	
	public List<DiaDanh> timKiem(String maTinhThanh) throws RemoteException;
	
	
	
	
	
	
	
}
