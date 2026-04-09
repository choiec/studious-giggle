# JPA Postgres Persistence

## Brief

- User pain: documents 와 ingest 는 내부 저장 경계를 갖게 되었지만, 아직 인메모리 구현만 있어 실제 운영용 persistence 경로를 검증할 수 없다.
- Scope: PostgreSQL, Flyway, JPA 기반 구현체를 documents 와 ingest 경계에 추가하고, 기본 실행 경로는 인메모리로 유지한다.
- Non-goals: 컨트롤러, Testcontainers, PostgreSQL 전용 타입 최적화, 다른 모듈의 도메인 계약 변경.
- Success metric: 기본 프로필에서는 datasource 없이 기존 테스트가 통과하고, `jpa-test` 프로필에서는 Flyway + JPA 경로가 H2 기반으로 검증된다.

## Reviewed Plan

1. 기본 프로필을 `in-memory` 로 두고 DataSource/JPA/Flyway 자동설정을 끈다.
2. `jpa` 와 `jpa-test` 프로필에서만 datasource, JPA validate, Flyway 를 활성화한다.
3. documents 와 ingest 에 JPA entity, Spring Data repository, adapter 를 추가한다.
4. 인메모리 adapter 는 `picker.persistence.mode=in-memory` 에서만 활성화한다.
5. JPA 경로 전용 통합 테스트를 추가한다.

## Risks

- H2 는 PostgreSQL 대체재가 아니므로 migration 은 단순 SQL 로 유지해야 한다.
- ingest 의 source 저장과 document 저장은 JPA 경로에서 하나의 트랜잭션으로 묶어야 한다.
