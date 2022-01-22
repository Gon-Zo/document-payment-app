## Desc 

문서를 결재하는 시스템 사용자가 결재된 문서를 분야 별로 직접 고른뒤 

결재자를 멀티로 선택이 되게 한다.

하지만 결재자가 여러명일 경우 첫번째 결재자가 결재를 하지 않으면 뒷 결재자는 결재를 하지 못한다.

만약 결재가가 한명이라도 승인을 거부 할 경우 승인이 되지 않는다.

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

## about

[css template](https://startbootstrap.com/template/sb-admin)
