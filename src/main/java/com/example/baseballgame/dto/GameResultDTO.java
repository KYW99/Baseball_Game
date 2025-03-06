package com.example.baseballgame.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class GameResultDTO {

    private Long id;

    private String playerName;
    private String guess;
    private int strike;
    private int ball;
    private boolean isCorrect;
    private int attempts;


    public GameResultDTO(String playerName, String guess, int strike, int ball, boolean isCorrect, int attempts) {
        this.playerName = playerName;
        this.guess = guess;
        this.strike = strike;
        this.ball = ball;
        this.isCorrect = isCorrect;
        this.attempts = attempts;
    }

}
