package shop.mtcoding.blog.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;

@Repository
public class BoardRepository {

    @Autowired
    private EntityManager em;

    @Transactional // 롤백과 커밋을 자동으로 실행
    public void save(WriteDTO writeDTO, Integer userId) {
        Query query = em.createNativeQuery(
                "insert into board_tb(title, content, user_id, created_at) values(:title, :content, :userId, now())");
        query.setParameter("title", writeDTO.getTitle());
        query.setParameter("content", writeDTO.getContent());
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    public List<Board> findAll() {
        Query query = em.createNativeQuery("select * from board_tb", Board.class);
        List<Board> boardList = query.getResultList();
        return boardList;
    }

    public Board findById(@PathVariable Integer id) {
        Query query = em.createNativeQuery("select * from board_tb where id = :id",
                Board.class);
        query.setParameter("id", id);
        Board board = (Board) query.getSingleResult();

        return board;
    }

    // public Board findByIdWithUsername(@PathVariable Integer id) {
    // Query query = em
    // .createNativeQuery("select * from board_tb bt inner join user_tb ut on bt.id
    // = ut.id where id = :id");
    // query.setParameter("id", id);
    // Board board = (Board) query.getSingleResult();

    // return board;
    // }
}
