package com.codeweb.springjdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.codeweb.springjdbc.controller.Controller;
import com.codeweb.springjdbc.model.Luat;
import com.codeweb.springjdbc.model.SuyDien;
import com.codeweb.springjdbc.model.TrieuChung;
import com.codeweb.springjdbc.model.VanDe;
import com.codeweb.springjdbc.repository.LuatRepository;
import com.codeweb.springjdbc.repository.SuyDienRepository;
import com.codeweb.springjdbc.repository.TrieuChungRepository;
import com.codeweb.springjdbc.repository.VanDeRepository;
import com.codeweb.springjdbc.subclass.LuatSuyDienLui;
import com.codeweb.springjdbc.subclass.LuatSuyDienTien;


@SpringBootApplication
public class SpringJdbcApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringJdbcApplication.class, args);
		VanDeRepository benhRepository = context.getBean(VanDeRepository.class);
		LuatRepository luatRepository = context.getBean(LuatRepository.class);
		SuyDienRepository suyDienRepository = context.getBean(SuyDienRepository.class);
		TrieuChungRepository trieuChungRepository = context.getBean(TrieuChungRepository.class);
		//khoi tao lay du lieu
		List<VanDe> benhList = benhRepository.findAll();
		List<Luat> luatList = luatRepository.findAll();
		List<SuyDien> suyDienList = suyDienRepository.findAll();
		List<TrieuChung> trieuChungList = trieuChungRepository.findAll();
		List<LuatSuyDienTien> luatSuyDienTiens= Controller.initLuatSuyDienTien(luatList);
		List<LuatSuyDienLui> luatSuyDienLuis = Controller.initLuatSuyDienLui(luatList);
        List<TrieuChung> trieuChungLonMac = new ArrayList<>();
		List<VanDe> benhDuDoan = new ArrayList<>();
		List<VanDe> benhDaMac= new ArrayList<>();
		//Thuc hien chuan doan
		//B1: Hoi ten nguoi dung
        System.out.println();
        System.out.println();
		System.out.println("--> Hệ thống xin Chào bạn tôi là hệ thống chat bot tư vấn 24/24 về vấn đề tư vấn và khám chữa bênh cho lợn.");
		Scanner sc = new Scanner(System.in);
		//B2: Hoi truoc suy dien de lay ra trieu chung dang bi
		trieuChungLonMac = Controller.hoiTruocSuyDien(trieuChungList,sc);
		if(trieuChungLonMac.isEmpty()){//Neu nguoi dung khong chon trieu chung nao thi ket thuc luon
			System.out.println("Có vẻ lợn nhà bạn không gặp vấn đề gì cả. Cảm ơn bạn đã sử dụng hệ thống của chúng tôi!!!!");
			shutdownApp(context);
		}
		//B3: Suy dien tien de chuan doan ra cac benh co the bi mac
		benhDuDoan = Controller.suDungSuyDienTien(luatSuyDienTiens,trieuChungLonMac,benhRepository);
		//B4: Su dung suy dien lui de chuan doan ra benh mac phai
		benhDaMac = Controller.suDungSuyDienLui(luatSuyDienLuis,benhDuDoan,trieuChungLonMac,sc);
		//B5: In ra chuan doan cho nguoi dung
		Controller.inKetQuaDuDoan(benhDaMac);
		///////////////////////////////////////ket thuc chuong trinh
		shutdownApp(context);
	}
	// Các hàm sử dụng
	private static void shutdownApp(ConfigurableApplicationContext context) {
		int exitCode = SpringApplication.exit(context, () -> 0);
		System.exit(exitCode);
	}
}
