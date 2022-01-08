package com.javaex.phone;

import java.util.List;
import java.util.Scanner;

public class PhoneApp {

	public static void main(String[] args) {

		PhoneDao phoneDao = new PhoneDao();
		Scanner sc = new Scanner(System.in);
		
		int personId;
		String name;
		String hp;
		String company;
		int count; 
		
		phoneDao.uimenu();
		boolean run = true;
		while (run) {
			phoneDao.listmenu();


			int menuNum = sc.nextInt();
			sc.nextLine(); 

			switch (menuNum) {

			case 1:
				System.out.println("<1.리스트>");
				List<PersonVo> personList = phoneDao.getPersonList();

				for (PersonVo vo : personList) {
					System.out.print(vo.getPersonId() + ".   ");
					System.out.print(vo.getName() + "\t");
					System.out.print(vo.getHp() + "\t");
					System.out.print(vo.getCompany() + "\t");
					System.out.println("");
				}
				break;

			case 2:
				System.out.println("<2.등록>");
				System.out.print("이름 > ");
				name = sc.nextLine();
				System.out.print("휴대전화 > ");
				hp = sc.nextLine();
				System.out.print("회사번호> ");
				company = sc.nextLine();
				
				PersonVo insertVo = new PersonVo(name, hp, company);
				
				count = phoneDao.personInsert(insertVo);
				System.out.println("["+ count + "건이 등록되었습니다.]");
				break;

			case 3:
				System.out.println("<3.수정>");
				// personId
				System.out.print("번호 > ");
				personId = sc.nextInt();
				sc.nextLine();
				System.out.print("이름 > ");
				name = sc.nextLine();
				System.out.print("휴대전화 > ");
				hp = sc.nextLine();
				System.out.print("회사번호 > ");
				company = sc.nextLine();
				
				PersonVo updateVo = new PersonVo(personId,  name, hp, company);
				count = phoneDao.personUpdate(updateVo);
				System.out.println("["+ count + "건이 수정되었습니다.]");
				break;

			// 4(����)
			case 4:
				System.out.println("<4.삭제>");
				System.out.print(">번호 : ");
				personId = sc.nextInt();
				count = phoneDao.personDelete(personId);
				System.out.println("["+ count + "건이 삭제되었습니다.]");
				break;

			case 5:
				System.out.println("<5.검색>");
				System.out.print("검색어> ");
				String keword = sc.nextLine();
				
				List<PersonVo> searchPersonList = phoneDao.getPersonList(keword);

				for (PersonVo vo : searchPersonList) {
//					System.out.print(vo.getpersonId() + ".   ");
					System.out.print(vo.getName() + "\t");
					System.out.print(vo.getHp() + "\t");
					System.out.print(vo.getCompany() + "\t");
					System.out.println("");
				}
				break;

			case 6:
				run = false;
				phoneDao.end();
				break;

			default:
				phoneDao.searcherror();
				break;

			}// switch() 끝

		} // while 끝


		sc.close();
	}


}