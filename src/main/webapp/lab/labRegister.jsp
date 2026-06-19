<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>연구실 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/common/header.jsp" />

<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />

    <main class="content">
        <div class="card">
            <h2>연구실 기본정보 등록</h2>

            <form action="${pageContext.request.contextPath}/labs/register" method="post">
                <h3>기본정보</h3>

                <div class="form-row">
                    <label>연구실 ID</label>
                    <input type="text" name="labId" placeholder="예: LAB002">
                </div>

                <div class="form-row">
                    <label>연구실명</label>
                    <input type="text" name="labName" placeholder="예: 소프트웨어공학실">
                </div>

                <div class="form-row">
                    <label>소속</label>
                    <input type="text" name="department" placeholder="예: 컴퓨터공학과">
                </div>

                <div class="form-row">
                    <label>건물</label>
                    <input type="text" name="building" placeholder="예: 공학관">
                </div>

                <div class="form-row">
                    <label>층</label>
                    <input type="text" name="floor" placeholder="예: 4">
                </div>

                <div class="form-row">
                    <label>호실</label>
                    <input type="text" name="roomNo" placeholder="예: 401">
                </div>

                <div class="form-row">
                    <label>책임자 ID</label>
                    <input type="text" name="managerId" placeholder="예: labmanager">
                </div>

                <div class="form-row">
                    <label>출입자 정보</label>
                    <textarea name="accessUserInfo" placeholder="예: 홍길동, 김철수"></textarea>
                </div>

                <hr>

                <h3>도면 정보 선택 입력</h3>
                <p>도면 정보가 없으면 비워두고 연구실 기본정보만 등록할 수 있습니다.</p>

                <div class="form-row">
                    <label>도면 ID</label>
                    <input type="text" name="drawingId" placeholder="미입력 시 DRW_연구실ID 자동 사용">
                </div>

                <div class="form-row">
                    <label>도면명</label>
                    <input type="text" name="drawingName" placeholder="예: 공학관 4층 평면도">
                </div>

                <div class="form-row">
                    <label>파일 형식</label>
                    <select name="fileType">
                        <option value="">선택</option>
                        <option value="pdf">pdf</option>
                        <option value="png">png</option>
                        <option value="jpg">jpg</option>
                        <option value="dwg">dwg</option>
                        <option value="cad">cad</option>
                    </select>
                </div>

                <div class="form-row">
                    <label>파일 경로</label>
                    <input type="text" name="filePath" placeholder="예: /drawings/building4_floor4.pdf">
                </div>

                <div class="form-row">
                    <label>기준일</label>
                    <input type="date" name="baseDate">
                </div>

                <hr>

                <h3>위치 매칭 정보 선택 입력</h3>
                <p>도면 위 연구실 영역을 지정하지 않으면 비워둘 수 있습니다.</p>

                <div class="form-row">
                    <label>위치 ID</label>
                    <input type="text" name="locationId" placeholder="미입력 시 LOC_연구실ID 자동 사용">
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

                <button type="submit" class="btn">등록</button>
                <a href="${pageContext.request.contextPath}/labs" class="btn">목록</a>
            </form>
        </div>
    </main>
</div>

<jsp:include page="/common/footer.jsp" />

</body>
</html>
