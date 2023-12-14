package com.codeweb.springjdbc.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "suy_dien")
public class SuyDien {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "luat_id")
    private Luat luat;

    @ManyToOne
    @JoinColumn(name = "trieu_chung_id")
    private TrieuChung trieuChung;

    @ManyToOne
    @JoinColumn(name = "van_de_id")
    private VanDe vande;

	public SuyDien() {
		super();
	}

	public SuyDien(String id, Luat luat, TrieuChung trieuChung, VanDe vande) {
		super();
		this.id = id;
		this.luat = luat;
		this.trieuChung = trieuChung;
		this.vande = vande;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Luat getLuat() {
		return luat;
	}

	public void setLuat(Luat luat) {
		this.luat = luat;
	}

	public TrieuChung getTrieuChung() {
		return trieuChung;
	}

	public void setTrieuChung(TrieuChung trieuChung) {
		this.trieuChung = trieuChung;
	}

	public VanDe getVande() {
		return vande;
	}

	public void setVande(VanDe vande) {
		this.vande = vande;
	}

}
