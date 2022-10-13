import javax.swing.plaf.basic.BasicLookAndFeel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Block {
    int x, y, color;

    public Block(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Group {
    int x, y, rainbow, size;
    List<Block> blocks;

    public Group(int x, int y, int rainbow, int size, List<Block> blocks) {
        this.x = x;
        this.y = y;
        this.rainbow = rainbow;
        this.size = size;
        this.blocks = blocks;
    }

    public int getX() {
        return -x;
    }

    public int getY() {
        return -y;
    }

    public int getRainbow() {
        return -rainbow;
    }

    public int getSize() {
        return -size;
    }
}

public class Main_BOJ_21609 {
    static int[][] blocks;
    static int N;
    static int M;
    static int score = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());//검 -1, 무 0

        blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                blocks[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (true) {
            visit = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] > 0) {
                        bfs(i, j, blocks[i][j]);
                    }
                }
            }

            if (groups.isEmpty()) {
                break;
            } else {
                removeGroup(groups.poll().blocks);
            }

            gravity();
            turn();
            gravity();
        }

        System.out.println(score);
    }

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    static PriorityQueue<Group> groups = new PriorityQueue<>(Comparator.comparing(Group::getSize).thenComparing(Group::getRainbow).thenComparing(Group::getX).thenComparing(Group::getY));
    static boolean[][] visit;

    static void bfs(int x, int y, int color) {
        int size = 1;
        int rainbow = 0;
        Queue<Block> queue = new ArrayDeque<>();
        List<Block> blockList = new ArrayList<>();

        queue.add(new Block(x, y, blocks[x][y]));
        visit[x][y] = true;
        blockList.add(new Block(x, y));

        while (!queue.isEmpty()) {
            Block now = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];

                if (isIn(nx, ny) && !visit[nx][ny] && blocks[nx][ny] >= 0) {
                    if (blocks[nx][ny] != color) {
                        if (blocks[nx][ny] == 0) {
                            rainbow++;
                        } else {
                            continue;
                        }
                    }
                    visit[nx][ny] = true;
                    queue.add(new Block(nx, ny, blocks[nx][ny]));
                    blockList.add(new Block(nx, ny));
                    size++;
                }
            }
        }

        if (size >= 2) {
            Group g = new Group(x, y, rainbow, size, blockList);
            groups.add(g);
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    visit[i][j] = false;
                }
            }
        }
    }

    static void removeGroup(List<Block> toRmv) {
        for (Block b : toRmv) {
            blocks[b.x][b.y] = -2;
        }

        groups.clear();
        score += (int) Math.pow(toRmv.size(), 2);
    }

    static void gravity() {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[j][i] == -1) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (stack.isEmpty()) {
                            break;
                        }
                        if (blocks[k][i] == -2) {//빈칸이면
                            blocks[k][i] = stack.pop();
                        }
                    }
                } else if (blocks[j][i] != -2) {
                    stack.add(blocks[j][i]);
                    blocks[j][i] = -2;
                }
            }

            for (int j = N - 1; j >= 0; j--) {
                if (stack.isEmpty()) {
                    break;
                }
                if (blocks[j][i] == -2) {//빈칸이면
                    blocks[j][i] = stack.pop();
                }
            }
        }
    }

    static void turn() {
        int[][] temp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                temp[i][j] = blocks[i][j];
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[N - 1 - j][i] = temp[i][j];
            }
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N;
    }
}
