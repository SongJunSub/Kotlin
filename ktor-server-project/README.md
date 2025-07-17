# Ktor 서버 프로젝트

이 프로젝트는 코틀린 기반의 경량 웹 프레임워크인 Ktor를 사용하여 구축한 샘플 서버 애플리케이션입니다.

## PostgreSQL 데이터베이스 연동 및 CRUD API

이 프로젝트는 PostgreSQL 데이터베이스를 사용하여 '작업(Task)'에 대한 CRUD(Create, Read, Update, Delete) API를 제공합니다. Docker Compose를 통해 PostgreSQL 컨테이너를 쉽게 실행할 수 있습니다.

**API 엔드포인트:**

* `GET /tasks`: 모든 작업 목록을 조회합니다.
* `GET /tasks/{id}`: 특정 ID의 작업을 조회합니다.
* `POST /tasks`: 새로운 작업을 생성합니다. (Request Body: `{"title": "string", "description": "string"}`)
* `PUT /tasks/{id}`: 특정 ID의 작업을 수정합니다. (Request Body: `{"title": "string", "description": "string"}`)
* `DELETE /tasks/{id}`: 특정 ID의 작업을 삭제합니다.

## 의존성 주입 (Dependency Injection)

이 프로젝트는 [Koin](https://insert-koin.io/) 라이브러리를 사용하여 의존성 주입을 관리합니다. `TaskService`와 `UserService`와 같은 서비스 클래스들은 Koin 컨테이너에 등록되어 필요할 때 주입받아 사용됩니다.

## JWT 인증 및 인가

이 프로젝트는 JWT(JSON Web Token)를 사용하여 사용자 인증 및 인가를 처리합니다.

**인증/인가 API 엔드포인트:**

* `POST /register`: 새로운 사용자를 등록합니다. (Request Body: `{"username": "string", "password": "string"}`)
* `POST /login`: 사용자 로그인 후 JWT 토큰을 발급받습니다. (Request Body: `{"username": "string", "password": "string"}`)

**보호된 엔드포인트:**

`/tasks` 엔드포인트는 JWT 토큰이 필요합니다. 요청 헤더에 `Authorization: Bearer <YOUR_JWT_TOKEN>` 형식으로 토큰을 포함해야 합니다.



### 요구사항

- Java 11 또는 그 이상
- Gradle

### 실행

다음 명령어를 사용하여 애플리케이션을 실행할 수 있습니다.

```bash
./gradlew run
```

서버는 `http://localhost:8080`에서 실행됩니다.

### 테스트

프로젝트의 API 엔드포인트에 대한 단위 및 통합 테스트가 작성되어 있습니다. 다음 명령어를 사용하여 테스트를 실행할 수 있습니다.

```bash
./gradlew test
```

테스트는 다음을 포함합니다:

*   **Task API 테스트**: Task 생성, 조회, 수정, 삭제 기능에 대한 테스트.
*   **인증 API 테스트**: 사용자 등록, 로그인, JWT 토큰을 사용한 보호된 엔드포인트 접근 테스트.
*   **서비스 단위 테스트 (MockK)**: MockK를 사용하여 `TaskService`와 같은 서비스 계층의 비즈니스 로직을 단위 테스트합니다. 데이터베이스와 같은 외부 의존성을 모킹하여 순수한 서비스 로직의 정확성을 검증합니다.
