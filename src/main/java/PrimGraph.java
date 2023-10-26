import org.jetbrains.annotations.NotNull;

import java.util.*;

// Realizar la implementación de la interfaz Graph, para grafos no dirigidos y ponderados
// con una Matriz de costos. Calcular el orden de cada operación.
// Implementar el algoritmo de Prim.
public class PrimGraph<T> implements Graph<T>{
    private final int size; //Matrix row and column size
    private final Comparator<T> comparator; //Comparator we will be using with Prim algorithm
    private int[][] costMatrix; //Cost Matrix

    public PrimGraph(@NotNull Comparator<T> comparator){
        this.comparator = comparator;
        this.size = 10;
        this.costMatrix = new int[size][size];
    }
    public PrimGraph(@NotNull Comparator<T> comparator, int size){
        this.comparator = comparator;
        this.size = size;
        this.costMatrix = new int[size][size];
    }
    //Edges should have values greater than or equal to 0, so we use -1 to represent that there is no edge between v and w
    private Map<T,Integer> vertices = new HashMap<>();//Map with pairs of vertices and indices
    private int N = 0; //Graph size
    private int alpha = 0;
    @Override
    public void addVertex(T x) {
        if (x == null) return;
        vertices.put(x,N++);
    }

    @Override
    public void removeVertex(T x) {
        if(x == null) return;
        if(!vertices.containsKey(x)) return;
        vertices.remove(x); N--;
    }

    @Override
    public boolean hasVertex(T v) {
        return vertices.containsKey(v);
    }

    @Override
    public List<T> getVertexes() {
        return vertices.keySet().stream().toList();
    }

    @Override
    public void addEdge(T v, T w, int weight) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w) || weight <0) return;
        int i = vertices.get(v), j = vertices.get(w);
        costMatrix[i][j] = costMatrix[j][i] = weight;
        alpha++;
    }

    @Override
    public void removeEdge(T v, T w) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w) || !hasEdge(v,w)) return;
        int i = vertices.get(v), j = vertices.get(w);
        costMatrix[i][j] = costMatrix[j][i] = -1; //Change costMatrix values in it.
        alpha--;
    }


    @Override
    public boolean hasEdge(T v, T w) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w)) return false;
        int i = vertices.get(v), j = vertices.get(w);
        return costMatrix[i][j] == costMatrix[j][i] && costMatrix[i][j] >=0;
    }

    @Override
    public int order() {
        return N;
    }

    @Override
    public int alpha() {return alpha;}

    @Override
    public List<T> getAdjacencyList(T v) {

        int i = vertices.get(v);
        List<T> adjacencyList = new ArrayList<>(N);
        List<T> vertexList = getVertexes();
        for (T w: vertexList) {
            int j = vertices.get(w);
            if(costMatrix[i][j] >=0) adjacencyList.add(w);
        }
        return adjacencyList;
    }
    //[[0,2,-1],
    // [2,0,4],
    // [-1,4,0] ]

    private void resize(boolean upsize) {
        // To avoid creating completely new matrices, we have a fixed
        // array size, which is not fully used. Instead, we use more or
        // less of it as size increases or increases.

        // If the size of the array is not enough, this method expands it.

        // If the size of the array is too large compared to the actual
        // portion used, we downsize it.

        int arraySize = costMatrix.length;
        int[][] result;

        if (upsize) {
            result = new int[arraySize * 2][arraySize * 2];
        } else {
            result = new int[arraySize / 2][arraySize / 2];
        }


        int currentRow = 0;
        int currentCol;
        for (int i = 0; i < size; i++) {
            if (costMatrix[0][i] == -1) continue;
            currentCol = 0;

            for (int j = 0; j < size; j++) {
                if (costMatrix[i][j] == -1) continue;
                result[currentRow][currentCol] = costMatrix[i][j];
                currentCol++;
            }
            currentRow++;
        }

        costMatrix = result;
    }

}
