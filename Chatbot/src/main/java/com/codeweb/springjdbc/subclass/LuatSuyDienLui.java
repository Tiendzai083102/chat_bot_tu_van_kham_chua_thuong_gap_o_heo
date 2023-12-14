package com.codeweb.springjdbc.subclass;

import java.util.List;

import com.codeweb.springjdbc.model.TrieuChung;
import com.codeweb.springjdbc.model.VanDe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LuatSuyDienLui {
    private String id;
    private List<TrieuChung> trieuChungList;
    private VanDe vanDe;
    
	public LuatSuyDienLui() {
		super();
	}

	public LuatSuyDienLui(String id, List<TrieuChung> trieuChungList, VanDe vanDe) {
		super();
		this.id = id;
		this.trieuChungList = trieuChungList;
		this.vanDe = vanDe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TrieuChung> getTrieuChungList() {
		return trieuChungList;
	}

	public void setTrieuChungList(List<TrieuChung> trieuChungList) {
		this.trieuChungList = trieuChungList;
	}

	public VanDe getVanDe() {
		return vanDe;
	}

	public void setVanDe(VanDe vanDe) {
		this.vanDe = vanDe;
	}
    
    
}
