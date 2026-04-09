# studious-giggle

org.cherrypick.picker 루트 패키지를 기준으로 하는 Spring Modulith 저장소다. 현재는 documents 와 ingest 모듈에 canonical body, retrieval segment, PostgreSQL/Flyway/JPA persistence 경로를 추가하고, retrieval 모듈에 segment-aware keyword search 기반을 올린 상태다.

## Accepted Layout

```text
src/main/kotlin/org/cherrypick/picker
├─ PickerApplication.kt
├─ shared
├─ documents
├─ ingest
├─ indexing
├─ retrieval
├─ review
├─ knowledge
└─ ops
```

각 비즈니스 모듈은 루트 패키지 직하에 두고, 외부 공개 타입은 각 모듈의 api 패키지에 둔다. 내부 구현은 application, domain, infrastructure, parser, jobs, monitoring 같은 내부 패키지에 남긴다. package-info.java 의 Spring Modulith ApplicationModule 과 NamedInterface 메타데이터로 허용 의존 방향을 고정한다.

## Current Scope

현재 저장소에는 다음이 포함된다.

- Gradle 9.3.0 wrapper 와 Kotlin DSL 빌드
- Spring Boot 4.0.5 + Spring Modulith 2.0.5 애플리케이션 골격
- documents 모듈의 revision-aware aggregate, canonical body, retrieval segment, 인메모리 repository, JPA repository adapter
- ingest 모듈의 request, parser, source storage 경계, XML canonicalization, 인메모리 storage, JPA storage adapter
- retrieval 모듈의 segment-aware keyword search 와 citation-like hit metadata
- Flyway migration 과 PostgreSQL profile 설정
- 기본 `in-memory` 경로와 `jpa`, `jpa-test` 프로필 분리
- ApplicationModules.of(...).verify() 구조 검증 테스트
- documents, ingest, indexing, retrieval 모듈 테스트와 JPA 통합 테스트

## Persistence Modes

- 기본 실행: `in-memory` 프로필이 기본값이며 DataSource, JPA, Flyway 자동설정을 끈다.
- PostgreSQL 실행: `SPRING_PROFILES_ACTIVE=jpa` 와 함께 `PICKER_DATASOURCE_URL`, `PICKER_DATASOURCE_USERNAME`, `PICKER_DATASOURCE_PASSWORD` 를 제공한다.
- JPA 검증 테스트: `jpa-test` 프로필에서 H2 PostgreSQL 호환 모드와 Flyway migration 을 사용한다.

## Chosen Stack

- JDK 25
- Kotlin 2.3.20
- Gradle 9.3.0
- Spring Boot 4.0.5
- Spring Modulith 2.0.5
- PostgreSQL
- Flyway

다음 스택은 아키텍처 선택으로 확정했지만, 현재는 실제 starter 연결을 보류했다.

- Spring AI 2.0.0-M4
- OpenAI
- Qdrant

## Validation

```bash
./gradlew --no-daemon ktlintCheck
./gradlew --no-daemon test
```

설계 메모는 plans/2026-04-09-modulith-bootstrap.md, plans/2026-04-09-documents-ingest-foundation.md, plans/2026-04-09-jpa-postgres-persistence.md, plans/2026-04-09-canonical-segmentation-retrieval.md 에 정리되어 있다.
