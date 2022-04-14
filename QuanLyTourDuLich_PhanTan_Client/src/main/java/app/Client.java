package app;

import java.rmi.Naming;

import dao.DiaDanh_DAO;
import dao.HuongDanVien_DAO;
import dao.KhachHang_DAO;
import dao.TaiKhoan_DAO;


public class Client {

	public static void main(String[] args) {
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager == null) {
			
			System.setProperty("java.security.policy", "policy/policy.policy");
			System.setSecurityManager(new SecurityManager());
			
		}
		
		try {
			
			DiaDanh_DAO diaDanh_DAO = (DiaDanh_DAO) Naming.lookup("rmi://DESKTOP-O44ASRI:9999/diaDanh_DAO");
			HuongDanVien_DAO huongDanVien_DAO =   (HuongDanVien_DAO) Naming.lookup("rmi://DESKTOP-O44ASRI:9999/huongDanVien_DAO");
			KhachHang_DAO khachHang_DAO = (KhachHang_DAO) Naming.lookup("rmi://DESKTOP-O44ASRI:9999/khachHang_DAO");
			TaiKhoan_DAO taiKhoan_DAO = (TaiKhoan_DAO) Naming.lookup("rmi://DESKTOP-O44ASRI:9999/taiKhoan_DAO");

			
		
//			diaDanh_DAO.getalltbDiaDanh().forEach(dd -> System.out.println(dd));
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
