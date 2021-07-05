package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.service.LoginOkServiceAction;
import kr.or.bit.service.RegisterOkServiceAction;

@WebServlet("*.do")
public class RegisterFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterFrontController() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    	
    	//1. 한글처리
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset=UTF-8");
    	
    	//2. **데이터받기**(URL방식)
    	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String urlcommand = requestURI.substring(contextPath.length()); //문자열 자르기
    	
    	System.out.println("requestURI : " + requestURI);   // /WebServlet_9/*.do
    	System.out.println("contextPath : " + contextPath); // /WebServlet_9
    	System.out.println("urlcommand : " + urlcommand);   // /*.do
    	
    	//kr.or.bit.service -> 서비스클래스
    	Action action = null;
    	ActionForward forward = null;
    	    	
    	if(urlcommand.equals("/Register.do")) { 
    		
    		//UI제공(서비스클래스필요없음)
    		forward = new ActionForward();
    		forward.setRedirect(false); //페이지주소변환
    		forward.setPath("/WEB-INF/Register/Register.jsp"); //뷰의 주소
    		
    	}else if(urlcommand.equals("/Registeok.do")) {
    		
    		//UI제공 + 로직처리 (서비스클래스필요함)
    		action = new RegisterOkServiceAction(); //action은 인터페이스니까 모든클래스 받을 수 있음(다형성)
    		forward = action.execute(request, response);
    		//*POINT* : execute함수에게 request객체의 주소와 response객체의 주소를 전달
    		
    	}else if(urlcommand.equals("/login.do")) {
    		
    		forward = new ActionForward();
    		forward.setRedirect(false);
    		forward.setPath("/WEB-INF/login/login.jsp"); //뷰의 주소가 바뀐다!
    		
    	}else if(urlcommand.equals("/loginok.do")) {
    		
    		action = new LoginOkServiceAction(); //인터테이스가 받는 객체가 바뀐다!
    		forward = action.execute(request, response);
    	}
    	
    	
    	if(forward != null) {
    		if(forward.isRedirect()) { //true라면 페이지를 재요청 (이렇게할일이 거의없다. 주소값이 바뀌기떄문에))
    			response.sendRedirect(forward.getPath());
    		}else { //false
    			//1. UI 전달된 경우
    			//2. UI + 로직 
    			System.out.println("forward : " + forward.getPath());
    			RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    			dis.forward(request, response);
    		}
    	}

    	
    	/*
    	//3. 요청판단해서 서비스 만들기(URl방식)
    	String viewpage = "";
    	
    	if(urlcommand.equals("/Register.do")) { //**마지막주소값**
    		//화면전달
    		viewpage = "/WEB-INF/Register/Register.jsp";
    	}else if(urlcommand.equals("/Registeok.do")) { //**마지막주소값**
    		//로직처리
    		//추가적인데이터
    		int id = Integer.parseInt(request.getParameter("id"));
    		String pwd = request.getParameter("pwd");
    		String email = request.getParameter("email");
    		
    		//controller -> [service요청] -> dao요청    		
    		Mvcregister dto = new Mvcregister();
    		dto.setId(id);
    		dto.setPwd(pwd);
    		dto.setEmail(email);
    		
    		Mvcregisterdao dao = new Mvcregisterdao();
    		int result = dao.writeOk(dto);
    		
    		
    		String resultdata = "";
    		if(result > 0) {
    			resultdata = "welcome to bit" + dto.getId() + "님";
    		}else {
    			resultdata = "Insert Fail retry";
    		}
    		
    		//결과 저장하기
    		request.setAttribute("data", resultdata);
    		//뷰페이지 만들기
    		viewpage = "/WEB-INF/Register/Register_welcome.jsp";
    	}
    	*/
    	//RequestDispatcher dis = request.getRequestDispatcher(viewpage);
    	
    	//dis.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
