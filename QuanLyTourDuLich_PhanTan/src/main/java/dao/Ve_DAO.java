package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.Ve;

public interface Ve_DAO extends Remote{
	public ArrayList<Ve> getalltbVe() throws RemoteException;
	public ArrayList<Ve> getVeTheoMaTour(String maTour) throws RemoteException;
	public ArrayList<Ve> DanhSachVeTheoMaTour(String maTour) throws RemoteException;
	public int LayMaVeLonNhat(String maTour) throws RemoteException;
	public boolean ThemVe(Ve ve) throws RemoteException;
}
