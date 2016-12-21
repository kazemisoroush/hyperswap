package structure;

import main.Helpers;

import java.util.ArrayList;

public class Graph extends Structure<Edge> {

    @Override
    public Node addNode(Node node) {
        return node;
    }

    @Override
    public ArrayList<Node> getNeighbors(Node node) {
        return null;
    }

    @Override
    public Edge addEdge(Edge edge) {
        return edge;
    }

    @Override
    public boolean hasNode(Node node) {
        return false;
    }

    @Override
    public boolean hasEdge(Edge edge) {
        return false;
    }

    @Override
    public double energy() {
        return 0;
    }

    @Override
    public String toString() {
        // list of nodes...
        String nodesString = Helpers.implode(this.nodes, ", ");

        // list of edges...
        String edgesString = Helpers.implode(this.edges, ", ");

        // format the object to string...
        return String.format("structure.Graph: |V| = %s, |E| = %s \n Nodes: [%s] \n Edges: [%s] \n", this.numberOfNodes(), this.numberOfEdges(), nodesString, edgesString);
    }
}
