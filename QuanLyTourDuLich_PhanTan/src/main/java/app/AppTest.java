package app;

import java.rmi.RemoteException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import antlr.Lookahead;
import dao.DiaDanh_DAO;
import dao.HuongDanVien_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import dao.Ve_DAO;
import daoImpl.DiaDanh_Impl;
import daoImpl.HuongDanVien_Impl;
import daoImpl.KhachHang_Impl;
import daoImpl.NhanVien_Impl;
import daoImpl.TaiKhoan_Impl;
import daoImpl.Ve_Impl;
import entity.HuongDanVien;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;

public class AppTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		EntityManager entityManager = Persistence.createEntityManagerFactory("Nhom100_QuanLyTourDuLich_PhanTan").createEntityManager();
		
		HuongDanVien_DAO huongDanVien_DAO = new HuongDanVien_Impl();
		NhanVien_DAO nhanVien_DAO = new NhanVien_Impl();
		KhachHang_DAO khachHang_DAO = new KhachHang_Impl();
		TaiKhoan_DAO taiKhoan_DAO = new TaiKhoan_Impl();
		DiaDanh_DAO diaDanh_DAO = new DiaDanh_Impl();
		Ve_DAO ve_DAO = new Ve_Impl();
		
		//lấy toàn bộ HDV
//		huongDanVien_DAO.LayToanBoHuongDanVien().forEach(hdv -> System.out.println(hdv));
		
		//thêm HDV
//		HuongDanVien huongDanVien = new HuongDanVien("HDV100", "Đỗ Đạt Đức", "duc@gmail.com", "128 Huỳnh Khương An", 
//				"0359806602", new Date(2021-1900, 11, 18), "281231129", true, true);
//		huongDanVien_DAO.themHDV(huongDanVien);
		
		//update
//		HuongDanVien huongDanVien = new HuongDanVien("HDV100", "Đỗ Đạt Đạt", "haha@gmail.com", "Thới Hòa Bình Dương", 
//				"0123456789", new Date(2021-1900, 11, 20), "281231129", false, false);
//		System.out.println(huongDanVien_DAO.updateHDV(huongDanVien));
		
		//getHDV Theo mã
//		HuongDanVien huongDanVien = huongDanVien_DAO.getHuongDanVienHDId("HDV100");
//		System.out.println(huongDanVien);
		
		
//		lấy nhân viên theo mã
//		NhanVien nhanVien = nhanVien_DAO.LayNhanVienTheoMa("NV0007");
//		System.out.println(nhanVien);
		
//		Lấy KH theo CMND
//		KhachHang khachHang = khachHang_DAO.LayKhachHangTheoCMND("1222222222");
//		System.out.println(khachHang);
		
		//lấy toàn bộ KH
//		khachHang_DAO.LayHetKhachHang().forEach(kh -> System.out.println(kh));
		
//		thêm KH
//		KhachHang khachHang = new KhachHang("KH1234", "Đạt Đức", "duc@gmail.com", "Bình Dương", "0123456789", "123456789", true);
//		System.out.println(khachHang_DAO.themKH(khachHang));
		
		//lấy mã KH lớn nhất
//		System.out.println(khachHang_DAO.LayMaKHLonNhat());
		
		//lấy kh theo id
//		KhachHang khachHang = khachHang_DAO.getKhachHangbyId("KH0100");
//		System.out.println(khachHang);
		
		//cập nhập KH
//		KhachHang khachHang = new KhachHang("KH0100", "Đạt Đức", "duc@gmail.com", "Bình Dương", "0123456789", "123456789", true);
//		System.out.println(khachHang_DAO.update(khachHang));
		
//		taiKhoan_DAO.DocTuBang().forEach(tk -> System.out.println(tk));
		
//		System.out.println(taiKhoan_DAO.Login("NV0009", "123456"));
		
//		diaDanh_DAO.getalltbDiaDanh().forEach(a -> System.out.println(a));
		
//		ve_DAO.getalltbVe().forEach(a->System.out.println(a));
		
//		System.out.println(huongDanVien_DAO.LayMaHDVLonNhat());

	}

}
