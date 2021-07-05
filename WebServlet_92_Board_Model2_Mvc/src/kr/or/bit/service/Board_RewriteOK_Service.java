package kr.or.bit.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Board;

public class Board_RewriteOK_Service implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		int dix = Integer.parseInt(request.getParameter("idx"));
		String writer = request.getParameter("writer");
		String email = request.getParameter("email");
		String homepage = request.getParameter("homepage");
		String pwd = request.getParameter("pwd");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String filename = request.getParameter("filename");
		
		Board board = new Board(dix, writer, pwd, subject, content, null, dix, filename, dix, homepage, email, dix, dix, dix);
		
		ActionForward forward = new ActionForward();
		
		try {
			BoardDao dao = new BoardDao();
			int result = dao.reWriteOk(board); //Board타입의 파라미터
			
			//list 이동시 현재 pagesize , cpage(필요에 따라서  url ="board_list.jsp?cp=<%=cpage";)
			String cpage = request.getParameter("cp"); //current page 
			String pagesize = request.getParameter("ps"); //pagesize
			
			String msg="";
		    String url="";
		    if(result > 0){
		    	msg ="rewrite insert success";
		    	url ="list.do";
		    }else{
		    	msg="rewrite insert fail"; 
		    	url="content.do?idx="+board.getIdx();
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
