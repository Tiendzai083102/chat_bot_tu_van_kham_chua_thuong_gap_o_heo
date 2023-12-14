package com.codeweb.springjdbc.subclass;

import java.util.List;

import com.codeweb.springjdbc.model.TrieuChung;
import com.codeweb.springjdbc.model.VanDe;


public class LuatSuyDienTien {
    private String id;
    private TrieuChung trieuChung;
    private List<VanDe> vanDeList;
    
	public LuatSuyDienTien() {
		super();
	}

	public LuatSuyDienTien(String id, TrieuChung trieuChung, List<VanDe> vanDeList) {
		super();
		this.id = id;
		this.trieuChung = trieuChung;
		this.vanDeList = vanDeList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TrieuChung getTrieuChung() {
		return trieuChung;
	}

	public void setTrieuChung(TrieuChung trieuChung) {
		this.trieuChung = trieuChung;
	}

	public List<VanDe> getVanDeList() {
		return vanDeList;
	}

	public void setVanDeList(List<VanDe> vanDeList) {
		this.vanDeList = vanDeList;
	}
    
    
}

