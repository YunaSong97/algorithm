import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_BOJ_11967 {
	static class Room {
		int x, y;
		boolean lightOn;
		List<Switch> switches;

		public Room(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void setLightOn(boolean lightOn) {
			if (!this.lightOn) {
				this.lightOn = lightOn;
				answer++;
			}
		}
	}

	static class Switch {
		Room to;

		public Switch(Room to) {
			this.to = to;
		}

		public void lightOn() {
			this.to.setLightOn(true);
		}
	}

	static int N;
	static int M;
	static Room[][] rooms;
	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };
	static int answer;
	static boolean[][] visit;
	static boolean[][] canMove;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		rooms = new Room[N][N];
		answer = 0;
		visit = new boolean[N][N];
		canMove = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				rooms[i][j] = new Room(i, j);
				rooms[i][j].switches = new ArrayList<>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;

			rooms[x][y].switches.add(new Switch(rooms[a][b]));
		}

		rooms[0][0].setLightOn(true);

		bfs();

		System.out.println(answer);
	}

	static void bfs() {
		Queue<Room> queue = new ArrayDeque<>();
		queue.add(new Room(0, 0));
		visit[0][0] = canMove[0][0] = true;

		while (!queue.isEmpty()) {
			Room now = queue.poll();

			for (int d = 0; d < 4; d++) {
				int nx = now.x + dx[d];
				int ny = now.y + dy[d];
				if (isIn(nx, ny) && !canMove[nx][ny] && !visit[nx][ny]) {
					canMove[nx][ny] = true;
				}
			}

			for (Switch s : rooms[now.x][now.y].switches) {
				s.lightOn();
				if (!visit[s.to.x][s.to.y] && canMove[s.to.x][s.to.y]) {
					visit[s.to.x][s.to.y] = true;
					queue.add(new Room(s.to.x, s.to.y));
				}
			}

			for (int d = 0; d < 4; d++) {
				int nx = now.x + dx[d];
				int ny = now.y + dy[d];
				if (isIn(nx, ny) && !visit[nx][ny] && rooms[nx][ny].lightOn) {
					visit[nx][ny] = true;
					queue.add(rooms[nx][ny]);
				}
			}

		}
	}

	static boolean isIn(int x, int y) {
		return x >= 0 && y >= 0 && x < N && y < N;
	}
}
