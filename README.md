# 스프링부트 블로그 만들기

## 기술스택
- springboot 2.7
- JDK 11
- VSCODE
- MySQL8.0

## 의존성
- LomBok
- DevTools
- Spring WEB
- JPA
- h2
- MySQL
- Mustache

## 프로젝트 시작
```sql
create database blogdb;
```

---
### 비밀번호 암호화

- 회원가입 코드 수정  
  JoinDTO로 받은 비밀번호를 BCrypt로 해시해서 DB에 insert 하기

- 로그인 코드 수정  
  LoginDTO로 받은 유저네임과 비밀번호중에 유저네임으로 User 찾기  
  User에 password와 LoginDTO의 password로 비교해서 true가 나오면 session 만들기

- 회원수정 코드 수정  
  UpdateDTO로 받은 비밀번호를 BCrypt로 해시 해서 DB에 update하기

