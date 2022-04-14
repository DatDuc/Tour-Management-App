package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.NhanVien_DAO;
import entity.DiaDanh;
import entity.NhanVien;

public class NhanVien_Impl extends UnicastRemoteObject implements NhanVien_DAO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4858047448715267936L;
	public EntityManager entityManager;

	public NhanVien_Impl() throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getEntityManager();
	}


	@Override
		public NhanVien LayNhanVienTheoMa(String mnv) throws RemoteException {
			NhanVien nhanVien = null;
			EntityTransaction tr = entityManager.getTransaction();
			try {
				tr.begin();
				nhanVien = entityManager.find(NhanVien.class, mnv);
				tr.commit();
			} catch (Exception e) {
				e.printStackTrace();
				tr.rollback();
			}
			return nhanVien;
		}

	@Override
	public boolean themNV(NhanVien nv) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();
		try {
			tr.begin();
			entityManager.persist(nv);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	@Override
	public boolean update(NhanVien nv) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();
		List<NhanVien> nhanViens = entityManager.createNativeQuery("select * from NhanViens where maNV = :x", 
				NhanVien.class).setParameter("x", nv.getMaNV()).getResultList();
		for(NhanVien nhanVien : nhanViens) {
			try {
				tr.begin();
				nhanVien.setTenNV(nv.getTenNV());
				nhanVien.setCmnd(nv.getCmnd());	
				nhanVien.setEmail(nv.getEmail());
				nhanVien.setSoDT(nv.getSoDT());
				nhanVien.setDiaChi(nv.getDiaChi());
				nhanVien.setGioiTinh(nv.isGioiTinh());
				nhanVien.setTinhTrang(nv.isTinhTrang());
				nhanVien.setNgayVaoLam(nv.getNgayVaoLam());				
				entityManager.merge(nhanVien);
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
	public List<NhanVien> getListMaNV() throws RemoteException {
		List<NhanVien> listNhanVien = new ArrayList<NhanVien>();
		String sql = "select * from NhanViens";

		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			NhanVien empl = entityManager.find(NhanVien.class, id);
			listNhanVien.add(empl);		
		}
		return listNhanVien;
	}

	@Override
	public List<NhanVien> timKiem(String tuKhoa) throws RemoteException {
		List<NhanVien> listNhanVien = new ArrayList<NhanVien>();
		String sql = "SELECT * from NhanViens  where cmnd like CONCAT('%', ?, '%') or tenNV like CONCAT('%', ?, '%')";

		List<?> temp = entityManager.createNativeQuery(sql)
				.setParameter(1, tuKhoa)
				.setParameter(2, tuKhoa)
				.getResultList();
		for (Object o : temp) {		

			Object[] rs = (Object[]) o;		
			
			String id = (String) rs[0];
			NhanVien nhanVien = entityManager.find(NhanVien.class, id);
			listNhanVien.add(nhanVien);		
		}
		return listNhanVien;
	}
	
	@Override
	public int layMaNhanVienLonNhat() throws RemoteException {
		int mNVln = 0;
		String sql = "select * from NhanViens";

		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			String maNV = id.substring(2);
			if(mNVln < Integer.parseInt(maNV)) {
				mNVln  = Integer.parseInt(maNV);
			}		

		}
		return mNVln;
	}
}
