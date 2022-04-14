package entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Ves")
public class Ve implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2709094821462483519L;
	@Id
	private String maVe;
	@Column(nullable = false)
	private Date ngayDatVe;
	
	@ManyToOne
	@JoinColumn(name = "maNV")
	private NhanVien nhanVien;
	
	@ManyToOne
	@JoinColumn(name = "maKH")
	private KhachHang khachHang;
	
	@ManyToOne
	@JoinColumn(name = "maTour")
	private Tour tour;
	
	
	
	/**
	 * 
	 */
	public Ve() {
		super();
	}
	
	/**
	 * @param maVe
	 * @param ngayDatVe
	 * @param khachHang
	 * @param nhanVien
	 * @param tour
	 */
	public Ve(String maVe, Date ngayDatVe, KhachHang khachHang, NhanVien nhanVien, Tour tour) {
		super();
		this.maVe = maVe;
		this.ngayDatVe = ngayDatVe;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
		this.tour = tour;
	}
	
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public String getMaVe() {
		return maVe;
	}
	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}
	public Date getNgayDatVe() {
		return ngayDatVe;
	}
	public void setNgayDatVe(Date ngayDatVe) {
		this.ngayDatVe = ngayDatVe;
	}
	public Tour getTour() {
		return tour;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
	}

	@Override
	public String toString() {
		return "Ve [maVe=" + maVe + ", ngayDatVe=" + ngayDatVe + ", nhanVien=" + nhanVien.getMaNV() + ", khachHang=" + khachHang.getMaKH()
				+ ", tour=" + tour.getMaTour() + "]";
	}
	
	
}
