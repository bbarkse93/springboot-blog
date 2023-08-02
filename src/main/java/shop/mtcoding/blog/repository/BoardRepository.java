package shop.mtcoding.blog.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
