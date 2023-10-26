import java.util.List;

// Realizar la implementación de la interfaz Graph, para grafos no dirigidos y ponderados con una Matriz de costos. Calcular el orden de cada operación.
// Implementar el algoritmo de Prim.
public class PrimGraph implements Graph{

    @Override
    public void addVertex(Object x) {

    }

    @Override
    public void removeVertex(Object x) {

    }

    @Override
    public boolean hasVertex(Object v) {
        return false;
    }

    @Override
    public List getVertexes() {
        return null;
    }

    @Override
    public void addEdge(Object v, Object w) {

    }

    @Override
    public void removeEdge(Object v, Object w) {

    }

    @Override
    public boolean hasEdge(Object v, Object w) {
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
    public List getAdjacencyList(Object v) {
        return null;
    }
}
