package shop.mtcoding.blog.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList);
        return "/index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        // 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        return "/board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // 유효성 검사
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            return "redirect:/40x";
        }
        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirect:/40x";
        }

        // 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        boardRepository.save(writeDTO, sessionUser.getId());

        return "redirect:/";
    }

    // @GetMapping("/board/{id}")
    // public String detail(@PathVariable Integer id, HttpServletRequest request) {
    // Board board = boardRepository.findById(id);
    // request.setAttribute("board", board);

    // return "/board/detail";
    // }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        Board board = boardRepository.findById(id);
        request.setAttribute("username", board.getUser().getUsername());
        request.setAttribute("board", board);

        return "/board/detail";
    }

}
