import java.util.List;

public interface Graph<T> {
    /**
     *addVertex: this method should add a vertex to the graph
     * @param x : T type object that should represent a vertex from the graph. SHOULD NOT BE NULL
     */
    void addVertex(T x);
    void removeVertex(T x);
    boolean hasVertex(T v);
    List<T> getVertexes();

    void addEdge(T v, T w);
    void removeEdge(T v, T w);
    boolean hasEdge(T v, T w);

    int order();
    int alpha();

    List<T> getAdjacencyList(T v);
}
