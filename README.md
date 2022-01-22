## Runbook

- bootJar build

```
./gradlew clean bootJar
```

- run jar

```
java -jar document-approval-0.0.1-SNAPSHOT.jar
```

## Spec

- spring boot 2.6.2
- jpa
- gradle
- 타임리프
- h2
- java 11

## 테이블 구조

<img width="747" alt="스크린샷 2022-01-18 오후 9 36 38" src="https://user-images.githubusercontent.com/53357210/149938895-83858208-91b7-4ed5-9c56-8aef3f6d00de.png">

| 테이블명            | 설명                   |
|-----------------|----------------------|
| USER            | 유저 테이블               |
| DOCUMENT        | 문서 테이블               |
| PAYMENT_STEP    | 문서의 현재 결재 순서 관리 테이블  |
| PAYMENT_STATE   | 문서의 현재 상태 (진행,거절,승인) |
| PAYMENT_USER    | 결제자 테이블              |
| PAYMENT_COMMENT | 결제자 승인 테이블 (결제 사유)   |
| CLASSIFICATION  | 마스터 분류 테이블           |
|AUTHORITY| 어플리케이션 권한 테이블        |
|AUTHORITY_USER|유저와 권한 을 매핑 테이블|

## 뷰 구조

|페이지명| 기능           |
|-----|--------------|
|로그인 페이지| 로그인          |
|index| 목록 페이지,로그아웃  |
|outbox 리스트 페이지| 문서작성,상세보기,삭제 |
|outbox 상세보기 페이지| 수정           |
|inbox 리스트 페이지| 결재           |
|archive 리스트 페이지| 상세           |
|archive 상세보기 페이지| 결재사유,결재내용 작성 |

## about

[css template](https://startbootstrap.com/template/sb-admin)
