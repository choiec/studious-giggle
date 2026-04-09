# Documents And Ingest Foundation

## Brief

- User pain: documents 와 ingest 가 아직 스캐폴드 수준이라 실제 저장 경계와 도메인 계약 없이 다음 모듈 구현을 진행하기 어렵다.
- Scope: documents 에 repository-backed aggregate 와 richer command/query DTO 를 추가하고, ingest 에 request, parser, source storage 경계를 추가한다. 외부 DB 는 아직 붙이지 않고 인메모리 어댑터로 유지한다.
- Non-goals: JPA, JDBC, Flyway, PostgreSQL, 컨트롤러, 완전한 XML 파싱, indexing/retrieval 전면 개편.
- Success metric: documents 와 ingest 가 애플리케이션 컨텍스트 안에서 상태를 유지하며, 성공/검증 실패/edge path 테스트와 전체 게이트가 모두 통과한다.

## Reviewed Plan

1. documents 의 bootstrap helper 를 repository-backed aggregate 로 교체한다.
2. documents.api 에 explicit register/get/latest 계약을 두고, 기존 canonical query 는 최소 호환 레이어로만 유지한다.
3. ingest.api 에 request 기반 import 계약을 추가하고, parser 와 source storage 를 내부 경계로 둔다.
4. documents 와 ingest 는 인메모리 infrastructure adapter 로만 실제 상태를 저장한다.
5. module tests 는 성공, validation failure, edge case 를 검증한다.
6. README 를 현재 결정에 맞게 갱신한다.

## Risks

- bootstrap canonical query 를 완전히 제거하면 downstream churn 이 커지므로 이번 PR 에서는 호환 query 를 유지한다.
- 인메모리 상태 저장으로 테스트 간 상호작용이 생길 수 있어, 테스트 데이터는 모두 고유 sourceId 를 사용한다.
