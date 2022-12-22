import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_16967 {
    static int H;
    static int W;
    static int X;
    static int Y;
    static int[][] B;
    static int[][] A;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        B = new int[H + X][W + Y];
        A = new int[H][W];

        for (int i = 0; i < H + X; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < W + Y; j++) {
                B[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < X + H; i++) {
            for (int j = 0; j < Y + W; j++) {
                if (!isInBound(i, j)) {
                    if (i < H && j < W) {//A
                        A[i][j] = B[i][j];
                    }
                } else {
                    A[i][j] = B[i][j] - A[i - X][j - Y];
                }
            }
        }

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean isInBound(int x, int y) {
        return x >= X && y >= Y && x < H && y < W;
    }
}
