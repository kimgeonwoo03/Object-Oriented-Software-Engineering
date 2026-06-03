<%@ page import="entity.Chemical" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    Chemical chemical = (Chemical) request.getAttribute("chemical");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>화학물질 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>화학물질 상세</h2>

            <% if (chemical != null) { %>
            <p><strong>화학물질 ID:</strong> <%= chemical.getChemicalId() %></p>
            <p><strong>제품명:</strong> <%= chemical.getProductName() %></p>
            <p><strong>제조사:</strong> <%= chemical.getManufacturer() %></p>
            <p><strong>CAT No:</strong> <%= chemical.getCatNo() %></p>
            <p><strong>CAS No:</strong> <%= chemical.getCasNo() %></p>
            <p><strong>용량:</strong> <%= chemical.getCapacity() %></p>
            <p><strong>단위:</strong> <%= chemical.getUnit() %></p>
            <p><strong>구성성분 정보:</strong> <%= chemical.getComponentInfo() %></p>
            <p><strong>법적규제 현황:</strong> <%= chemical.getLegalRegulation() %></p>
            <p><strong>GHS 표지:</strong> <%= chemical.getGhsLabel() %></p>
            <p><strong>물리화학적 특성:</strong> <%= chemical.getPhysicalProperties() %></p>
            <p><strong>유해·위험성 정보:</strong> <%= chemical.getHazardInfo() %></p>
            <p><strong>혼합물 여부:</strong> <%= chemical.getIsComplex() == 1 ? "혼합물" : "단일물질" %></p>
            <p><strong>등록일:</strong> <%= chemical.getRegisteredDate() %></p>
            <% } else { %>
            <p>화학물질 정보가 없습니다.</p>
            <% } %>

            <a href="${pageContext.request.contextPath}/chemicals" class="btn">목록</a>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>