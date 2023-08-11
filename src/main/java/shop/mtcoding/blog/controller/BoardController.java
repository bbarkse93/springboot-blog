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
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.BoardDetailDTO;
import shop.mtcoding.blog.dto.BoardWriteDTO;
import shop.mtcoding.blog.dto.UpdateDTO;
import shop.mtcoding.blog.dto.WriteDTO;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.Reply;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.repository.BoardRepository;
import shop.mtcoding.blog.repository.ReplyRepository;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private HttpSession session;

    @ResponseBody
    @GetMapping("/test/board/1")
    public Board test() {
        Board board = boardRepository.findById(1);
        return board;
    }

    @ResponseBody
    @GetMapping("/test/reply")
    public List<Reply> test1() {
        List<Reply> replies = replyRepository.findByBoardId(4);
        return replies;
    }

    @ResponseBody
    @GetMapping("/test/count")
    public String countTest() {
        int count = boardRepository.count("나");
        return "제목에 '나'가 포한된 게시글 수: " + count;
    }

    // index페이지를 줘!(글목록페이지)
    @GetMapping({ "/", "/board" })
    public String index(
            // RequestParam은 null값일 경우 default값을 입력해준다
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {

        // 1. 유효성 검사 필요 없음
        // 2. 인증검사 필요 없음

        List<Board> boarList = null;
        int totalCount = 0;
        request.setAttribute("keyword", keyword); // 공백 or 값 있음
        if (keyword.isBlank()) {
            boarList = boardRepository.findAll(page);
            totalCount = boardRepository.count();

        } else {
            boarList = boardRepository.findAll(page, keyword);
            totalCount = boardRepository.count(keyword);
        }

        // totalCount = 6
        int totalpage = totalCount / 3; // totalPage = 1
        if (totalCount % 3 > 0) {
            totalpage = totalpage + 1; // totalPage = 2
        }
        boolean last = totalpage - 1 == page;

        request.setAttribute("boardList", boarList);
        request.setAttribute("prevPage", page - 1);
        request.setAttribute("nextPage", page + 1);
        request.setAttribute("first", page == 0 ? true : false);
        request.setAttribute("last", last);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalpage);

        return "index";
    }

    // 글쓰기 페이지를 줘!!
    @GetMapping("/board/saveForm")
    public String saveForm() {
        // 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        return "/board/saveForm";
    }

    // 글수정 페이지를 줘!!
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        // 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; //
        }

        // 권한 검사
        Board board = boardRepository.findById(id);
        if (sessionUser.getId() != board.getUser().getId()) {
            return "redirect:/40x";
        }

        request.setAttribute("board", board);

        return "board/updateForm";

    }

    // id가 @@인 보드를 줘!
    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser"); // 세션 접근

        List<BoardDetailDTO> dtos = null;

        if (sessionUser == null) {
            dtos = boardRepository.findByIdJoinReply(id, null);
        } else {
            dtos = boardRepository.findByIdJoinReply(id, sessionUser.getId());
        }

        Boolean pageOwner = false; // 세션 초기값 false
        if (sessionUser != null) {
            pageOwner = sessionUser.getId() == dtos.get(0).getBoardUserId();
        }

        List<BoardWriteDTO> list = boardRepository.findByIdJoinUser(id);
        request.setAttribute("list", list);
        request.setAttribute("dtos", dtos); // request에 board 담기
        request.setAttribute("pageOwner", pageOwner); // request에 세션에 접근한 pageOwner값(true or false) 담기

        return "board/detail";
    }

    // 글을 인서트 해줘!!
    @PostMapping("/board/save")
    public String save(WriteDTO writeDTO) {
        // 유효성 검사
        if (writeDTO.getTitle() == null || writeDTO.getTitle().isEmpty()) {
            return "redirect:/40x";
        }
        if (writeDTO.getContent() == null || writeDTO.getContent().isEmpty()) {
            return "redirect:/40x";
        }

        // 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }

        // 핵심로직: 게시글 작성
        boardRepository.save(writeDTO, sessionUser.getId());

        return "redirect:/";
    }

    // 번호가 id인 글을 딜리트 해줘!!
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) { // Pathvariable 값 받기

        // 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm"; //
        }
        // 200번대는 잘 됐으니까 크게 신경 안써도 되고
        // 300번대는 redirection이기에 중요도 낮다
        // 하지만 400번대는 클라이언트의 오류이기때문에 상세하게 응답해야한다.
        // 404 view를 찾지 못했을 때
        // 405 method가 다를 때

        // 권한 검사
        Board board = boardRepository.findById(id);
        if (sessionUser.getId() != board.getUser().getId()) {
            return "redirect:/40x";
        }

        // 핵심 로직: 게시글 삭제
        // delete from board_tb where id = :id
        boardRepository.deleteById(id);

        return "redirect:/";
    }

    // 번호가 id인 글을 수정해 줘!
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, UpdateDTO updateDTO) {
        // 인증 검사
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/loginForm";
        }
        // 권한 검사
        Board board = boardRepository.findById(id);
        if (sessionUser.getId() != board.getUser().getId()) {
            return "redirect:/40x";
        }
        // 핵심 로직: 게시글 수정
        // query: update board_tb set title = :title, content = :content where id = :id;
        boardRepository.update(updateDTO, id);

        return "redirect:/board/{id}";
    }

}
