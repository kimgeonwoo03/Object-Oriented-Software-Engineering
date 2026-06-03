<%@ page import="common.Role" %>
<%@ page import="common.AccessPolicy" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    String roleName = (String) session.getAttribute("userRole");
    Role loginRole = Role.fromName(roleName);
    String contextPath = request.getContextPath();
%>

<aside class="sidebar">
    <nav>
        <ul>
            <li>
                <a href="<%= contextPath %>/index.jsp">홈</a>
            </li>

            <% if (AccessPolicy.canAccessUserManagement(loginRole)) { %>
            <li>
                <a href="<%= contextPath %>/users/groups">사용자 관리</a>
            </li>
            <% } %>

            <% if (AccessPolicy.canAccessLabManagement(loginRole)) { %>
            <li>
                <a href="<%= contextPath %>/labs">연구실 관리</a>
            </li>
            <% } %>

            <% if (AccessPolicy.canAccessChemicalManagement(loginRole)) { %>
            <li>
                <a href="<%= contextPath %>/chemicals">화학물질 관리</a>
            </li>
            <% } %>

            <% if (AccessPolicy.canAccessWasteManagement(loginRole)) { %>
            <li>
                <a href="<%= contextPath %>/wastes">폐기물 관리</a>
            </li>
            <% } %>

            <% if (AccessPolicy.canAccessInspectionManagement(loginRole)) { %>
            <li>
                <a href="<%= contextPath %>/inspections/sectors">점검 관리</a>
            </li>
            <% } %>

            <% if (AccessPolicy.canAccessEducationManagement(loginRole)) { %>
            <li>
                <a href="<%= contextPath %>/educations/journals">안전교육 관리</a>
            </li>
            <% } %>
        </ul>
    </nav>
</aside>