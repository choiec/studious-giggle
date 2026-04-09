# studious-giggle

org.cherrypick.picker 루트 패키지를 기준으로 하는 Spring Modulith 부트스트랩 저장소다. 첫 PR 범위는 확정된 모듈 경계와 검증 가능한 최소 애플리케이션 골격을 코드로 내리는 데 맞춘다.

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

각 비즈니스 모듈은 루트 패키지 직하에 두고, 외부 공개 타입은 각 모듈의 api 패키지에 둔다. 내부 구현은 application, domain, parser, jobs, monitoring 같은 내부 패키지에 남긴다. 현재 스캐폴드는 package-info.java 에 Spring Modulith ApplicationModule 과 NamedInterface 메타데이터를 추가해 허용 의존 방향을 먼저 고정한다.

## Current Scope

현재 저장소에는 다음이 포함된다.

- Gradle 9.3.0 wrapper 와 Kotlin DSL 빌드
- Spring Boot 4.0.5 + Spring Modulith 2.0.5 애플리케이션 골격
- shared, documents, ingest, indexing, retrieval, review, knowledge, ops 모듈 스캐폴드
- 모듈 간 공개 API bean 과 최소 facade 구현
- ApplicationModules.of(...).verify() 구조 검증 테스트
- 모듈별 @ApplicationModuleTest 스모크 테스트

## Chosen Stack

- JDK 25
- Kotlin 2.3.20
- Gradle 9.3.0
- Spring Boot 4.0.5
- Spring Modulith 2.0.5

다음 스택은 아키텍처 선택으로 확정했지만, 첫 PR에서는 green bootstrap 유지를 위해 실제 starter 연결을 보류했다.

- Spring AI 2.0.0-M4
- OpenAI
- Qdrant
- PostgreSQL
- Flyway

## Validation

```bash
./gradlew --no-daemon ktlintCheck
./gradlew --no-daemon test
```

추가적인 설계 메모와 범위는 plans/2026-04-09-modulith-bootstrap.md 에 정리되어 있다.
