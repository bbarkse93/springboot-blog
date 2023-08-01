package shop.mtcoding.blog.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.JoinDTO;
import shop.mtcoding.blog.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {

        // 부가로직
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

        try {
            userRepository.save(joinDTO); // 핵심로직
        } catch (Exception e) {
            return "redirect:/50x";

        }

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
        return "redirect:/";
    }
}
