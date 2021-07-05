package kr.or.bit.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.BoardDao;


@WebServlet("/Reply_Write")
public class Reply_Write extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Reply_Write() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String writer = request.getParameter("reply_writer");
		String content = request.getParameter("reply_content");
		String pwd = request.getParameter("reply_pwd");
		String idx = request.getParameter("idx");
		String userid = "empty";
		
		PrintWriter out = response.getWriter();

		try {
			
			BoardDao dao = new BoardDao();
			int idx_fk = Integer.parseInt(idx);
			int result = dao.replywrite(idx_fk, writer, userid, content, pwd);
			
			out.print(result); //result값을 $.ajax에서 사용할거면 필요하다.

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
