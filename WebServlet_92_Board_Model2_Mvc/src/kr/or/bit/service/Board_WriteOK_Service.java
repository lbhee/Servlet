package kr.or.bit.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;

public class Board_WriteOK_Service implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String writer = request.getParameter("writer");
		String pwd = request.getParameter("pwd");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String filename = request.getParameter("filename");
		String homepage = request.getParameter("homepage");
		String email = request.getParameter("email");
		
		Board boarddata = new Board(0, writer, pwd, subject, content, null, 0, filename, 0, homepage, email, 0, 0, 0);
		
		ActionForward forward = new ActionForward();
		
		try {
			BoardDao dao = new BoardDao();
			int result = dao.writeok(boarddata); //Board 타입의 파라미터
			
			String msg="";
		    String url="";
		    if(result > 0){
		    	msg ="insert success";
		    	url ="list.do";
		    }else{
		    	msg="insert fail";
		    	url="write.do";
		    }
		    
		    request.setAttribute("board_msg",msg);
		    request.setAttribute("board_url", url);
					
		    forward.setPath("/board/redirect.jsp");
		    
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return forward;
	}
}
