# Modulith Bootstrap Brief

## Brief

- User pain: 합의된 org.cherrypick.picker 모듈 구조가 문서로만 존재하고, 실제로 빌드·검증·리뷰 가능한 코드 골격이 없다.
- Scope: Gradle 9.3.0 / JDK 25 / Kotlin 2.3.20 / Spring Boot 4.0.5 / Spring Modulith 2.0.5 기반의 단일 애플리케이션 스캐폴드를 만들고, 루트 패키지 직하 모듈 구조와 모듈 검증 테스트를 추가한다.
- Non-goals: 비즈니스 로직 구현, 데이터 스키마, 외부 AI·DB·벡터 스토어 연동, 운영 프로퍼티, 마이그레이션 작성.
- Success metric: ./gradlew --no-daemon ktlintCheck 와 ./gradlew --no-daemon test 가 통과하고, ApplicationModules.of(...).verify() 가 모듈 경계를 검증한다.

## Reviewed Plan

- 외부 통합 의존성은 첫 PR에서 제외한다. PostgreSQL, Qdrant, Flyway, Spring AI는 문서상 확정 스택으로 기록하고 실제 starter 연결은 후속 PR로 미룬다.
- 첫 PR은 wrapper, build script, 루트 애플리케이션, 패키지 메타데이터, 최소 Kotlin 타입, 모듈 검증 테스트, README 업데이트까지만 포함한다.
- 모듈 간 방향성은 package-info.java 의 ApplicationModule / NamedInterface 메타데이터로 먼저 고정하고, 실제 교차 모듈 호출은 후속 구현에서 붙인다.

## Implementation Plan

1. Gradle wrapper 와 Kotlin/Spring Boot build 구성을 만든다.
2. org.cherrypick.picker 루트 아래 shared, documents, ingest, indexing, retrieval, review, knowledge, ops 모듈 패키지를 만든다.
3. 각 모듈의 api 서브패키지를 Named Interface 로 노출하고, 루트 모듈에는 허용 의존 방향을 선언한다.
4. 각 모듈에 최소 public api 와 내부 facade bean 을 둬서 패키지 구조를 코드로 명시한다.
5. ApplicationModules.verify() 테스트와 모듈별 ApplicationModuleTest 를 추가한다.
6. README 에 최종 구조, 확정 스택, 검증 명령, 후속 작업을 정리한다.

## Execution Path

- 로컬 구현 + 로컬 검증 + GitHub 원격 draft PR 생성 경로를 사용한다.
- 구현은 feat/modulith-bootstrap 브랜치에서 진행한다.

## Risks

- Spring Boot 4.0.5, Kotlin 2.3.20, Gradle 9.3.0, ktlint 플러그인 조합에서 빌드 스크립트 미세 조정이 필요할 수 있다.
- Spring Modulith package-info 메타데이터의 allowedDependencies / NamedInterface 문법이 실제 버전과 다를 경우 검증 단계에서 수정이 필요하다.
