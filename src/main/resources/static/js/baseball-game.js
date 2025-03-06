let playerId = '';
let attempts = 0;
let gameOver = false;

function submitId() {
    playerId = document.getElementById('playerIdInput').value.trim();
    if (playerId) {
        alert('아이디가 제출되었습니다: ' + playerId);

        // 서버에 아이디를 보내서 게임 시작
        fetch('/game/start?playerId=' + playerId, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);  // 게임 시작 메시지
            })
            .catch(error => {
                console.error('Error:', error);
            });
    } else {
        alert('아이디를 입력해주세요.');
    }
}

function submitGuess(){
    let guessInput = document.getElementById("guessInput").value;

    if (!guessInput) {
        alert('숫자를 입력해주세요.');
        return;
    }

    attempts++;
    document.getElementById('attempts').innerText = `시도 횟수: ${attempts}`;

    if (gameOver){
        alert('게임이 끝났습니다. 다시 시작하려면 "다시하기" 버튼을 눌러주세요.')
    }

    fetch('/game/guess', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            playerId: playerId,  // 제출된 아이디
            guess: guessInput     // 입력된 숫자 추측
        })
    })
        .then(response => response.json())
        .then(data => {
            // 서버에서 받은 데이터를 처리하는 로직
            document.getElementById('result').innerText = `스트라이크: ${data.strike}, 볼: ${data.ball}`;
            document.getElementById('attempts').innerText = `시도 횟수: ${data.attempts}`;

            // 게임 종료 처리
            if (data.strike === 3) {
                gameOver = true;
                document.getElementById('submitButton').innerText = '다시하기'; // 버튼 텍스트 변경
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function resetGame() {
    if (!playerId) {
        alert("먼저 플레이어 아이디를 입력해주세요.");
        return;
    }

    // 서버에 새로운 게임 시작 요청 보내기
    fetch('/game/start?playerId=' + playerId, { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            alert(data.message); // 서버에서 새로운 게임 시작 메시지 받기

            // UI 초기화
            attempts = 0;
            gameOver = false;
            document.getElementById('attempts').innerText = `시도 횟수: ${attempts}`;
            document.getElementById('result').innerText = '';
            document.getElementById('submitButton').innerText = '확인'; // 버튼 텍스트 초기화
            document.getElementById('guessInput').value = ''; // 입력 필드 초기화
        })
        .catch(error => {
            console.error('Error:', error);
            alert("게임을 다시 시작하는 중 오류가 발생했습니다.");
        });
}