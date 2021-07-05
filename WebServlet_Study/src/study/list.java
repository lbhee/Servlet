package study;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/list")
public class list extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public list() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8"); 

         
        try { 
         Class.forName("oracle.jdbc.OracleDriver");
         Connection conn = null;
         PreparedStatement  pstmt = null;
         ResultSet rs = null;
         
         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","bituser", "1004");
           
            String sql="select * from teamthree"; 
            
            pstmt = conn.prepareStatement(sql); 
             
            rs = pstmt.executeQuery(); 
  
			//String listpage = null;
			
            
			
			if(rs.next()) {
				do{
					System.out.println("이름: " + rs.getString("name") + " / 나이:" + rs.getInt("age") + 
									    " / 성별: "+ rs.getString("gender") + " / 번호 :" + rs.getString("phone"));
				}while(rs.next());
			}else {
				System.out.println("조회된 데이터가 없습니다.");
			}
			
			
			//RequestDispatcher dis = request.getRequestDispatcher(listpage);
	    	
	    	//dis.forward(request, response);	
             
        } catch (Exception e) { 
            
        } 
      
	}
}
