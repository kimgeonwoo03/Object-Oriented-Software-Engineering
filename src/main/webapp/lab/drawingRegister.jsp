<%@ page import="entity.LabBasicInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    LabBasicInfo lab = (LabBasicInfo) request.getAttribute("lab");
    String labId = lab != null ? lab.getLabId() : "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도면 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>연구실 도면 등록</h2>

            <% if (lab != null) { %>
            <p><strong>대상 연구실:</strong> <%= lab.getLabName() %> (<%= lab.getLabId() %>)</p>
            <% } %>

            <form action="${pageContext.request.contextPath}/labs/drawings/register" method="post">
                <input type="hidden" name="labId" value="<%= labId %>">

                <div class="form-row">
                    <label>도면 ID</label>
                    <input type="text" name="drawingId" value="DRW_<%= labId %>">
                </div>

                <div class="form-row">
                    <label>도면명</label>
                    <input type="text" name="drawingName" placeholder="예: 공학관 3층 평면도">
                </div>

                <div class="form-row">
                    <label>파일 형식</label>
                    <select name="fileType">
                        <option value="pdf">pdf</option>
                        <option value="png">png</option>
                        <option value="jpg">jpg</option>
                        <option value="dwg">dwg</option>
                        <option value="cad">cad</option>
                    </select>
                </div>

                <div class="form-row">
                    <label>파일 경로</label>
                    <input type="text" name="filePath" placeholder="예: /drawings/building3_floor3.pdf">
                </div>

                <div class="form-row">
                    <label>기준일</label>
                    <input type="date" name="baseDate">
                </div>

                <button type="submit" class="btn">도면 등록</button>
                <a href="${pageContext.request.contextPath}/labs/detail?labId=<%= labId %>" class="btn">상세</a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
