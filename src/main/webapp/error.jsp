<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");

    if (errorMessage == null) {
        errorMessage = "알 수 없는 오류가 발생했습니다.";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>오류</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>오류 발생</h2>

            <div class="error-box">
                <%= errorMessage %>
            </div>

            <br>

            <button class="btn" onclick="history.back()">이전으로</button>
            <a class="btn" href="${pageContext.request.contextPath}/index.jsp">홈으로</a>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>