package kr.or.bit.service;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;

public class Board_EditOK_Service implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		
		String idx = request.getParameter("idx");
		
		String msg="";
		String url="";
		
		ActionForward forward = new ActionForward();
		
		  if(idx == null || idx.trim().equals("")){
			  msg = "글번호 입력 오류";
			  url = "list.do";
		  }
		  
		try {
			BoardDao dao = new BoardDao();
			int result = dao.boardEdit(request);
			
			if(result > 0){
				msg="edit success";
				url="list.do";
			}else{
				msg="edit fail";
				url="edit.do?idx="+idx;
			}
			
			request.setAttribute("board_msg",msg);
			request.setAttribute("board_url",url);

			forward.setPath("/board/redirect.jsp");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return forward;
	}
}
