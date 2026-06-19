<%@ page import="common.Role" %>
<%@ page import="common.AccessPolicy" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    String roleName = (String) session.getAttribute("userRole");
    Role loginRole = Role.fromName(roleName);
    String contextPath = request.getContextPath();
    String currentPage = (String) request.getAttribute("currentPage");
    if (currentPage == null) {
        currentPage = "";
    }
%>

<nav class="sub-menu">
    <ul>
        <li class="<%= "approve".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">사용자 등록 승인</span>
        </li>
        <li class="<%= "bulk".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">사용자 대량 등록</span>
        </li>
        <li class="<%= "userList".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">사용자 조회</span>
        </li>
        <li class="<%= "userEdit".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">사용자 수정</span>
        </li>
        <li class="<%= "userDelete".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">사용자 삭제</span>
        </li>
        <li class="<%= "myProfile".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">내 정보 수정</span>
        </li>
        <li class="<%= "groupRegister".equals(currentPage) ? "menu-active" : "" %>">
            <% if (AccessPolicy.canRegisterGroup(loginRole)) { %>
            <a href="<%= contextPath %>/users/groups/register">그룹 등록</a>
            <% } else { %>
            <span class="menu-disabled">그룹 등록</span>
            <% } %>
        </li>
        <li class="<%= "groupList".equals(currentPage) ? "menu-active" : "" %>">
            <% if (AccessPolicy.canViewGroup(loginRole)) { %>
            <a href="<%= contextPath %>/users/groups">그룹 조회</a>
            <% } else { %>
            <span class="menu-disabled">그룹 조회</span>
            <% } %>
        </li>
        <li class="<%= "groupEdit".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">그룹 수정</span>
        </li>
        <li class="<%= "groupDelete".equals(currentPage) ? "menu-active" : "" %>">
            <span class="menu-disabled">그룹 삭제</span>
        </li>
    </ul>
</nav>
