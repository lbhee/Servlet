package kr.or.bit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.memodao;
import kr.or.bit.dto.memo;

@WebServlet("/MemoList")
public class MemoList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MemoList() {
        super();
  
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[ 목록보기 ]");
		
		//요청을 받고 서비스를 수행(service역할까지)
		//전체 조회 요청 -> DB서비스 -> DAO
		memodao dao = new memodao();
		try {
			
			List<memo> momolist = dao.getMemoList();
			
			//화면에 출력해서 클라이언트에게 전달(View사용 -> 별도의 jsp)
			//데이터저장
			request.setAttribute("memolist", momolist);
			//뷰페이지 설정
			RequestDispatcher dis = request.getRequestDispatcher("/memolist.jsp");
			//뷰페이지로 forward해서 전달
			dis.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
