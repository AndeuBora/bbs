<%@page import="bbs.Cookie_util"%>
<%@page import="bbs.BbsDTO"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="dao" class="bbs.BbsDAO" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록보기</title>
<link href="./cleditor/jquery.cleditor.css" rel="stylesheet"
	type="text/css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="./cleditor/jquery.cleditor.min.js"></script>
<script type="text/javascript"
	src="./cleditor/jquery.cleditor.table.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#content").cleditor({
			width : 500,
			height : 300
		})
	});

	//유효성검사하는 메소드
	function validate() {
		var editor = $("#content").cleditor()[0]; //??
		var f = document.form;
		if (f.name.value.replace(/\s/g, "") == "") {
			alert("이름 입력바람");
			f.name.focus();
			return;
		}
		if (f.email.value.replace(/\s/g, "") == "") {
			alert("email 입력바람");
			f.email.focus();
			return;
		}
		if (f.subject.value.replace(/\s/g, "") == "") {
			alert("제목 입력바람");
			f.subject.focus();
			return;
		}
		if (f.content.value.replace(/\s/g, "") == "") {
			alert("내용 입력바람");
			f.content.focus();
			return;
		}
		if (f.pass.value.replace(/\s/g, "") == "") {
			alert("비밀번호 입력바람");
			f.pass.focus();
			return;
		}
		f.submit();
	}

	//제목이랑비밀번호초기화
	function resets() {
		var e = $("#content").cleditor()[0]; //??
		var f = document.form;
		f.subject.value = "";
		f.pass.value = "";
		e.focus().clear();
	}

	//시작하면 이름에 포커스
	window.onload = function() {
		document.form.name.focus();
	}
</script>
</head>
<body>
	<%
	request.setCharacterEncoding("utf-8");
		String name = "";
		String email = "";
		String homepage = "";
		Cookie_util ck = new Cookie_util(request);
		if (ck.exists("name")) {
			name = ck.get_value("name");
		}
		if (ck.exists("email")) {
			email = ck.get_value("email");
		}
		if (ck.exists("homepage")) {
			homepage = ck.get_value("homepage");
		}
	%>
	<form name="form" method="post" action="write_action.jsp">
		<table>
			<tr>
				<td bgcolor="#c0c0c0" height="25" align="center" class="bold">
					글쓰기</td>
			</tr>
			<tr>
				<td align="center">
					<table>
						<tr>
							<td width="100%" align="right" colspan="2" class="redcolor">
								[ # 표시항목은 필수입력 항목입니다.]</td>
						</tr>
						<tr>
							<td width="15%"># 이름</td>
							<td width="85%"><input type="text" name="name" size="20"
								maxlength="12" value="<%= name%>"></td>
						</tr>
						<tr>
							<td width="15%"># e-mail</td>
							<td width="85%"><input type="text" name="email" size="40"
								maxlength="35" value="<%= email %>"></td>
						</tr>
						<tr>
							<td width="15%">홈페이지</td>
							<td width="85%">http://<input type="text" name="homepage" size="50"
								maxlength="45" value="<%= homepage %>"></td>
						</tr>
						<tr>
							<td width="15%"># 제목</td>
							<td width="85%"><input type="text" name="subject" size="80"
								maxlength="45"></td>
						</tr>
						<tr>
							<td width="15%"># 내용</td>
							<td width="85%"><textarea id="content" name="content"
									rows="15" cols="50"></textarea></td>
						</tr>
						<tr>
							<td width="15%"># 비밀번호</td>
							<td width="85%"><input type="password" name="pass" size="15"
								maxlength="12"></td>
						</tr>
						<tr>
							<td colspan="2"><hr size="2"></td>
						</tr>
						<tr>
							<td colspan="2"><input type="button" value="등록"
								onclick="validate()"> <input type="button" value="다시쓰기"
								onclick="resets()"><input type="button" value="뒤로"
								onclick="history.go(-1)"></td>
						</tr>
						<tr>
							<td colspan="2"><input type="hidden" name="ip"
								value="<%= request.getRemoteAddr() %>"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>