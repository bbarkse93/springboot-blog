package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
    public static void main(String[] args) {

        String encPassword1 = BCrypt.hashpw("1234", BCrypt.gensalt());
        System.out.println("encPassword: " + encPassword1);
        String encPassword2 = BCrypt.hashpw("1234", BCrypt.gensalt());
        System.out.println("encPassword: " + encPassword2);

        boolean isvalid1 = BCrypt.checkpw("12345", encPassword1);
        System.out.println(isvalid1);

        boolean isvalid2 = BCrypt.checkpw("12345", encPassword1);
        System.out.println(isvalid2);

        System.out.println("length: " + encPassword1.length());
    }
}
