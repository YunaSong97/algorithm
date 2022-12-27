import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_BOJ_2632 {
    static int N;
    static int m, n;
    static int[] A;
    static int[] B;
    static int answer = 0;

    static int[] Asum;
    static int[] Bsum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        A = new int[m];
        B = new int[n];
        Asum = new int[2000001];
        Bsum = new int[2000001];

        for (int i = 0; i < m; i++) {
            A[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < n; i++) {
            B[i] = Integer.parseInt(br.readLine());
        }

        makeMap(Asum, A, m);
        makeMap(Bsum, B, n);
        Asum[0] = 1;
        Bsum[0] = 1;

        for (int i = 0; i <= N; i++) {
            answer += Asum[i] * Bsum[N - i];
        }

        System.out.println(answer);
    }

    static void makeMap(int[] sumAry, int[] ary, int size) {
        for (int start = 0; start < size; start++) {
            int sum = 0;

            for (int end = start; end < start + size; end++) {
                if (end % size == start - 1) {
                    break;
                }
                sum += ary[end % size];

                if (sum <= N) {
                    sumAry[sum]++;
                } else {
                    break;
                }
            }
        }
    }
}
