package com.codeweb.springjdbc.subclass;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LuatSuyDien {
    private List<String> veTrai;
    private List<String> vePhai;
    
	public LuatSuyDien() {
		super();
	}
	
	public LuatSuyDien(List<String> veTrai, List<String> vePhai) {
		super();
		this.veTrai = veTrai;
		this.vePhai = vePhai;
	}
	
	public List<String> getVeTrai() {
		return veTrai;
	}
	public void setVeTrai(List<String> veTrai) {
		this.veTrai = veTrai;
	}
	public List<String> getVePhai() {
		return vePhai;
	}
	public void setVePhai(List<String> vePhai) {
		this.vePhai = vePhai;
	}
    
    
}
