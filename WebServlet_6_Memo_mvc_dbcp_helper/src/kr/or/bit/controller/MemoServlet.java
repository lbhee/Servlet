package kr.or.bit.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;
//서블릿하나만 가지고 작업가능하다(FrontServlet)
//현재 실습은 요청당 서블릿1개를 만들어서 하는 중
@WebServlet("/MemoServlet")
public class MemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemoServlet() {
        super();

    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//localhost:8090/WebServlet_4_Memo_mvc/Memoservlet 이렇게 요청이들어오면 실행된다.
    	
    	//insert
    	//1.한글처리
    	//2. 데이터받기
    	//3. 비즈니스(처리)
    	//4. 결과
    	
    	request.setCharacterEncoding("UTF-8");
    	
    	String id = request.getParameter("id");
    	String email = request.getParameter("email");
    	String content = request.getParameter("content");
    	
    	System.out.println(id + " / " + email + " / " + content);   //값이 들어갔는지 확인!
    	
    	//별도의 ui를 가지지않고, 삽입에 성공했다면 목록을 보여주는 로직을 짜보자. 실패하면 재입력
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	
    	try {
			
    		memodao dao = new memodao();
    		
    		//memo m = new memo(id, email, content);
    		//dao.insertMemo(m);
    		int row = dao.insertMemo(new memo(id, email, content));
    		
    		System.out.println("반영된 행의 수 : " + row); //DB에 반영됬는지 확인!
    		
    		if(row > 0) {
    			out.print("<script>");                      //스크립트에러는 웹 콘솔창에서 확인가능!
    				out.print("alert('등록성공');");
    				out.print("location.href='MemoList';"); //localhost:8090/WebServlet_4_Memo_mvc/Memoservlet 서버에게 다시 요청보내서 목록보기하기
    			out.print("</script>");
    		}else {
    			//
    		}

    	} catch (Exception e) {
    		out.print("<script>");
			out.print("alert('등록실패');");
			out.print("location.href='memo.html';");
			out.print("</script>");
			System.out.println(e.getMessage());
		}
    	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
