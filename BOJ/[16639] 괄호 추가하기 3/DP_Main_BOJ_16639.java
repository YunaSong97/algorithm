import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static int answer = 0;
    static int[][] min;
    static int[][] max;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        min = new int[N][N];
        max = new int[N][N];

        for (int i = 0; i < N; i++) {
            Arrays.fill(min[i], Integer.MAX_VALUE);
            Arrays.fill(max[i], Integer.MIN_VALUE);
        }

        String equation = br.readLine();

        for (int i = 0; i < N; i += 2) {
            min[i][i] = Character.getNumericValue(equation.charAt(i));
            max[i][i] = Character.getNumericValue(equation.charAt(i));
        }

        for (int j = 2; j < N; j += 2) {
            for (int i = 0; i < N - j; i += 2) {
                for (int k = i; k <= i + j - 2; k += 2) {
                    char op = equation.charAt(k + 1);
                    int[] temp = new int[4];
                    temp[0] = calculate(min[i][k], min[k + 2][i + j], op);
                    temp[1] = calculate(min[i][k], max[k + 2][i + j], op);
                    temp[2] = calculate(max[i][k], min[k + 2][i + j], op);
                    temp[3] = calculate(max[i][k], max[k + 2][i + j], op);

                    Arrays.sort(temp);

                    min[i][i + j] = Math.min(min[i][i + j], temp[0]);
                    max[i][i + j] = Math.max(max[i][i + j], temp[3]);
                }
            }
        }

        System.out.println(max[0][N - 1]);
    }

    static int calculate(int a, int b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
        }

        return 0;
    }
}
