# 프로젝트 구조 및 확장 가이드

## 기술 스택

| 항목 | 내용 |
|---|---|
| 언어 | Java 17 |
| 서버 | Tomcat 10 (내장, cargo 플러그인) |
| 뷰 | JSP (JSTL 포함) |
| 데이터 저장 | JSON 파일 (`data/` 폴더) |
| 빌드 | Maven (WAR 패키징) |
| API 규격 | Jakarta Servlet 6.0 |

DB가 없고 JSON 파일로 데이터를 관리한다. `JsonFileUtil`이 파일 읽기/쓰기를 담당한다.

---

## 아키텍처: MVC 레이어

```
브라우저
  │  HTTP 요청
  ▼
[Filter] LoginCheckFilter          ← 비로그인 시 /login 으로 리다이렉트
  │
  ▼
[Controller] *Servlet              ← @WebServlet("/url")
  │  doGet: 폼/목록 화면 렌더링
  │  doPost: 데이터 처리 후 리다이렉트
  ▼
[Service] *Service                 ← 유효성 검사, 비즈니스 로직
  ▼
[Repository] *Repository           ← JSON 파일 CRUD
  ▼
[Util] JsonFileUtil                ← data/*.json 파일 읽기·쓰기
  ▼
data/*.json                        ← 실제 데이터 저장소
```

---

## 패키지 구조

```
src/main/java/
├── common/
│   ├── Role.java              ← 역할 enum (7가지 역할)
│   └── AccessPolicy.java      ← 역할별 기능 접근 권한 정의
│
├── entity/                    ← 데이터 모델 (getter/setter만 있는 POJO)
│   ├── User.java
│   └── Chemical.java
│
├── repository/                ← JSON 파일 CRUD
│   ├── AuthRepository.java
│   └── ChemicalRepository.java
│
├── service/                   ← 비즈니스 로직 + 유효성 검사
│   ├── AuthService.java
│   └── ChemicalService.java
│
├── controller/
│   ├── auth/
│   │   ├── LoginServlet.java
│   │   └── LogoutServlet.java
│   └── chemical/
│       ├── ChemicalListServlet.java
│       ├── ChemicalRegisterServlet.java
│       ├── ChemicalDetailServlet.java
│       └── KoshaSearchServlet.java
│
├── filter/
│   └── LoginCheckFilter.java  ← 모든 요청에 적용되는 로그인 필터
│
├── exception/                 ← 커스텀 예외 (Service에서 throw, Servlet에서 catch)
│   ├── DatabaseException.java
│   ├── DuplicateDataException.java
│   ├── EmptyFieldException.java
│   ├── ExternalApiException.java
│   └── NotFoundException.java
│
└── util/
    ├── AuthUtil.java          ← 세션에서 로그인 정보 꺼내는 헬퍼
    ├── DateUtil.java          ← 오늘 날짜 문자열 반환
    ├── JsonFileUtil.java      ← JSON 파일 읽기/쓰기 핵심 유틸
    └── ValidationUtil.java    ← 필드 필수값 검증

src/main/webapp/
├── WEB-INF/web.xml
├── common/
│   ├── header.jsp
│   ├── sidebar.jsp            ← 역할별 메뉴 노출 (AccessPolicy 사용)
│   └── footer.jsp
├── chemical/
│   ├── chemicalList.jsp
│   ├── chemicalRegister.jsp
│   └── chemicalDetail.jsp
├── css/style.css
├── index.jsp
├── login.jsp
└── error.jsp

data/                          ← JSON 데이터 파일
├── user_info.json
├── chemical.json
├── safety_journal.json        ← 안전교육일지 데이터 (현재 빈 배열)
└── ...
```

---

## 핵심 규칙

### 1. 역할 (Role)

```
SYSTEM_ADMIN       시스템관리자     (admin / 1234)
GROUP_ADMIN        그룹관리자       (group / 1234)
LAB_MANAGER        연구실책임자     (labmanager / 1234)
LAB_SAFETY_MANAGER 연구실안전관리담당자 (labsafety / 1234)
RESEARCH_WORKER    연구활동종사자   (worker / 1234)
SAFETY_TEAM_MANAGER 안전관리팀 담당자 (safetyteam / 1234)
EXTERNAL_SYSTEM    외부연계시스템   (external / 1234)
```

### 2. 권한 체계 (AccessPolicy)

안전교육 관련 권한은 이미 `AccessPolicy.java`에 정의되어 있다.

```java
// 안전교육 메뉴 접근 가능 역할
canAccessEducationManagement(role)
  → SYSTEM_ADMIN, LAB_MANAGER, LAB_SAFETY_MANAGER, RESEARCH_WORKER, SAFETY_TEAM_MANAGER

// 안전교육일지 등록 가능 역할
canRegisterEducation(role)
  → SYSTEM_ADMIN, LAB_SAFETY_MANAGER, SAFETY_TEAM_MANAGER

// 안전교육일지 조회 가능 역할
canViewEducation(role)
  → SYSTEM_ADMIN, LAB_MANAGER, LAB_SAFETY_MANAGER, RESEARCH_WORKER, SAFETY_TEAM_MANAGER
```

