<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>폐기물 등록</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
  <jsp:include page="/common/sidebar.jsp" />

  <main class="content">
    <div class="card">
      <h2>폐기물 배출정보 등록</h2>

      <form action="${pageContext.request.contextPath}/wastes/register" method="post">

        <div class="form-row">
          <label>폐기물 ID</label>
          <input type="text" name="wasteId">
        </div>

        <div class="form-row">
          <label>연구실 ID</label>
          <input type="text" name="labId">
        </div>

        <div class="form-row">
          <label>배출일</label>
          <input type="date" name="dischargeDate">
        </div>

        <div class="form-row">
          <label>폐기물 종류</label>
          <input type="text" name="wasteType" placeholder="예: 폐산, 폐알칼리, 폐유기용제">
        </div>

        <div class="form-row">
          <label>폐기물 상태</label>
          <input type="text" name="wasteState" placeholder="예: 액체, 고체, 기체">
        </div>

        <div class="form-row">
          <label>수량</label>
          <input type="number" name="quantity" step="0.01" min="0">
        </div>

        <div class="form-row">
          <label>단위</label>
          <input type="text" name="unit" placeholder="예: L, kg, g">
        </div>

        <div class="form-row">
          <label>보관 위치</label>
          <input type="text" name="storageLocation">
        </div>

        <button type="submit" class="btn">등록</button>
        <a href="${pageContext.request.contextPath}/wastes" class="btn">목록</a>
      </form>
    </div>
  </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>