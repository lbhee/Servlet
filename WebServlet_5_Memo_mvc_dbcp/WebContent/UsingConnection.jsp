<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tomcat자원이 가지고 있는 Connection Pool사용하기</title>
</head>
<body>
<%

	/*
	커넥션 풀이란?
	웹 컨테이너(WAS)가 실행되면서 DB와 미리 connection(연결)을 해놓은 객체들을 pool에 저장해두었다가
	클라이언트 요청이 오면 connection을 빌려주고, 처리가 끝나면 다시 connection을 반납받아 pool에 저장하는 방식
	*/ 
	Connection conn= null;
	
	Context context = new InitialContext();  //현재 프로젝트에서 특정이름을 가진 녀석을 검색하겠다.(이름기반으로 검색) -> JNDI
	DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle"); //java:comp/env/ --> 여기까진 정해진 약속 + 이름(context.xml에서 설정된 name)
	//데이터소스타입의 ds에 검색해서 찾아온 커넥션을 넣는다. 
									
	//Pool안에서 connection을 가지고 오기
	conn = ds.getConnection();
	
	out.print("DB 연결여부 : " + conn.isClosed() + "<br>");
	
	//POINT
	//반드시 작업이 끝나면 [반환]해야한다!
	conn.close();
	out.print("DB 연결여부 : " + conn.isClosed() + "<br>");
%>
</body>
</html>