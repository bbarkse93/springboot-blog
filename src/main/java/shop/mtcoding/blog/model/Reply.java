package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "reply_tb")
@Entity
public class Reply {
    // 연관관계
    // User(1) - Reply(N)
    // Board(1) - Reply(N)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String comment; // 댓글 내용

    // private Timestamp createdAt; 날짜 넣을 거면 이거 하면 돼

    @JoinColumn(name = "user_id") // 칼럼명을 설정
    @ManyToOne
    private User user; // FK user_id

    @ManyToOne
    private Board board; // FK board_id

}
