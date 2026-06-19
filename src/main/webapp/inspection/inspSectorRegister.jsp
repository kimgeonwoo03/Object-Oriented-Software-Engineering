<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    String formError = (String) request.getAttribute("formError");

    String sectorIdError = (String) request.getAttribute("sectorIdError");
    String sectorNameError = (String) request.getAttribute("sectorNameError");
    String sectorDescError = (String) request.getAttribute("sectorDescError");
    String useYnError = (String) request.getAttribute("useYnError");

    String sectorId = (String) request.getAttribute("sectorId");
    String sectorName = (String) request.getAttribute("sectorName");
    String sectorDesc = (String) request.getAttribute("sectorDesc");
    String useYn = (String) request.getAttribute("useYn");

    if (sectorId == null) sectorId = "";
    if (sectorName == null) sectorName = "";
    if (sectorDesc == null) sectorDesc = "";
    if (useYn == null) useYn = "1";
%>

<html>
<head>
    <title>점검 분야 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <style>
        .field-error {
            margin-top: 4px;
            color: #dc2626;
            font-size: 13px;
            font-weight: bold;
        }

        .input-error {
            border: 1px solid #dc2626 !important;
            background-color: #fff7f7;
        }
    </style>
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>점검 분야 등록</h2>

            <% if (formError != null) { %>
            <div class="error-box">
                <%= formError %>
            </div>
            <br>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/inspections/sectors/register">
                <div class="form-row">
                    <label>점검 분야 ID</label>
                    <input
                            type="text"
                            name="sectorId"
                            value="<%= sectorId %>"
                            placeholder="예: INS002"
                            class="<%= sectorIdError != null ? "input-error" : "" %>"
                    />

                    <% if (sectorIdError != null) { %>
                    <div class="field-error"><%= sectorIdError %></div>
                    <% } %>
                </div>

                <div class="form-row">
                    <label>점검 분야명</label>
                    <input
                            type="text"
                            name="sectorName"
                            value="<%= sectorName %>"
                            placeholder="예: 전기 안전"
                            class="<%= sectorNameError != null ? "input-error" : "" %>"
                    />

                    <% if (sectorNameError != null) { %>
                    <div class="field-error"><%= sectorNameError %></div>
                    <% } %>
                </div>

                <div class="form-row">
                    <label>점검 분야 설명</label>
                    <textarea
                            name="sectorDesc"
                            rows="5"
                            placeholder="점검 분야 설명을 입력하세요"
                            class="<%= sectorDescError != null ? "input-error" : "" %>"><%= sectorDesc %></textarea>

                    <% if (sectorDescError != null) { %>
                    <div class="field-error"><%= sectorDescError %></div>
                    <% } %>
                </div>

                <div class="form-row">
                    <label>사용 여부</label>
                    <select
                            name="useYn"
                            class="<%= useYnError != null ? "input-error" : "" %>"
                    >
                        <option value="1" <%= "1".equals(useYn) ? "selected" : "" %>>사용</option>
                        <option value="0" <%= "0".equals(useYn) ? "selected" : "" %>>미사용</option>
                    </select>

                    <% if (useYnError != null) { %>
                    <div class="field-error"><%= useYnError %></div>
                    <% } %>
                </div>

                <button type="submit" class="btn">등록</button>
                <a class="btn" href="${pageContext.request.contextPath}/inspections/sectors">
                    목록
                </a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>