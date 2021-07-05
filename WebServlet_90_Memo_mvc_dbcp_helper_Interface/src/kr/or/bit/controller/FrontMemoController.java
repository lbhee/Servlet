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
import kr.or.bit.service.MemoAddService;
import kr.or.bit.service.MemoListService;

@WebServlet("*.do") 
public class FrontMemoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FrontMemoController() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	//1. 한글처리
    	request.setCharacterEncoding("UTF-8"); //파라메터로 전달되는 데이터 한글처리
    	response.setContentType("text/html;charset=UTF-8"); //브라우저로 보내지는 데이터 한글처리(뷰단.jsp파일없이 서블릿으로만 처리할때 필요함)
    	
    	//2. 데이터받기
    	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String urlcommand = requestURI.substring(contextPath.length());
    	
    	
    	System.out.println("requestURI : " + requestURI);   
    	System.out.println("contextPath : " + contextPath); 
    	System.out.println("urlcommand : " + urlcommand);
    	
    	//서비스클래스
    	Action action = null;
    	ActionForward forward = null;	    
    	
    	if(urlcommand.equals("/MemoList.do")) {
    		
    		action = new MemoListService();
    		forward = action.execute(request, response);
    			
    	}else if(urlcommand.equals("/MemoServlet.do")) {
    		
    		action = new MemoAddService();
    		forward = action.execute(request, response);
    	}
    	
    	if(forward != null) {
    		if(forward.isRedirect()) { //true
    			response.sendRedirect(forward.getPath());
    		}else { //false 
    			System.out.println("forward : " + forward.getPath());
    			RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    			dis.forward(request, response);
    		}
    	}
    	
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
