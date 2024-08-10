package com.spring.board_summer.controller;

import com.spring.board_summer.dto.BoardDto;
import com.spring.board_summer.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/list")
    public String boardList(Model model) {
        List<BoardDto> boardList = boardService.findAllList();
        model.addAttribute("boardList", boardList);
        return "list";
    }

    @GetMapping("/detail/{id}")
    public String boardDetail(@PathVariable Long id, Model model) {
        BoardDto detailBoard = boardService.findBoardById(id);
        model.addAttribute("detailBoard", detailBoard);
        return "detail";
    }

    @GetMapping("/post")
    public String boardPostForm(Model model) {
        model.addAttribute("post", new BoardDto());
        return "post";
    }

    @PostMapping("/post")
    public String boardPost(@ModelAttribute BoardDto boardDto, Model model) {
        boardService.saveBoard(boardDto);
        if (boardDto.getBoardId() != null) {
            return "redirect:/detail/" + boardDto.getBoardId();
        } else {
            model.addAttribute("error", "Failed to create post");
            return "post";
        }
    }

    @GetMapping("/edit/{id}")
    public String boardEditForm(@PathVariable Long id, Model model) {
        BoardDto board = boardService.findBoardById(id);
        if (board != null) {
            model.addAttribute("post", board);
            return "edit";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String boardEdit(@PathVariable Long id, @ModelAttribute BoardDto boardDto) {
        boardService.updateBoard(id, boardDto);
        return "redirect:/detail/" + boardDto.getBoardId();
    }

    @PostMapping("/delete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/list";
    }
}
