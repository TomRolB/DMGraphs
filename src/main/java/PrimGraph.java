import java.util.List;

// Realizar la implementación de la interfaz Graph, para grafos no dirigidos y ponderados
// con una Matriz de costos. Calcular el orden de cada operación.
// Implementar el algoritmo de Prim.
public class PrimGraph<T> implements Graph<T>{

    @Override
    public void addVertex(T x) {

    }

    @Override
    public void removeVertex(T x) {

    }

    @Override
    public boolean hasVertex(T v) {
        return false;
    }

    @Override
    public List<T> getVertexes() {
        return null;
    }

    @Override
    public void addEdge(T v, T w) {

    }

    @Override
    public void removeEdge(T v, T w) {

    }

    @Override
    public boolean hasEdge(T v, T w) {
        return false;
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public int alpha() {
        return 0;
    }

    @Override
    public List<T> getAdjacencyList(T v) {
        return null;
    }
}
