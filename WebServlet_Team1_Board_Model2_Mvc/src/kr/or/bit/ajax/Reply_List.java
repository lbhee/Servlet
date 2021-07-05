package kr.or.bit.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.Reply;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/Reply_List")
public class Reply_List extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Reply_List() {
        super();
    }

    
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	response.setContentType("text/html;charset=UTF-8");
    	
    	PrintWriter out = response.getWriter();
    	
    	String idx = request.getParameter("idx");

			try {
				
				BoardDao dao = new BoardDao();
				
				List<Reply> replylist = dao.replylist(idx);
				
				DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
				
				JSONArray jsonarr = new JSONArray();
				
				for(int i = 0; i < replylist.size(); i++) {
					String writedate = date.format(replylist.get(i).getWritedate()); //JSON객체로 만들어줄떄는 Date타입은 들어갈 수 없어서
					
					JSONObject jsonobj = new JSONObject(); // {"no":"1", "writer":"홍길동" ......},
					jsonobj.put("no", replylist.get(i).getNo());  
					jsonobj.put("writer", replylist.get(i).getWriter());
					jsonobj.put("userid", replylist.get(i).getUserid());
					jsonobj.put("pwd", replylist.get(i).getPwd());
					jsonobj.put("content", replylist.get(i).getContent());
					jsonobj.put("writedate", writedate);
					jsonobj.put("Idx_fk", replylist.get(i).getIdx_fk());
					
					jsonarr.add(jsonobj); //JSON객체를 JSON객체배열에 추가
				}
				out.print(jsonarr);
				
			} catch (NamingException e) {
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


/*
 response.setContentType("text/html;charset=UTF-8");
    	
    	String idx = request.getParameter("idx");
    	
    	try {
			BoardDao dao = new BoardDao();
			List<Reply> replylist = dao.replylist(idx);
			
			PrintWriter out = response.getWriter();
			
			String tr = "<tr align='left'>";
			
			for(Reply list : replylist) {
				tr += "<td width='80%'>["+ list.getWriter() + "] : " + list.getContent() ;
				tr += "<br> 작성일 : " + list.getWritedate().toString() + "</td><td width='20%'>";
				tr += "<form method='POST' name='replyDel'>";
				tr += "<input type='hidden' name='no' value='" + list.getNo() + "'>"; 
				tr += "<input type='hidden' name='idx' value='" + list.getIdx_fk() + "'>";
				tr += "password :<input type='password' name='delPwd' size='4'>";
				tr += "<input type='button' value='삭제'>"; 
				tr += "</form>";
				tr += "</td></tr>";
			}

			out.print(tr);
			
		} catch (NamingException e) {
			e.printStackTrace();
		}
 */
