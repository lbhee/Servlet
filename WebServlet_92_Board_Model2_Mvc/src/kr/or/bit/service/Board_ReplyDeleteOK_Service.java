package kr.or.bit.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;

public class Board_ReplyDeleteOK_Service implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String idx_fk=request.getParameter("idx");
		String no = request.getParameter("no");
		String pwd = request.getParameter("delPwd");

		String msg="";
	    String url="";
	    
		ActionForward forward = new ActionForward();
		
		if(idx_fk == null || no == null || pwd == null || no.trim().equals("")){ //사실상 없어도 되는 코드
			msg ="글번호가 넘어오지 않았습니다.";
			url="content.do?idx="+idx_fk;
		}
		
		try {
			BoardDao dao = new BoardDao();
			int result = dao.replyDelete(no, pwd);

		    if(result > 0){
		    	msg ="댓글 삭제 성공";
		    	url ="content.do?idx="+idx_fk;
		    }else{
		    	msg="댓글 삭제 실패";
		    	url="content.do?idx="+idx_fk;
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
