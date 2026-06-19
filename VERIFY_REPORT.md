# 안전교육일지(SS-EDU) 구현 검증 보고서

> 검증 일시: 2026-06-19  
> 검증 방식: Playwright headless Chromium (자동화 브라우저)  
> 테스트 계정: `safetyteam / 1234` (SAFETY_TEAM_MANAGER)  
> 서버: http://localhost:8080 (내장 Tomcat 10, cargo:run)

---

## 결론

| 기능 | 결과 |
|---|---|
| 안전교육일지 목록 조회 | PASS |
| 안전교육일지 등록 | PASS |
| 중복 등록 차단 | PASS |

---

## 검증 단계별 결과

### STEP 1 — 로그인

- 실행: `safetyteam / 1234` 로 로그인 폼 제출
- 결과: `index.jsp` 로 리다이렉트 성공, 헤더에 `사용자: 안전관리팀 담당자 | 역할: SAFETY_TEAM_MANAGER` 표시

### STEP 2 — 안전교육일지 목록 조회 (데이터 없음)

- 실행: 사이드바 "안전교육 관리" 클릭 → `GET /educations/journals`
- 결과: **안전교육일지 목록** 화면 정상 표시
- 검색 필터(교육 일자 시작/종료, 연구실 ID) 노출 확인
- 빈 상태 메시지 "등록된 안전교육일지가 없습니다." 정상 출력

![목록 화면 (빈 상태)](verify_screenshots/03_journal_list_empty.png)

### STEP 3 — 안전교육일지 등록 폼 진입

- 실행: "안전교육일지 등록" 버튼 클릭 → `GET /educations/journals/register`
- 결과: **안전교육일지 등록** 폼 정상 표시
- 입력 필드: 연구실 ID, 교육 일자(date picker), 교육 내용(textarea), 참석자 수

![등록 폼 화면](verify_screenshots/04_register_form.png)

### STEP 4 — 안전교육일지 등록 제출

- 실행: 아래 데이터 입력 후 "등록" 버튼 제출

| 필드 | 입력값 |
|---|---|
| 연구실 ID | LAB-001 |
| 교육 일자 | 2026-06-19 |
| 교육 내용 | 실험실 안전 관리 및 화재 대피 요령 교육 |
| 참석자 수 | 10 |

- 결과: `POST /educations/journals/register` → `GET /educations/journals` 리다이렉트 성공

### STEP 5 — 등록 후 목록 확인

- 실행: 제출 직후 목록 페이지 확인
- 결과: 등록한 데이터가 테이블에 즉시 반영됨

| 교육일지 ID | 연구실 ID | 교육 일자 | 교육 내용 | 참석자 수 | 작성자 |
|---|---|---|---|---|---|
| ebed363b-7c5a-... | LAB-001 | 2026-06-19 | 실험실 안전 관리 및 화재 대피 요령 교육 | 10 | safetyteam |

![등록 후 목록 화면](verify_screenshots/06_after_submit.png)

### STEP 6 — 중복 등록 차단 (프로브)

- 실행: 동일 연구실 ID(LAB-001) + 동일 날짜(2026-06-19)로 재등록 시도
- 결과: 등록 차단 후 오류 화면 표시

> **오류 발생**  
> 동일 교육일자·연구실의 안전교육일지가 이미 존재합니다.

![중복 등록 오류 화면](verify_screenshots/07_duplicate_error.png)

---

## 구현된 파일 목록

```
src/main/java/
├── entity/SafetyJournal.java
│     journalId, labId, educationDate, content, attendeeCount, authorId
│
├── repository/SafetyJournalRepository.java
│     insert / findByCondition / findById / existsByDateAndLab
│
├── service/SafetyJournalService.java
│     createJournal (formalInspection → duplicateInspection → insert)
│     readJournalList / readJournal
│
└── controller/education/
      SafetyJournalListServlet.java     GET /educations/journals
      SafetyJournalRegisterServlet.java GET/POST /educations/journals/register

src/main/webapp/education/
├── journalList.jsp
└── journalRegister.jsp
```

---

## 설계서(SDD) 모호한 부분 처리 내역

| 항목 | 설계서 내용 | 처리 방식 |
|---|---|---|
| `eduType` 필드 | 검색 파라미터로만 등장, Entity 속성에 없음 | Entity에 추가하지 않음. 검색 조건으로만 사용 예정이면 현행 유지. 저장 필요 시 `SafetyJournal`에 필드 추가 필요 |
| Control 반환 타입 | 인터페이스 명세 `List<SafetyJournal>` vs Control 명세 `bool` 불일치 | 인터페이스 명세 기준(`List<SafetyJournal>`) 채택 |
| `educationDate` 타입 | 설계서에 `Date` 명시 | JSON 직렬화 호환을 위해 `String (yyyy-MM-dd)` 사용 (기존 프로젝트 DateUtil 관례와 동일) |
| 메서드명 케이스 | 설계서에 `DuplicateInspection`, `FormalInspection` (파스칼) | Java 컨벤션에 따라 `duplicateInspection`, `formalInspection` (카멜)으로 변경 |
| `AuthUtil.getRole()` / `getUserId()` | 가이드 초안에 존재하지 않는 메서드 사용 | 실제 구현체 기준으로 `AuthUtil.getLoginRole(request)` / `AuthUtil.getLoginUser(request).getUserId()` 로 수정 |

---

## 비고

- `data/safety_journal.json` 은 기존에 빈 배열 `[]` 로 존재하므로 별도 생성 불필요
- 사이드바 `/educations/journals` 링크는 기존 `sidebar.jsp` 에 이미 포함되어 있어 수정 없이 동작
- 서버 재시작 시 반드시 `mvn clean package` 후 `cargo:run` 순서로 실행해야 새 코드 반영됨
