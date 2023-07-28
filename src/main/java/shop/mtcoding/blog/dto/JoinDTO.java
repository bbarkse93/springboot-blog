package shop.mtcoding.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 회원가입 API
 * 1. URL: http://localhost:8080/join
 * 2. method: post
 * 3. requestBody: username=값(String)&password=값(String)&email=값(String)
 * 4. MIME타입: X-WWW-Form-urlencoded
 * 5. 응답: view를 응답
 */

//컨트롤러를 만들고 API문서를 참조해서 VIEW 생성
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {
    private String username;
    private String password;
    private String email;
}
