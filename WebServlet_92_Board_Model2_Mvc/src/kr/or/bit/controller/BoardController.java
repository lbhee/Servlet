package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.service.Board_DeleteOK_Service;
import kr.or.bit.service.Board_EditOK_Service;
import kr.or.bit.service.Board_ReplyDeleteOK_Service;
import kr.or.bit.service.Board_ReplyOK_Service;
//import kr.or.bit.service.Board_ReplyOK_Service;
import kr.or.bit.service.Board_RewriteOK_Service;
import kr.or.bit.service.Board_WriteOK_Service;

@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String requestURI = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String urlCommand = requestURI.substring(contextPath.length());

    	System.out.println("urlcommand : " + urlCommand);
    	
    	Action action = null;
    	ActionForward forward = new ActionForward();
    	
    	if(urlCommand.equals("/list.do")){ //목록보기
 
    		forward.setPath("/board/board_list.jsp");
    		
    	}else if(urlCommand.equals("/write.do")){ //글쓰기

    		forward.setPath("/board/board_write.jsp");
    		
    	}else if(urlCommand.equals("/writeok.do")) { //글쓰기 확인(완료)
    		
    		action = new Board_WriteOK_Service();			 
    		forward = action.execute(request, response); 
    		
    	}else if(urlCommand.equals("/content.do")) { //글 상세보기
    		
    		forward.setPath("/board/board_content.jsp");
    		
    	}else if(urlCommand.equals("/edit.do")) { //글 수정
    		
    		forward.setPath("/board/board_edit.jsp");
    		
    	}else if(urlCommand.equals("/editok.do")) { //글 수정 확인(완료)
    		
    		action = new Board_EditOK_Service();		 
    		forward = action.execute(request, response); 
    		
    	}else if(urlCommand.equals("/delete.do")) { //글 삭제

    		forward.setPath("/board/board_delete.jsp");
    		
    	}else if(urlCommand.equals("/deleteok.do")) { //글 삭제 확인(완료)
    		
    		action = new Board_DeleteOK_Service();		 
    		forward = action.execute(request, response); 
    		
    	}else if(urlCommand.equals("/rewrite.do")) { //답글쓰기
    		
    		forward.setPath("/board/board_rewrite.jsp");
    		
    	}else if(urlCommand.equals("/rewriteok.do")) { //답글쓰기 확인(완료)
    		
    		action = new Board_RewriteOK_Service();		 
    		forward = action.execute(request, response); 
    		
    	}else if(urlCommand.equals("/replyok.do")) { //댓글쓰기 확인(완료)
    		
    		action = new Board_ReplyOK_Service();
    		forward = action.execute(request, response); 
    		
    	}else if(urlCommand.equals("/replydeleteok.do")) { //댓글삭제 확인(완료)
    		
    		action = new Board_ReplyDeleteOK_Service();
    		forward = action.execute(request, response); 
    		
    	}

    	RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    	dis.forward(request, response);

	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
