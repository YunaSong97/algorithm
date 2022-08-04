import java.io.*;
import java.util.*;

public class Main_BOJ_1620 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> hashMap2 = new HashMap<>();

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            String str = st.nextToken();
            hashMap.put(String.valueOf(i+1), str);
            hashMap2.put(str, String.valueOf(i+1));
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            String str =st.nextToken();
            if (hashMap.get(str)!=null) {
                System.out.println(hashMap.get(str));
            } else {
                System.out.println(hashMap2.get(str));
            }
        }
    }
}

