<%@ page import="java.util.List" %>
<%@ page import="entity.WasteDischarge" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    List<WasteDischarge> wasteList = (List<WasteDischarge>) request.getAttribute("wasteList");

    String fWasteId = (String) request.getAttribute("wasteId");
    String fLabId = (String) request.getAttribute("labId");
    String fDischargeDate = (String) request.getAttribute("dischargeDate");
    String fWasteType = (String) request.getAttribute("wasteType");
    String fWasteState = (String) request.getAttribute("wasteState");
    String fStatus = (String) request.getAttribute("status");

    if (fWasteId == null) fWasteId = "";
    if (fLabId == null) fLabId = "";
    if (fDischargeDate == null) fDischargeDate = "";
    if (fWasteType == null) fWasteType = "";
    if (fWasteState == null) fWasteState = "";
    if (fStatus == null) fStatus = "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>폐기물 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>폐기물 배출정보 목록</h2>

            <form action="${pageContext.request.contextPath}/wastes" method="get">
                <table class="table">
                    <thead>
                    <tr>
                        <th>폐기물 ID</th>
                        <th>연구실 ID</th>
                        <th>배출일</th>
                        <th>폐기물 종류</th>
                        <th>폐기물 상태</th>
                        <th>처리 상태</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input type="text" name="wasteId" value="<%= fWasteId %>"></td>
                        <td><input type="text" name="labId" value="<%= fLabId %>"></td>
                        <td><input type="text" name="dischargeDate" value="<%= fDischargeDate %>" placeholder="YYYY-MM-DD"></td>
                        <td><input type="text" name="wasteType" value="<%= fWasteType %>"></td>
                        <td><input type="text" name="wasteState" value="<%= fWasteState %>"></td>
                        <td><input type="text" name="status" value="<%= fStatus %>"></td>
                        <td><button type="submit" class="btn">검색</button></td>
                    </tr>
                    </tbody>
                </table>
            </form>

            <br>

            <a href="${pageContext.request.contextPath}/wastes/register" class="btn">폐기물 등록</a>

            <br><br>

            <table class="table">
                <thead>
                <tr>
                    <th>폐기물 ID</th>
                    <th>연구실 ID</th>
                    <th>배출일</th>
                    <th>폐기물 종류</th>
                    <th>폐기물 상태</th>
                    <th>수량</th>
                    <th>단위</th>
                    <th>처리 상태</th>
                    <th>상세</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (wasteList != null && !wasteList.isEmpty()) {
                        for (WasteDischarge waste : wasteList) {
                %>
                <tr>
                    <td><%= waste.getWasteId() %></td>
                    <td><%= waste.getLabId() %></td>
                    <td><%= waste.getDischargeDate() %></td>
                    <td><%= waste.getWasteType() %></td>
                    <td><%= waste.getWasteState() %></td>
                    <td><%= waste.getQuantity() %></td>
                    <td><%= waste.getUnit() %></td>
                    <td><%= waste.getStatus() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/wastes/detail?wasteId=<%= waste.getWasteId() %>">
                            상세
                        </a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="9">검색 결과가 없습니다.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>