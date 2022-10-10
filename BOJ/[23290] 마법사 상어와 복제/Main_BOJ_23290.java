package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node {
    List<Integer> fishes;
    List<Boolean> smells;
    List<Integer> temp;

    public Node() {
        this.fishes = new ArrayList<>();
        this.smells = new ArrayList<>();
        this.temp = new ArrayList<>();
        smells.add(false);
        smells.add(false);
    }
}

class Shark {
    int x, y;

    public Shark(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main_BOJ_23290 {
    static int M, S;
    static Node[][] map = new Node[5][5];
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static Shark shark;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                map[i][j] = new Node();
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken()) - 1;

            map[x][y].fishes.add(dir);
        }

        st = new StringTokenizer(br.readLine());
        shark = new Shark(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        for (int i = 0; i < S; i++) {
            moveFishes();

            maxFish = -1;

            int[] route = new int[3];
            getSharkRoute(0, route);

            moveShark(maxRoute);
            removeSmell();
        }

        int answer = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                answer += map[i][j].fishes.size();
            }
        }

        System.out.println(answer);
    }

    static void moveFishes() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (map[i][j].fishes.size() != 0) {
                    for (int f : map[i][j].fishes) {
                        for (int d = 0; d < 8; d++) {
                            int nx = i + dx[(f - d + 8) % 8];
                            int ny = j + dy[(f - d + 8) % 8];

                            if (isIn(nx, ny) && !(shark.x == nx && shark.y == ny) && map[nx][ny].smells.stream().allMatch(s -> s == false)) {
                                map[nx][ny].temp.add((f - d + 8) % 8);

                                break;
                            }
                            if (d == 7) {
                                map[i][j].temp.add(f);
                            }
                        }
                    }
                }
            }
        }
    }

    static int maxFish = -1;
    static Shark[] maxRoute = new Shark[3];

    static void getSharkRoute(int depth, int[] route) {
        int[] sx = {-1, 0, 1, 0};//상 좌 하 우
        int[] sy = {0, -1, 0, 1};

        if (depth == 3) {
            int sum = 0;

            Shark s = new Shark(shark.x, shark.y);
            boolean[][] visit = new boolean[5][5];
            Shark[] res = new Shark[3];

            for (int i = 0; i < 3; i++) {
                s.x += sx[route[i]];
                s.y += sy[route[i]];

                if (!isIn(s.x, s.y)) {
                    return;
                }

                if (!visit[s.x][s.y]) {
                    sum += map[s.x][s.y].temp.size();
                    visit[s.x][s.y] = true;
                }

                res[i] = new Shark(s.x, s.y);
            }

            if (sum > maxFish) {
                maxFish = sum;

                for (int i = 0; i < 3; i++) {
                    maxRoute[i] = res[i];
                }
            }
            return;
        }

        for (int d = 0; d < 4; d++) {
            route[depth] = d;

            getSharkRoute(depth + 1, route);
        }
    }

    static void moveShark(Shark[] route) {
        for (Shark s : route) {
            if (map[s.x][s.y].temp.size() != 0) {
                map[s.x][s.y].temp.clear();
                map[s.x][s.y].smells.add(true);
            }
        }
        shark.x = route[2].x;
        shark.y = route[2].y;

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (map[i][j].smells.size() != 3) {
                    map[i][j].smells.add(false);
                }
            }
        }
    }

    static void removeSmell() {
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                map[i][j].smells.remove(0);
            }
        }

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                if (map[i][j].temp.size() != 0) {
                    for (int f : map[i][j].temp) {
                        map[i][j].fishes.add(f);
                    }
                    map[i][j].temp.clear();
                }
            }
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 1 && y >= 1 && x <= 4 && y <= 4;
    }
}
