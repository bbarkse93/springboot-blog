package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blog.dto.UpdateDTO;
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

    @GetMapping({ "/", "/board" })
    public String index(
            // RequestParam은 null값일 경우 default값을 입력해준다
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {
        // 1. 유효성 검사 필요 없음
        // 2. 인증검사 필요 없음

        List<Board> boarList = boardRepository.findAll(page); // page = 1

        int totalcount = boardRepository.count(); // totalCount = 5
        int totalpage = totalcount / 3; // totalPage = 1
        if (totalcount % 3 > 0) {
            totalpage = totalpage + 1; // totalPage = 2
        }
        boolean last = totalpage - 1 == page;

        // System.out.println("TEST: " + boarList.size());
        // System.out.println("TEST: " + boarList.get(0).getTitle());

        request.setAttribute("boardList", boarList);
        request.setAttribute("prevPage", page - 1);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", last);
        request.setAttribute("totalCount", totalcount);
        request.setAttribute("totalPage", totalpage);

        return "index";
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

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        // 2-1. 인증체크

        // 2-2. 권한검사

        // 3. 핵심 로직:
        Board board = boardRepository.findById(id);
        request.setAttribute("board", board);

        return "board/updateForm";

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

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션 접근
        Board board = boardRepository.findById(id);

        Boolean pageOwner = false; // 세션 초기값 false
        if (sessionUser != null) {
            pageOwner = sessionUser.getId() == board.getUser().getId();
        }

        request.setAttribute("board", board); // request에 board 담기
        request.setAttribute("userName", board.getUser().getUsername()); // request에 유저네임 담기
        request.setAttribute("pageOwner", pageOwner); // request에 세션에 접근한 pageOwner값(true or false) 담기

        return "/board/detail";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) { // Pathvariable 값 받기

        // 1. 인증체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; //
        }
        // 200번대는 잘 됐으니까 크게 신경 안써도 되고
        // 300번대는 redirection이기에 중요도 낮다
        // 하지만 400번대는 클라이언트의 오류이기때문에 상세하게 응답해야한다.
        // 404 view를 찾지 못했을 때
        // 405 method가 다를 때

        // 2. 권한검사
        Board board = boardRepository.findById(id);
        if (sessionUser.getId() != board.getUser().getId()) {
            return "redirect:/40x";
        }

        // 3. 모델에 접근해서 삭제
        // delete from board_tb where id = :id
        boardRepository.deleteById(id);

        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, UpdateDTO updateDTO) {
        // 1. 인증 체크
        // 2. 권한 검사
        // 3. 핵심 로직
        // update board_tb set title = :title, content = :content where id = :id;
        boardRepository.update(updateDTO, id);

        return "redirect:/board/{id}";
    }

}
