import java.util.*;

class Graph {
    int v;
    List<List<int[]>> adj;

    Graph(int v) {
        this.v = v;
        adj = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adj.add(new ArrayList<>());
        }
    }

    void addEdge(int u, int v, int w) {
        adj.get(u).add(new int[] { v, w });
        adj.get(v).add(new int[] { u, w });
    }
}
