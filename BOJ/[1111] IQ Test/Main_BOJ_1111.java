import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class ABSet {
    int a, b;

    public ABSet(int a, int b) {
        this.a = a;
        this.b = b;
    }
}

public class Main_BOJ_1111 {
    static int N;
    static int[] numbers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        numbers = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        if (N == 1) {
            System.out.println("A");
            return;
        }

        if (N == 2) {
            if (numbers[0] == numbers[1]) {
                System.out.println(numbers[0]);
            } else {
                System.out.println("A");
            }
            return;
        }

        //3개 이상
        if (numbers[1] == numbers[0]) {
            for (int i = 2; i < N; i++) {//앞에 두개 같은데 다른게 나오면
                if (numbers[i] != numbers[0]) {
                    System.out.println("B");
                    return;
                }
            }

            //전부 같으면
            System.out.println(numbers[0]);
        } else {//앞 두수가 다름
            if ((numbers[2] - numbers[1]) % (numbers[1] - numbers[0]) != 0 || (numbers[1] * numbers[1] - numbers[0] * numbers[2]) % (numbers[1] - numbers[0]) != 0) {
                System.out.println("B");
                return;
            }

            ABSet abSet = new ABSet((numbers[2] - numbers[1]) / (numbers[1] - numbers[0]), (numbers[1] * numbers[1] - numbers[0] * numbers[2]) / (numbers[1] - numbers[0]));

            if (N == 3) {
                System.out.println(getNext(numbers[2], abSet));
            } else {
                for (int i = 2; i < N - 1; i++) {
                    if (numbers[i + 1] != abSet.a * numbers[i] + abSet.b) {
                        System.out.println("B");
                        return;
                    }
                }
                System.out.println(getNext(numbers[N - 1], abSet));
            }
        }
    }

    static int getNext(int x0, ABSet abSet) {

        return x0 * abSet.a + abSet.b;
    }
}
