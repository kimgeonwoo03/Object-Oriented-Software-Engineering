<%@ page import="java.util.List" %>
<%@ page import="entity.Chemical" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    List<Chemical> chemicalList = (List<Chemical>) request.getAttribute("chemicalList");
    String condition = (String) request.getAttribute("condition");

    if (condition == null) {
        condition = "";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>화학물질 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>화학물질 목록</h2>

            <form action="${pageContext.request.contextPath}/chemicals" method="get">
                <input type="text" name="condition"
                       value="<%= condition %>"
                       placeholder="제품명, CAS No, 제조사 검색">
                <button type="submit" class="btn">검색</button>
            </form>

            <a href="${pageContext.request.contextPath}/chemicals/register" class="btn">화학물질 등록</a>

            <br><br>

            <table class="table">
                <thead>
                <tr>
                    <th>화학물질 ID</th>
                    <th>제품명</th>
                    <th>제조사</th>
                    <th>CAS No</th>
                    <th>용량</th>
                    <th>단위</th>
                    <th>등록일</th>
                    <th>상세</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (chemicalList != null && !chemicalList.isEmpty()) {
                        for (Chemical chemical : chemicalList) {
                %>
                <tr>
                    <td><%= chemical.getChemicalId() %></td>
                    <td><%= chemical.getProductName() %></td>
                    <td><%= chemical.getManufacturer() %></td>
                    <td><%= chemical.getCasNo() %></td>
                    <td><%= chemical.getCapacity() %></td>
                    <td><%= chemical.getUnit() %></td>
                    <td><%= chemical.getRegisteredDate() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/chemicals/detail?id=<%= chemical.getChemicalId() %>">
                            상세
                        </a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="8">등록된 화학물질이 없습니다.</td>
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
