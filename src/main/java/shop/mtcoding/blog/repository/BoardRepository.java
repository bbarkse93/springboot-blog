package shop.mtcoding.blog.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import javax.transaction.Transactional;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.BoardWriteDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
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

    // select id, title from board)tb
    // resultClass 안붙이고 직접 파싱하려면
    // Object[]로 리턴 됨
    // ex- Object[0] = 1 , Object[1] = 안녕
    public int count() {
        // Entity 타입이 아니어도, 기본 자료형도 리턴이 된다. 사실 안되더라
        Query query = em.createNativeQuery("select count(*) from board_tb");
        // 원래는 Object 배열로 리턴 받는다.
        // Object 배열은 칼럼의 연속이다.
        // 그룹함수를 써서 하나의 칼럼을 조회하면 Object로 리턴된다.
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }

    public int count(String keyword) {

        Query query = em.createNativeQuery("select count(*) from board_tb where title like :keyword");
        query.setParameter("keyword", "%" + keyword + "%");
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }

    public List<Board> findAll(int page) {
        // 상수(대문자)
        final int SIZE = 3;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit :page, :size", Board.class);

        // localhost:8080/page=0
        query.setParameter("page", page * SIZE);
        query.setParameter("size", SIZE);

        return query.getResultList();
    }

    public List<Board> findAll(int page, String keyword) {
        final int SIZE = 3;
        Query query = em.createNativeQuery(
                "select * from board_tb where title like :keyword order by id desc limit :page, :size", Board.class);
        query.setParameter("page", page * SIZE);
        query.setParameter("size", SIZE);
        query.setParameter("keyword", "%" + keyword + "%");

        return query.getResultList();
    }

    public Board findById(Integer id) {
        Query query = em.createNativeQuery("select * from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        Board board = (Board) query.getSingleResult();
        return board;
    }

    @Transactional
    public void deleteById(Integer id) {
        Query query = em.createNativeQuery("delete from board_tb where id = :id", Board.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void update(UpdateDTO updateDTO, Integer id) {
        Query query = em.createNativeQuery("update board_tb set title = :title, content = :content where id = :id");
        query.setParameter("title", updateDTO.getTitle());
        query.setParameter("content", updateDTO.getContent());
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public List<BoardDetailDTO> findByIdJoinReply(Integer boardId, Integer sessionUserId) {
        String sql = "select ";
        sql += "b.id board_id, ";
        sql += "b.content board_content, ";
        sql += "b.title board_title, ";
        sql += "b.user_id board_user_id, ";
        sql += "r.id reply_id, ";
        sql += "r.comment reply_comment, ";
        sql += "r.user_id reply_user_id, ";
        sql += "ru.username reply_user_username, ";
        if (sessionUserId == null) {
            sql += "false reply_owner ";
        } else {
            sql += "case when r.user_id = :sessionUserId then true else false end reply_owner ";
        }

        sql += "from board_tb b left outer join reply_tb r ";
        sql += "on b.id = r.board_id ";
        sql += "left outer join user_tb ru ";
        sql += "on r.user_id = ru.id ";
        sql += "where b.id = :boardId ";
        sql += "order by r.id desc";
        Query query = em.createNativeQuery(sql);

        query.setParameter("boardId", boardId);
        if (sessionUserId != null) {
            query.setParameter("sessionUserId", sessionUserId);

        }

        JpaResultMapper mapper = new JpaResultMapper();
        List<BoardDetailDTO> dtos = mapper.list(query, BoardDetailDTO.class);

        return dtos;
    }

    public List<BoardWriteDTO> findByIdJoinUser(Integer boardId) {
        Query query = em.createNativeQuery(
                "select b.id board_id, b.created_at board_created_at, b.user_id board_user_id, u.id user_id, u.username user_username from user_tb u inner join board_tb b on u.id = b.user_id where b.id = :boardId");
        query.setParameter("boardId", boardId);

        JpaResultMapper mapper = new JpaResultMapper();
        List<BoardWriteDTO> list = mapper.list(query, BoardWriteDTO.class);
        return list;
    }

}
