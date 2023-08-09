package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.model.Reply;

// UserController, BoardController, ReplyRepository, ErrorController, 
// UserRepository, BoardRepository, ReplyRepository
// EntityManager, HttpSession
@Repository
public class ReplyRepository {

    @Autowired
    private EntityManager em;

    public List<Reply> findByBoardId(Integer boardId) {
        Query query = em.createNativeQuery("select * from reply_tb where board_id = :boardId", Reply.class);
        query.setParameter("boardId", boardId);
        return query.getResultList();
    }

    @Transactional
    public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {
        Query query = em.createNativeQuery(
                "insert into reply_tb(comment, user_id, board_id) values(:comment, :userId, :boardId)");
        query.setParameter("comment", replyWriteDTO.getComment());
        query.setParameter("boardId", replyWriteDTO.getBoardId());
        query.setParameter("userId", userId);

        query.executeUpdate();

    }

    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from reply_tb where id = :replyId");
        query.setParameter("replyId", id);
        query.executeUpdate();

    }

    public Reply findById(int id) {
        Query query = em.createNativeQuery("select * from reply_tb where id = :id", Reply.class);
        query.setParameter("id", id);

        return (Reply) query.getSingleResult();
    }
}
