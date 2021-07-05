package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//서블릿파일만들때 함께 생성가능
@WebServlet(
		description = "서블릿은 클래스입니다", 
		urlPatterns = { 
				"/NowServlet", 
				"/Now.do", 
				"/Now.action", 
				"/Now.star"
		}, 
		initParams = { 
				@WebInitParam(name = "id", value = "bit", description = "id초기값 설정"), 
				@WebInitParam(name = "jdbcDriver", value = "oracle.jdbc.OracleDriver", description = "오라클 드라이버 제공")
		})
public class NowServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public NowServlet() {
        super();
        System.out.println("생성자는 객체생성시 한번 호출");
    }

	public void init(ServletConfig config) throws ServletException {
		//초기화 함수(명시적으로 이름을 부르지 않아도 어떠한 시점이 되면 자동호출되는 함수)
		//호출시점 : NowServlet 클래스파일에 대한 [최초요청시 한번만 실행]
		//재실행(개발자가 코드수정, 서버의 재시작)
		
		//it.co.kr에 서버오픈 > WAS (안에 서블릿 : NowServlet.java) 
		//홍길동이 첫 접속자 -> it.co.kr/NowServlet 요청 -> 이때 NowServlet 클래스 컴파일 ->
		// NowServlet 클래스 실행 -> 생성자호출 -> init함수 자동호출 -> get/post방식에 따라서 doGET or doPost 자동호출
		//김유신 접속자 -> 요청 -> NowServlet 클래스 실행 -> get/post방식에 따라서 doGET or doPost 자동호출
		
		//init : 클래스 내에 다른 함수가 사용하는 공통자원의 load와 초기화를 담당한다.
		
		//db연결시 한번만 해놓는다.init
		String drivername = config.getInitParameter("jdbcDriver");
		System.out.println("최초 요청시 한번 실행 : 드라이버 로딩 : " + drivername);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGET() call");
		
		//servlet -> jsp
		//servlet만 있었던 시대의 코드..이게 힘들어서 jsp가 나왔다.
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<html>");
			out.print("<head><title>Hello</title></head>");
			out.print("<body>");
				out.print("현재날짜 : " + new Date() + "<br>");
			out.print("</body>");
		out.print("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPOST() call");
	}

}
