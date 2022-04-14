package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "NhanViens")
public class NhanVien implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306174430773041680L;
	@Id
	private String maNV;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String tenNV;
	@Column(unique = true, nullable = false)
	private String cmnd;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String soDT;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String diaChi;
	@Column(nullable = false)
	private boolean gioiTinh;
	@Column(nullable = false)
	private boolean tinhTrang;
	@Column(nullable = false)
	private Date ngayVaoLam;
	
	@OneToMany(mappedBy = "nhanVien")
	private List<Ve> ves;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private TaiKhoan taiKhoan;
	
	
	
	/**
	 * 
	 */
	public NhanVien() {
		super();
	}

	
	public String getMaNV() {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getCmnd() {
		return cmnd;
	}
	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
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
	
	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	
	public String getSoDT() {
		return soDT;
	}

	public void setSoDT(String soDT) {
		this.soDT = soDT;
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
	
	public List<Ve> getVes() {
		return ves;
	}

	public void setVes(List<Ve> ves) {
		this.ves = ves;
	}

	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}

	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	
	/**
	 * @param maNV
	 */
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}


	
	/**
	 * @param maNV
	 * @param cmnd
	 */
	public NhanVien(String maNV, String cmnd) {
		super();
		this.maNV = maNV;
		this.cmnd = cmnd;
	}


	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", cmnd=" + cmnd + ", email=" + email + ", soDienThoai="
				+ soDT + ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + ", tinhTrang=" + tinhTrang
				+ ", ngayVaoLam=" + ngayVaoLam + "]";
	}
	
	
	/**
	 * @param maNV
	 * @param tenNV
	 * @param email
	 * @param diaChi
	 * @param soDienThoai
	 * @param cmnd
	 * @param ngayVaoLam
	 * @param gioiTinh
	 * @param tinhTrang
	 */
	public NhanVien(String maNV, String tenNV,String email,String diaChi,String soDienThoai,String cmnd, 
			 Date ngayVaoLam,boolean gioiTinh, boolean tinhTrang) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.cmnd = cmnd;
		this.email = email;
		this.soDT = soDienThoai;
		this.diaChi = diaChi;
		this.gioiTinh = gioiTinh;
		this.tinhTrang = tinhTrang;
		this.ngayVaoLam = ngayVaoLam;
	}
	
}