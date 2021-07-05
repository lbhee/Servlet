package kr.or.bit.service;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;

public class MemoListService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		memodao dao = new memodao();
		List<memo> momolist = null;
		ActionForward forward = new ActionForward();
		try {
			
			momolist = dao.getMemoList();
			request.setAttribute("memolist", momolist);		
			forward.setRedirect(false);
			forward.setPath("/memolist.jsp");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forward;
	}

}
