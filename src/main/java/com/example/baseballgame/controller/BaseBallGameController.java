package com.example.baseballgame.controller;

import com.example.baseballgame.entity.GameResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.baseballgame.service.BaseBallGameService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class BaseBallGameController {

    private final BaseBallGameService baseBallGameService;

    @PostMapping("/game/start")
    public ResponseEntity<?> startGame(@RequestParam String playerId) {
        // 플레이어 아이디로 게임을 시작하는 로직 추가
        // 예: 새로운 게임을 시작하거나, 현재 진행 중인 게임이 있으면 새로 시작
        Map<String, String> response = new HashMap<>();
        response.put("message", "게임을 시작합니다. 아이디: " + playerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/game/guess")
    public Map<String, Object> guess(@RequestBody Map<String, String> requestData){
        String playerId = requestData.get("playerId");
        String guess = requestData.get("guess");
        return baseBallGameService.checkAnswer(playerId, guess);
    }

    @GetMapping("/game/leaderboard")
    public ResponseEntity<List<GameResult>> getLeaderboard() {
        List<GameResult> leaderboard = baseBallGameService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }
}
