package com.example.baseballgame.service;

import com.example.baseballgame.dto.GameResultDTO;
import com.example.baseballgame.entity.GameResult;
import com.example.baseballgame.repository.GameResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class BaseBallGameService {

    private final List<Integer> answer = RandomNumbers();
    private final GameResultRepository gameResultRepository;
    private int attempts = 0;


    private List<Integer> RandomNumbers() {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        while (numbers.size() < 3) {
            int number = random.nextInt(9) + 1;
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }
        return numbers;
    }

    public Map<String, Object> checkAnswer(String playerId, String input) {
        List<Integer> guess = new ArrayList<>();
        for (String s : input.split(",")) {
            guess.add(Integer.parseInt(s.trim()));
        }
        int strike = 0, ball = 0;
        attempts++;

        for (int i =0; i<3; i++){
            if (guess.get(i).equals(answer.get(i))) {
                strike++;
            } else if (answer.contains(guess.get(i))) {
                ball++;
            }
        }

        boolean isCorrect = strike == 3;


        if (isCorrect || strike == 0 && ball == 0) {
            GameResultDTO gameResultDTO = new GameResultDTO(playerId, input, strike, ball, isCorrect, attempts);

            // GameResult 생성
            GameResult gameResult = new GameResult();
            gameResult.setPlayerName(gameResultDTO.getPlayerName());
            gameResult.setGuess(gameResultDTO.getGuess());
            gameResult.setStrike(gameResultDTO.getStrike());
            gameResult.setBall(gameResultDTO.getBall());
            gameResult.setCorrect(gameResultDTO.isCorrect());

            // 게임이 끝날 때만 결과를 저장
            gameResultRepository.save(gameResult);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("strike", strike);
        result.put("ball", ball);
        result.put("out", (strike == 0 && ball == 0));
        result.put("attempts", attempts);

        return result;
    }

    public List<GameResult> getLeaderboard() {
        // 시도 횟수가 적은 순으로 게임 결과를 정렬하여 반환
        return gameResultRepository.findAllByOrderByAttemptsAsc();
    }
}
