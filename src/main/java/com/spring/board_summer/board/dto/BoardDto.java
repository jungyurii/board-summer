package com.spring.board_summer.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {

    private Long boardId;

    private String title;

    private String content;

}