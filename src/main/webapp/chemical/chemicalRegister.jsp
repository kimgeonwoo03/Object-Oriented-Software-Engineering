<%@ page import="entity.Chemical" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<%
    Chemical koshaInfo = (Chemical) request.getAttribute("koshaInfo");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>화학물질 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>화학물질 등록</h2>

            <!-- KOSHA 조회 폼 -->
            <form action="${pageContext.request.contextPath}/chemicals/kosha" method="get">
                <div class="form-row">
                    <label>CAS No 기반 KOSHA 조회</label>
                    <input type="text" name="casNo" placeholder="예: 64-17-5">
                    <button type="submit" class="btn">KOSHA 조회</button>
                </div>
            </form>

            <hr>

            <!-- 화학물질 등록 폼 -->
            <form action="${pageContext.request.contextPath}/chemicals/register" method="post">
                <div class="form-row">
                    <label>화학물질 ID</label>
                    <input type="text" name="chemicalId">
                </div>

                <div class="form-row">
                    <label>제품명</label>
                    <input type="text" name="productName">
                </div>

                <div class="form-row">
                    <label>제조사</label>
                    <input type="text" name="manufacturer">
                </div>

                <div class="form-row">
                    <label>CAT No</label>
                    <input type="text" name="catNo">
                </div>

                <div class="form-row">
                    <label>CAS No</label>
                    <input type="text" name="casNo"
                           value="<%= koshaInfo != null ? koshaInfo.getCasNo() : "" %>">
                </div>

                <div class="form-row">
                    <label>용량</label>
                    <input type="text" name="capacity">
                </div>

                <div class="form-row">
                    <label>단위</label>
                    <input type="text" name="unit" placeholder="mL, g, kg 등">
                </div>

                <div class="form-row">
                    <label>구성성분 정보</label>
                    <textarea name="componentInfo"><%= koshaInfo != null ? koshaInfo.getComponentInfo() : "" %></textarea>
                </div>

                <div class="form-row">
                    <label>법적규제 현황</label>
                    <textarea name="legalRegulation"><%= koshaInfo != null ? koshaInfo.getLegalRegulation() : "" %></textarea>
                </div>

                <div class="form-row">
                    <label>GHS 표지</label>
                    <textarea name="ghsLabel"><%= koshaInfo != null ? koshaInfo.getGhsLabel() : "" %></textarea>
                </div>

                <div class="form-row">
                    <label>물리화학적 특성</label>
                    <textarea name="physicalProperties"><%= koshaInfo != null ? koshaInfo.getPhysicalProperties() : "" %></textarea>
                </div>

                <div class="form-row">
                    <label>유해·위험성 정보</label>
                    <textarea name="hazardInfo"><%= koshaInfo != null ? koshaInfo.getHazardInfo() : "" %></textarea>
                </div>

                <div class="form-row">
                    <label>혼합물 여부</label>
                    <select name="isComplex">
                        <option value="0">단일물질</option>
                        <option value="1">혼합물</option>
                    </select>
                </div>

                <button type="submit" class="btn">등록</button>
                <a href="${pageContext.request.contextPath}/chemicals" class="btn">목록</a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>