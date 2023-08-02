package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDTO {

    /*
     * 글수정 API
     * 1. URL: http://localhost:8080/board/{id}/update
     * 2. method: post
     * 3. requestBody: title=값(String)&content=값(String)
     * 4. MIME타입: X-WWW-Form-urlencoded
     * 5. 응답: view(html)를 응답 /board/{id}/detail페이지
     */
    private String title;
    private String content;
}
