package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailDTO {
    private Integer boardId;
    private String boardContent;
    private String boardTitle;
    private Integer boardUserId;
    private Integer replyId;
    private String replyComment;
    private Integer replyUserId;
    private String replyUserUsername;
    private Boolean replyOwner;

    public BoardDetailDTO(Integer boardId, String boardContent, String boardTitle, Integer boardUserId, Integer replyId,
            String replyComment, Integer replyUserId, String replyUserUsername, Boolean replyOwner) {
        this.boardId = boardId;
        this.boardContent = boardContent;
        this.boardTitle = boardTitle;
        this.boardUserId = boardUserId;
        this.replyId = replyId;
        this.replyComment = replyComment;
        this.replyUserId = replyUserId;
        this.replyUserUsername = replyUserUsername;
        this.replyOwner = replyOwner;
    }

}
