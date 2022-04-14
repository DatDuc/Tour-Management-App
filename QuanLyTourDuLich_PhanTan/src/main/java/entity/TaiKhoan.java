package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TaiKhoans")
public class TaiKhoan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3351636676228863929L;
	
	@Id
	private String maNV;
	private String matKhau;
	@OneToOne
	@MapsId
	@JoinColumn(name= "maNV")
	private NhanVien nhanVien;
	
	

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public NhanVien getNhanVien() {
		return nhanVien;
	}

	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	

	@Override
	public String toString() {
		return "TaiKhoan [maNV=" + maNV + ", matKhau=" + matKhau + "]";
	}

	

	
	/**
	 * @param maNV
	 * @param matKhau
	 */
	public TaiKhoan(String maNV, String matKhau) {
		super();
		this.maNV = maNV;
		this.matKhau = matKhau;
	}

	/**
	 * 
	 */
	public TaiKhoan() {
		super();
	}



	


}
