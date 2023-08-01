package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 글쓰기 API
 * 1. URL: http://localhost:8080/board/save
 * 2. method: post 
 * 3. requestBody: title=값(String)&content=값(String)
 * 4. MIME타입: X-WWW-Form-urlencoded
 * 5. 응답: view(html)를 응답 index페이지 
 */

@Getter
@Setter
public class WriteDTO {

    private String title;
    private String content;
}
