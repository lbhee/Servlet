<%@page import="jdk.internal.misc.FileSystemOption"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!--
cos.jar 에서 다중 파일 업로드를 지원하는 객체가 바로 >> MultipartRequest  >> request 객체를 넘겨준다
***MultipartRequest 객체의 생성자**************************************************
      MultipartRequest(
          javax.servlet.http.HttpServletRequest request, 
          java.lang.String saveDirectory,
          int maxPostSize,
          java.lang.String encoding,
          FileRenamePolicy policy)
    
     request : MultipartRequest와 연결될 request객체를 의미한다. 
     saveDirectiory : 서버측에 파일이 실질적으로 저당될 경로를 의미한다. 
     maxPostSize : 한번에 업로드 할수있는 최대 파일 크기를 의미한다. 
     encoding : 파일의 이름 인코딩 방식을 의미한다. 
     policy : 파일이름 중복 처리를 위한 인자를 의미한다.  기본값 : 똑같은 파일 덮어쓰기
    
     ***MultipartRequest 객체의 메서드**************************************************
     getParameterNames() :  폼에서 전송된 파라미터의 타임이 file이 아닌 이름들을 Enumeration 타입으로 리턴한다. 
     getParameterValues() : 폼에서 전송된 파라미터 값들을 배열로 받아온다. 
     getParameter() : Request객체에 있는 해당 파라미터의 값을 가져온다. 
     getFileNames() : 파일을 여러개 업로드 할 경우 타입이 file인 파라미터 이름들을 Enumeration 타입으로 리턴한다. 
     getFileSystemName() : 서버에 실제로 업로드 된 파일의 이름을 의미한다. 
     getOriginalFileName() : 클라이언트가 업로드 한 파일의 원본 이름을 의미한다. 
     getContentType() : 업로드 된 파일의 컨텐트 타입을 얻을때 사용한다. 
     getFile() :  서버에 업로드 된 파일 객체 자체를 반환하는 메소드이다.





      가상경로(웹) : http://localhost:8090/WebServlet_93_FileUpload/upload/
      물리적 경로(웹 서버 특정경로) :
  1. D:\bit\WEBJSP\Labs\WebServlet_93_FileUpload\WebContent\upload
  2. 이클립스 톰켓 내장 : 실 배포 경로
         실제 파일 저장 경로(아래 있는 경로)
     D:\bit\WEBJSP\Labs\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\WebServlet_93_FileUpload\upload
-->     
<%
	String uploadpath = application.getRealPath("upload");
	System.out.println(uploadpath);
    
	//cos.jar 파일에서 제공하는 MultipartRequest 객체 사용
	
	//업로드 파일에 대한 기본 정보 세팅
	int size = 1024*1024*10; //10M 네이버 계산기
	String name="";
	String title="";
	String filename1="";
	String filename2="";
	String orifilename1="";
	String orifilename2="";
	
	
	MultipartRequest multi = new MultipartRequest(
		request, //javax.servlet.http.HttpServletRequest request의 주소값
		uploadpath, //실저장경로(배포된 경로)
		size, //최대10메가
		"UTF-8",
		new DefaultFileRenamePolicy() //파일 중복이 걸리면 자동으로 이름을 변경해서 업로드 (a.jpg -> a1.jpg로 자동 변경되서 업로드)
	);
	//여기까지의 코드가 서버에 upload폴더에 파일이 저장되는 코드
	//C:\Users\LEEBOHEE\bit2021\Web(Back)\WebLabs\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\WebServlet_92_FileUpload
	//업로드가 안되면 실제 배포폴더안에 upload폴더가 생겼는지 확인할 필요가 있다!
	
	
	//자료실(2가지 작업)
	//1. 서버에 파일 업로드(위의 코드)
	//2. 작성자, 제목, 내용, 파일명, 파일사이즈를 뽑아내서 DB에 insert하는 작업 (MultipartRequest에서 가져오기)
	name = multi.getParameter("name");
	title = multi.getParameter("title");
	String hid = multi.getParameter("hid");
	
 	Enumeration filenames = multi.getFileNames();
 	//<input type="file" name="filename1">
 	//<input type="file" name="filename2">
 	/*
 	while(filenames.hasMoreElements()) {
 		System.out.println(filenames.nextElement());
 		//input태그의 name값을 뽑아낸다.
 		//filename2
 		//filename1
 	}
 	*/
 	
 	String file2 = (String)filenames.nextElement(); //filename2에서
 	filename2 = multi.getFilesystemName(file2);
 	orifilename2 = multi.getOriginalFileName(file2); 
 	//기존에 a.jpg 있는데
 	//똑같은 a.jpg를 업로드시켰을때
 	//orifilename2 --> a.jpg (원래 이름)
 	//filename2 --> a1.jpg   (정책에 의해 자동변경된 이름)
 	
 	String file1 = (String)filenames.nextElement(); //filename1에서
 	filename1 = multi.getFilesystemName(file1);
 	orifilename1 = multi.getOriginalFileName(file1); 
 	
 	out.print("name : " + name + "<br>");
 	out.print("title : " + title + "<br>");
 	out.print("hid : " + hid + "<br>");
 	out.print("file2 : " + file2 + "<br>");
 	out.print("filename2 : " + filename2 + "<br>");
 	out.print("orifilename2 : " + orifilename2 + "<br>");
 	out.print("file1 : " + file1 + "<br>");
 	out.print("filename1 : " + filename1 + "<br>");
 	out.print("orifilename1 : " + orifilename1 + "<br>");
 	out.print("content : " + multi.getParameter("content") + "<br>");
 	//이렇게 받고 DB연결하기
 	//insert하기 [insert into fileboard(title, ...filename ...) values(?,?,?,...)]
 	//실행하고, 목록보기로 이동한 결과가 아래화면		
%>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판_목록보기</title>
</head>
<body>
	    <form action="Ex03_upload_detail.jsp" method="get" name="filelist">
	   	<input type="hidden" name="name" value="<%=name %>">
	   	<input type="hidden" name="title" value="<%=title %>">
	   	<input type="hidden" name="filename2" value="<%=filename2 %>">
	   	<input type="hidden" name="orifilename2" value="<%=orifilename2 %>">
	   	<input type="hidden" name="filename1" value="<%=filename1 %>">
	   	<input type="hidden" name="orifilename1" value="<%=orifilename1%>">
	   	<a href="#" onclick="javascript:filelist.submit()">상세보기</a>
	   	</form>
</body>
</html>