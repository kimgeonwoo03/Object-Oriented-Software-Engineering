<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="entity.InspSector" %>

<html>
<head>
    <title>점검 분야 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>점검 분야 상세</h2>

            <%
                InspSector sector = (InspSector) request.getAttribute("sector");

                if (sector == null) {
            %>
            <div class="error-box">
                점검 분야 정보를 찾을 수 없습니다.
            </div>
            <%
            } else {
            %>
            <table class="table">
                <tr>
                    <th>점검 분야 ID</th>
                    <td><%= sector.getSectorId() %></td>
                </tr>
                <tr>
                    <th>점검 분야명</th>
                    <td><%= sector.getSectorName() %></td>
                </tr>
                <tr>
                    <th>점검 분야 설명</th>
                    <td><%= sector.getSectorDesc() %></td>
                </tr>
                <tr>
                    <th>사용 여부</th>
                    <td><%= sector.getUseYn() == 1 ? "사용" : "미사용" %></td>
                </tr>
                <tr>
                    <th>등록일</th>
                    <td><%= sector.getRegDate() %></td>
                </tr>
            </table>
            <%
                }
            %>

            <br>

            <a class="btn" href="${pageContext.request.contextPath}/inspections/sectors">
                목록
            </a>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>