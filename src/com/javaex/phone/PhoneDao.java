package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	private void getConnection() {
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: ����̹� �ε� ���� - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int personInsert(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			String query = "";
			query += " INSERT INTO person ";
			query += " VALUES (seq_person_id.nextval, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query); 

			pstmt.setString(1, personVo.getName()); 
			pstmt.setString(2, personVo.getHp()); 
			pstmt.setString(3, personVo.getCompany());

			count = pstmt.executeUpdate(); // ������ ����


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public List<PersonVo> getPersonList() {
		return getPersonList("");
	}

	public List<PersonVo> getPersonList(String keword) {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();

		try {

			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person";

			if (keword != "" || keword == null) {
				query += " where name like ? ";
				query += " or hp like  ? ";
				query += " or company like ? ";
				pstmt = conn.prepareStatement(query); 

				pstmt.setString(1, '%' + keword + '%'); 
				pstmt.setString(2, '%' + keword + '%'); 
				pstmt.setString(3, '%' + keword + '%');
			} else {
				pstmt = conn.prepareStatement(query); 
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return personList;

	}


	public int personUpdate(PersonVo personVo) {
		int count = 0;
		getConnection();

		try {

			String query = ""; 
			query += " update person ";
			query += " set name = ? , ";
			query += "     hp = ? , ";
			query += "     company = ? ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany()); 
			pstmt.setInt(4, personVo.getPersonId()); 

			count = pstmt.executeUpdate(); 


		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	public int personDelete(int personId) {
		int count = 0;
		getConnection();

		try {
			String query = ""; 
			query += " delete from person ";
			query += " where person_id = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, personId);

			count = pstmt.executeUpdate(); 

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	public void uimenu() {
		System.out.println("***********************************");
		System.out.println("*      전화번호 관리프로그램      *");
		System.out.println("***********************************");
	}
	
	public void listmenu() {

		System.out.println("");
		System.out.println("1.리스트 2.등록 3.수정 4.삭제 5.검색 6.종료");
		System.out.println("------------------------------");
		System.out.print(">메뉴번호 : ");

	}
	
	public void end() {
		System.out.println("");
		System.out.println("******************************");
		System.out.println("*         감사합니다         *");
		System.out.println("******************************");

	}
	
	public void error() {
		System.out.println("");
		System.out.println(" [다시 입력해 주세요.] ");
	}
	
	public void searcherror() {
		System.out.println("검색결과가없습니다 ");
	}
	
}