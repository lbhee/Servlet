package kr.or.bit.action;
/*
역할 : servlet 요청을 받는다.
      요청 1. 화면보여주세요
	      2. 처리해주세요
	      
화면,로직,뷰의 경로를 담는 클래스
*/
public class ActionForward {
	private boolean isRedirect = false; //화면전환여부
	private String path = null;         //이동경로(뷰의 주소)
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}
