<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String currentRole = (String) session.getAttribute("userRole");

    // 로그인 기능 구현 전 임시 역할
    if (currentRole == null) {
        currentRole = "ADMIN";
        session.setAttribute("userRole", currentRole);
    }

    String contextPath = request.getContextPath();
%>

<aside class="sidebar">
    <nav>
        <ul>
            <li>
                <a href="<%= contextPath %>/index.jsp">홈</a>
            </li>

            <% if ("ADMIN".equals(currentRole)) { %>
                <li>
                    <a href="<%= contextPath %>/users/groups">사용자 관리</a>
                </li>
            <% } %>

            <% if ("ADMIN".equals(currentRole) || "SAFETY_MANAGER".equals(currentRole)) { %>
                <li>
                    <a href="<%= contextPath %>/labs">연구실 관리</a>
                </li>
                <li>
                    <a href="<%= contextPath %>/chemicals">화학물질 관리</a>
                </li>
                <li>
                    <a href="<%= contextPath %>/inspections/sectors">점검 관리</a>
                </li>
            <% } %>

            <% if ("ADMIN".equals(currentRole) || "SAFETY_MANAGER".equals(currentRole) || "LAB_MANAGER".equals(currentRole)) { %>
                <li>
                    <a href="<%= contextPath %>/wastes">폐기물 관리</a>
                </li>
            <% } %>

            <% if ("ADMIN".equals(currentRole) || "SAFETY_MANAGER".equals(currentRole) || "EDU_MANAGER".equals(currentRole)) { %>
                <li>
                    <a href="<%= contextPath %>/educations/journals">안전교육 관리</a>
                </li>
            <% } %>
        </ul>
    </nav>
</aside>