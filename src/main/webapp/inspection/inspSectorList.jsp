<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.InspSector" %>

<html>
<head>
    <title>점검 분야 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>점검 분야 목록</h2>

            <form method="get" action="${pageContext.request.contextPath}/inspections/sectors">
                <div class="form-row">
                    <label>사용 여부</label>
                    <select name="filter">
                        <option value="">전체</option>
                        <option value="1">사용</option>
                        <option value="0">미사용</option>
                    </select>
                </div>

                <button type="submit" class="btn">검색</button>
                <a class="btn" href="${pageContext.request.contextPath}/inspections/sectors/register">
                    점검 분야 등록
                </a>
            </form>

            <br>

            <table class="table">
                <tr>
                    <th>점검 분야 ID</th>
                    <th>점검 분야명</th>
                    <th>점검 분야 설명</th>
                    <th>사용 여부</th>
                    <th>등록일</th>
                    <th>상세</th>
                </tr>

                <%
                    List<InspSector> sectorList = (List<InspSector>) request.getAttribute("sectorList");

                    if (sectorList == null || sectorList.isEmpty()) {
                %>
                <tr>
                    <td colspan="6">등록된 점검 분야가 없습니다.</td>
                </tr>
                <%
                } else {
                    for (InspSector sector : sectorList) {
                %>
                <tr>
                    <td><%= sector.getSectorId() %></td>
                    <td><%= sector.getSectorName() %></td>
                    <td><%= sector.getSectorDesc() %></td>
                    <td><%= sector.getUseYn() == 1 ? "사용" : "미사용" %></td>
                    <td><%= sector.getRegDate() %></td>
                    <td>
                        <a class="btn" href="${pageContext.request.contextPath}/inspections/sectors/detail?sectorId=<%= sector.getSectorId() %>">
                            상세보기
                        </a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>