import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_17780 {
    static class Chess {
        int x;
        int y;
        int color;
        List<Piece> pieces;

        public Chess(int x, int y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.pieces = new ArrayList<>();
        }

        public void movePieces(int nx, int ny, int color) {
            if (color == 0) {
                chess[nx][ny].pieces.addAll(this.pieces);
            } else if (color == 1) {
                Collections.reverse(this.pieces);
                chess[nx][ny].pieces.addAll(this.pieces);
            }

            for (Piece p : chess[nx][ny].pieces) {
                p.setPos(nx, ny);
                pieceAry[p.idx].setPos(nx, ny);
            }

            this.pieces.clear();
        }
    }

    static class Piece {
        int idx;
        int x, y;
        int dir;//오, 왼, 위, 아래

        public Piece(int idx, int x, int y, int dir) {
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        public void move() {
            if (chess[this.x][this.y].pieces.get(0).idx != this.idx) {
                return;
            }

            int nx = this.x + dx[this.dir];
            int ny = this.y + dy[this.dir];

            if (!isIn(nx, ny)) {
                setDir();
                nx = this.x + dx[this.dir];
                ny = this.y + dy[this.dir];

                if (chess[nx][ny].color != 2) {
                    move();
                }
                return;
            }

            if (chess[nx][ny].color == 0) {//흰
                chess[this.x][this.y].movePieces(nx, ny, 0);
            } else if (chess[nx][ny].color == 1) {//빨
                chess[this.x][this.y].movePieces(nx, ny, 1);
            } else if (chess[nx][ny].color == 2) {//파
                setDir();
                nx = this.x + dx[this.dir];
                ny = this.y + dy[this.dir];

                if (!isIn(nx, ny)) {
                    return;
                }
                if (chess[nx][ny].color != 2) {
                    move();
                }
            }
        }

        public void setPos(int nx, int ny) {
            this.x = nx;
            this.y = ny;
        }

        public void setDir() {
            switch (this.dir) {
                case 0:
                    this.dir = 1;
                    break;
                case 1:
                    this.dir = 0;
                    break;
                case 2:
                    this.dir = 3;
                    break;
                case 3:
                    this.dir = 2;
            }
        }
    }

    static int N;
    static int K;
    static Chess[][] chess;
    static Piece[] pieceAry;

    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        chess = new Chess[N][N];
        pieceAry = new Piece[K];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                chess[i][j] = new Chess(i, j, Integer.parseInt(st.nextToken()));
            }
        }

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int dir = Integer.parseInt(st.nextToken()) - 1;

            chess[x][y].pieces.add(new Piece(i, x, y, dir));
            pieceAry[i] = new Piece(i, x, y, dir);
        }

        int turn = 0;
        while (turn++ <= 1000) {
            for (int i = 0; i < K; i++) {
                pieceAry[i].move();
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (chess[i][j].pieces.size() >= 4) {
                        System.out.println(turn);
                        return;
                    }
                }
            }
        }

        System.out.println(-1);
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
}
