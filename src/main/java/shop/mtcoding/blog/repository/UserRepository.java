package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.JoinDTO;

// IoC에 떠있는 것들
// BoardController, UserController, UserRepository
// EntityManger, HttpSession
@Repository
public class UserRepository {

    @Autowired
    private EntityManager em;

    @Transactional // 롤백과 커밋을 자동으로 실행
    public void save(JoinDTO joinDTO) {
        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        // 새로운 기술을 도입하려면 문서를 작성해라 (aka.샘플코드)
        query.setParameter("username", joinDTO.getUsername());
        query.setParameter("password", joinDTO.getPassword());
        query.setParameter("email", joinDTO.getEmail());
        query.executeUpdate();
    }

}
