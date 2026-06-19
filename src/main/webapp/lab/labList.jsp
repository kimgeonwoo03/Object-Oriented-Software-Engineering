<%@ page import="java.util.List" %>
<%@ page import="entity.LabBasicInfo" %>
<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>

<%
    List<LabBasicInfo> labList =
            (List<LabBasicInfo>) request.getAttribute("labList");

    String condition =
            (String) request.getAttribute("condition");

    String drawingRegistered =
            (String) request.getAttribute("drawingRegistered");

    if (condition == null) {
        condition = "";
    }

    if (drawingRegistered == null) {
        drawingRegistered = "";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>연구실 목록</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>
        .success-message {
            margin: 15px 0;
            padding: 14px 18px;
            border: 1px solid #9ad3a1;
            border-radius: 6px;
            background-color: #edf9ef;
            color: #236b2e;
            font-weight: bold;
        }
    </style>
</head>

<body>

<jsp:include page="/common/header.jsp"/>

<div class="layout">

    <jsp:include page="/common/sidebar.jsp"/>

    <main class="content">

        <div class="card">

            <h2>연구실 기본정보 조회</h2>

            <!-- 등록 완료 메시지 -->
            <c:if test="${not empty successMessage}">
                <div class="success-message">
                    <c:out value="${successMessage}"/>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/labs"
                  method="get">

                <input type="text"
                       name="condition"
                       value="<%= condition %>"
                       placeholder="연구실명, 소속, 책임자, 건물, 층, 호실 검색">

                <select name="drawingRegistered">

                    <option value=""
                        <%= "".equals(drawingRegistered)
                                ? "selected" : "" %>>
                        도면 등록 여부 전체
                    </option>

                    <option value="1"
                        <%= "1".equals(drawingRegistered)
                                ? "selected" : "" %>>
                        도면 등록
                    </option>

                    <option value="0"
                        <%= "0".equals(drawingRegistered)
                                ? "selected" : "" %>>
                        도면 미등록
                    </option>

                </select>

                <button type="submit" class="btn">
                    검색
                </button>
            </form>

            <br>

            <a href="${pageContext.request.contextPath}/labs/register"
               class="btn">
                연구실 등록
            </a>

            <br><br>

            <table class="table">

                <thead>
                <tr>
                    <th>연구실 ID</th>
                    <th>연구실명</th>
                    <th>소속</th>
                    <th>건물</th>
                    <th>층</th>
                    <th>호실</th>
                    <th>책임자 ID</th>
                    <th>도면 등록 여부</th>
                    <th>상세</th>
                </tr>
                </thead>

                <tbody>

                <%
                    if (labList != null
                            && !labList.isEmpty()) {

                        for (LabBasicInfo lab : labList) {
                %>

                <tr>
                    <td><%= lab.getLabId() %></td>
                    <td><%= lab.getLabName() %></td>
                    <td><%= lab.getDepartment() %></td>
                    <td><%= lab.getBuilding() %></td>
                    <td><%= lab.getFloor() %></td>
                    <td><%= lab.getRoomNo() %></td>
                    <td><%= lab.getManagerId() %></td>

                    <td>
                        <%= lab.getDrawingRegistered() == 1
                                ? "등록" : "미등록" %>
                    </td>

                    <td>
                        <a href="${pageContext.request.contextPath}/labs/detail?labId=<%= lab.getLabId() %>">
                            상세
                        </a>
                    </td>
                </tr>

                <%
                        }
                    } else {
                %>

                <tr>
                    <td colspan="9">
                        등록된 연구실 정보가 없습니다.
                    </td>
                </tr>

                <%
                    }
                %>

                </tbody>
            </table>

        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp"/>

</body>
</html>