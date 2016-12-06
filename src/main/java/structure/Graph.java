package structure;

import main.Helpers;

public class Graph extends Structure {

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
