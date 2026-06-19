<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    request.setAttribute("currentPage", "hub");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>사용자 관리</h2>
            <p>아래 메뉴에서 원하는 기능을 선택하세요.</p>

            <jsp:include page="/user/userSubMenu.jsp" />
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
