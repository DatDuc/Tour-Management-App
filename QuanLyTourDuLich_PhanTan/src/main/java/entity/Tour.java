package entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Tours")
public class Tour implements Serializable{
	@Id
	private String maTour;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String tenTour;
	@Column(nullable = false)
	private float giaTour;
	@Column(nullable = false)
	private int soLuongNguoi;
	@Column(nullable = false)
	private Date ngayKhoiHanh;
	@Column(nullable = false)
	private Date ngayKetThuc;
	@Column(columnDefinition = "nvarchar(MAX)" ,nullable = false)
	private String moTa;
	@Column(nullable = false)
	private boolean tinhTrang;
	@Column(columnDefinition = "nvarchar(255)")
	private String hinhAnh;
	
	
	@ManyToOne
	@JoinColumn(name="maHuongDanVien")
	private HuongDanVien huongDanVien;
	
	
	@ManyToOne
	@JoinColumn(name="maDiaDanh")
	private DiaDanh diaDanh;
	
	
	@ManyToOne()
	@JoinColumn(name = "maLoaiTour")
	private LoaiTour loaiTour;
	
	@OneToMany(mappedBy = "tour")
	private List<Ve> ves;
	
	
	

	/**
	 * 
	 */
	public Tour() {
		super();
	}


	

	/**
	 * @param maTour
	 * @param tenTour
	 * @param giaTour
	 * @param soLuongNguoi
	 * @param ngayKhoiHanh
	 * @param ngayKetThuc
	 * @param moTa
	 * @param tinhTrang
	 * @param hinhAnh
	 */
	public Tour(String maTour, String tenTour, float giaTour, int soLuongNguoi, Date ngayKhoiHanh, Date ngayKetThuc,
			String moTa, boolean tinhTrang, String hinhAnh) {
		super();
		this.maTour = maTour;
		this.tenTour = tenTour;
		this.giaTour = giaTour;
		this.soLuongNguoi = soLuongNguoi;
		this.ngayKhoiHanh = ngayKhoiHanh;
		this.ngayKetThuc = ngayKetThuc;
		this.moTa = moTa;
		this.tinhTrang = tinhTrang;
		this.hinhAnh = hinhAnh;
	}



	@Override
	public String toString() {
		return tenTour;
	}

	public HuongDanVien getHuongDanVien() {
		return huongDanVien;
	}

	public void setHuongDanVien(HuongDanVien huongDanVien) {
		this.huongDanVien = huongDanVien;
	}

	public LoaiTour getLoaiTour() {
		return loaiTour;
	}

	public void setLoaiTour(LoaiTour loaiTour) {
		this.loaiTour = loaiTour;
	}

	public Tour(String tenTour) {
		this.tenTour = tenTour;
		
	}
	
	public String getMaTour() {
		return maTour;
	}
	public void setMaTour(String maTour) {
		this.maTour = maTour;
	}
	public String getTenTour() {
		return tenTour;
	}
	public void setTenTour(String tenTour) {
		this.tenTour = tenTour;
	}
	public float getGiaTour() {
		return giaTour;
	}
	public void setGiaTour(float giaTour) {
		this.giaTour = giaTour;
	}
	public int getSoLuongNguoi() {
		return soLuongNguoi;
	}
	public void setSoLuongNguoi(int soLuongNguoi) {
		this.soLuongNguoi = soLuongNguoi;
	}
	
	public Date getNgayKhoiHanh() {
		return ngayKhoiHanh;
	}
	public void setNgayKhoiHanh(Date ngayKhoiHanh) {
		this.ngayKhoiHanh = ngayKhoiHanh;
	}
	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}
	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public boolean isTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public DiaDanh getDiaDanh() {
		return diaDanh;
	}
	public void setDiaDanh(DiaDanh diaDanh) {
		this.diaDanh = diaDanh;
	}

	public List<Ve> getVes() {
		return ves;
	}

	public void setVes(List<Ve> ves) {
		this.ves = ves;
	}
	
	
	
}
