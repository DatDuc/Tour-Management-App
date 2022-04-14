package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.DiaDanh_DAO;
import entity.DiaDanh;

public class DiaDanh_Impl extends UnicastRemoteObject implements DiaDanh_DAO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4088140472952395835L;
	public EntityManager entityManager;

	public DiaDanh_Impl() throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getEntityManager();
	}

	/**
	 * Lấy hết tất cả địa danh
	 */
	@Override
	public List<DiaDanh> getalltbDiaDanh() throws RemoteException {
		List<DiaDanh> listDiaDanh = new ArrayList<DiaDanh>();
		String sql = "select * from DiaDanhs";

		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			DiaDanh empl = entityManager.find(DiaDanh.class, id);
			listDiaDanh.add(empl);		
		}
		return listDiaDanh;
	}

	/**
	 * Lấy mã địa danh lớn nhất
	 */
	@Override
	public int layMaDiaDanhLonNhat() throws RemoteException {
		int mddln = 0;
		String sql = "select * from DiaDanhs";

		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			String maDD = id.substring(2);
			if(mddln < Integer.parseInt(maDD)) {
				mddln  = Integer.parseInt(maDD);
			}		

		}
		return mddln;
	}

	/**
	 * Thêm địa danh mới
	 */
	@Override
	public boolean themDiaDanh(DiaDanh diadanh) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();

		try {
			tr.begin();
			entityManager.persist(diadanh);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return false;
	}

	@Override
	public boolean update(DiaDanh diadanh) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();
		List<DiaDanh> diaDanhs = entityManager.createNativeQuery("select * from DiaDanhs where maDiaDanh = :x", 
				DiaDanh.class).setParameter("x", diadanh.getMaDiaDanh()).getResultList();
		for(DiaDanh diaDanh : diaDanhs) {
			try {
				tr.begin();
				diaDanh.setTenDiaDanh(diadanh.getTenDiaDanh());
				diaDanh.setMoTa(diadanh.getMoTa());	//Phải test đầy đủ mới sửa đc
				diaDanh.setTinhThanh(diadanh.getTinhThanh());
				entityManager.merge(diaDanh);
				tr.commit();
				return true;
			}catch (RuntimeException e) {
				tr.rollback();
				throw e;
			}
		}
		return false;
	}

	@Override
	public List<DiaDanh> timKiem(String maTinhThanh) throws RemoteException {
		List<DiaDanh> listDiaDanh = new ArrayList<DiaDanh>();
		String sql = "select * from DiaDanhs where tinhThanh LIKE CONCAT('%', ?, '%')";

		List<?> temp = entityManager.createNativeQuery(sql)
				.setParameter(1, maTinhThanh)
				.getResultList();

		for (Object o : temp) {		

			Object[] rs = (Object[]) o;		
			
			String id = (String) rs[0];
			DiaDanh diaDanh = entityManager.find(DiaDanh.class, id);
			listDiaDanh.add(diaDanh);		
		}
		return listDiaDanh;
	}



}
