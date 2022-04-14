package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "KhachHangs")
public class KhachHang implements Serializable {
	private static final long serialVersionUID = -404167952913673513L;
	@Id
	private String maKH;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String tenKH;
	@Column(nullable = false)
	private String email;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String diaChi;
	@Column(nullable = false)
	private String soDT;
	@Column(nullable = false)
	private String cmnd;
	@Column(nullable = false)
	private boolean gioiTinh;
	
	@OneToMany(mappedBy = "khachHang")
	private List<Ve> ves;
	
	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
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

	public List<Ve> getVes() {
		return ves;
	}

	public void setVes(List<Ve> ves) {
		this.ves = ves;
	}

//	public KhachHang() {
//		// TODO Auto-generated constructor stub
//	}
	
		
	/**
	 * 
	 */
	public KhachHang() {
		super();
	}


	/**
	 * @param maKH
	 * @param tenKH
	 * @param email
	 * @param diaChi
	 * @param soDT
	 * @param cmnd
	 * @param gioiTinh
	 */
	public KhachHang(String maKH, String tenKH, String email, String diaChi, String soDT, String cmnd, boolean gioiTinh) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.email = email;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.cmnd = cmnd;
		this.gioiTinh = gioiTinh;
	}

	
	/**
	 * @param maKH
	 */
	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}

	
	


	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", email=" + email + ", diaChi=" + diaChi + ", soDT="
				+ soDT + ", cmnd=" + cmnd + ", gioiTinh=" + gioiTinh + "]";
	}


}
