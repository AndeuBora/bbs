<%@page import="bbs.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="dao" class="bbs.BbsDAO" />
<jsp:useBean id="dto" class="bbs.BbsDTO" />
<jsp:setProperty property="*" name="dto" />
<%
	//쿠키저장
	response.addCookie(Cookie_util.create_cookie("name", dto.getName(), "/", 3600 * 24 * 7));
	response.addCookie(Cookie_util.create_cookie("email", dto.getEmail(), "/", 3600 * 24 * 7));
	if(dto.getHomepage() != null){
		response.addCookie(Cookie_util.create_cookie("homepage", dto.getHomepage(), "/", 3600*24*7));
	}
	
	//추가
	dao.insert_data(dto);
	//이동
	response.sendRedirect("list.jsp");
%>