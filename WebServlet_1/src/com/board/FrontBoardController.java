package com.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/FrontBoardController") //web.xml에서 설정하기!
public class FrontBoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public FrontBoardController() {
        super();

    }
    
    private void doProcess(HttpServletRequest request, HttpServletResponse response, String method) throws ServletException, IOException{
    	//GET, POST 어떤 방식으로 들어와도 이 함수가 실행한다.
    	//들어오는 method에 따라 요청이 get인지 post인지 확인가능
    	System.out.println("클라이언트 요청 : " + method);
    	
    	//1. 한글처리
    	
    	//2. 데이터요청받기(request)
    	
    	//3. 요청판단
    	//command 방식 --> parameter 기준
    	//localhost:8090:WebServlet_1/board?cmd=login&di=lee&pwd=1004 서버로 요청
    	//서버는 cmd변수가 가지는 값을 가지고 판단 : login >> 로그인처리를 요청하는구나 알 수 있음.
    	//localhost:8090:WebServlet_1/board?cmd=list로 요청이 오면 게시판보여달라는거구나 알 수 있음.
    	//String command = request.getParameter("cmd");
    	//if(command.equals("login") { 로그인 서비스 처리 }
    	//else if(command.equls("list")) { 게시판 목록보기 서비스 }
    	
    	//URL 방식(권장방법)
    	//localhost:8090:WebServlet_1/board/boardlist
    	//localhost:8090:WebServlet_1/board/boardwrite?title=방가&content=방가방가
    	//마지막주소값을 추출 /boardlist --> 게시판목록보기라고 판단
    	//              /boardwrite?title=방가&content=방가방가  --> 게시판글쓰기라고 판단
    	
    	//4. 결과저장 (request, session, application 안에다가)
    	
    	//5. view지정
    	//view page : *.jsp
    	//WebContent > board > boardlist.jsp
    	//WebContent > error > error404.jsp
    	//위 방식은 보안상 문제가 있다. 클라이언트가 직접받아보지 않아도 되는 것들을 접근이 가능해짐.
    	//따라서 실제개발시에는 WebContent > WEB_INF > views 폴더생성 > board , member , admin 폴더관리
    	// ex) 보안폴더 : WEB-INF > views > board > boardlist.jsp
    	
    	//6. view에게 request객체를 forward해서 사용가능하도록 만들어주기.
    	
//-------------------------------------------------------------------------------------------------------
    	
    	request.setCharacterEncoding("UTF-8");
    	
    	String cmd = request.getParameter("cmd");
    	
    	//요청판단
    	String viewpage = null;
    	
    	//cmd가 null이면 error.jsp 서비스
    	//cmd가 boardlist면 list.jsp 서비스
    	//cmd가 boardwrite면 write.jsp 서비스
    	if(cmd == null) {
    		viewpage = "/error/error.jsp"; //error.jsp를 클라이언트에게 전달하자
    	}else if(cmd.equals("boardlist")) {
    		//실제업무처리
    		/*
    		  DB연결하고 select해서 객체를 담고 저장을 하자
    		  boardDao dao = new boardDao();
    		  List<board> boardlist = dao.selectBoardList();
    		  request.setAttribute("list", boardlist);
    		  forward -> view 전달 (request.getAttribute() 가지고와서 화면출력하겠다.)
    		  출력시에는 EL과 JSTL을 사용하겠다.    		  
    		 */
    		viewpage = "/board/boardlist.jsp"; //boardlist.jsp를 서비스하자
    	}else if(cmd.equals("boardwrite")) {
    		viewpage = "/board/boardwrite.jsp"; //boardwrite.jsp를 서비스하자
    	}else if(cmd.equals("login")) {
    		viewpage = "/WEB-INF/views/login/login.jsp";
    	}else {
    		viewpage = "/error/error.jsp";
    	}
    	
    	//결과저장
    	//List<Emp> list = new ArrayList<>();
    	//list.add(new Emp(200, 김유신);
    	//request.setAttribute("emplist", list);
    	
    	//view지정
    	RequestDispatcher dis = request.getRequestDispatcher(viewpage);
    	
    	//데이터전달(forward)
    	dis.forward(request, response);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response, "GET");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response, "POST");
	}

}
