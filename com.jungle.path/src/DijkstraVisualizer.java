/**
 * @author JungleXia
 * @version 1.0
 * @title ${NAME}
 * @description ${description}
 * @date 2025/3/24 16:54:11
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class DijkstraVisualizer extends JPanel {
    private static final int INF = Integer.MAX_VALUE;
    private final int V;
    private final int[][] graph;
    private final int[] dist;
    private final boolean[] visited;
    private final int[] prev;
    private final List<Point> nodes;
    private final List<int[]> edges;
    private int step = 0;

    public DijkstraVisualizer(int vertices) {
        this.V = vertices;
        graph = new int[V][V];
        dist = new int[V];
        visited = new boolean[V];
        prev = new int[V];
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        Arrays.fill(prev, -1);
        for (int i = 0; i < V; i++) {
            Arrays.fill(graph[i], INF);
            graph[i][i] = 0;
        }

        Random rand = new Random();
        for (int i = 0; i < V; i++) {
            nodes.add(new Point(rand.nextInt(400) + 50, rand.nextInt(400) + 50));
        }
    }

    public void addEdge(int u, int v, int weight) {
        graph[u][v] = weight;
        graph[v][u] = weight;
        edges.add(new int[]{u, v, weight});
    }

    public void dijkstra(int src) {
        Arrays.fill(dist, INF);
        dist[src] = 0;
        for (int i = 0; i < V - 1; i++) {
            int u = minDistance();
            if (u == -1) break;
            visited[u] = true;
            for (int v = 0; v < V; v++) {
                if (!visited[v] && graph[u][v] != INF && dist[u] != INF && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
            step++;
            repaint();
            try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private int minDistance() {
        int min = INF, minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int[] edge : edges) {
            Point p1 = nodes.get(edge[0]);
            Point p2 = nodes.get(edge[1]);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            g.drawString(String.valueOf(edge[2]), (p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        }
        for (int i = 0; i < nodes.size(); i++) {
            g.setColor(visited[i] ? Color.GREEN : Color.RED);
            g.fillOval(nodes.get(i).x - 10, nodes.get(i).y - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), nodes.get(i).x, nodes.get(i).y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dijkstra Visualization");
        DijkstraVisualizer panel = new DijkstraVisualizer(10);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

        panel.addEdge(0, 1, 4);
        panel.addEdge(0, 2, 1);
        panel.addEdge(1, 2, 2);
        panel.addEdge(1, 3, 5);
        panel.addEdge(2, 3, 8);
        panel.addEdge(2, 4, 10);
        panel.addEdge(3, 5, 6);
        panel.addEdge(4, 5, 2);
        panel.addEdge(5, 6, 3);
        panel.addEdge(6, 7, 7);
        panel.addEdge(7, 8, 9);
        panel.addEdge(8, 9, 2);
        panel.addEdge(6, 9, 4);

        new Thread(() -> panel.dijkstra(0)).start();
    }
}

