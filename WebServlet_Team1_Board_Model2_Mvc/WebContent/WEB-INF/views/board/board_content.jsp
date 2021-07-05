<%@page import="kr.or.bit.dto.Reply"%>
<%@page import="java.util.List"%>
<%@page import="kr.or.bit.dto.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>board_content</title>
	<link rel="Stylesheet"
		href="${pageContext.request.contextPath}/style/default.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<c:set var="board" value="${requestScope.board}" />
	<c:set var="idx" value="${requestScope.idx }" />
	<c:set var="cpage" value="${requestScope.cp}" />
	<c:set var="pagesize" value="${requestScope.ps}" />
	<c:set var="replyList" value="${requestScope.replyList}" />
	<c:set var="path" value="<%=request.getContextPath()%>" />

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div id="pageContainer">
		<div style="padding-top: 30px; text-align: center">
			<center>
				<b>게시판 글내용</b>
				<table width="80%" border="1">
					<tr>
						<td width="20%" align="center"><b> 글번호 </b></td>
						<td width="30%">${idx}</td>
						<td width="20%" align="center"><b>작성일</b></td>
						<td>${board.writedate}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>글쓴이</b></td>
						<td width="30%">${board.writer}</td>
						<td width="20%" align="center"><b>조회수</b></td>
						<td>${board.readnum}</td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>홈페이지</b></td>
						<td>${board.homepage}</td>
						<td width="20%" align="center"><b>첨부파일</b></td>
						<td><a href="${path}/download.jsp?file_name=${board.filename}">${board.filename}</a></td>
					</tr>
					<tr>
						<td width="20%" align="center"><b>제목</b></td>
						<td colspan="3">${board.subject}</td>
					</tr>
					<tr height="100">
						<td width="20%" align="center"><b>글내용</b></td>
						<td colspan="3">${fn:replace(board.content, newLineChar,"<br>")}</td>
					</tr>
					<tr>
						<td colspan="4" align="center"><a
							href="BoardList.do?cp=${cpage}&ps=${pagesize}">목록가기</a> |<a
							href="BoardEdit.do?idx=${idx}&cp=${cpage}&ps=${pagesize}">편집</a> |<a
							href="BoardDelete.do?idx=${idx}&cp=${cpage}&ps=${pagesize}">삭제</a>
							|<a
							href="BoardRewrite.do?idx=${idx}&cp=${cpage}&ps=${pagesize}&subject=${board.subject}">답글</a>
						</td>
					</tr>
				</table>
				<!--  꼬리글 달기 테이블 -->
				<form name="reply" action="ReplyOk.do" method="POST">
						<!-- hidden 태그  값을 숨겨서 처리  -->
						<input type="hidden" name="idx" value="${idx}" id="idx"> 
						<input type="hidden" name="userid" value=""><!-- 추후 필요에 따라  -->
						<!-- hidden data -->
						<table width="80%" border="1">
							<tr>
								<th colspan="2">덧글 쓰기</th>
							</tr>
							<tr>
								<td align="left">작성자 :
								 	<input type="text" name="reply_writer" id="reply_writer"><br /> 
								 	내&nbsp;&nbsp;용 : 
								 	<textarea name="reply_content" rows="2" cols="50" id="reply_content"></textarea>
								</td>
								<td align="left">
									비밀번호:
									<input type="password" name="reply_pwd" size="4" id="reply_pwd"> 
									<input type="button" value="등록" id="reply_btn">
								</td>
							</tr>
						</table>
				</form>
				<br>
				<!-- 꼬리글 목록 테이블 -->
				<table width="80%" border="1" id="reply_list">
					<tr>
						<th colspan="2">REPLY LIST</th>
					</tr>
				</table>
							
			</center>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	ReplyWrite();
	ReplyList();
});

function ReplyWrite(){
	$('#reply_btn').click(function(){
		var frm = document.reply;
		if (frm.reply_writer.value == "" || frm.reply_content.value == ""
			|| frm.reply_pwd.value == "") {
					alert("리플 내용, 작성자, 비밀번호를 모두 입력해야합니다.");
			return false;
		}
		
		$.ajax(
				{
				   url:"Reply_Write",
				   data: {
						   	reply_writer: $('#reply_writer').val(),
							reply_content: $('#reply_content').val(),
							reply_pwd: $('#reply_pwd').val(),
							idx: $('#idx').val()
				   		 },
				   success:function(responsedata){ 

					   if(responsedata > 0){ //로직없이 ReplyList()을 바로 호출해줘도 된다. 
						   alert("댓글 입력 성공");
						   ReplyList();
						   $('#reply_writer').val("");
						   $('#reply_content').val("");
						   $('#reply_pwd').val("");
						   
					   }else{
						   alert("댓글 입력 실패");
					   }
				   },
				   error:function(xhr){
					   alert(xhr.status);
				   }
				}		
			);
	});
};

	
function ReplyList(){
		$('#reply_list').find('tr').not(":first").remove();
		
		$.ajax(
				{
					url:"Reply_List", 
					data:{idx:$('#idx').val()},
					dataType:"JSON",
					success:function(responsedata){ //JOSN객체배열(리스트)

						$.each(responsedata, function(index, obj){ 
							//console.log(obj);
							$('#reply_list').append(
									
									"<tr align='left'><td width='80%'>["+ obj.writer + "] : " + obj.content +
									"<br> 작성일 : " + obj.writedate + "</td><td width='20%'>" +
									"<form method='POST' name='replyDel'>" +
									"<input type='hidden' name='no' value='" + obj.no + "'>" +
									"<input type='hidden' name='idx' value='" + obj.Idx_fk + "'>" +
									"password :<input type='password' name='delPwd' size='4'>" +
									"<input type='button' value='삭제' onclick='ReplyDel(this.form)'>" +
									"</form></td></tr>"
							);
						});

					},
				  	error:function(xhr){
					   alert(xhr.status);
				   }
				}	
		);
	};
	

function ReplyDel(frm) {
	
	if (frm.delPwd.value == "") {
		alert("비밀번호를 입력하세요.");
		frm.delPwd.focus();
		return false;
	}
	
	$.ajax(
			{
				url:"Reply_Del",
				data:
					{
						idx: frm.idx.value,
						no: frm.no.value,
						delPwd: frm.delPwd.value
					},
				success:function(responsedata){
					ReplyList();
				},
			  	error:function(xhr){
					   alert(xhr.status);
				}
			}
	);
};
</script>
</html>

<!-- 
삭제시 비밀번호 틀렸을 때 해보기.

if($('#reply_pwd').val() != frm.delPwd.value) {
		alert("비밀번호가 틀렸습니다.");
		frm.delPwd.focus();
		return false;
	}
-->
				
