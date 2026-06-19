<%@ page import="entity.LabBasicInfo" %>
<%@ page import="entity.DrawingInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    LabBasicInfo lab = (LabBasicInfo) request.getAttribute("lab");
    DrawingInfo drawingInfo = (DrawingInfo) request.getAttribute("drawingInfo");
    String labId = lab != null ? lab.getLabId() : "";
    String drawingId = drawingInfo != null ? drawingInfo.getDrawingId() : "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>연구실 위치 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>연구실 위치 매칭 등록</h2>

            <% if (lab != null) { %>
            <p><strong>대상 연구실:</strong> <%= lab.getLabName() %> (<%= lab.getLabId() %>)</p>
            <% } %>

            <% if (drawingInfo != null) { %>
            <p><strong>연결 도면:</strong> <%= drawingInfo.getDrawingName() %> (<%= drawingInfo.getDrawingId() %>)</p>
            <% } else { %>
            <p>위치 정보를 등록하려면 먼저 도면 정보가 필요합니다.</p>
            <% } %>

            <form action="${pageContext.request.contextPath}/labs/locations/register" method="post">
                <input type="hidden" name="labId" value="<%= labId %>">
                <input type="hidden" name="drawingId" value="<%= drawingId %>">

                <div class="form-row">
                    <label>위치 ID</label>
                    <input type="text" name="locationId" value="LOC_<%= labId %>">
                </div>

                <div class="form-row">
                    <label>X 좌표</label>
                    <input type="text" name="coordX" placeholder="예: 120">
                </div>

                <div class="form-row">
                    <label>Y 좌표</label>
                    <input type="text" name="coordY" placeholder="예: 80">
                </div>

                <div class="form-row">
                    <label>가로 영역</label>
                    <input type="text" name="width" placeholder="예: 200">
                </div>

                <div class="form-row">
                    <label>세로 영역</label>
                    <input type="text" name="height" placeholder="예: 100">
                </div>

                <button type="submit" class="btn">위치 등록</button>
                <a href="${pageContext.request.contextPath}/labs/detail?labId=<%= labId %>" class="btn">상세</a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
