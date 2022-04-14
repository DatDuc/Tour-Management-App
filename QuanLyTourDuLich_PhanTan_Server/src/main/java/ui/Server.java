package ui;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.time.LocalDateTime;

import dao.DiaDanh_DAO;
import dao.HuongDanVien_DAO;
import dao.KhachHang_DAO;
import dao.LoaiTour_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import dao.ThongKe_DAO;
import dao.Tour_DAO;
import dao.Ve_DAO;
import daoImpl.DiaDanh_Impl;
import daoImpl.HuongDanVien_Impl;
import daoImpl.KhachHang_Impl;
import daoImpl.LoaiTour_Impl;
import daoImpl.NhanVien_Impl;
import daoImpl.TaiKhoan_Impl;
import daoImpl.ThongKe_Impl;
import daoImpl.Tour_Impl;
import daoImpl.Ve_Impl;



public class Server {

	public static void main(String[] args) {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		
		try {
			String config = "rmi://DESKTOP-O44ASRI:9999";
			DiaDanh_DAO diaDanh_DAO = new DiaDanh_Impl();
			HuongDanVien_DAO huongDanVien_DAO = new HuongDanVien_Impl();
			KhachHang_DAO khachHang_DAO = new KhachHang_Impl();
			LoaiTour_DAO loaiTour_DAO = new LoaiTour_Impl();
			NhanVien_DAO nhanVien_DAO = new NhanVien_Impl();
			TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_Impl();
			ThongKe_DAO thongKe_DAO = new ThongKe_Impl();
			Tour_DAO tour_DAO = new Tour_Impl();
			Ve_DAO ve_DAO = new Ve_Impl();
			
			LocateRegistry.createRegistry(9999);
			Naming.bind(config+"/diaDanh_DAO", diaDanh_DAO);
			Naming.bind(config+"/huongDanVien_DAO", huongDanVien_DAO);
			Naming.bind(config+"/khachHang_DAO", khachHang_DAO);
			Naming.bind(config+"/loaiTour_DAO", loaiTour_DAO);
			Naming.bind(config+"/nhanVien_DAO", nhanVien_DAO);
			Naming.bind(config+"/taiKhoan_DAO", taiKhoan_DAO);
			Naming.bind(config+"/thongKe_DAO", thongKe_DAO);
			Naming.bind(config+"/tour_DAO", tour_DAO);
			Naming.bind(config+"/ve_DAO", ve_DAO);
			
			System.out.println("Server stated at " + LocalDateTime.now());
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
