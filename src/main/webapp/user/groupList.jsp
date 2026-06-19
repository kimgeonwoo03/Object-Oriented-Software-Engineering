<%@ page import="java.util.List" %>
<%@ page import="entity.Group" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    List<Group> groupList = (List<Group>) request.getAttribute("groupList");
    String condition = (String) request.getAttribute("condition");

    if (condition == null) {
        condition = "";
    }

    request.setAttribute("currentPage", "groupList");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>그룹 조회</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>그룹 조회</h2>

            <jsp:include page="/user/userSubMenu.jsp" />

            <hr>

            <form action="${pageContext.request.contextPath}/users/groups" method="get">
                <input type="text" name="condition"
                       value="<%= condition %>"
                       placeholder="그룹 ID, 그룹명, 관리자 ID 검색">
                <button type="submit" class="btn">검색</button>
            </form>

            <br>

            <a href="${pageContext.request.contextPath}/users" class="btn">사용자 관리 홈</a>

            <br><br>

            <table class="table">
                <thead>
                <tr>
                    <th>그룹 ID</th>
                    <th>그룹명</th>
                    <th>관리자 ID</th>
                    <th>설명</th>
                    <th>등록일</th>
                    <th>상태</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (groupList != null && !groupList.isEmpty()) {
                        for (Group group : groupList) {
                %>
                <tr>
                    <td><%= group.getGroupId() %></td>
                    <td><%= group.getGroupName() %></td>
                    <td><%= group.getManagerId() %></td>
                    <td><%= group.getDescription() != null ? group.getDescription() : "" %></td>
                    <td><%= group.getCreatedDate() %></td>
                    <td><%= group.getStatus() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">등록된 그룹이 없습니다.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
