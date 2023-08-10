package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;
import shop.mtcoding.blog.model.User;

// IoC에 떠있는 것들
// BoardController, UserController, UserRepository
// EntityManger, HttpSession
@Repository
public class UserRepository {

    @Autowired
    private EntityManager em;

    // Trasaction(상대적인 일의 최소 단위)
    @Transactional // 롤백과 커밋을 자동으로 실행
    public void save(JoinDTO joinDTO) {

        Query query = em.createNativeQuery(
                "insert into user_tb(username, password, email) values(:username, :password, :email)");
        // 새로운 기술을 도입하려면 문서를 작성해라 (aka.샘플코드)
        query.setParameter("username", joinDTO.getUsername());
        String password = BCrypt.hashpw(joinDTO.getPassword(), BCrypt.gensalt());
        query.setParameter("password", password);
        query.setParameter("email", joinDTO.getEmail());
        query.executeUpdate(); // 쿼리를 전송 (DBMS)

    }

    public User findByUsername(String username) {
        try {
            Query query = em.createNativeQuery("select * from user_tb where username=:username",
                    User.class);
            query.setParameter("username", username);
            return (User) query.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsernameAndPassword(LoginDTO loginDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username=:username and password=:password",
                User.class);
        query.setParameter("username", loginDTO.getUsername());
        query.setParameter("password", loginDTO.getPassword());
        return (User) query.getSingleResult();
    }

    public User findById(Integer id) {
        Query query = em.createNativeQuery("select * from user_tb where id = :id", User.class);
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Transactional
    public void update(Integer id, UserUpdateDTO userUpdateDTO) {
        Query query = em.createNativeQuery(
                "update user_tb set password = :password where id = :id");
        query.setParameter("id", id);
        String password = BCrypt.hashpw(userUpdateDTO.getPassword(), BCrypt.gensalt());
        query.setParameter("password", password);
        query.executeUpdate();
    }

}
