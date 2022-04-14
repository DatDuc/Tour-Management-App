package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.HuongDanVien_DAO;
import entity.HuongDanVien;
import entity.KhachHang;

public class HuongDanVien_Impl extends UnicastRemoteObject implements HuongDanVien_DAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6163583003534000196L;
	private EntityManager em;
	
	public HuongDanVien_Impl() throws RemoteException{
		em = HibernateUtil.getInstance().getEntityManager();
	}
	
	@Override
	public List<HuongDanVien> LayToanBoHuongDanVien() throws RemoteException {
		List<HuongDanVien> huongDanViens = new ArrayList<HuongDanVien>();
		List<?> list = em.createNativeQuery("select * from HuongDanViens").getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String id = (String) rs[0];
			HuongDanVien huongDanVien = em.find(HuongDanVien.class, id);
			huongDanViens.add(huongDanVien);
		}
		return huongDanViens;
	}

	@Override
	public boolean themHDV(HuongDanVien hdv) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(hdv);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

//	select * from HuongDanViens hdv where hdv.maHuongDanVien = 'HDV100'
	@Override
	public boolean updateHDV(HuongDanVien hdv) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		List<HuongDanVien> list = em.createNativeQuery("select * from HuongDanViens hdv where hdv.maHuongDanVien = :x", HuongDanVien.class)
				.setParameter("x", hdv.getMaHuongDanVien())
				.getResultList();
		
		for(HuongDanVien huongDanVien : list) {
			try {
				tr.begin();
				huongDanVien.setTenHuongDanVien(hdv.getTenHuongDanVien());
				huongDanVien.setEmail(hdv.getEmail());
				huongDanVien.setDiaChi(hdv.getDiaChi());
				huongDanVien.setSoDT(hdv.getSoDT());
				huongDanVien.setNgayVaoLam(hdv.getNgayVaoLam());
				huongDanVien.setCmnd(hdv.getCmnd());
				huongDanVien.setGioiTinh(hdv.isGioiTinh());
				huongDanVien.setTinhTrang(hdv.isTinhTrang());
				em.merge(huongDanVien);
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
	public HuongDanVien getHuongDanVienHDId(String ma) throws RemoteException {
		HuongDanVien huongDanVien = null;
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			huongDanVien =  em.find(HuongDanVien.class, ma);
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return huongDanVien;
	}

	@Override
	public boolean xoaHDV(String maNV) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

//	select * from HuongDanViens where maHuongDanVien like '%a%' or tenHuongDanVien like '%a%' or cmnd like '%a%' or diaChi like '%a%' or email like '%a%' or soDT like '%a%'
	@Override
	public List<HuongDanVien> timKiem(String value) throws RemoteException {
		List<HuongDanVien> huongDanViens = new ArrayList<HuongDanVien>();
		List<?> list = em.createNativeQuery("select * from HuongDanViens where maHuongDanVien like '%"+value+"%' "
				+ "or tenHuongDanVien like '%"+value+"%' or cmnd like '%"+value+"%' or diaChi like '%"+value+"%' "
						+ "or email like '%"+value+"%' or soDT like '%"+value+"%'")
				.getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String id = (String) rs[0];
			HuongDanVien huongDanVien = em.find(HuongDanVien.class, id);
			huongDanViens.add(huongDanVien);
		}
		return huongDanViens;
	}

	@Override
	public int LayMaHDVLonNhat() throws RemoteException {
		int maHDVmax = 0;
		List<?> list = em.createNativeQuery("select * from HuongDanViens").getResultList();
		for(Object object : list) {
			Object[] rs = (Object[]) object;
			String id = (String) rs[0];
			String maHDV = id.substring(3);
			if(maHDVmax < Integer.parseInt(maHDV)) {
				maHDVmax = Integer.parseInt(maHDV);
			}
		}	
		return maHDVmax;
	}

}
