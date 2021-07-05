package com;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
 	web.xml설정
 	<url-pattern>/simple</url-pattern>
 	
 	localhost:8090:WebServlet_1/simple 이렇게 요청이 오면
 	
 	public class SimpleController 자바파일 컴파일 실행
 	
 	
 	<servlet> : java로 만든 웹서비스 파일
 	조건 - java파일이 HttpServlet를 상속해야한다. >> extends하면 java가 웹이 가진 요청(requset), 응답(response)객체를 사용할 수 있다.
 	    - servlet은 url에서 바로 요청이 안된다.  >> 요청 >> mapping >> 요청주소생성
 	                                                1. web.xml
 	                                                2. @Webservlet
 	
 	HttpServlet을 상속한 SimpleController는 웹전용 서블릿파일이 된다.
 	서블릿에도 특정사건(이벤트)이 발생하면 자동으로 호출되는 함수가 존재한다.
 	 - 자동호출함수 : protected void doGet() - 클라이언트가 서버에 요청한 방식(localhost:8090:WebServlet_1/simple)이 get방식이면 자동호출
 	 			  protected void doPost() - 요청방식이 post방식이면 자동호출
 	 			  
 	  doGet(HttpServletRequest request, HttpServletResponse response)
 	  doPost(HttpServletRequest request, HttpServletResponse response)
 	   --> 함수안에서 requset, response 객체를 사용가능
 	    			  
*/

public class SimpleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public SimpleController() {
        super();
        System.out.println("SimpleController 생성자 함수 호출");
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트 요청 : GET방식");
		
		//JSP페이지에서 작업한 내용을 여기에 그대로하면 된다.
		//1. 한글처리
		request.setCharacterEncoding("UTF-8");
		//2. 데이터 받기(클라이언트가 요청한 의도파악)
		String type = request.getParameter("type");
		//3. 로직(요청에 따른 업무수행) >> service
		Object resultobj = null;
		if(type == null || type.equals("greeting")) {
			resultobj = "hello world";
		}else if(type.equals("date")) {
			resultobj = new Date();
		}else {
			resultobj = "invalid String type";
		}
		//4. 요청완료에 따른 결과를 저장
		//MVC패턴방식 (화면 : jsp로 만든다) -> 서블릿에서 만든 객체정보를 JSP까지 전달
		//결과를 저장 : 모든페이지에 적용할거면 session변수, 특정페이지에 적용할거면(include, forward) request변수
		request.setAttribute("result", resultobj);
		
		//5. 저장한 결과를 JSP에 전달해서 UI구성
		//결과를 forward방식을 통해서 jsp에게 넘겨주자.(클라이언트가 요청한 주소는 변화가 없고, 내용만 달라진다)
		RequestDispatcher dis = request.getRequestDispatcher("/simpleview.jsp");  //view페이지를 정의하는 역할
		
		//정의한 페이지를 forward
		dis.forward(request, response);
		//servlet이 가지고 있는 request객체의 주소와 response객체의 주소를 넘겨서 사용..jsp
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트 요청 : POST방식");
	}

}
