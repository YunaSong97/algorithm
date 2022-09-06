package SWEA;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_암호코드_스캔 {
    static int ans;
    static int[][] code;
    static int n;
    static int m;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T;
        T = Integer.parseInt(st.nextToken());

        for (int test_case = 1; test_case <= T; test_case++) {
            ans = 0;
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            code = new int[n][m * 4];

            for (int i = 0; i < n; i++) {
                String str = br.readLine();

                for (int j = 0; j < m; j++) {
                    int dec = Integer.parseInt(String.valueOf(str.charAt(j)), 16);
                    String bin = Integer.toBinaryString(dec);

                    int x = 0;
                    for (int k = 0; k < 4; k++) {
                        if (k < 4 - bin.length()) {
                            code[i][j * 4 + k] = 0;
                        } else {
                            code[i][j * 4 + k] = Character.getNumericValue(bin.charAt(x++));
                        }
                    }
                }
            }

            check();

            System.out.println("#" + test_case + " " + ans);
        }
        br.close();
    }

    static void check() {
        int[] num = new int[8];
        int cnt = 7;
        for (int i = 1; i < n; i++) {
            for (int j = 4 * m - 1; j >= 0; j--) {
                if (code[i][j] == 1 && code[i - 1][j] == 0) {
                    int[] rate = new int[3];

                    while (code[i][j] == 1) {
                        rate[2]++;
                        j--;
                    }
                    while (code[i][j] == 0) {
                        rate[1]++;
                        j--;
                    }
                    while (code[i][j] == 1) {
                        rate[0]++;
                        j--;
                    }

                    j++;
                    int min = 9999;
                    for (int k = 0; k < 3; k++) {
                        min = Math.min(min, rate[k]);
                    }

                    for (int k = 0; k < 3; k++) {
                        rate[k] /= min;
                    }

                    num[cnt--] = decode(rate);

                    if (cnt == -1) {
                        int even = 0;
                        int odd = 0;
                        for (int a = 1; a <= 8; a++) {
                            if (a % 2 == 1) {
                                odd += num[a - 1];
                            } else {
                                even += num[a - 1];
                            }
                        }

                        if ((odd * 3 + even) % 10 == 0) {
                            ans += (even + odd);
                        }

                        cnt = 7;
                    }
                }
            }
        }
    }

    static int decode(int[] ary) {
        int n = 0;

        if (ary[0] == 4) {
            return 3;
        } else if (ary[0] == 3) {
            return 7;
        } else {
            if (ary[2] == 4) {
                return 6;
            } else if (ary[2] == 3) {
                return 8;
            } else {
                if (ary[0] == 1) {
                    if (ary[1] == 3) {
                        return 4;
                    } else if (ary[1] == 1) {
                        return 9;
                    } else {
                        return 2;
                    }
                } else if (ary[0] == 2) {
                    if (ary[1] == 1) {
                        return 0;
                    } else if (ary[1] == 2) {
                        return 1;
                    } else {
                        return 5;
                    }
                }
            }
        }

        return n;
    }
}
