import java.util.Arrays;

class FloydWarshall {
    private static final int INF = Integer.MAX_VALUE; // 设定一个较大的数值，表示不可达
    private final int V; // 图中的顶点数
    private final int[][] dist; // 存储最短路径的距离矩阵
    private final int[][] next; // 记录路径中的下一个节点

    /**
     * 构造函数，初始化图的邻接矩阵
     * @param vertices 顶点数量
     */
    public FloydWarshall(int vertices) {
        this.V = vertices;
        dist = new int[V][V]; // 初始化邻接矩阵
        next = new int[V][V]; // 初始化路径矩阵
        for (int i = 0; i < V; i++) {
            Arrays.fill(dist[i], INF); // 初始时所有顶点间距离设为无穷大
            Arrays.fill(next[i], -1); // 初始时路径未知
            dist[i][i] = 0; // 自己到自己的距离为 0
        }
    }

    /**
     * 添加一条边，设置两个顶点之间的权重
     * @param u 起点
     * @param v 终点
     * @param weight 边的权重
     */
    public void addEdge(int u, int v, int weight) {
        dist[u][v] = weight; // 设置 u 到 v 的权重
        dist[v][u] = weight; // 无向图，v 到 u 也需要设定相同的权重
        next[u][v] = v; // 记录路径
        next[v][u] = u;
    }

    /**
     * Floyd-Warshall 算法核心实现
     * 计算所有节点间的最短路径，并记录路径信息
     */
    public void floydWarshall() {
        for (int k = 0; k < V; k++) { // 选取中间节点 k
            for (int i = 0; i < V; i++) { // 遍历所有起点 i
                for (int j = 0; j < V; j++) { // 遍历所有终点 j
                    // 如果 i 经过 k 到 j 的路径比 i 直接到 j 更短，则更新
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j]; // 更新最短距离
                        next[i][j] = next[i][k]; // 更新路径信息
                    }
                }
            }
            System.out.println("Step " + (k + 1) + ":");
            printSolution(); // 每次迭代后打印当前矩阵
        }
    }

    /**
     * 递归重构路径，按顺序输出走过的节点
     */
    public void printPath(int u, int v) {
        if (next[u][v] == -1) {
            System.out.println("No path");
            return;
        }
        System.out.print("Path from " + u + " to " + v + ": " + u);
        while (u != v) {
            u = next[u][v];
            System.out.print(" -> " + u);
        }
        System.out.println();
    }

    /**
     * 打印当前的最短路径矩阵
     */
    private void printSolution() {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) System.out.print("INF "); // 如果无法到达，则输出 INF
                else System.out.print(dist[i][j] + " "); // 输出最短路径长度
            }
            System.out.println();
        }
        System.out.println(); // 输出空行分隔每一步
    }

    /**
     * 主方法，创建图并运行 Floyd-Warshall 算法
     */
    public static void main(String[] args) {
        int vertices = 20; // 设置 20 个顶点
        FloydWarshall fw = new FloydWarshall(vertices);

        // 添加边及其权重
        fw.addEdge(0, 1, 5);
        fw.addEdge(0, 2, 3);
        fw.addEdge(0, 3, 8);
        fw.addEdge(1, 3, 6);
        fw.addEdge(2, 3, 2);
        fw.addEdge(2, 4, 8);
        fw.addEdge(3, 4, 3);
        fw.addEdge(3, 5, 1);
        fw.addEdge(4, 6, 4);
        fw.addEdge(5, 7, 7);
        fw.addEdge(6, 8, 9);
        fw.addEdge(7, 9, 2);
        fw.addEdge(8, 10, 6);
        fw.addEdge(9, 11, 3);
        fw.addEdge(10, 12, 5);
        fw.addEdge(11, 13, 4);
        fw.addEdge(12, 14, 7);
        fw.addEdge(13, 15, 6);
        fw.addEdge(14, 16, 3);
        fw.addEdge(15, 17, 8);
        fw.addEdge(16, 18, 2);
        fw.addEdge(17, 19, 4);
        fw.addEdge(18, 19, 2);

        // 运行 Floyd-Warshall 算法
        fw.floydWarshall();

        // 测试路径输出
        fw.printPath(0, 19);
        fw.printPath(3, 16);

        System.out.printf(a)
    }
}
