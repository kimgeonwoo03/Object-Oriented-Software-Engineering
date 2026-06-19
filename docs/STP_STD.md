# STP+STD — 그룹 등록·조회

# 4. 시험 계획
## 4.1 단위 시험
### 4.1.1 목적 및 정의
■ 프로그램의 기본 단위인 모듈(클래스 및 메서드)이 명세서대로 오류 없이 기능하는지 검증한다.
■ 내부 로직 및 데이터 처리의 정확성을 확인한다.

### 4.1.2 단위 시험 대상
| 유스케이스 식별자 | 업무 구분 | 유스케이스 | 컴포넌트 ID | 단위 시험 ID
| --- | --- | --- | --- | --- |
| UCS-USR-009 | 사용자 관리 | 그룹 등록 | COM-01 | UT-01 |
| UCS-USR-010 | 사용자 관리 | 그룹 조회 | COM-01 | UT-02 |

### 4.1.3 세부 접근 방법
| 단위 시험 ID | 수행 절차 | 검증 기준 | 합격 기준 |
| --- | --- | --- | --- |
| UT-01 | GroupService.createGroup() 직접 호출 | validateGroup → checkDuplicateGroup → insertGroup (JSON 파일 저장) 순서로 실행됨 | 정상 입력 시 true 반환, 오류 입력 시 적절한 예외(EmptyFieldException, DuplicateDataException 등) 발생 |
| UT-02 | GroupService.readGroupList() 직접 호출 | groupRepository.findGroupsByCondition(condition) 호출, 검색어 없음 시 전체 목록 반환, 검색어 있음 시 그룹 ID·그룹명·관리자 ID 부분 일치 필터링 | 조건에 맞는 그룹 목록 반환, 검색어 미입력·공백 시 전체 목록 반환 |


## 4.2 통합 시험
### 4.2.1 목적 및 정의
■ 단위 시험이 완료된 모듈들을 결합하여, 서브시스템 간의 데이터 인터페이스와 상호작용에서 발생하는 오류를 조기에 검출한다.

### 4.2.2 통합 시험 대상
| 업무 구분 | 컴포넌트 ID | 통합 시험 ID |
| --- | --- | --- |
| 사용자 관리 | COM-01 | IT-01 |

### 4.2.3 세부 접근 방법
| 업무 구분 | 사용 기법 | 검증 기준 | 합격 기준 |
| --- | --- | --- | --- |
| 사용자 관리 | HTTP 요청 → Servlet → Service → Repository → JSON 파일 순차 호출 | 각 계층 통과 후 data/group_info.json에 데이터 기록 및 AccessPolicy 권한 검증 여부 | 등록 후 JSON 파일에 그룹 데이터가 존재하고, 목록 조회 시 조건에 맞는 데이터가 정상 반환됨 |


## 4.3 시스템 시험
### 4.3.1 목적 및 정의
■ 소프트웨어와 다른 시스템 요소(하드웨어, 정보 및 다른 소프트웨어, 비기능 요구사항)들과 통합하며 모든 요소들이 적절히 조화를 이루어 시스템의 기능을 만족하는지 확인한다.
■ 실제 구현된 시스템과 계획된 사양(요구사항 명세서)을 서로 비교하여 일치하는지 검증하는 작업을 수행한다. 
■ 시스템의 전체 기능을 시험 확인하고, 시스템의 성능이나 기능 수행에 제한 사항이 없는지 확인한다. 복구 테스트, 보안 테스트, 스트레스 테스트, 성능 테스트 등을 통하여 소프트웨어의 잠재적인 문제점이 없는지 종합적으로 시험한다.

### 4.3.2 시스템 시험 대상
| 요구사항명 | 요구사항 식별자 | 시스템 시험 ID |
| --- | --- | --- |
| 사용자 운영 | REQ-USR-001 | ST-01

### 4.3.3 시스템 접근 방법
| 대상 | 사용 기법| 설명 |
| --- | --- | --- |
| ST-01 | 기능 시험 | 시스템에서 사용자 권한별(연구실 책임자, 안전관리자, 일반 학생 등) 동작 및 기능이 명세서대로 정상 수행되는지 식별한다.


