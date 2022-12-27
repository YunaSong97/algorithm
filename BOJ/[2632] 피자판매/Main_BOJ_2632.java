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
    static HashMap<Integer, Integer> mapA;
    static HashMap<Integer, Integer> mapB;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        A = new int[m];
        B = new int[n];
        mapA = new HashMap<>();
        mapB = new HashMap<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            A[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            B[i] = Integer.parseInt(st.nextToken());
        }

        makeMap(mapA, A, m);
        makeMap(mapB, B, n);

        for (Map.Entry<Integer, Integer> entry : mapA.entrySet()) {
            if (N - entry.getKey() == 0) {
                answer += entry.getValue();
            } else {
                answer += entry.getValue() * mapB.getOrDefault(N - entry.getKey(), 0);
            }
        }

        for (Map.Entry<Integer, Integer> entry : mapB.entrySet()) {
            if (N - entry.getKey() == 0) {
                answer += entry.getValue();
            }
        }

        System.out.println(answer);
    }

    static void makeMap(HashMap<Integer, Integer> hashMap, int[] ary, int size) {
        for (int start = 0; start < size; start++) {
            int sum = 0;

            for (int end = start; end < start + size; end++) {
                if (end % size == start - 1) {
                    break;
                }
                sum += ary[end % size];

                if (sum <= N) {
                    hashMap.put(sum, hashMap.getOrDefault(sum, 0) + 1);
                } else {
                    break;
                }
            }
        }
    }
}
