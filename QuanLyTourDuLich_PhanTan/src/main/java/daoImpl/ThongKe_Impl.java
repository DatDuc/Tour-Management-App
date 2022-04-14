package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import Util.HibernateUtil;

import dao.ThongKe_DAO;
import entity.DiaDanh;
import entity.Tour;

public class ThongKe_Impl extends UnicastRemoteObject implements ThongKe_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5621192454871484125L;
	public EntityManager entityManager;

	public ThongKe_Impl() throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getEntityManager();
	}

	@Override
	public int tongSoLuongVeTheoNgayChon(String tuNgay, String denNgay) throws RemoteException {
		int tong = 0;
		String sql = "SELECT COUNT(maVe) FROM Ves where ngayDatVe > ? and ngayDatVe < ?";

		List<?> temp = entityManager.createNativeQuery(sql).setParameter(1, tuNgay).setParameter(2, denNgay).getResultList();

		for (Object o : temp) {		
			tong =   (int) o;
		}
		return tong;
	}

	@Override
	public double TongDTtheoNgayChon(String tuNgay, String denNgay) throws RemoteException {
		double tong = 0;
		String sql = "Select SUM(giaTour) from Ves a join Tours b on a.maTour = b.maTour where ngayDatVe > ? and ngayDatVe < ?";

		List<?> temp = entityManager.createNativeQuery(sql).setParameter(1, tuNgay).setParameter(2, denNgay).getResultList();

		for (Object o : temp) {		
			tong =   (double) o;
		}
		return tong;
	}

	@Override
	public int SoLuongVeTheoQuy(String nam, String thang1, String thang2, String thang3) throws RemoteException {
		int tong = 0;
		String sql = "select COUNT(maVe) "
				+ "from Ves where YEAR(ngayDatVe) = ? AND ( MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ?)";

		List<?> temp = entityManager.createNativeQuery(sql)
				.setParameter(1, nam)
				.setParameter(2, thang1)
				.setParameter(3, thang2)
				.setParameter(4, thang3)
				.getResultList();

		for (Object o : temp) {		
			tong =   (int) o;
		}
		return tong;
	}

	@Override
	public double DoanhThuTheoQuy(String nam, String thang1, String thang2, String thang3) throws RemoteException {
		double tong = 0;
		String sql = "select  COALESCE(sum(giaTour),0) from Ves v join Tours t on v.maTour=t.maTour "
				+ "where YEAR(ngayDatVe) = ? AND ( MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ?)";

		List<?> temp = entityManager.createNativeQuery(sql)
				.setParameter(1, nam)
				.setParameter(2, thang1)
				.setParameter(3, thang2)
				.setParameter(4, thang3)
				.getResultList();

		for (Object o : temp) {		
			tong =   (double) o;
		}
		return tong;
	}

	@Override
	public Map<Tour, Double> thongKeTheoNgay(String tuNgay, String denNgay) throws RemoteException {

		Map<Tour, Double> map = new HashMap<Tour, Double>();

		String sqlString = "select  t.maTour, t.tenTour, TONGTIEN = sum(giaTour) from Ves v join Tours t on v.maTour = t.maTour "
				+ "where ngayDatVe > ? and ngayDatVe < ? "
				+ "group by t.maTour,t.tenTour, t.giaTour "
				+ "order by TONGTIEN desc";
		List<?> temp = entityManager.createNativeQuery(sqlString)
				.setParameter(1, tuNgay)
				.setParameter(2, denNgay)
				.getResultList();

		for(Object o : temp) {
			Object[] rs = (Object[]) o;
			String id = (String) rs[0];
			Tour tour = entityManager.find(Tour.class, id);
			map.put(tour, (double) rs[2]);
		}
		System.out.println(map);
		return map;
	}

	@Override
	public Map<Tour, Double> thongKeSanPhamTheoQuy4(String nam, String thang1, String thang2, String thang3) throws RemoteException {

		Map<Tour, Double> map = new HashMap<Tour, Double>();

		String sql = "select t.maTour, t.tenTour , TONGTIEN = sum(t.giaTour) from Ves v join Tours t on v.maTour = t.maTour "
				+ "where YEAR(ngayDatVe) = ? AND ( MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ?) "
				+ "group by t.maTour,t.tenTour, t.giaTour "
				+ "order by TONGTIEN desc ";
		List<?> temp = entityManager.createNativeQuery(sql)
				.setParameter(1, nam)
				.setParameter(2, thang1)
				.setParameter(3, thang2)
				.setParameter(4, thang3)
				.getResultList();

		for(Object o : temp) {
			Object[] rs = (Object[]) o;
			String id = (String) rs[0];
			Tour tour = entityManager.find(Tour.class, id);
			map.put(tour, (double) rs[2]);
		}
		return map;
	}

	//	@Override
	//	public List<Tour> thongKeSanPhamTheoQuy4(String nam, String thang1, String thang2, String thang3)
	//			throws RemoteException {
	//		List<Tour> listTour = new ArrayList<Tour>();
	//		String sql = "select t.maTour, t.tenTour , SUM(t.giaTour) as 'TONGTIEN' from Ves v join Tours t on v.maTour = t.maTour "
	//				+ "where YEAR(ngayDatVe) = ? AND ( MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ? OR MONTH(ngayDatVe) = ?) "
	//				+ "group by t.maTour,t.tenTour, t.giaTour "
	//				+ "order by TONGTIEN desc ";
	//		
	//		List<?> temp = entityManager.createNativeQuery(sql)
	//				.setParameter(1, nam)
	//				.setParameter(2, thang1)
	//				.setParameter(3, thang2)
	//				.setParameter(4, thang3)
	//				.getResultList();
	//		
	//		for (Object o : temp) {		
	//			Object[] rs = (Object[]) o;				
	//			String id = (String) rs[0];
	//			Tour tour = entityManager.find(Tour.class, id);
	//			listTour.add(tour);		
	//		}
	//		return listTour;
	//	}


}
