package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

public class TaiKhoan_Impl extends UnicastRemoteObject implements TaiKhoan_DAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3111304828983788972L;

	private EntityManager em;
	
	public TaiKhoan_Impl() throws RemoteException{
		em = HibernateUtil.getInstance().getEntityManager();
	}
	
	
	@Override
	public List<TaiKhoan> DocTuBang() throws RemoteException {
		List<TaiKhoan> taiKhoans = new ArrayList<TaiKhoan>();
		List<?> list = em.createNativeQuery("select * from TaiKhoans").getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String maNV = (String) rs[0];
			TaiKhoan taiKhoan = em.find(TaiKhoan.class, maNV);
			taiKhoans.add(taiKhoan);
		}
		return taiKhoans;
	}

//	select * from TaiKhoans where maNV = 'NV0007' and matKhau = '123456'
	@Override
	public TaiKhoan Login(String username, String password) throws RemoteException {
		TaiKhoan taiKhoan;
		List<TaiKhoan> list = em.createNativeQuery("select * from TaiKhoans where maNV = :x and matKhau = :y")
				.setParameter("x", username)
				.setParameter("y", password)
				.getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String maNV = (String) rs[0];
			String mk = (String) rs[1];
			taiKhoan =  new TaiKhoan(maNV, mk);
			return taiKhoan;
		}
		return null;
	}

	@Override
	public boolean Create(TaiKhoan tk) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(tk);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	@Override
	public boolean Delete(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Update(String maNV) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
