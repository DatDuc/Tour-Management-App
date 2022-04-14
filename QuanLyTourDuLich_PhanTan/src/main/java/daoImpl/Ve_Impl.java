package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.Ve_DAO;
import entity.LoaiTour;
import entity.Ve;

public class Ve_Impl extends UnicastRemoteObject implements Ve_DAO{

	public EntityManager entityManager;
	public Ve_Impl() throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getEntityManager();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8165252437810484002L;

	@Override
	public ArrayList<Ve> getalltbVe() throws RemoteException {
		ArrayList<Ve> dsVe = new ArrayList<Ve>();
		String sql = "select * from Ves ";
		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Ve empl = entityManager.find(Ve.class, id);
			dsVe.add(empl);		
		}
		return dsVe;
	}

	@Override
	public ArrayList<Ve> getVeTheoMaTour(String maTour) throws RemoteException {
		ArrayList<Ve> dsVe = new ArrayList<Ve>();
		String sql = "select v.maVe,v.ngayDatVe, v.maKH,v.maNV,v.maTour from Ves v join Tours t on v.maTour = t.maTour join KhachHangs kh on v.maKH=kh.maKH join NhanViens nv on nv.maNV = v.maNV    where v.maTour = :x";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", maTour).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Ve empl = entityManager.find(Ve.class, id);
			dsVe.add(empl);		
		}
		return dsVe;
	}

	@Override
	public ArrayList<Ve> DanhSachVeTheoMaTour(String maTour) throws RemoteException {
		ArrayList<Ve> dsVe = new ArrayList<Ve>();
		String sql = "select * from Ves v where v.maTour = :x";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", maTour).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Ve empl = entityManager.find(Ve.class, id);
			dsVe.add(empl);		
		}
		return dsVe;
	}

	@Override
	public int LayMaVeLonNhat(String maTour) throws RemoteException {
		int ma =0;
		ArrayList<Ve> listVe = DanhSachVeTheoMaTour(maTour);
		if(listVe.size()>0)
			ma = listVe.size();
		return ma;
	}

	@Override
	public boolean ThemVe(Ve ve) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();

		try {
			tr.begin();
			entityManager.persist(ve);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return false;
	}

}
