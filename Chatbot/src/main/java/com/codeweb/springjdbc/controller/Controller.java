package com.codeweb.springjdbc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import com.codeweb.springjdbc.model.Luat;
import com.codeweb.springjdbc.model.SuyDien;
import com.codeweb.springjdbc.model.TrieuChung;
import com.codeweb.springjdbc.model.VanDe;
import com.codeweb.springjdbc.repository.TrieuChungRepository;
import com.codeweb.springjdbc.repository.VanDeRepository;
import com.codeweb.springjdbc.subclass.LuatSuyDien;
import com.codeweb.springjdbc.subclass.LuatSuyDienLui;
import com.codeweb.springjdbc.subclass.LuatSuyDienTien;

public class Controller {

	public static List<LuatSuyDienTien> initLuatSuyDienTien(List<Luat> luatList) {
		List<LuatSuyDienTien> luatSuyDienTiens = new ArrayList<>();
		for (Luat i : luatList) {
			if (i.isTrangThai()) {
				LuatSuyDienTien luatSuyDienTien = new LuatSuyDienTien();
				luatSuyDienTien.setId(i.getId());
				luatSuyDienTien.setTrieuChung(i.getSuyDienList().get(0).getTrieuChung());
				List<VanDe> vandes = new ArrayList<>();
				for (SuyDien y : i.getSuyDienList()) {
					vandes.add(y.getVande());
				}
				luatSuyDienTien.setVanDeList(vandes);
				luatSuyDienTiens.add(luatSuyDienTien);
			}
		}
		return luatSuyDienTiens;
	}

	///////////////////////////////////////////////////////////////////////////
	public static List<LuatSuyDienLui> initLuatSuyDienLui(List<Luat> luatList) {
		List<LuatSuyDienLui> luatSuyDienLuis = new ArrayList<>();
		for (Luat i : luatList) {
			if (!i.isTrangThai()) {
				LuatSuyDienLui luatSuyDienLui = new LuatSuyDienLui();
				luatSuyDienLui.setId(i.getId());
				luatSuyDienLui.setVanDe(i.getSuyDienList().get(0).getVande());
				List<TrieuChung> trieuChungs = new ArrayList<>();
				for (SuyDien y : i.getSuyDienList()) {
					trieuChungs.add(y.getTrieuChung());
				}
				luatSuyDienLui.setTrieuChungList(trieuChungs);
				luatSuyDienLuis.add(luatSuyDienLui);
			}
		}
		return luatSuyDienLuis;
	}

	///////////////////////////////////////////////////////////////////////////
	private static Set<TrieuChung> getTrieuChungCuaMotBenh(String idBenh, List<LuatSuyDienLui> luatSuyDienLuis) {
		Set<TrieuChung> trieuChungs = new HashSet<>();
		for (LuatSuyDienLui i : luatSuyDienLuis) {
			if (i.getVanDe().getId().trim().equalsIgnoreCase(idBenh.trim())) {
				trieuChungs.addAll(i.getTrieuChungList());
			}
		}
		return trieuChungs;
	}
	///////////////////////////////////////////////////////////////////////////

