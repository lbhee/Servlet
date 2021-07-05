package kr.or.bit.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;

public class MemoAddService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
    	String email = request.getParameter("email");
    	String content = request.getParameter("content");

    	//memo m = new memo(); [권장방법]
    	//m.setId(id);
    	
    	//int row = dao.insertMemo(new memo(m));

    	ActionForward forward =new ActionForward();
    	
    	try {
			PrintWriter out = response.getWriter();
    		memodao dao = new memodao();
    		int row = dao.insertMemo(new memo(id, email, content));
    		
    		System.out.println("반영된 행의 수 : " + row); //DB에 반영됬는지 확인!
    		
    		if(row > 0) {
    			out.print("<script>");                      
    				out.print("alert('등록성공');");
    			out.print("</script>");
    			forward.setRedirect(false);
    			forward.setPath("/MemoList.do");
    		}else {
    			//
    		}
    	} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return forward;
	}

}