### 3. Servlet 패턴

모든 Servlet은 아래 구조를 따른다.

```java
@WebServlet("/url")
public class XxxServlet extends HttpServlet {

    private XxxService xxxService = new XxxService();

    @Override
    protected void doGet(...) {
        // 1. 권한 확인 (AccessPolicy)
        // 2. 데이터 조회 (Service)
        // 3. request.setAttribute(...)
        // 4. JSP로 forward
    }

    @Override
    protected void doPost(...) {
        request.setCharacterEncoding("UTF-8");
        try {
            // 1. 권한 확인
            // 2. 파라미터 → Entity 변환
            // 3. Service 호출
            // 4. 목록 페이지로 redirect
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(...);
        }
    }
}
```

### 4. Repository 패턴

```java
public class XxxRepository {
    private static final String FILE = "xxx.json";  // data/ 폴더 기준

    public int insert(Xxx entity) {
        List<Xxx> list = JsonFileUtil.readList(FILE, Xxx.class);
        list.add(entity);
        JsonFileUtil.writeList(FILE, list);
        return 1;
    }

    public List<Xxx> findAll() {
        return JsonFileUtil.readList(FILE, Xxx.class);
    }

    public Xxx findById(String id) {
        return JsonFileUtil.readList(FILE, Xxx.class).stream()
            .filter(e -> e.getId().equals(id))
            .findFirst().orElse(null);
    }
}
```

### 5. JSP 공통 구조

```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/common/header.jsp" />
<div class="layout">
    <jsp:include page="/common/sidebar.jsp" />
    <main class="content">
        <div class="card">
            <!-- 본문 내용 -->
        </div>
    </main>
</div>
<jsp:include page="/common/footer.jsp" />
</body>
</html>
```

폼 전송은 `action="${pageContext.request.contextPath}/url"` 형태로 한다.

---

## 안전교육일지 기능 확장 계획

사이드바(`sidebar.jsp`)에는 이미 `/educations/journals` 링크가 있다.  
데이터 파일 `data/safety_journal.json`도 빈 배열로 준비되어 있다.  
`AccessPolicy`의 교육 관련 메서드도 이미 정의되어 있다.  
**새로 만들어야 할 파일은 아래 5가지다.**

### 추가할 파일 목록

```
src/main/java/
├── entity/
│   └── SafetyJournal.java              ← (1) 안전교육일지 데이터 모델
├── repository/
│   └── SafetyJournalRepository.java    ← (2) safety_journal.json CRUD
├── service/
│   └── SafetyJournalService.java       ← (3) 비즈니스 로직 + 유효성 검사
└── controller/
    └── education/
        ├── SafetyJournalListServlet.java     ← (4) GET /educations/journals
        └── SafetyJournalRegisterServlet.java ← (5) GET/POST /educations/journals/register

src/main/webapp/
└── education/
    ├── journalList.jsp      ← (6) 조회 화면
    └── journalRegister.jsp  ← (7) 등록 화면
```

### URL 설계

| HTTP 메서드 | URL | 역할 |
|---|---|---|
| GET | `/educations/journals` | 안전교육일지 목록 조회 |
| GET | `/educations/journals/register` | 안전교육일지 등록 폼 표시 |
| POST | `/educations/journals/register` | 안전교육일지 등록 처리 후 목록으로 redirect |

### SafetyJournal 필드 (설계서 전 임시안)

```java
public class SafetyJournal {
    private String journalId;       // 교육일지 ID (고유값)
    private String educationDate;   // 교육 일자 (yyyy-MM-dd)
    private String educationType;   // 교육 종류 (정기/특별/채용시 등)
    private String educationTitle;  // 교육 제목
    private String educationContent;// 교육 내용
    private String instructor;      // 강사명
    private int durationMinutes;    // 교육 시간 (분)
    private String targetGroup;     // 교육 대상 (연구실명 또는 그룹)
    private String registeredBy;    // 등록자 userId
    private String registeredDate;  // 등록일 (자동 입력, DateUtil.today())
}
```

> 설계서를 받으면 필드를 확정한 뒤 `SafetyJournal.java`를 작성한다.

---

## 개발 순서 (권장)

```
1. entity/SafetyJournal.java          ← 필드 + getter/setter
2. repository/SafetyJournalRepository ← insertJournal, findAll, findById
3. service/SafetyJournalService       ← createJournal, readJournalList, readJournal + 유효성 검사
4. controller/.../SafetyJournalListServlet    ← @WebServlet("/educations/journals")
5. controller/.../SafetyJournalRegisterServlet ← @WebServlet("/educations/journals/register")
6. webapp/education/journalList.jsp
7. webapp/education/journalRegister.jsp
```

---

## 실행 방법

```powershell
# 프로젝트 루트에서
& "C:\Program Files\JetBrains\IntelliJ IDEA 2024.1.1\plugins\maven\lib\maven3\bin\mvn.cmd" cargo:run
```

브라우저: http://localhost:8080  
테스트 계정: `admin` / `1234` (모든 기능 접근 가능)
