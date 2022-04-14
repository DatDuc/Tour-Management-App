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
			//Xét xem đây có bị trùng mã k, nếu có bỏ qua
			if(!maTourMoi.substring(3).equals(t.getMaTour().toString().substring(3))) {
				//Ngày khởi hành (ngày mới tạo) nằm trong khoảng ngày khởi hành và ngày kết thúc
				if(ngayKH.compareTo(t.getNgayKetThuc())<=0&&ngayKH.compareTo(t.getNgayKhoiHanh())>=0) {
					Tour tour = LayTourTheoMaTour(t.getMaTour());
					return tour;
				}
				//Ngày khởi hành (mới tạo) và ngày kết thúc (mới tạo) bao cả ngày khởi hành và ngày kết thúc cũ
				if(ngayKH.compareTo(t.getNgayKhoiHanh())<=0&&ngayKT.compareTo(t.getNgayKetThuc())>=0) {
					Tour tour = LayTourTheoMaTour(t.getMaTour());
					return tour;
				}
				//Ngày kết thúc (ngày mới tạo) nằm trong khoảng ngày khởi hành và ngày kết thúc cũ
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
		//nếu loại là true thì tìm kiếm cho quản lý tour, false là tìm kiếm cho quản lý vé
		ArrayList<Tour> tourTimDuoc = new ArrayList<Tour>();
		ArrayList<Tour> danhSachTour = new ArrayList<Tour>(); 
		if(loai==true)
			danhSachTour=LayHetTour();
		else
			danhSachTour = DSTCoTheDatVe(LocalDate.now());
		if(nhapTour.trim().length()==0)
			return danhSachTour;
		else {
			//Tìm tour theo tên và trả về danh sách tên thôi
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
			
			//Kiểm tra và đưa vào Array list tour tìm được
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
			message.setSubject("Một Tour du lịch vừa mới ra mắt !");
			MimeBodyPart imgAttachment = new MimeBodyPart();
			imgAttachment.attachFile(tour.getHinhAnh());
			MimeBodyPart tinNhan = new MimeBodyPart();
			
			tinNhan.setContent("<h1>Thông báo một Tour du lịch sắp ra mắt !</h1><br>","text/html; charset=UTF-8");
			
			MimeBodyPart diaDiem = new MimeBodyPart();
			//String dd= tour.getDiaDanh().getTenDiaDanh();
			diaDiem.setContent("<b>Địa điểm: </b>"+diaDanh+"<br>","text/html; charset=UTF-8");
			MimeBodyPart loaiT = new MimeBodyPart();
			//String lt = tour.getLoaiTour().getTenLoaiTour();
			loaiT.setContent("<b>Loại tour: </b>"+loaiTour+"<br>","text/html; charset=UTF-8");
			MimeBodyPart ngayKhoiHanh = new MimeBodyPart();
			ngayKhoiHanh.setContent("<b>Ngày khởi hành: </b>"+tour.getNgayKhoiHanh().toString()+"<br>","text/html; charset=UTF-8");
			MimeBodyPart ngayKetThuc = new MimeBodyPart();
			ngayKetThuc.setContent("<b>Ngày kết thúc : </b>"+tour.getNgayKetThuc().toString()+"<br>","text/html; charset=UTF-8");
			MimeBodyPart diaChi = new MimeBodyPart();
			diaChi.setContent("<b>Địa chỉ: </b>"+" Số 12 Nguyễn Văn Bảo, Phường 4,Quận Gò Vấp, Thành phố Hồ Chí Minh"+"<br>","text/html; charset=UTF-8");
			MimeBodyPart soDT = new MimeBodyPart();
			soDT.setContent("<b>Điện thoại: </b>"+"0327364753"+"<br>","text/html; charset=UTF-8");
			MimeBodyPart email = new MimeBodyPart();
			email.setContent("<b>Email: </b>"+"vinhmasxibua9@gmail.com"+"<br>","text/html; charset=UTF-8");			
			
			DecimalFormat formatter = new DecimalFormat("###,###,###");
			MimeBodyPart giaVe = new MimeBodyPart();
			giaVe.setContent("<b>Giá vé: </b>"+formatter.format(tour.getGiaTour())+" VNĐ"+"<br>","text/html; charset=UTF-8");
			
			
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
