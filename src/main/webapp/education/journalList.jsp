<%@ page import="java.util.List" %>
<%@ page import="entity.SafetyJournal" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    List<SafetyJournal> journalList = (List<SafetyJournal>) request.getAttribute("journalList");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    String labId = (String) request.getAttribute("labId");

    if (startDate == null) startDate = "";
    if (endDate == null) endDate = "";
    if (labId == null) labId = "";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>안전교육일지 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>안전교육일지 목록</h2>

            <form action="${pageContext.request.contextPath}/educations/journals" method="get">
                <div class="form-row">
                    <label>교육 일자 (시작)</label>
                    <input type="date" name="startDate" value="<%= startDate %>">
                    <label>교육 일자 (종료)</label>
                    <input type="date" name="endDate" value="<%= endDate %>">
                    <label>연구실 ID</label>
                    <input type="text" name="labId" value="<%= labId %>" placeholder="연구실 ID">
                    <button type="submit" class="btn">검색</button>
                </div>
            </form>

            <br>

            <a href="${pageContext.request.contextPath}/educations/journals/register" class="btn">안전교육일지 등록</a>

            <br><br>

            <table class="table">
                <thead>
                <tr>
                    <th>교육일지 ID</th>
                    <th>연구실 ID</th>
                    <th>교육 일자</th>
                    <th>교육 내용</th>
                    <th>참석자 수</th>
                    <th>작성자</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (journalList != null && !journalList.isEmpty()) {
                        for (SafetyJournal journal : journalList) {
                %>
                <tr>
                    <td><%= journal.getJournalId() %></td>
                    <td><%= journal.getLabId() %></td>
                    <td><%= journal.getEducationDate() %></td>
                    <td><%= journal.getContent() %></td>
                    <td><%= journal.getAttendeeCount() %></td>
                    <td><%= journal.getAuthorId() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">등록된 안전교육일지가 없습니다.</td>
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
