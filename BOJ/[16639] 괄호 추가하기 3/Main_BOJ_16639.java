import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int N;
    static List<Integer> numList;
    static List<Integer> nums;
    static List<Character> opList;
    static List<Character> ops;
    static int answer = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        numList = new ArrayList<>();
        nums = new ArrayList<>();
        opList = new ArrayList<>();
        ops = new ArrayList<>();

        String equation = br.readLine();

        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                numList.add(Character.getNumericValue(equation.charAt(i)));
            } else {
                opList.add(equation.charAt(i));
            }
        }

        nums.addAll(numList);
        ops.addAll(opList);

        int[] res = new int[N / 2];
        boolean[] visit = new boolean[N / 2];
        dfs(res, visit, 0, nums, ops);

        System.out.println(answer);
    }

    static void dfs(int[] res, boolean[] visit, int depth, List<Integer> nums, List<Character> ops) {
        if (depth == N / 2) {
            for (int i = 0; i < N / 2; i++) {
                calculate(res[i], nums, ops);
                for (int j = i + 1; j < N / 2; j++) {
                    if (res[j] > res[i]) {
                        res[j]--;
                    }
                }
            }

            answer = Math.max(answer, nums.get(0));

            nums.clear();
            nums.addAll(numList);
            ops.clear();
            ops.addAll(opList);

            return;
        }

        for (int i = 0; i < N / 2; i++) {
            if (visit[i]) {
                continue;
            }

            visit[i] = true;
            res[depth] = i;
            dfs(res, visit, depth + 1, nums, ops);
            visit[i] = false;
        }
    }

    static void calculate(int opIndex, List<Integer> nums, List<Character> ops) {
        char op = ops.get(opIndex);
        int res = 0;
        ops.remove(opIndex);

        switch (op) {
            case '+':
                res = nums.get(opIndex) + nums.get(opIndex + 1);
                break;
            case '-':
                res = nums.get(opIndex) - nums.get(opIndex + 1);
                break;
            case '*':
                res = nums.get(opIndex) * nums.get(opIndex + 1);
        }

        nums.remove(opIndex);
        nums.remove(opIndex);
        nums.add(opIndex, res);
    }
}
