package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import kr.or.bit.dto.Mvcregister;
import kr.or.bit.utils.ConnectionHelper;

public class Mvcregisterdao {
	//CRUD함수 만들기
	
	//글쓰기 함수(writeOk)
	public int writeOk(Mvcregister dto) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int resultrow=0;
			
		try {
				conn = ConnectionHelper.getConnection("oracle");
				String sql="insert into mvcregister(id,pwd,email) values(?,?,?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getId());
				pstmt.setString(2, dto.getPwd());
				pstmt.setString(3, dto.getEmail());
				
				resultrow = pstmt.executeUpdate();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally {
			ConnectionHelper.close(pstmt);
			ConnectionHelper.close(conn);
		}
		return resultrow;
	}
	
}
