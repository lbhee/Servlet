package study;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/three")
public class three extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public three() {
        super();
       
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response, "get");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response, "post");
	}	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response, String method) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn =null;
			PreparedStatement pstmt = null;
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","bituser","1004");
			

			String sql = "insert into teamthree(name,age,gender,phone) values(?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, age);
			pstmt.setString(3, gender);
			pstmt.setString(4, phone);
			
			int n = pstmt.executeUpdate();
			String viewpage = null;
			
			if(n > 0) {
	    		viewpage = "/out.jsp"; 
	    	}else { 
	    		viewpage = "/error.jsp";
	    	}
			
			RequestDispatcher dis = request.getRequestDispatcher(viewpage);
	    	
	    	dis.forward(request, response);
			

		} catch (Exception e) {
			e.getMessage();
		}
		
	}

}