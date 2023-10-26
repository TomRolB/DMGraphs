import java.util.List;

public interface Graph<T> {
    /**
     *addVertex: this method should add a vertex to the graph
     * @param x : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL
     */
    void addVertex(T x);
    /**
     *removeVertex: this method should delete a vertex that should already exist in the structure. If not, this method should throw an exception
     * @param x : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL.
     */
    void removeVertex(T x);
    /**
     *hasVertex: this method should return whether a vertex is in the structure.
     * @param v : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL.
     */
    boolean hasVertex(T v);
    /**
     *getVertexes: this method should return a list containing all vertices
     */
    List<T> getVertexes();
    /**
     *addEdge: this method should connect two vertices that should already exist in the structure. If not, this method should do nothing
     * @param v : index that should point to a vertex from the graph. SHOULD NOT BE NULL
     * @param w : index that should point to a vertex from the graph. SHOULD NOT BE NULL
     */
    void addEdge(T v, T w, int weight);
    /**
     *removeEdge: this method should remove the edge between two vertices that should already exist in the structure. If not, this method should do nothing
     * @param v : position that should point to a vertex from the graph. SHOULD NOT BE NULL
     * @param w : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL
     */
    void removeEdge(T v, T w);
    /**
     *hasEdge: this method should return true if there exists an edge between two vertices that should already exist in the structure. If not, returns false
     * @param v : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL
     * @param w : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL
     */
    boolean hasEdge(T v, T w);
    /**
     *order: this method should return the graph's order
     */
    int order();
    /**
     *alpha: this method should return the number of edges in the graph
     */
    int alpha();
    /**
     *getAdjacencyList: this method should return the adjacency list of the vertex "v".
     * @param v : vertex from which the adjacency list will be gotten, that should be in the graph . SHOULD NOT BE NULL
     */
    List<T> getAdjacencyList(T v);
}
