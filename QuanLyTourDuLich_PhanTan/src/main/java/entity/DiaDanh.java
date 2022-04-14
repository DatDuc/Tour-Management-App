package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DiaDanhs")
public class DiaDanh implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2315904624161691824L;
	@Id
	private String maDiaDanh;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String tenDiaDanh;
	@Column(columnDefinition = "nvarchar(MAX)")
	private String moTa;
	@Column(columnDefinition = "nvarchar(255)", nullable = false)
	private String tinhThanh;
	
	@OneToMany(mappedBy = "diaDanh")
	private List<Tour> tours;
	
	public DiaDanh() {
		
	}
	
	/**
	 * @param maDiaDanh
	 * @param tenDiaDanh
	 * @param moTa
	 * @param tinhThanh
	 */
	public DiaDanh(String maDiaDanh, String tenDiaDanh, String moTa, String tinhThanh) {
		super();
		this.maDiaDanh = maDiaDanh;
		this.tenDiaDanh = tenDiaDanh;
		this.moTa = moTa;
		this.tinhThanh = tinhThanh;
	}
	
	public DiaDanh(String maDiaDanh) {
		this.maDiaDanh = maDiaDanh;
	}
	
	public String getMaDiaDanh() {
		return maDiaDanh;
	}
	
	public void setMaDiaDanh(String maDiaDanh) {
		this.maDiaDanh = maDiaDanh;
	}
	public String getTenDiaDanh() {
		return tenDiaDanh;
	}
	public void setTenDiaDanh(String tenDiaDanh) {
		this.tenDiaDanh = tenDiaDanh;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public String getTinhThanh() {
		return tinhThanh;
	}
	public void setTinhThanh(String tinhThanh) {
		this.tinhThanh = tinhThanh;
	}
	public List<Tour> getTours() {
		return tours;
	}
	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}
	@Override
	public String toString() {
		return tenDiaDanh;
	}
	
	
}
