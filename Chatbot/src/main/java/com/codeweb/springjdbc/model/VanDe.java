package com.codeweb.springjdbc.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "van_de")
public class VanDe {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ten_van_de")
    private String tenVanDe;

    @Column(name = "nguyen_nhan")
    private String nguyenNhan;

    @Column(name = "giai_phap")
    private String giaiPhap;

	public VanDe() {
		super();
	}

	public VanDe(String id, String tenVanDe, String nguyenNhan, String giaiPhap) {
		super();
		this.id = id;
		this.tenVanDe = tenVanDe;
		this.nguyenNhan = nguyenNhan;
		this.giaiPhap = giaiPhap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenVanDe() {
		return tenVanDe;
	}

	public void setTenVanDe(String tenVanDe) {
		this.tenVanDe = tenVanDe;
	}

	public String getNguyenNhan() {
		return nguyenNhan;
	}

	public void setNguyenNhan(String nguyenNhan) {
		this.nguyenNhan = nguyenNhan;
	}

	public String getGiaiPhap() {
		return giaiPhap;
	}

	public void setGiaiPhap(String giaiPhap) {
		this.giaiPhap = giaiPhap;
	}


	
}
