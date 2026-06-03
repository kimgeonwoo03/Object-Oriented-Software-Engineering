<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    String userName = (String) session.getAttribute("userName");
    String userRole = (String) session.getAttribute("userRole");

    if (userName == null) {
        userName = "비로그인";
    }

    if (userRole == null) {
        userRole = "NONE";
    }

    String contextPath = request.getContextPath();
%>

<header class="header">
    <h1>연구실 안전관리 시스템</h1>

    <div class="user-info">
        <span>사용자: <%= userName %></span>
        <span> | 역할: <%= userRole %></span>
        <a href="<%= contextPath %>/logout">로그아웃</a>
    </div>
</header>