package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.TaiKhoan;

public interface TaiKhoan_DAO extends Remote{
	public List<TaiKhoan> DocTuBang() throws RemoteException;
	public TaiKhoan Login(String username, String password) throws RemoteException;
	public boolean Create (TaiKhoan tk) throws RemoteException;
	public boolean Delete(String ID) throws RemoteException;
	public boolean Update (String maNV) throws RemoteException;
}
