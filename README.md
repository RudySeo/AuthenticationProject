

## 📌 프로젝트 소개

> **Kotlin + Spring Boot** 기반의 백엔드 애플리케이션으로,
> 기본적인 회원가입 기능을 **Spring Security 없이** 구현했습니다.
> JPA를 사용해 사용자 정보를 DB에 저장하며, 비밀번호는 암호화되어 처리됩니다.

---

## 🛠️ 사용 기술 스택

| 항목         | 기술                       |
| ---------- | ------------------------ |
| Language   | Kotlin                   |
| Framework  | Spring Boot              |
| ORM        | Spring Data JPA          |
| Database   | MySQL                    |
| Build Tool | Gradle (Kotlin DSL)      |


---

## 📁 주요 디렉토리 구조

```
src
└── main
    ├── kotlin
    │   └── com.example.member
    │       ├── controller       // 회원가입 API 컨트롤러
    │       ├── dto              // 요청 DTO
    │       ├── entity           // JPA 엔티티
    │       ├── repository       // JPA Repository
    │       └── service          // 비즈니스 로직
    └── resources
        └── application.yml      // DB 설정
```

---


## ✅ API 명세 (회원가입)

| 메서드    | URL                 | 설명       |
| ------ | ------------------- | -------- |
| `POST` | `/api/users/signup` | 사용자 회원가입 |

#### 🔸 요청 바디 예시

```json
{
  "username": "sampleuser",
  "password": "samplepassword",
  "email": "user@example.com"
}
```

#### 🔸 응답 예시

```json
{
  "message": "회원가입이 완료되었습니다."
}
```

---

## 📝 향후 구현 예정 기능

* [ ] 로그인 API (JWT 기반)
* [ ] 로그인 시 토큰 발급 및 검증
* [ ] Spring Security 연동
* [ ] 사용자 권한(Role) 분리

---