## 4.4 인수 시험
### 4.4.1 목적 및 정의
■ 실제 운영 환경과 유사한 상태에서 사용자가 직접 테스트를 수행하여, 시스템이 실무에 도입될 준비가 되었는지 최종 승인한다.

### 4.4.2 시스템 시험 대상
| 업무 구분 | 유스케이스 | 인수 시험 ID
| --- | --- | --- |
| 사용자 관리 | 그룹 등록 | AT-01 |
| 시용자 관리 | 그룹 조회 | AT-02

### 4.4.3 세부 접근 방법
| 대상 | 사용 기법 | 설명 |
| --- | --- | --- |
| AT-01 | 역할별 계정 로그인 후 그룹 등록 시도 | SYSTEM_ADMIN(허용) / GROUP_ADMIN, RESEARCH_WORKER(차단) 각각 확인 및 등록 후 그룹 목록 반영 확인 |
| AT-02 | 역할별 계정 로그인 후 그룹 목록 조회 및 검색 시도 | SYSTEM_ADMIN, GROUP_ADMIN(허용) / RESEARCH_WORKER, EXTERNAL_SYSTEM(차단) 각각 확인 |


# 5. 단위 시험 케이스 명세

## 5.1 UT-01 — 그룹 정보 등록

단위 시험 ID: UT-01  
시험 케이스명: 그룹 정보 등록

| 시험 절차 | 입력 데이터 | 검증 기준 | 합격 기준 | 시험 결과 |
| --- | --- | --- | --- | --- |
| 정상 등록: 유효한 필수 필드 입력 후 GroupService.createGroup() 호출 | groupId=GRP003, groupName=연구안전그룹, managerId=MGR003, description=단위시험용 그룹, createdDate=2026-06-19, status=ACTIVE | 반환값 true, JSON 파일(group_info.json)에 해당 데이터가 직렬화되어 추가됨 | true 반환 및 JSON 파일 내 객체 저장 확인 | P |
| 필수 항목 누락: groupName(그룹명) 값을 누락하여 등록 시도 | groupId=GRP004, groupName=null, managerId=MGR004 | validateGroup() 로직에서 필수값 검증 실패 및 EmptyFieldException 발생 | 예외 발생 및 "그룹명 값은 필수입니다." 메시지 출력 | P |
| 그룹 정보 없음: Group 객체를 null로 createGroup() 호출 | group=null | validateGroup() 로직에서 null 검증 실패 및 EmptyFieldException 발생 | 예외 발생 및 "그룹 정보가 없습니다." 메시지 출력 | P |
| 중복 등록: 이미 존재하는 ID로 createGroup() 재호출 | groupId=GRP001 (JSON에 이미 존재하는 ID), groupName=중복그룹, managerId=MGR001 | checkDuplicateGroup() 로직에서 DB(JSON) 중복 검사 수행 | DuplicateDataException 발생 및 "이미 존재하는 그룹 ID입니다." 메시지 출력 | P |

## 5.2 UT-02 — 그룹 목록 조회

단위 시험 ID: UT-02  
시험 케이스명: 그룹 목록 조회

| 시험 절차 | 입력 데이터 | 검증 기준 | 합격 기준 | 시험 결과 |
| --- | --- | --- | --- | --- |
| 전체 조회: 검색 조건 없이 readGroupList() 호출 | condition=null | groupRepository.findGroupsByCondition() 호출, JSON 파일(group_info.json)의 전체 목록 반환 | 등록된 모든 그룹이 List에 포함되어 반환됨 | P |
| 그룹 ID 검색: 그룹 ID 키워드로 readGroupList() 호출 | condition=GRP001 | 그룹 ID 부분 일치 필터링 수행 | GRP001 그룹만 포함된 목록 반환 | P |
| 그룹명 검색: 그룹명 키워드로 readGroupList() 호출 | condition=안전 | 그룹명 부분 일치 필터링 수행(대소문자 무시) | "안전관리그룹" 등 키워드에 일치하는 그룹만 반환 | P |
| 검색 결과 없음: 존재하지 않는 키워드로 readGroupList() 호출 | condition=NOTEXIST | 그룹 ID·그룹명·관리자 ID 모두 불일치 시 빈 목록 반환 | 빈 List 반환 | P |
