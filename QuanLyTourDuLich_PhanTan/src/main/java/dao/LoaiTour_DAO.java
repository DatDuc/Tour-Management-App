package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.LoaiTour;

public interface LoaiTour_DAO extends Remote{
	public ArrayList<LoaiTour> layHetLoaiTour() throws RemoteException;
}
