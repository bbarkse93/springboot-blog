package shop.mtcoding.blog.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardWriteDTO {
    private Integer boardId;
    private Timestamp boardCreatedAt;
    private Integer boardUserId;
    private Integer userId;
    private String userUsername;

    public BoardWriteDTO(Integer boardId, Timestamp boardCreatedAt, Integer boardUserId, Integer userId,
            String userUsername) {
        this.boardId = boardId;
        this.boardCreatedAt = boardCreatedAt;
        this.boardUserId = boardUserId;
        this.userId = userId;
        this.userUsername = userUsername;
    }

}
