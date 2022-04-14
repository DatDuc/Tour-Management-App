package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import Util.HibernateUtil;
import dao.LoaiTour_DAO;
import entity.DiaDanh;
import entity.LoaiTour;


public class LoaiTour_Impl  extends UnicastRemoteObject implements LoaiTour_DAO{
	public EntityManager entityManager;
	public LoaiTour_Impl() throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getEntityManager();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3689677107970209506L;

	@Override
	public ArrayList<LoaiTour> layHetLoaiTour() throws RemoteException {
		ArrayList<LoaiTour> loaiTours = new ArrayList<LoaiTour>();
		String sql = "select * from LoaiTours";
		
		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			LoaiTour empl = entityManager.find(LoaiTour.class, id);
			loaiTours.add(empl);		
		}
		return loaiTours;
	}

}
