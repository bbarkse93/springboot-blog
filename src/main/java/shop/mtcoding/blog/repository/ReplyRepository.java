package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;

// UserController, BoardController, ReplyRepository, ErrorController, 
// UserRepository, BoardRepository, ReplyRepository
// EntityManager, HttpSession
@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    @Transactional
    public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {
        Query query = em.createNativeQuery(
                "insert into reply_tb(comment, user_id, board_id) values(:comment, :userId, :boardId)");
        query.setParameter("comment", replyWriteDTO.getComment());
        query.setParameter("boardId", replyWriteDTO.getBoardId());
        query.setParameter("userId", userId);

        query.executeUpdate();

    }
}
