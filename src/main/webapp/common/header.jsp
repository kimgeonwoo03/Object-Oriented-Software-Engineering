<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<header class="header">
    <h1>연구실 안전관리 시스템</h1>

    <div class="user-info">
        <%
            String userRole = (String) session.getAttribute("userRole");

            // 로그인 기능 구현 전 임시 역할
            if (userRole == null) {
                userRole = "ADMIN";
                session.setAttribute("userRole", userRole);
            }
        %>
        <span>현재 역할: <%= userRole %></span>
    </div>
</header>
