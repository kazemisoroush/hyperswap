package structure;

import main.Helpers;

public class Graph extends Structure<Node, Edge> {

    @Override
    public int addNode(Node node) {
        return 0;
    }

    @Override
    public int addEdge(Edge edge) {
        return 0;
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
    public String toString() {
        // list of nodes...
        String nodesString = Helpers.implode(this.nodes, ", ");

        // list of edges...
        String edgesString = Helpers.implode(this.edges, ", ");

        // format the object to string...
        return String.format("structure.Graph: |V| = %s, |E| = %s \n Nodes: [%s] \n Edges: [%s] \n", this.numberOfNodes(), this.numberOfEdges(), nodesString, edgesString);
    }
}
