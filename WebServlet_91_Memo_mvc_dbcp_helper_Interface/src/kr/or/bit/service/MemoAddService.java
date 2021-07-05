package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.memodao;

public class MemoAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
    	String id = request.getParameter("id");
    	String email = request.getParameter("email");
    	String content = request.getParameter("memo");
    	
    	memodao dao = new memodao(); //POINT
		int result = dao.insertMemo(id, email, content);
    	
		 	String msg="";
		 	String url="";
		    if(result > 0){
		    	msg ="등록성공";
		    	url ="MemoList.memo";
		    }else{
		    	msg="등록실패";
		    	url="memo.html";
		    }
		    
		    request.setAttribute("board_msg",msg);  //request객체에 담아준다.
		    request.setAttribute("board_url", url); 
		
		    ActionForward forward = new ActionForward(); //ActionForward클래스 사용할거야.
		    forward.setRedirect(false);						//redirect은 이거고.
		    forward.setPath("/WEB-INF/views/redirect.jsp"); //path는 이거야.
		    
		return forward; //세팅한걸 반환해.
	}

}






