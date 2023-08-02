package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원수정 API
 * 1. URL: http://localhost:8080/user/{id}/update
 * 2. method: post
 * 3. requestBody: username=값(String)&password=값(String)&email=값(String)
 * 4. MIME타입: X-WWW-Form-urlencoded
 * 5. 응답: view(html)를 응답 updateForm페이지
 */

@Getter
@Setter
public class UserUpdateDTO {
    private String username;
    private String password;
    private String email;
}
