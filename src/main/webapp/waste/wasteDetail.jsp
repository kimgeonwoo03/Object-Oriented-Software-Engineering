<%@ page import="entity.WasteDischarge" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
  WasteDischarge waste = (WasteDischarge) request.getAttribute("waste");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>폐기물 상세</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
  <jsp:include page="/common/sidebar.jsp" />

  <main class="content">
    <div class="card">
      <h2>폐기물 배출정보 상세</h2>

      <% if (waste != null) { %>
      <p><strong>폐기물 ID:</strong> <%= waste.getWasteId() %></p>
      <p><strong>연구실 ID:</strong> <%= waste.getLabId() %></p>
      <p><strong>배출일:</strong> <%= waste.getDischargeDate() %></p>
      <p><strong>폐기물 종류:</strong> <%= waste.getWasteType() %></p>
      <p><strong>폐기물 상태:</strong> <%= waste.getWasteState() %></p>
      <p><strong>수량:</strong> <%= waste.getQuantity() %></p>
      <p><strong>단위:</strong> <%= waste.getUnit() %></p>
      <p><strong>보관 위치:</strong> <%= waste.getStorageLocation() %></p>
      <p><strong>처리 상태:</strong> <%= waste.getStatus() %></p>
      <% } else { %>
      <p>폐기물 정보가 없습니다.</p>
      <% } %>

      <a href="${pageContext.request.contextPath}/wastes" class="btn">목록</a>
    </div>
  </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>