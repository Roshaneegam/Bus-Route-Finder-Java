import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] stops = {
    "Ameerpet",
    "Punjagutta",
    "SR Nagar",
    "LB Nagar",
    "Kukatpally",
    "Hitech City",
    "Miyapur",
    "Banjara Hills",
    "Secunderabad",
    "Begumpet",
    "Gachibowli",
    "Dilsukhnagar"
};

        Graph g = new Graph(12);
        g.addEdge(5, 6, 6);
        g.addEdge(6, 7, 5);
        g.addEdge(7, 8, 7);
        g.addEdge(8, 9, 3);
        g.addEdge(9, 10, 4);
        g.addEdge(10, 11, 6);
        g.addEdge(2, 9, 8);
        g.addEdge(4, 10, 5);
        g.addEdge(1, 7, 9);


        System.out.println("Available Bus Stops:");
        for (int i = 0; i < stops.length; i++) {
            System.out.println(i + " - " + stops[i]);
        }

        System.out.println("\nEnter source stop number:");
        int src = sc.nextInt();

        System.out.println("Enter destination stop number:");
        int dest = sc.nextInt();

        Dijkstra d = new Dijkstra();
        d.shortestPath(g, src, dest, stops);
    }
}
