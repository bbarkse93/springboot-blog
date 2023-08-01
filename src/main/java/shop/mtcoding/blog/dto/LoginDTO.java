package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 로그인 API
 * 1. URL: http://localhost:8080/login
 * 2. method: post (로그인은 select이지만 post요청한다.)
 * 3. requestBody: username=값(String)&password=값(String)
 * 4. MIME타입: X-WWW-Form-urlencoded
 * 5. 응답: view(html)를 응답 index페이지 
 */

@Getter
@Setter
public class LoginDTO {
    private String username;
    private String password;
}
