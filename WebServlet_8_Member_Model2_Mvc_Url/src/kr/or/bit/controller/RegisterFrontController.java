package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.bit.dao.Mvcregisterdao;
import kr.or.bit.dto.Mvcregister;

/*
	<Command방식>
	@WebServlet("/web.do") 고정되어있어도 상관없다.
	web.do?cmd=login
	web.do?cmd=loginok
	cmd로 판단하기때문에.
	
	<Url방식>
	주소가 고정되면 안된다.
	@WebServlet("*.do")
	*.do >> login.do
	*.do >> loginok.do

*/



@WebServlet("*.do")
public class RegisterFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegisterFrontController() {
        super();
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	/*
    	요청주소
    	목록보기    : list.do
    	글쓰기     : write.do
    	글쓰기 처리 : writeok.do 
		이렇게 요청이오면 doProcess를 실행
		
		어떤요청인지 판단하는 방법(서비스판단) 
		 1. command 방식 :  servlet.do?cmd=login&id=kglim&pwd=1004   > cmd > if(cmd.equals("login"))
  		 2. url 방식     :  login.do?id=kglim&pwd=1004  				>  login.do > url 주소로 요청을 판단
    	*/
    	
    	//1. 한글처리
    	request.setCharacterEncoding("UTF-8");
    	
    	//2. **데이터받기**(URL방식)
    	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String urlcommand = requestURI.substring(contextPath.length()); //문자열 자르기
    	
    	System.out.println("requestURI : " + requestURI);   // /WebServlet_8_Member_Model2_Mvc_Url/Register.do
    	System.out.println("contextPath : " + contextPath); // /WebServlet_8_Member_Model2_Mvc_Url
    	System.out.println("urlcommand : " + urlcommand);   // /Register.do
    	
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
    	
    	RequestDispatcher dis = request.getRequestDispatcher(viewpage);
    	
    	dis.forward(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
