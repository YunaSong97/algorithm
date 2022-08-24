package BOJ;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

class Node {
    int x;
    int y;
    int food;
    int init;
    PriorityQueue<Tree> priorityQueue = new PriorityQueue<>();
    ArrayList<Tree> deadTrees = new ArrayList<>();

    public Node(int x, int y, int food, int init) {
        this.x = x;
        this.y = y;
        this.food = food;
        this.init = init;
    }
}

class Tree implements Comparable<Tree> {
    int x, y;
    int age;

    public Tree(int x, int y, int age) {
        this.x = x;
        this.y = y;
        this.age = age;
    }

    @Override
    public int compareTo(Tree o) {
        return this.age - o.age;
    }
}

class Main_BOJ_16235 {
    static int n;
    static int m;
    static int k;
    static int ans;
    static ArrayList<Node> nodes;
    static int[] dx = {1, 1, -1, -1, -1, 0, 1, 0};
    static int[] dy = {1, 0, -1, 0, 1, 1, -1, -1};

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        ans = 0;
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();
        nodes = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                nodes.add(new Node(i, j, 5, sc.nextInt()));
            }
        }

        for (int i = 0; i < m; i++) {
            Tree t = new Tree(sc.nextInt(), sc.nextInt(), sc.nextInt());
            nodes.get((t.x - 1) * n + t.y - 1).priorityQueue.add(t);
        }

        for (int i = 0; i < k; i++) {
            eat();
            deadToFood();
            grow();
            addFood();
        }

        for (Node n : nodes) {
            ans += n.priorityQueue.size();
        }

        System.out.println(ans);
    }

    static void eat() {
        ArrayList<Tree> live = new ArrayList<>();

        for (Node node : nodes) {
            while (!node.priorityQueue.isEmpty()) {
                Tree t = node.priorityQueue.poll();

                if (node.food >= t.age) {// 먹음
                    node.food -= t.age;
                    t.age++;
                    live.add(t);
                } else {
                    node.deadTrees.add(t);
                }
            }
            node.priorityQueue.addAll(live);
            live.clear();
        }
    }

    static void deadToFood() {
        for (Node node : nodes) {
            for (Tree dead : node.deadTrees) {
                node.food += (dead.age / 2);
            }
            node.deadTrees.clear();
        }
    }

    static void grow() {
        for (Node node : nodes) {
            for (Tree t : node.priorityQueue) {
                if (t.age % 5 == 0) {
                    for (int i = 0; i < 8; i++) {
                        int nx = node.x + dx[i];
                        int ny = node.y + dy[i];

                        if (!isIn(nx, ny)) {
                            continue;
                        }

                        Node nNode = nodes.get((nx - 1) * n + (ny - 1));
                        if (nNode != null) {
                            nNode.priorityQueue.add(new Tree(nx, ny, 1));
                        }
                    }
                }
            }
        }
    }

    static void addFood() {
        for (Node node : nodes) {
            node.food += node.init;
        }
    }

    static boolean isIn(int x, int y) {
        return x >= 1 && y >= 1 && x <= n && y <= n;
    }
}