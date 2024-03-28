package kr.easw.lesson3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGameWithoutTails {

    private static final int BOARD_SIZE = 10;
    // 0 - 빈 타일
    // 1 - 스네이크 블럭
    // 2 - 아이템
    private static final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    private static final Random RANDOM = new Random();

    private static int score = 0;

    private static SnakeLocation location = new SnakeLocation(0, 0);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.print("[우측 (r) | 좌측 (l) | 위 (u) | 아래 (d) | 종료 (0) ] : ");
            if (!nextDirection(scanner.next())) {
                System.out.println("게임 오버!");
                System.out.printf("점수: %d\n", score);
                break;
            }
            if (!hasItemOnBoard())
                placeRandomItem();
        }
    }

    /**
     * 해당 메서드는 다음과 같은 역할을 가져야 합니다 :
     * 사용자의 입력을 받고, 다음 위치로 옮기거나 게임을 종료해야 합니다.
     * <p>
     * 허용되는 입력은 다음과 같습니다 :
     * - 우측(r) | 좌측 (l) | 위 (u) | 아래 (d) | 종료 (0)
     * <p>
     * 다음 좌표는 location 변수에 계속해서 업데이트되어야 합니다.
     * 만약 다음 좌표에 아이템이 존재한다면, 점수를 1 증가하고 다음 좌표의 값을 0으로 되돌려야 합니다.
     *
     * 만약 값이 최대 값 (BOARD_SIZE)이상이 되거나 최소 값(0) 아래로 내려간다면 같은 좌표로 설정하여 이동하지 않도록 해야합니다.
     *
     * 만약 사용자의 입력이 종료(0)였다면, false값을 반환하여 게임을 종료해야 합니다.
     */
    private static boolean nextDirection(String keyword) {
    // 사용자 입력에 따라 location 업데이트
    switch (keyword) {
        case "r": // 우측으로 이동
            if (location[1] < BOARD_SIZE - 1) location[1]++;
            break;
        case "l": // 좌측으로 이동
            if (location[1] > 0) location[1]--;
            break;
        case "u": // 위로 이동
            if (location[0] > 0) location[0]--;
            break;
        case "d": // 아래로 이동
            if (location[0] < BOARD_SIZE - 1) location[0]++;
            break;
        case "0": // 게임 종료
            return false;
        default: // 유효하지 않은 입력
            System.out.println("유효하지 않은 입력입니다. 다시 입력해주세요.");
            break;
    }
    
    // 다음 위치에 아이템이 있는지 확인하고 점수 업데이트
    if (board[location[0]][location[1]] == 1) {
        score++;
        board[location[0]][location[1]] = 0; // 아이템을 획득했으므로 보드에서 제거
    }

    return true; // 게임 계속 진행
}

    private static void printBoard() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (location.getX() == x && location.getY() == y) {
                    System.out.print("◼ ");
                    continue;
                }
                switch (board[x][y]) {
                    case 0:
                        System.out.print("・ ");
                        break;
                    case 1:
                        System.out.print("◼");
                        break;
                    case 2:
                        System.out.println("* ");
                        break;
                }
            }
            System.out.println();
        }
    }

    private static void placeRandomItem() {
        // for문의 조건으로 랜덤을 넣을 경우, 계속 비교하여 최종 결과값이 바뀌므로 변수로 선언합니다.
        int toPlace = (int) (RANDOM.nextDouble() * 10);
        for (int i = 0; i < toPlace; i++) {
            int retry = 0;
            while (retry < 5) {
                SnakeLocation locate = new SnakeLocation(RANDOM.nextInt() * BOARD_SIZE, RANDOM.nextInt() * BOARD_SIZE);
                if (board[locate.getX()][locate.getY()] != 0) {
                    retry++;
                    continue;
                }
                board[locate.getX()][locate.getY()] = 2;
                break;
            }
        }
    }

    private static boolean hasItemOnBoard() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (board[x][y] == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class SnakeLocation {
        private final int x;
        private final int y;

        public SnakeLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public SnakeLocation adjust(int x, int y) {
            return new SnakeLocation(this.x + x, this.y + y);
        }
    }
}
