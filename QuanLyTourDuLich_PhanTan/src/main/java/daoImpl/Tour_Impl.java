package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import Util.HibernateUtil;
import dao.Tour_DAO;
import entity.Tour;
import entity.Ve;

public class Tour_Impl extends UnicastRemoteObject implements Tour_DAO{
	public EntityManager entityManager;
	public Tour_Impl() throws RemoteException {
		super();
		entityManager = HibernateUtil.getInstance().getEntityManager();
	}

	private static final long serialVersionUID = 7386605451592006599L;

	@Override
	public ArrayList<Tour> getalltbTour() throws RemoteException {
		ArrayList<Tour> dsTour = new ArrayList<Tour>();
		String sql = "select * from Tours ";
		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Tour empl = entityManager.find(Tour.class, id);
			dsTour.add(empl);		
		}
		return dsTour;
	}

	@Override
	public ArrayList<Tour> getTourTheoMa(String maTour) throws RemoteException {
		ArrayList<Tour> dsTour = new ArrayList<Tour>();
		String sql = "select * from Tours where maTour = :x";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", maTour).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Tour empl = entityManager.find(Tour.class, id);
			dsTour.add(empl);		
		}
		return dsTour;
	}

	@Override
	public ArrayList<Tour> DSTCoTheDatVe(LocalDate ngayHienTai) throws RemoteException {
		ArrayList<Tour> dsTour = new ArrayList<Tour>();
		String sql = "select * from Tours t where t.ngayKhoiHanh > :x order by ngayKhoiHanh desc";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", ngayHienTai.toString()).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Tour empl = entityManager.find(Tour.class, id);
			dsTour.add(empl);		
		}
		return dsTour;
	}

	@Override
	public boolean ThemTour(Tour tour) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();

		try {
			tr.begin();
			entityManager.persist(tour);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return false;
	}

	@Override
	public boolean SuaTour(Tour tour) throws RemoteException {
		EntityTransaction tr = entityManager.getTransaction();
		List<Tour> tours = entityManager.createNativeQuery("select * from Tours where maTour = :x", 
				Tour.class).setParameter("x", tour.getMaTour()).getResultList();
		for(Tour t : tours) {
			try {
				tr.begin();
				t.setDiaDanh(tour.getDiaDanh());
				t.setGiaTour(tour.getGiaTour());
				t.setHinhAnh(tour.getHinhAnh());
				t.setHuongDanVien(tour.getHuongDanVien());
				t.setLoaiTour(tour.getLoaiTour());
				t.setMoTa(tour.getMoTa());
				t.setNgayKetThuc(tour.getNgayKetThuc());
				t.setNgayKhoiHanh(tour.getNgayKhoiHanh());
				t.setSoLuongNguoi(tour.getSoLuongNguoi());
				t.setTenTour(tour.getTenTour());
				t.setTinhTrang(tour.isTinhTrang());
				
				entityManager.merge(tour);
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
	public int LayMaTourLonNhat() throws RemoteException {
		int mtln=0;
		ArrayList<Tour> listTour = getalltbTour();
		if(listTour.size()>0) {
			for (Tour tour : listTour) {
				if(mtln<Integer.parseInt(tour.getMaTour().substring(3)) ) {
					mtln = Integer.parseInt(tour.getMaTour().substring(3));
				}
			}
		}
		
		return mtln;
	}

	@Override
	public Tour KiemTraCoTheChoHuongDanVien(String maTourMoi, String maHDV, Date ngayKH, Date ngayKT) throws RemoteException {
		ArrayList<Tour> dsTour = new ArrayList<Tour>();
		String sql = "select * from Tours where maHuongDanVien = :x";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", maHDV).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Tour empl = entityManager.find(Tour.class, id);
			dsTour.add(empl);		
		}
		for (Tour t : dsTour) {
			//X??t xem ????y c?? b??? tr??ng m?? k, n???u c?? b??? qua
			if(!maTourMoi.substring(3).equals(t.getMaTour().toString().substring(3))) {
				//Ng??y kh???i h??nh (ng??y m???i t???o) n???m trong kho???ng ng??y kh???i h??nh v?? ng??y k???t th??c
				if(ngayKH.compareTo(t.getNgayKetThuc())<=0&&ngayKH.compareTo(t.getNgayKhoiHanh())>=0) {
					Tour tour = LayTourTheoMaTour(t.getMaTour());
					return tour;
				}
				//Ng??y kh???i h??nh (m???i t???o) v?? ng??y k???t th??c (m???i t???o) bao c??? ng??y kh???i h??nh v?? ng??y k???t th??c c??
				if(ngayKH.compareTo(t.getNgayKhoiHanh())<=0&&ngayKT.compareTo(t.getNgayKetThuc())>=0) {
					Tour tour = LayTourTheoMaTour(t.getMaTour());
					return tour;
				}
				//Ng??y k???t th??c (ng??y m???i t???o) n???m trong kho???ng ng??y kh???i h??nh v?? ng??y k???t th??c c??
				if(ngayKT.compareTo(t.getNgayKhoiHanh())>=0&&ngayKT.compareTo(t.getNgayKetThuc())<=0) {
					Tour tour = LayTourTheoMaTour(t.getMaTour());
					return tour;
				}
			}
		}
		return null;
	}

	@Override
	public Tour LayTourTheoMaTour(String mt) throws RemoteException {
		Tour tour = new Tour();
		String sql = "select * from Tours where maTour = :x";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", mt).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			tour = entityManager.find(Tour.class, id);
				
		}
		return tour;
	}

	@Override
	public boolean XoaTour(String maTour) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> tachChuoiTim(String chuoiTim) throws RemoteException {
		ArrayList<String> chuoiTach = new ArrayList<String>();
		String chuoi = chuoiTim.trim();
		int t=0;
		for(int i =0;i<chuoi.length();i++) {
			if(i==chuoi.length()-1) {
				chuoiTach.add(chuoi.substring(t,i+1));
				break;
			}
			if(chuoi.codePointAt(i)==32) {		
				if(chuoi.substring(t,i).codePointAt(0)!=32)
				{				
					chuoiTach.add(chuoi.substring(t,i));
					t=i+1;
				}	
			}
		}
		
		return chuoiTach;
	}

	@Override
	public ArrayList<Tour> TimTour(String nhapTour, boolean loai) throws RemoteException {
		//n???u lo???i l?? true th?? t??m ki???m cho qu???n l?? tour, false l?? t??m ki???m cho qu???n l?? v??
		ArrayList<Tour> tourTimDuoc = new ArrayList<Tour>();
		ArrayList<Tour> danhSachTour = new ArrayList<Tour>(); 
		if(loai==true)
			danhSachTour=LayHetTour();
		else
			danhSachTour = DSTCoTheDatVe(LocalDate.now());
		if(nhapTour.trim().length()==0)
			return danhSachTour;
		else {
			//T??m tour theo t??n v?? tr??? v??? danh s??ch t??n th??i
			String nhap = nhapTour;
			ArrayList<String> chuoiTach = new ArrayList<String>();
			chuoiTach = tachChuoiTim(nhap);
			ArrayList<String> danhSach = new ArrayList<String>();
			for (Tour tour : danhSachTour) {
				danhSach.add(tour.getTenTour());
			}
			
			ArrayList<String> danhSachTam = new ArrayList<String>();
			for(int i=0;i<chuoiTach.size();i++) {
				String pattern = ".*"+chuoiTach.get(i)+".*";

				danhSachTam = new ArrayList<String>();
				for(String a : danhSach) {
					if(a.toLowerCase().matches(pattern.toLowerCase())) {
						danhSachTam.add(a);
					}
				}
				danhSach = danhSachTam;
			}
			
			//Ki???m tra v?? ????a v??o Array list tour t??m ???????c
			for (String tenTour : danhSach) {
				
				 for(Tour tour : danhSachTour) { 
					 if(tour.getTenTour().toLowerCase().equals(tenTour.toLowerCase())) {
						 tourTimDuoc.add(tour); 
					 }
					 
				 }
			}
		}
		return tourTimDuoc;
	}

	@Override
	public ArrayList<Tour> getTourTheoMaDiaDanh(String maDiaDanh) throws RemoteException {
		ArrayList<Tour> dsTour = new ArrayList<Tour>();
		String sql = "select * from Tours where maDiaDanh = :x";
		List<?> temp = entityManager.createNativeQuery(sql).setParameter("x", maDiaDanh).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Tour tour = entityManager.find(Tour.class, id);
				dsTour.add(tour);
		}
		return dsTour;
	}

	@Override
	public ArrayList<Tour> LayHetTour() throws RemoteException {
		ArrayList<Tour> dsTour = new ArrayList<Tour>();
		String sql = "select * from Tours ";
		List<?> temp = entityManager.createNativeQuery(sql).getResultList();
		for (Object o : temp) {		
			Object[] rs = (Object[]) o;		
			String id = (String) rs[0];
			Tour empl = entityManager.find(Tour.class, id);
			dsTour.add(empl);		
		}
		return dsTour;
	}

	@Override
	public void GuiEmail(Tour tour, String diaDanh, String loaiTour) throws RemoteException {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			Session session = Session.getDefaultInstance(properties,
				new Authenticator() {

					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						// TODO Auto-generated method stub
						return new PasswordAuthentication("vinhmasxibua9@gmail.com","Salamander2712");
					}
				
			});
			Message message = new MimeMessage(session);
			message.setSubject("M???t Tour du l???ch v???a m???i ra m???t !");
			MimeBodyPart imgAttachment = new MimeBodyPart();
			imgAttachment.attachFile(tour.getHinhAnh());
			MimeBodyPart tinNhan = new MimeBodyPart();
			
			tinNhan.setContent("<h1>Th??ng b??o m???t Tour du l???ch s???p ra m???t !</h1><br>","text/html; charset=UTF-8");
			
			MimeBodyPart diaDiem = new MimeBodyPart();
			//String dd= tour.getDiaDanh().getTenDiaDanh();
			diaDiem.setContent("<b>?????a ??i???m: </b>"+diaDanh+"<br>","text/html; charset=UTF-8");
			MimeBodyPart loaiT = new MimeBodyPart();
			//String lt = tour.getLoaiTour().getTenLoaiTour();
			loaiT.setContent("<b>Lo???i tour: </b>"+loaiTour+"<br>","text/html; charset=UTF-8");
			MimeBodyPart ngayKhoiHanh = new MimeBodyPart();
			ngayKhoiHanh.setContent("<b>Ng??y kh???i h??nh: </b>"+tour.getNgayKhoiHanh().toString()+"<br>","text/html; charset=UTF-8");
			MimeBodyPart ngayKetThuc = new MimeBodyPart();
			ngayKetThuc.setContent("<b>Ng??y k???t th??c : </b>"+tour.getNgayKetThuc().toString()+"<br>","text/html; charset=UTF-8");
			MimeBodyPart diaChi = new MimeBodyPart();
			diaChi.setContent("<b>?????a ch???: </b>"+" S??? 12 Nguy???n V??n B???o, Ph?????ng 4,Qu???n G?? V???p, Th??nh ph??? H??? Ch?? Minh"+"<br>","text/html; charset=UTF-8");
			MimeBodyPart soDT = new MimeBodyPart();
			soDT.setContent("<b>??i???n tho???i: </b>"+"0327364753"+"<br>","text/html; charset=UTF-8");
			MimeBodyPart email = new MimeBodyPart();
			email.setContent("<b>Email: </b>"+"vinhmasxibua9@gmail.com"+"<br>","text/html; charset=UTF-8");			
			
			DecimalFormat formatter = new DecimalFormat("###,###,###");
			MimeBodyPart giaVe = new MimeBodyPart();
			giaVe.setContent("<b>Gi?? v??: </b>"+formatter.format(tour.getGiaTour())+" VN??"+"<br>","text/html; charset=UTF-8");
			
			
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(tinNhan);
			multipart.addBodyPart(diaDiem);
			multipart.addBodyPart(loaiT);
			multipart.addBodyPart(ngayKhoiHanh);
			multipart.addBodyPart(ngayKetThuc);
			multipart.addBodyPart(giaVe);
			multipart.addBodyPart(diaChi);
			multipart.addBodyPart(soDT);
			multipart.addBodyPart(email);
			multipart.addBodyPart(imgAttachment);
			message.setContent(multipart);
			message.setFrom(new InternetAddress("vinhmasxibua9@gmail.com"));
			message.setRecipient(RecipientType.TO, new InternetAddress("vinhmasxibua@gmail.com"));
			message.setSentDate(new java.util.Date());
			
			Transport.send(message);
		} catch (Exception e2) {
			System.out.println(e2);
		}
		
	}

}
