<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>안전교육일지 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>안전교육일지 등록</h2>

            <form action="${pageContext.request.contextPath}/educations/journals/register" method="post">

                <div class="form-row">
                    <label>연구실 ID</label>
                    <input type="text" name="labId" required>
                </div>

                <div class="form-row">
                    <label>교육 일자</label>
                    <input type="date" name="educationDate" required>
                </div>

                <div class="form-row">
                    <label>교육 내용</label>
                    <textarea name="content" required></textarea>
                </div>

                <div class="form-row">
                    <label>참석자 수</label>
                    <input type="number" name="attendeeCount" min="1" required>
                </div>

                <button type="submit" class="btn">등록</button>
                <a href="${pageContext.request.contextPath}/educations/journals" class="btn">목록</a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
