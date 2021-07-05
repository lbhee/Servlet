package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import kr.or.bit.dto.memo;
import kr.or.bit.utils.SingletonHelper;

//DB의 데이터에 접근하기 위한 객체(데이터조회 및 조작을 전담한다.)

/*
	1. DB연결
	2. CRUD함수생성
		- 1개의 테이블에 대해서 (memo테이블에 대해서)
		  : 전체조회  select id, email, content from memo;
		    조건조회  select id, email, content from memo where id=? (id PK => 데이터1건이라는 확신)
		    삽입     insert into memo(id, email, content) values(?,?,?)
		    수정     update memo set email=? , content=?, where id=?
		    삭제     delete from memo where id=?
		    검색     where email like '%naver@%'
	

*/

public class memodao {
	
	
	/*
	 * 싱글톤을 사용한 DB연결은 학습용으로만.. 더이상은 사용하지않는다!
	Connection conn = null;
	
	public memodao() {
		conn = SingletonHelper.getConnection("oracle"); //singleton이니까 닫지않아요
	}
	*/
	
	//Pool방식의 코드사용
	DataSource ds = null;
	public memodao() {
		
		try {
			Context context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	//전체조회
	public List<memo> getMemoList() throws SQLException {
		
		PreparedStatement pstmt = null;
		String sql = "select id, email, content from memo";
		
		//Pool(모든코드마다 넣어줘야하는 것)/////////
		Connection conn = ds.getConnection();
		////////////////////////////////////
		
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<memo> memolist = new ArrayList<memo>(); //*******
		//[new memo()][new memo()][new memo()][new memo()]
		while(rs.next()) {
			memo m = new memo();
			m.setId(rs.getString("id"));
			m.setEmail(rs.getString("email"));
			m.setContent(rs.getString("content"));
			
			memolist.add(m);
		}
		
		SingletonHelper.close(rs);
		SingletonHelper.close(pstmt);
		
		//Pool반환/////
		conn.close();
		/////////////
		
		return memolist;
	}
	
	//조건조회(where id=?) -> 데이터1건 보장(id컬럼이 unique, primary key라는 조건하에)
	public memo getMemoListById(String id) {
		//select id, email, content from memo where id=?
		//결과가 1건이므로 memo m = new memo();  ,  return m
		return null;
	}
	

	//삽입(반영된 행의 개수)
	//public int insertMemo(String id, Stirng email, String content) {} --> 문제는 없지만 파라메터를 객체로 받자!
	public int insertMemo(memo m) {
		System.out.println(m.toString()); //DB로넘어갔는지 확인!
		
		Connection conn = null;
		
		int resultrow = 0;
		
		PreparedStatement pstmt = null;
		String sql = "insert into memo(id, email, content) values(?,?,?)";
		
		try {
			
			//Pool(모든코드마다 넣어줘야하는 것)/////////
			conn = ds.getConnection();
			////////////////////////////////////
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getContent());
			
			resultrow = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SingletonHelper.close(pstmt);
			
			try {
				conn.close();  //Pool반환
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return resultrow;
	}
	
	//수정
	public int updateMemo(memo m) {
		return 0;
	}
	
	//삭제
	public int deleteMemo(String id) {
		return 0;
	}
	
	//검색
	public memo idSearchByEmail(String email) {
		return null;
	}
	
	//아이디유무 판단함수
	public String isCheckById(String id) {
		
		Connection conn = null;
		String ismemoid = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select id from memo where id=?";
		
		try {
			
			//Pool(모든코드마다 넣어줘야하는 것)/////////
			conn = ds.getConnection();
			////////////////////////////////////
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//같은 id가 존재
				ismemoid = "false";
			}else {
				//사용가능한 id
				ismemoid = "true";
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			SingletonHelper.close(rs);
			SingletonHelper.close(pstmt);
			
			try {
				conn.close(); //Pool반환
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ismemoid;
	}
}
