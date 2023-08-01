package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.WriteDTO;

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
}
