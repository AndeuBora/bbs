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
<link href="../style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function check_value() {
		var f = document.search;
		if (f.word.value.replace(/\s/g, "")) {
			alert("검색어 입력하시오");
			f.word.focus();
			return;
		}
		f.submit();
	}
	function list() {
		var l = document.list;
		l.action = "list.jsp";
		l.submit();
	}
	function read(value) { //제목클릭시
		var r = document.read;
		r.action = "read.jsp";
		r.num.value = value;
		r.submit();
	}
</script>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");

		//초기화
		int now_page = 0;
		int now_block = 0;
		int total_record = 0; //레코드 (행) 전체글수
		int total_page = 0;
		int total_block = 0;
		int begin_per_page = 0; //페이지 시작 번호
		int num_per_page = 10; //페이지당 레코드 수
		int page_per_block = 10; // 블록당 페이지 수

		String field = ""; //검색필드
		String word = ""; //검색초기화

		if (request.getParameter("word") != null) {
			word = request.getParameter("word");
			field = request.getParameter("field");
		}
		if (request.getParameter("reload") != null) {
			if (request.getParameter("reload").equals("true")) {
				word = "";
				field = "";
			}
		}

		//전체게시글
		Vector<Object> list = dao.get_data_list(field, word);
		total_record = list.size();
		if (request.getParameter("page") != null) {
			now_page = Integer.parseInt(request.getParameter("page"));
		}
		begin_per_page = now_page * num_per_page; //페이지별 시작번호=현페이지번호*묶음
		total_page = (int) Math.ceil((double) total_record / num_per_page); //전체페이지

		if (request.getParameter("now_block") != null) {
			now_block = Integer.parseInt(request.getParameter("now_block"));
		}

		total_block = (int) Math.ceil((double) total_page / page_per_block); //전체블록 개수
	%>
	<table>
		<tr>
			<td colspan="2" align="center">
				<h2>목록보기 - 게시판</h2>
			</td>
		</tr>
		<tr>
			<td align="left"># 검색어 : <%
				if (word.equals("")) {
			%> 없음 <%
				} else {
			%> <font color="red"><%=word%></font> <%
 	}
 %>
			</td>
			<td align="right">전체글수:<%=total_record%>개 (<font color="red"><%=now_page + 1%>/<%=total_page%>page</font>)
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<table>
					<tr bgcolor="#c0c0c0" height="120%" class="bold">
						<td align="center" width="50">번호</td>
						<td align="center" width="350">제목</td>
						<td align="center" width="50">이름</td>
						<td align="center" width="100">날짜</td>
						<td align="center" width="40">조회수</td>
					</tr>
					<%
						if (list.isEmpty()) {
					%>
					<tr>
						<td colspan="5" align="center">등록된 글이 없습니다ㅠㅠ..</td>
					</tr>
					<%
						} else {
							for (int i = begin_per_page; i < (begin_per_page + num_per_page); i++) {
								if (i == total_record) {
									break;
								}
								BbsDTO dto = (BbsDTO) list.elementAt(i);
								String name = dto.get_name_short();
								String subject = dto.getSubject();
								String email = dto.getEmail();
								String regdate = dto.getRegdate();
								int depth = dto.getDepth();
								int num = dto.getNum();
								int count = dto.getCount();
					%>
					<tr align="center">
						<td><%=total_record - i%></td>
						<td>
							<%
								if (depth > 0) {
											for (int j = 0; j < depth; j++) {
							%> &nbsp;&nbsp; <%
 	}
 %> ㄴRE: <%
 	}
 %> <a href="javascript:read('<%=num%>')"><%=subject%></a>
						</td>
						<td align="center"><%=name%></td>
						<td align="center"><%=regdate%></td>
						<td align="center"><%=count%></td>

					</tr>
					<tr>
						<td colspan="5" height="1" bgcolor="#E7E7E7"></td>
					</tr>
					<%
						}
						}
					%>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2"><br> <br></td>
		</tr>
		<tr>
			<td align="left">페이지 이동 <%
				if (total_record != 0) { //전체글수가 0이아니라면
					if (now_block > 0) { //현재 블록이 0보다 클경우
			%> <a
				href="list.jsp?now_bolck=<%=now_block - 1%>&page=<%=(now_block - 1) * page_per_block%>&field=<%=field%>&word=<%=word%>">이전
					${page_per_block }개</a> <%
 	}
 %>◀<%
 	for (int i = 0; i < page_per_block; i++) {
 %> <a
				href="list.jsp?now_bolck=<%=now_block%>&page=<%=now_block * page_per_block + i%>&field=<%=field%>&word=<%=word%>"><span
					style="font-size: 14pt; font-weight: bold;"><%=(now_block * page_per_block) + i + 1%></span></a>
				<%
					if ((now_block * page_per_block) + i + 1 == total_page) {
								break;
							} //현재블록*블록개수+i+1==전체총페이지수
						} //for문
				%>▶<%
					if (total_block > now_block) {
				%> <a
				href="list.jsp?now_bolck=<%=now_block+1 %>&page=<%=(now_block+1)*page_per_block %>&field=<%=field %>&word=<%= word%>">다음<%= page_per_block%>개</a>
				<%
					}
					}
				%>
			</td>
			<td align="right"><a href="write.jsp">[글쓰기]</a> <a
				href="javascript:list()">[처음으로]</a></td>
		</tr>
	</table>

	<!-- 검색폼작성 -->
	<form name="search" method="post" action="list.jsp">
		<table>
			<tr>
				<td align="center" valign="bottom"><select name="field" size=1>
						<option value="name">이름</option>
						<option value="subject">제목</option>
						<option value="content" selected="selected">내용</option>
				</select> <input type="text" size="20" name="word"><input
					type="button" value="search" onclick="check_value()"> <input
					type="hidden" name="page" value="0"></td>
			</tr>
		</table>
	</form>
	<!-- 검색결과목록 페이지로 보냄 -->
	<form name="read" method="post">
		<input type="hidden" name="num" value=""> <input type="hidden"
			name="page" value="<%=now_page%>"> <input type="hidden"
			name="field" value="<%=field%>"> <input type="hidden"
			name="word" value="<%=word%>">
	</form>
	<form name="list" method="post">
		<input type="hidden" name="reload" value="true"> <input
			type="hidden" name="page" value="0"> <input type="hidden"
			name="now_block" value="0">
	</form>
</body>
</html>