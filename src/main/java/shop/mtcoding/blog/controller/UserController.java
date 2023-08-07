package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.dto.LoginDTO;
import shop.mtcoding.blog.dto.UserUpdateDTO;

import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session; // request는 가방 session은 서랍

    // localhost:8080/check?username=ssar

    @GetMapping("/check")
    public ResponseEntity<String> check(String username) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>("유저네임이 중복되었습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("사용할 수 있습니당!", HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/test/login")
    public String testLogin() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "로그인이 되지 않았습니다";
        } else {
            return "로그인 됨 : " + sessionUser.getUsername();
        }
    }

    @PostMapping("/login")
    public String login(LoginDTO loginDTO) {
        // 부가로직
        // validation check (유효성 검사)
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }

        try {
            // 핵심기능
            User user = userRepository.findByUsernameAndPassword(loginDTO);
            session.setAttribute("sessionUser", user);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/exLogErr";
        }

    }

    // 실무
    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        // validation check (유효성 검사)
        if (joinDTO.getUsername() == null || joinDTO.getUsername().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getPassword() == null || joinDTO.getPassword().isEmpty()) {
            return "redirect:/40x";
        }
        if (joinDTO.getEmail() == null || joinDTO.getEmail().isEmpty()) {
            return "redirect:/40x";
        }
        // DB에 해당 username이 있는지 체크해보기
        User user = userRepository.findByUsername(joinDTO.getUsername());
        if (user != null) {
            return "redirect:/50x";
        }
        userRepository.save(joinDTO); // 핵심 기능
        return "redirect:/loginForm";
    }

    // DS의 책임 (컨트롤러 메서드 찾기, 바디데이터 파싱)
    // @PostMapping("/join")
    // public String join(String username, String password, String email) {
    // System.out.println("username: " + username);
    // System.out.println("password: " + password);
    // System.out.println("email: " + email);

    // return "redirect:/loginForm";
    // }

    // DS가 바디데이터를 파싱안하고 컨트롤러 method만 찾은 상황
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) {
    // String usename = request.getParameter("username");
    // String password = request.getParameter("password");
    // String email = request.getParameter("email");

    // return "redirect:/loginForm";
    // }

    // 얘 비정상임
    // @PostMapping("/join")
    // public String join(HttpServletRequest request) throws IOException {
    // // username=ssar&password=1234&email=ssar@nate.com
    // BufferedReader br = request.getReader();
    // String body = br.readLine();

    // // 이거 null인 이유: 버퍼에 데이터가 없어서
    // // 41번 라인에서 버퍼에 담긴 데이터가 전부 소모되었기 때문에
    // String username = request.getParameter("username");

    // System.out.println("body: " + body);
    // System.out.println("username: " + username);

    // return "redirect:/loginForm";
    // }

    // GET요청은 a태그, form태그 method=get, postman으로만 가능
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("user/{id}/update")
    public String update(@PathVariable Integer id, UserUpdateDTO userUpdateDTO) {
        // 1. 인증검사
        // 2. 권한검사
        // 3. 핵심로직
        // update user_tb set username = :username, password = :password, email = :email
        // where id = :id;
        // User user = userRepository.findById(id);
        userRepository.update(id, userUpdateDTO);
        return "redirect:/";

    }
}
