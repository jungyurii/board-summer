package com.spring.board_summer.service;

import com.spring.board_summer.dto.BoardDto;
import com.spring.board_summer.entity.Board;
import com.spring.board_summer.repository.BoardRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardDto> findAllList() {

        List<Board> boardList = boardRepository.findAll(); // boardList 가져오기 (Entity 형태)
        ArrayList<BoardDto> boardDtoArrayList = new ArrayList<>(); // boardDto로 변환 준비

        // BoardEntity -> BoardDto
        BoardDto boardDto = null;
        for (Board board : boardList) {
            boardDtoArrayList.add(entityToDto(board)); // 변환된 것 하나씩 넣기
        }
        return boardDtoArrayList;
    }

    public BoardDto findBoardById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            return entityToDto(optionalBoard.get());
        } else {
            return null;
        }
    }

    public void saveBoard(BoardDto boardDto) {
        Board board = dtoToEntity(boardDto);
        Board savedBoard = boardRepository.save(board);
        boardDto.setBoardId(savedBoard.getBoardId());
    }

    public void updateBoard(Long id, BoardDto boardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get(); // 게시글 가져오기
            board.setTitle(boardDto.getTitle());
            board.setContent(boardDto.getContent());
            boardRepository.save(board);
            boardDto.setBoardId(board.getBoardId());
        }
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    // Entity -> DTO
    private BoardDto entityToDto(Board board) {
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }

    // DTO -> Entity
    private Board dtoToEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        return board;
    }
}
