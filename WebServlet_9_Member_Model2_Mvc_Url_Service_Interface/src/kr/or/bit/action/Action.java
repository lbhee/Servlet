package kr.or.bit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response); //서블릿이 가지고 있는 파라메터
	
	//당신이 만약 Action인터페이스를 구현한다면
	//당신은 execute함수를 반드시 구현해야한다.(강제)
	//execute() {return new ActionForward();}
}
