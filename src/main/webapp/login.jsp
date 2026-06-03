<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
  String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="content">
  <div class="card">
    <h2>연구실 안전관리 시스템 로그인</h2>

    <% if (errorMessage != null) { %>
    <div class="error-box">
      <%= errorMessage %>
    </div>
    <br>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="form-row">
        <label>사용자 ID</label>
        <input type="text" name="userId">
      </div>

      <div class="form-row">
        <label>비밀번호</label>
        <input type="password" name="password">
      </div>

      <button type="submit" class="btn">로그인</button>
    </form>

    <hr>

    <h3>테스트 계정</h3>
    <ul>
      <li>admin / 1234 / 시스템관리자</li>
      <li>group / 1234 / 그룹관리자</li>
      <li>labmanager / 1234 / 연구실책임자</li>
      <li>labsafety / 1234 / 연구실안전관리담당자</li>
      <li>worker / 1234 / 연구활동종사자</li>
      <li>safetyteam / 1234 / 안전관리팀 담당자</li>
      <li>external / 1234 / 외부연계시스템</li>
    </ul>
  </div>
</div>

</body>
</html>