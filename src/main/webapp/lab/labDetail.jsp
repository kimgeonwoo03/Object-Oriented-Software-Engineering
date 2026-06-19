<%@ page import="entity.LabBasicInfo" %>
<%@ page import="entity.DrawingInfo" %>
<%@ page import="entity.LabLocationInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    LabBasicInfo lab = (LabBasicInfo) request.getAttribute("lab");
    DrawingInfo drawingInfo = (DrawingInfo) request.getAttribute("drawingInfo");
    LabLocationInfo locationInfo = (LabLocationInfo) request.getAttribute("locationInfo");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>연구실 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>연구실 기본정보 상세</h2>

            <% if (lab != null) { %>
            <h3>기본정보</h3>
            <p><strong>연구실 ID:</strong> <%= lab.getLabId() %></p>
            <p><strong>연구실명:</strong> <%= lab.getLabName() %></p>
            <p><strong>소속:</strong> <%= lab.getDepartment() %></p>
            <p><strong>건물:</strong> <%= lab.getBuilding() %></p>
            <p><strong>층:</strong> <%= lab.getFloor() %></p>
            <p><strong>호실:</strong> <%= lab.getRoomNo() %></p>
            <p><strong>책임자 ID:</strong> <%= lab.getManagerId() %></p>
            <p><strong>출입자 정보:</strong> <%= lab.getAccessUserInfo() %></p>
            <p><strong>도면 등록 여부:</strong> <%= lab.getDrawingRegistered() == 1 ? "등록" : "미등록" %></p>
            <p><strong>등록일:</strong> <%= lab.getRegisteredDate() %></p>

            <hr>

            <h3>도면 정보</h3>
            <% if (drawingInfo != null) { %>
            <p><strong>도면 ID:</strong> <%= drawingInfo.getDrawingId() %></p>
            <p><strong>도면명:</strong> <%= drawingInfo.getDrawingName() %></p>
            <p><strong>파일 형식:</strong> <%= drawingInfo.getFileType() %></p>
            <p><strong>파일 경로:</strong> <%= drawingInfo.getFilePath() %></p>
            <p><strong>기준일:</strong> <%= drawingInfo.getBaseDate() %></p>
            <% } else { %>
            <p>연결된 도면 정보가 없습니다.</p>
            <a href="${pageContext.request.contextPath}/labs/drawings/register?labId=<%= lab.getLabId() %>" class="btn">도면 등록</a>
            <% } %>

            <hr>

            <h3>위치 매칭 정보</h3>
            <% if (locationInfo != null) { %>
            <p><strong>위치 ID:</strong> <%= locationInfo.getLocationId() %></p>
            <p><strong>X 좌표:</strong> <%= locationInfo.getCoordX() %></p>
            <p><strong>Y 좌표:</strong> <%= locationInfo.getCoordY() %></p>
            <p><strong>가로 영역:</strong> <%= locationInfo.getWidth() %></p>
            <p><strong>세로 영역:</strong> <%= locationInfo.getHeight() %></p>
            <% } else { %>
            <p>등록된 위치 매칭 정보가 없습니다.</p>
            <% if (drawingInfo != null) { %>
            <a href="${pageContext.request.contextPath}/labs/locations/register?labId=<%= lab.getLabId() %>" class="btn">위치 등록</a>
            <% } %>
            <% } %>

            <% } else { %>
            <p>연구실 정보가 없습니다.</p>
            <% } %>

            <br><br>
            <a href="${pageContext.request.contextPath}/labs" class="btn">목록</a>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
