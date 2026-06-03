<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>연구실 안전관리 시스템</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>연구실 안전관리 시스템</h2>
            <p>좌측 메뉴에서 기능을 선택하세요.</p>

            <h3>구현 서브시스템</h3>
            <ul>
                <li>사용자 관리</li>
                <li>연구실 관리</li>
                <li>화학물질 관리</li>
                <li>폐기물 관리</li>
                <li>점검 관리</li>
                <li>안전교육 관리</li>
            </ul>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>