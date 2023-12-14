package com.codeweb.springjdbc.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "luat")
public class Luat {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "trang_thai")
    private boolean trangThai;

    @OneToMany(mappedBy = "luat",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<SuyDien> suyDienList;

	public Luat() {
		super();
	}

	public Luat(String id, boolean trangThai, List<SuyDien> suyDienList) {
		super();
		this.id = id;
		this.trangThai = trangThai;
		this.suyDienList = suyDienList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public List<SuyDien> getSuyDienList() {
		return suyDienList;
	}

	public void setSuyDienList(List<SuyDien> suyDienList) {
		this.suyDienList = suyDienList;
	}
    
    
}
