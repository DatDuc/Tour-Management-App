package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HuongDanViens")
public class HuongDanVien implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6414639373011021127L;
	@Id
	private String maHuongDanVien;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String tenHuongDanVien;
	@Column(nullable = false)
	private String email;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String diaChi;
	@Column(nullable = false)
	private String soDT;
	@Column(nullable = false)
	private Date ngayVaoLam;
	@Column(nullable = false)
	private String cmnd;
	@Column(nullable = false)
	private boolean gioiTinh;
	@Column(nullable = false)
	private boolean tinhTrang;
	
	@OneToMany(mappedBy = "huongDanVien")
	private List<Tour> tours;
	
	
	public HuongDanVien() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param maHuongDanVien
	 */
	public HuongDanVien(String maHuongDanVien) {
		super();
		this.maHuongDanVien = maHuongDanVien;
	}
	
	/**
	 * @param maHuongDanVien
	 * @param tenHuongDanVien
	 * @param email
	 * @param diaChi
	 * @param soDT
	 * @param ngayVaoLam
	 * @param cmnd
	 * @param gioiTinh
	 * @param tinhTrang
	 */
	public HuongDanVien(String maHuongDanVien, String tenHuongDanVien, String email, String diaChi, String soDT,
			Date ngayVaoLam, String cmnd, boolean gioiTinh, boolean tinhTrang) {
		super();
		this.maHuongDanVien = maHuongDanVien;
		this.tenHuongDanVien = tenHuongDanVien;
		this.email = email;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.ngayVaoLam = ngayVaoLam;
		this.cmnd = cmnd;
		this.gioiTinh = gioiTinh;
		this.tinhTrang = tinhTrang;
	}


	
	public String getMaHuongDanVien() {
		return maHuongDanVien;
	}

	public void setMaHuongDanVien(String maHuongDanVien) {
		this.maHuongDanVien = maHuongDanVien;
	}
	public String getTenHuongDanVien() {
		return tenHuongDanVien;
	}
	public void setTenHuongDanVien(String tenHuongDanVien) {
		this.tenHuongDanVien = tenHuongDanVien;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public boolean isTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	
	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	@Override
	public String toString() {
		return "HuongDanVien [maHuongDanVien=" + maHuongDanVien + ", tenHuongDanVien=" + tenHuongDanVien + ", email="
				+ email + ", diaChi=" + diaChi + ", soDT=" + soDT + ", ngayVaoLam=" + ngayVaoLam + ", cmnd=" + cmnd
				+ ", gioiTinh=" + gioiTinh + ", tinhTrang=" + tinhTrang + "]";
	}
	
}
