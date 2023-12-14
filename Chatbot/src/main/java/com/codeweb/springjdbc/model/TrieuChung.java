package com.codeweb.springjdbc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trieu_chung")
public class TrieuChung {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "trieu_chung")
    private String trieuChung;

	public TrieuChung() {
		super();
	}

	public TrieuChung(String id, String trieuChung) {
		super();
		this.id = id;
		this.trieuChung = trieuChung;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrieuChung() {
		return trieuChung;
	}

	public void setTrieuChung(String trieuChung) {
		this.trieuChung = trieuChung;
	}
    
    
}
