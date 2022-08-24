import java.io.*;
import java.util.*;

public class Main_BOJ_1676 {
    static HashMap<Integer, Integer> hashMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());

        for (int i = n; i > 0; i--) {
            factorization(i);
        }

        if (hashMap.get(2) == null || hashMap.get(5) == null) {
            System.out.println(0);
        }else{
            System.out.println(Math.min(hashMap.get(2), hashMap.get(5)));

        }
    }

    static void factorization(int num) {
        for (int i = 2; i <= num; i++) {
            while (num % i == 0) {
                hashMap.put(i, hashMap.getOrDefault(i, 0) + 1);
                num = num/i;
            }
        }
    }
}

