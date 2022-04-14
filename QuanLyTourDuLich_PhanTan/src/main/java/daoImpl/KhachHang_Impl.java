package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.KhachHang_DAO;
import entity.HuongDanVien;
import entity.KhachHang;

public class KhachHang_Impl extends UnicastRemoteObject implements KhachHang_DAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -648880564995108536L;
	private EntityManager em;
	
	public KhachHang_Impl() throws RemoteException{
		em = HibernateUtil.getInstance().getEntityManager();
	}

//	select * from KhachHangs where cmnd = '1222222222'
	@Override
	public KhachHang LayKhachHangTheoCMND(String cmndNhap) throws RemoteException {
		KhachHang khachHang = null;
		try {
			khachHang = (KhachHang) em.createNativeQuery("select * from KhachHangs where cmnd = :x", KhachHang.class)
					.setParameter("x", cmndNhap)
					.getSingleResult();
		} catch (Exception e) {
			if(khachHang!=null)
			e.printStackTrace();
		}
		return khachHang;
	}

//	select * from KhachHangs
	@Override
	public List<KhachHang> LayHetKhachHang() throws RemoteException {
		List<KhachHang> khachHangs = new ArrayList<KhachHang>();
		List<?> list = em.createNativeQuery("select * from KhachHangs").getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String id = (String) rs[0];
			KhachHang khachHang = em.find(KhachHang.class, id);
			khachHangs.add(khachHang);
		}
		return khachHangs;
	}

	@Override
	public int LayMaKHLonNhat() throws RemoteException {
		int maKHmax = 0;
		List<?> list = em.createNativeQuery("select * from KhachHangs").getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String id = (String) rs[0];
			String maKH = id.substring(2);
			if(maKHmax < Integer.parseInt(maKH)) {
				maKHmax = Integer.parseInt(maKH);
			}
		}	
		return maKHmax;
	}

	@Override
	public boolean themKH(KhachHang kh) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(kh);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

//	select * from KhachHangs where maKH = 'KH0100'
	@Override
	public boolean update(KhachHang kh) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		List<KhachHang> list = em.createNativeQuery("select * from KhachHangs where maKH = :x", KhachHang.class)
				.setParameter("x", kh.getMaKH())
				.getResultList();
		for(KhachHang khachHang : list) {
			try {
				tr.begin();
				khachHang.setTenKH(kh.getTenKH());
				khachHang.setEmail(kh.getEmail());
				khachHang.setDiaChi(kh.getDiaChi());
				khachHang.setSoDT(kh.getSoDT());
				khachHang.setCmnd(kh.getCmnd());
				khachHang.setGioiTinh(kh.isGioiTinh());
				em.merge(khachHang);
				tr.commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				tr.rollback();
			}
		}
		return false;
	}

	@Override
	public KhachHang getKhachHangbyId(String ma) throws RemoteException {
		KhachHang khachHang = null;
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			khachHang =  em.find(KhachHang.class, ma);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return khachHang;
	}

	@Override
	public boolean xoaNV(String maKH) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

//	select * from KhachHangs where tenKH like '%a%' or maKH like '%a%'
	@Override
	public List<KhachHang> timKiem(String value) throws RemoteException {
		List<KhachHang> khachHangs  = new ArrayList<KhachHang>();
		List<?> list = em.createNativeQuery("select * from KhachHangs where tenKH like '%"+value+"%' or maKH like '%"+value+"%' "
				+ "or cmnd like '%"+value+"%'")
				.getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String id = (String) rs[0];
			KhachHang khachHang = em.find(KhachHang.class, id);
			khachHangs.add(khachHang);
		}
		return khachHangs;
	}

}