	private static Set<String> suyDienTien(List<LuatSuyDien> rules, Set<String> facts) {
		int currentLengthOfFaces = 0;
		int afterLengthOfFaces = 0;

		do {
			currentLengthOfFaces = facts.size();
			for (LuatSuyDien i : rules) {
				if (facts.containsAll(i.getVeTrai()) && !facts.containsAll(i.getVePhai())) {
					for (String factInRight : i.getVePhai()) {
						facts.add(factInRight);
					}
					break;
				}
			}
			afterLengthOfFaces = facts.size();
		} while (afterLengthOfFaces > currentLengthOfFaces);
		return facts;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private static boolean suyDienLuiDungDeChuanDoanBenh(List<LuatSuyDienLui> luatSuyDienLuis, VanDe benhDuDoan,
			List<TrieuChung> trieuChung) {
		List<LuatSuyDien> luatSuyDiens = new ArrayList<>();
		Set<String> facts = new HashSet<>();
		Stack<String> goal = new Stack<>();
		goal.push(benhDuDoan.getId());
		for (LuatSuyDienLui i : luatSuyDienLuis) {
			List<String> vePhai = new ArrayList<>();
			vePhai.add(i.getVanDe().getId());
			List<String> veTrai = new ArrayList<>();
			for (TrieuChung j : i.getTrieuChungList()) {
				veTrai.add(j.getId());
			}
			luatSuyDiens.add(new LuatSuyDien(veTrai, vePhai));
		}
		for (TrieuChung i : trieuChung) {
			facts.add(i.getId());
		}
		return suyDienLui(luatSuyDiens, facts, goal, new Stack<>());
	}

	///////////////////////////////////////////////////////////////////////////
	private static boolean suyDienLui(List<LuatSuyDien> rules, Set<String> facts, Stack<String> goal,
			Stack<String> isCheck) {
		while (true) {
			int check = 0;
			if (facts.contains(goal.peek())) {
				goal.pop();
				check = 1;
			}
			if (goal.isEmpty()) {
				return true;
			}
			for (LuatSuyDien i : rules) {
				if (i.getVePhai().contains(goal.peek()) && isCheck.search(goal.peek()) == -1) {
					isCheck.push(goal.peek());
					goal.pop();
					for (String y : i.getVeTrai()) {
						goal.push(y);
					}
					check = 1;
					break;
				}
			}
			if (check == 0) {
				return false;
			}
		}
	}

	public static List<Integer> checkAnswer(String a) {
		ArrayList<Integer> danhSachTrieuChung = new ArrayList<>();
		String[] mangChuoi = a.split(",");

		String[] symptoms1 = { "sốt", "sốt cao", "nhiệt độ cao", "nhiệt độ bất thường", "sốt 40 độ c", "sốt 41 độ c",
				"sốt 42 độ c" };
		String[] symptoms2 = { "không đi lại được", "đi lại khó khăn", "bị què" };
		String[] symptoms3 = { "bị nổi mụn quanh miệng", "bị loét ở miệng" };
		String[] symptoms4 = { "Long móng", "Lở loét", "Lở loét quanh móng" };
		String[] symptoms5 = { "Tiêu chảy", "Phân nhiều nước", "Phân có bọt", "Phân màu trắng", "Phân màu vàng",
				"Phân có mùi hôi tanh" };
		String[] symptoms6 = { "Bị nôn", "Bụng hóp", "Mắt lõm", "Da tím tái", "Da thâm" };
		String[] symptoms7 = { "Kém ăn", "Không chịu ăn", "Lười ăn", "Người gầy", "Còi cọc" };
		String[] symptoms8 = { "Mất nước", "Lông xù", "Bỏ bú", "Không chịu bú", "Cơ thể suy kiệt" };
		String[] symptoms9 = { "Uể oải", "Mệt mỏi", "Nằm ì một chỗ", "Không chịu đi lại" };
		String[] symptoms10 = { "Tiêu chảy", "Phân màu trắng sữa", "Phân vàng, xám", "Phân màu vàng, xám",
				"Phân sền sệt", "Phân xanh lá cây", "Phân xanh lục" };
		String[] symptoms11 = { "Khó thở", "Không thở được", "Thở khó khăn", "Nằm vật vã" };
		String[] symptoms12 = { "Bị suy tim", "Có hiện tượng suy tim", "Người co giật", "Co giật dữ dội",
				"Hộc máu ở mũi", "Máu chảy ra từ mũi", "Hộc máu ở họng", "Máu chảy ra từ họng" };
		String[] symptoms13 = { "Bị vàng da", "Da có màu vàng", "Vàng da", "Da vàng" };
		String[] symptoms14 = { "Có mùi khét", "Người có mùi khét" };
		String[] symptoms15 = { "Lợn nái bị xảy thai" };
		String[] symptoms16 = { "dịch hoàn bị sưng", "Dịch hoàn sưng to" };
		String[] symptoms17 = { "Tiếng kêu khan", "Tiếng kêu bị khan", "Mắt bị sưng", "Mí mắt sưng", "Mắt có dỉ" };
		String[] symptoms18 = { "Người đi loạng choạng", "Người đi không vững", "Đi vòng quanh", "Đi lòng lòng",
				"Đi vòng quanh 1 chỗ", "Đi giật lùi" };
		String[] symptoms19 = { "Mũi bị phù", "Mũi phù", "Phù mũi", "Phù đầu", "Đầu bị phù", "Mặt bị phù", "Tai bị phù",
				"Sùi bọt mép", "Người run từng cơn", "Chân bơi trong không khí" };
		String[] symptoms20 = { "Người gầy yếu", "Bị ho", "Khó thở" };
		String[] symptoms21 = { "Bài tiết không ổn định", "Bài tiết bất thường", "Đi ngoài bất thường",
				"Đi ngoài không ổn định", "Tiêu chảy", "Táo bón", "Bị tiêu chảy", "Bị táo bón", "Đi ngoài có vấn đề" };
		String[] symptoms22 = { "Thở mạnh", "Thở nặng nhọc", "Thở khó khăn", "Xuất hiện nốt đỏ trên da",
				"Xuất hiện nốt đỏ chỗ da mỏng", "Có đám xuất huyết lớn", "Bong da vẩy", "Da bong vẩy", "Da bị bong vẩy",
				"Các nốt đỏ bị tím lại", "Các nốt đỏ bị thối loét" };
		String[] symptoms23 = { "Mắt có màu trắng", "Mắt trắng", "Mắt đục trắng", "Mũi bị viêm", "Mũi viêm", "Viêm mũi",
				"Bị viêm mũi", "Nước mũi đặc", "Đi ngoài không ổn định", "Đi ngoài có vấn đề", "Đi ngoài bất thường" };
		String[] symptoms24 = { "Khó thở", "Thở nặng nhọc", "Thở khó", "Thở khó khăn", "Xuất hiện tụ máu đỏ ở tai",
				"Tai xuất hiện tụ máu đỏ", "Xuất hiện các tụ máu đỏ ở bụng", "Bụng xuất hiện tụ máu đỏ",
				"Xuất hiện các tụ máu đỏ ở đùi trong", "Đùi trong xuất hiện tụ máu",
				"Xuất hiện các tụ máu đỏ ở tai, bụng, đùi trong", "Tụ máu đỏ chuyển sang xanh tím" };
		String[] symptoms25 = { "Chảy nhiều nước dãi", "Chảy nước dãi nhiều", "Nước dãi chảy nhiều",
				"Chảy nhiều nước mũi", "Chảy nước mũi nhiều", "Nước mũi chảy nhiều" };
		String[] symptoms26 = { "Da vùng mũi có màu tím xanh", "Da ở mũi có màu tím xanh", "Da ở mũi có màu xanh tím",
				"Da mũi có màu xanh tím", "Da mũi có màu tím xanh", "Da ở tai có màu tím xanh",
				"Da ở tai có màu xanh tím", "Da ở chân có màu tím xanh", "Bị bại huyết nặng",
				"Có hiện tượng bại huyết" ,"bại huyết"};
		String[] symptoms27 = { "Chết đột ngột", "Đột ngột chết", "Đột nhiên chết", "Chết bất ngờ",
				"Chết không có dấu hiệu" };
		String[] symptoms28 = { "Nhịp tim rối loạn", "Rối loạn nhịp tim", "Nhịp tim bất thường", "Tim đập bất thường",
				"Tim đập rối loạn", "Tim đập không bình thường" };
		String[] symptoms29 = { "Phổi bị viêm", "Bị viêm phổi", "Viêm phổi", "Viêm phổi có tính chất đối xứng",
				"Phổi bị viêm có tính chất đối xứng", "Vùng phổi viêm có màu đen", "Chỗ viêm phổi có màu đen",
				"Vùng phổi viêm có màu tím", "Chỗ viêm phổi có màu tím" };
		String[] symptoms30 = { "Mắt đỏ", "Đỏ mắt", "Mắt màu đỏ", "Mắt có màu đỏ", "Có hành vi điên cuồng, lồng lộn",
				"Hành động điên cuồng, khó đoán", "Rúc đầu vào khe tường", "Đầu rúc vào khe tường", "Hộc máu",
				"Máu hộc ra ngoài" };
		String[] symptoms31 = { "Mổ thấy thận sưng tụ máu từng đám", "Mổ ra thấy thân sưng tụ máu", "Thận sưng tụ máu",
				"Thận sưng tụ máu từng đám", "Thận bị sưng", "Thịt bị thắng lên" };
		String[] symptoms32 = { "Hiện các dấu đỏ vuông trên thân", "Hiện các dấu đỏ tròn trên thân",
				"Hiện các dấu đỏ tam giác trên thân", "Hiện các dấu đỏ vuông, tròn, tam giác trên thân" };
		String[] symptoms33 = { "Da hồng", "Da có màu hồng", "Da màu hồng", "Da ửng hồng",
				"có vết bầm thâm tím trên da", "Da xuất hiện vết bầm tím", "Xuất hiện vết bầm tím trên da", "Rộp da",
				"Da phồng rộp", "Tím tai", "Tai tím", "Tai tím tái", "Tím đuôi", "Đuôi tím", "Đuôi tím tái",
				"Heo con chân bẹt ra ngoài", "Chân heo con bẹt ra ngoài", "Heo con người run rẩy",
				"Người heo con run rẩy", "Heo con bị tiêu chảy", "Heo con tiêu chảy", "Heo con có dấu hiệu tiêu chảy" };
		String[] symptoms34 = { "Heo con bị xảy thai giai đoạn cuối", "Thai chết lưu giai đoạn 2",
				"Thai chết yểu sau khi sinh", "Thai chết sớm sau sinh" };
		String[] symptoms35 = { "Bị viêm phổi", "Phổi bị viêm", "Viêm phổi", "Phổi viêm", "Có hiện tượng viêm phổi" };
		String[] symptoms36 = { "Heo nái nuôi con bị mất sữa", "Heo nái bị mất sữa", "Heo nái ít sữa",
				"Heo nái không đủ sữa nuôi con", "Heo nái bị viêm vú", "Heo nái viêm vú", "Heo nái da biến màu",
				"Heo nái da chuyển màu", "Heo nái sinh non", "Heo nái sinh con non" };
		String[] symptoms37 = { "Da chuyển màu từ hồng đỏ sang tím xanh nhạt",
				"Chuyển màu da từ hồng đỏ sang tím xanh nhạt", "Có biểu hiện hắt hơi", "Bị hắt hơi", "Hắt hơi",
				"Hơi thở nhanh", "Thở nhanh" };

		// Mảng 2 chiều chứa danh sách triệu chứng
		String[][] tapBi = { symptoms1, symptoms2, symptoms3, symptoms4, symptoms5, symptoms6, symptoms7, symptoms8,
				symptoms9, symptoms10, symptoms11, symptoms12, symptoms13, symptoms14, symptoms15, symptoms16,
				symptoms17, symptoms18, symptoms19, symptoms20, symptoms21, symptoms22, symptoms23, symptoms24,
				symptoms25, symptoms26, symptoms27, symptoms28, symptoms29, symptoms30, symptoms31, symptoms32,
				symptoms33, symptoms34, symptoms35, symptoms36, symptoms37 };
		// Kiểm tra từ cần tìm trong từng chuỗi
		for (String chuoi : mangChuoi) {

			boolean found = false;

			for (int i = 0; i < 37; i++) {
				for (int j = 0; j < tapBi[i].length; j++) {
					String tuCanTim = tapBi[i][j];
					if (chuoi.trim().toLowerCase().contains(tuCanTim.toLowerCase())) {
						danhSachTrieuChung.add(i);
						found = true;
						break; // Nếu tìm thấy từ trong tập Bi, thoát vòng lặp j
					}
				}
				if (found) {
					break;
				}

			}

		}
		return danhSachTrieuChung;
	}
	public static boolean Checking(String a) {
		String b[]= {"không","ko","0","tôi không"};
		for(String d:b) {
			if(a.trim().toLowerCase().contains(d.toLowerCase())) {
				return false;			
			}
		}
		return true;
	}

	///////////////////////////////////////////////////////////////////////////
	public static List<TrieuChung> hoiTruocSuyDien(List<TrieuChung> trieuChungs, Scanner sc) {
		List<TrieuChung> trieuChungLonGap = new ArrayList<>();
		System.out.println(
				"-->Hệ thống:Lợn của bạn có gặp vấn đề gì không?Hãy cho chúng tôi biết những vấn đề mà lợn của bạn đang gặp phải.");

		boolean found = false;
		while (true) {
			System.out.println("-> Người dùng :");
			String answer = sc.nextLine().trim();
			if (Checking(answer)==false) {
				found = true;
			} else {
				List<Integer> a = new ArrayList<>();
				a = checkAnswer(answer);
				if (a.size()==0) {
					System.out.println(
							"-->Hệ thống:Bạn có thể miêu tả lại triệu chứng mà bạn đang gặp cho tôi được không");
				} else {
					for (Integer b : a) {
						trieuChungLonGap.add(trieuChungs.get(b));
					}
					System.out.println("-->Hệ thống:Không biết lợn của bạn còn gặp phải triệu chứng nào không ngoài những triệu chứng trên không?");
					a = null;
				}
			}
			if (found == true) {
				System.out.println("-->Hệ thống:Chúng tôi đã ghi nhận những triệu chứng mà lợn của bạn đã măc phải");
				break;
			}
		}

		return trieuChungLonGap;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static List<VanDe> suDungSuyDienTien(List<LuatSuyDienTien> luatSuyDienTiens, List<TrieuChung> trieuChungs,
			VanDeRepository benhRepository) {
		List<LuatSuyDien> luatSuyDiens = new ArrayList<>();
		Set<String> facts = new HashSet<>();
		for (LuatSuyDienTien i : luatSuyDienTiens) {
			List<String> veTrai = new ArrayList<>();
			veTrai.add(i.getTrieuChung().getId());
			List<String> vePhai = new ArrayList<>();
			for (VanDe j : i.getVanDeList()) {
				vePhai.add(j.getId());
			}
			luatSuyDiens.add(new LuatSuyDien(veTrai, vePhai));
		}
		for (TrieuChung i : trieuChungs) {
			facts.add(i.getId());
		}
		Set<String> idBenhs = suyDienTien(luatSuyDiens, facts);
		List<VanDe> vandes = benhRepository.findAllById(idBenhs);
		return vandes;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static List<VanDe> suDungSuyDienLui(List<LuatSuyDienLui> luatSuyDienLuis, List<VanDe> benhDuDoan,
			List<TrieuChung> trieuChungDaMac, Scanner sc) {
		List<VanDe> benhDaMac = new ArrayList<>();
		System.out.print(
				"-->Hệ thống: Dựa theo những triệu chứng mà bạn đã đưa ra thì\n những chú lợn của bạn có thể mắc những bệnh sau: ");
		for (VanDe i : benhDuDoan) {
			String output = i.getTenVanDe() + " ";
			System.out.print(output.substring(0, output.length()));
		}
		System.out.println();
		System.out.println("-->Hệ thống: Chúng tôi cần hỏi bạn một số câu hỏi để có được chuẩn đoán chính xác nhất");
		List<TrieuChung> trieuChungDaHoiNhungKhongMac = new ArrayList<>();
		for (VanDe i : benhDuDoan) {
			Set<TrieuChung> trieuChungCuaMotBenh = getTrieuChungCuaMotBenh(i.getId(), luatSuyDienLuis);
			for (TrieuChung y : trieuChungCuaMotBenh) {
				if (!trieuChungDaMac.contains(y) && !trieuChungDaHoiNhungKhongMac.contains(y)) {
					while (true) {
						System.out.println(
								"-->Hệ thống: Lợn nhà bạn có bị mắc thêm những triệu chứng sau đây,hãy quan sát những chú lợn của bạn thật kỹ và hãy trả lời tôi"
										+ y.getTrieuChung() + " không?\n\t1.Có\n\t2.Không");
						System.out.println("-------------Câu trả lời của bạn--------------");
						System.out.print("--> : Câu trả lời của tôi là: ");
						String answer = sc.nextLine().trim();
						if (answer.equals("1") || answer.equals("2")) {
							if (answer.equals("1") || answer.equals("có") || answer.equals("Có")) {
								trieuChungDaMac.add(y);
							} else {
								trieuChungDaHoiNhungKhongMac.add(y);
							}
							break;
						} else {
							System.out.println("-->Hệ thống: Bạn vui lòng chỉ nhập 1 hoặc 2");
						}
					}
				}
			}
			if (suyDienLuiDungDeChuanDoanBenh(luatSuyDienLuis, i, trieuChungDaMac)) {
				benhDaMac.add(i);
			}
		}
		return benhDaMac;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void inKetQuaDuDoan(List<VanDe> benhDaMac) {
		if (benhDaMac.isEmpty()) {
			System.out.println(
					"-->Hệ thống: Có vẻ lợn của bạn chưa xuất hiện dấu hiệu gì rõ ràng.\nBạn có thể cho tôi lại dấu hiệu chính xác của bệnh để chúng tôi có thể phán đoán rõ hơn!!!");
		} else {
			System.out.println(
					"-->Hệ thống: Dựa trên tất cả triệu chứng mà bạn đã nhập vào thì tôi có thể chuẩn đoán lợn của bạn mắc những bệnh sau: ");
			for (VanDe i : benhDaMac) {
				System.out.println("\t" + (benhDaMac.indexOf(i) + 1) + ". " + i.getTenVanDe().trim());
				System.out.println("\t\t-Nguyên nhân có thể do: ");
				List<String> nguyenNhan = List.of(i.getNguyenNhan().split("-"));
				for (String y : nguyenNhan) {
					System.out.println("\t\t\t+ " + y.trim());
				}
				System.out.println("\t\t-Lời khuyên dành mà chúng tôi dành cho những chú lợn của bạn: ");
				List<String> giaiPhap = List.of(i.getGiaiPhap().split("-"));
				for (String y : giaiPhap) {
					System.out.println("\t\t\t+ " + y.trim());
				}
			}
			System.out.println("-->Hệ thống: Cảm ơn bạn đã sử dụng hệ thống của chúng tôi!!! ");
		}
	}
}