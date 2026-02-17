import java.util.*;

class Dijkstra {
    void shortestPath(Graph g, int src, int dest, String[] stops) {
        int[] dist = new int[g.v];
        boolean[] vis = new boolean[g.v];
        int[] parent = new int[g.v];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[] { src, 0 });

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];

            if (vis[u])
                continue;
            vis[u] = true;

            for (int[] n : g.adj.get(u)) {
                int v = n[0];
                int w = n[1];

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    pq.add(new int[] { v, dist[v] });
                }
            }
        }

        if (dist[dest] == Integer.MAX_VALUE) {
            System.out.println("No path found");
            return;
        }

        List<Integer> path = new ArrayList<>();
        for (int at = dest; at != -1; at = parent[at]) {
            path.add(at);
        }

        Collections.reverse(path);

        System.out.println("\nShortest Path:");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(stops[path.get(i)]);
            if (i != path.size() - 1)
                System.out.print(" -> ");
        }

        System.out.println("\nDistance: " + dist[dest] + " km");
    }
}
