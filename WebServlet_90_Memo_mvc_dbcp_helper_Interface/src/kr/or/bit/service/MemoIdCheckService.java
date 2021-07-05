package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.memodao;

public class MemoIdCheckService implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
    	memodao dao = new memodao();
    	String isCheck = dao.isCheckById(id);
    	/*
    	ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("/Memoid.do");
		*/
		return null;
	}

}
