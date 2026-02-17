import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class BusRouteGUI extends JFrame {

    String[] stops = {
    "Ameerpet","Punjagutta","SR Nagar","LB Nagar","Kukatpally",
    "Hitech City","Miyapur","Banjara Hills","Secunderabad","Begumpet",
    "Gachibowli","Dilsukhnagar","Uppal","Nagole","Charminar",
    "Mehdipatnam","Tarnaka","ECIL","Kompally","Shamirpet",
    "Patancheru","JNTU","Chandanagar","Nampally","Malakpet"
    };


    Graph g;
    JComboBox<String> sourceBox;
    JComboBox<String> destBox;
    JTextArea resultArea;

    public BusRouteGUI() {

        setTitle("Bus Route Finder");
        setSize(550,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(30,30,30));

        JLabel title = new JLabel("Bus Route Finder");
        title.setBounds(180,10,250,30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial",Font.BOLD,20));
        panel.add(title);

        JLabel srcLabel = new JLabel("Source:");
        srcLabel.setBounds(60,60,80,25);
        srcLabel.setForeground(Color.WHITE);
        panel.add(srcLabel);

        sourceBox = new JComboBox<>(stops);
        sourceBox.setBounds(140,60,150,25);
        panel.add(sourceBox);

        JLabel destLabel = new JLabel("Destination:");
        destLabel.setBounds(60,100,80,25);
        destLabel.setForeground(Color.WHITE);
        panel.add(destLabel);

        destBox = new JComboBox<>(stops);
        destBox.setBounds(140,100,150,25);
        panel.add(destBox);

        JButton findBtn = new JButton("Find Route");
        JButton addRouteBtn = new JButton("Add Route");
        addRouteBtn.setBounds(320,120,120,30);
        addRouteBtn.setBackground(new Color(0,200,120));
        addRouteBtn.setForeground(Color.WHITE);
        panel.add(addRouteBtn);

        JLabel distLabel = new JLabel("Distance:");
        distLabel.setBounds(60,130,80,25);
        distLabel.setForeground(Color.WHITE);
        panel.add(distLabel);

        JTextField distField = new JTextField();
        distField.setBounds(140,130,150,25);
        panel.add(distField);

        findBtn.setBounds(320,80,120,30);
        findBtn.setBackground(new Color(0,150,255));
        findBtn.setForeground(Color.WHITE);
        panel.add(findBtn);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced",Font.BOLD,14));
        resultArea.setBackground(Color.BLACK);
        resultArea.setForeground(Color.GREEN);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(60,150,380,170);
        panel.add(scroll);
        JLabel nameLabel = new JLabel("Developed by Roshan");
        nameLabel.setBounds(350,320,200,20);
        nameLabel.setForeground(Color.LIGHT_GRAY);
        nameLabel.setFont(new Font("Arial",Font.PLAIN,12));
        panel.add(nameLabel);

        add(panel);

        g = new Graph(25);
        g.addEdge(11,12,5);
        g.addEdge(12,13,4);
        g.addEdge(13,14,6);
        g.addEdge(14,15,7);
        g.addEdge(15,16,3);
        g.addEdge(16,17,5);
        g.addEdge(17,18,6);
        g.addEdge(18,19,4);
        g.addEdge(19,20,8);
        g.addEdge(20,21,5);
        g.addEdge(21,22,4);
        g.addEdge(22,23,6);
        g.addEdge(23,24,3);

        g.addEdge(5,10,3);
        g.addEdge(10,15,6);
        g.addEdge(3,14,7);
        g.addEdge(8,16,5);
        g.addEdge(6,18,9);
        g.addEdge(2,12,6);
        g.addEdge(4,21,8);


        findBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int src = sourceBox.getSelectedIndex();
                int dest = destBox.getSelectedIndex();
                resultArea.setText(findPath(src,dest));
            }
        });
        addRouteBtn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        int s1 = sourceBox.getSelectedIndex();
        int s2 = destBox.getSelectedIndex();

        try {
            int d = Integer.parseInt(distField.getText());
            g.addEdge(s1, s2, d);
            resultArea.setText("New route added:\n" 
                + stops[s1] + " ↔ " + stops[s2] 
                + "\nDistance: " + d + " km");
        } catch (Exception ex) {
            resultArea.setText("Enter valid distance");
        }
    }
});


        setVisible(true);
    }

    String findPath(int src,int dest){
        int[] dist=new int[g.v];
        boolean[] vis=new boolean[g.v];
        int[] parent=new int[g.v];

        Arrays.fill(dist,Integer.MAX_VALUE);
        Arrays.fill(parent,-1);
        dist[src]=0;

        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->a[1]-b[1]);
        pq.add(new int[]{src,0});

        while(!pq.isEmpty()){
            int[] cur=pq.poll();
            int u=cur[0];
            if(vis[u])continue;
            vis[u]=true;

            for(int[] n:g.adj.get(u)){
                int v=n[0],w=n[1];
                if(dist[u]+w<dist[v]){
                    dist[v]=dist[u]+w;
                    parent[v]=u;
                    pq.add(new int[]{v,dist[v]});
                }
            }
        }

        if(dist[dest]==Integer.MAX_VALUE) return "No path found";

        java.util.List<Integer> path=new ArrayList<>();
        for(int at=dest;at!=-1;at=parent[at]) path.add(at);
        Collections.reverse(path);

        String res="Route:\n";
        for(int i=0;i<path.size();i++){
            res+=stops[path.get(i)];
            if(i!=path.size()-1) res+=" -> ";
        }
        res+="\n\nDistance: "+dist[dest]+" km";
        return res;
    }

    public static void main(String[] args){
        new BusRouteGUI();
    }
}
