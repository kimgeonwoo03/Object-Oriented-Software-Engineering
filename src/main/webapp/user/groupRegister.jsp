<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    request.setAttribute("currentPage", "groupRegister");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>그룹 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>그룹 등록</h2>

            <jsp:include page="/user/userSubMenu.jsp" />

            <hr>

            <form action="${pageContext.request.contextPath}/users/groups/register" method="post">
                <div class="form-row">
                    <label>그룹 ID</label>
                    <input type="text" name="groupId">
                </div>

                <div class="form-row">
                    <label>그룹명</label>
                    <input type="text" name="groupName">
                </div>

                <div class="form-row">
                    <label>관리자 ID</label>
                    <input type="text" name="managerId">
                </div>

                <div class="form-row">
                    <label>설명</label>
                    <textarea name="description"></textarea>
                </div>

                <button type="submit" class="btn">등록</button>
                <a href="${pageContext.request.contextPath}/users/groups" class="btn">목록</a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
