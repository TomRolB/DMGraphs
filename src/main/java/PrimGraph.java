import org.jetbrains.annotations.NotNull;

import java.util.*;

//Implement the Graph interface, using a non-directed and ponderated graph
//Use a cost Matrix. Calculate each and every operation order (Big "O" notation)
//Implement Prim algorithm as a primitive from the class.

public class PrimGraph<T> implements Graph<T>{
    private final int size; //Matrix row and column size
    private final Comparator<T> comparator; //Comparator we will be using with Prim algorithm
    private final IndexComparator idxComp = new IndexComparator(); // Comparator used to resize
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
    private int N = 0; //Vertices amount
    private int alpha = 0;//Edges amount
    private int fixPosition = 0;//Position we will be shifting whenever adding a vertex

    @Override
    public void addVertex(T x) {
        if (x == null) return;
        vertices.put(x,fixPosition++);
        N++;
    }// amortized O(1). Whenever needs to do the resize() it becomes an O(N^2)

    @Override
    public void removeVertex(T x) {
        if(x == null) return;
        if(!vertices.containsKey(x)) return;
        vertices.remove(x); N--;
        removeAllEdges(x);
    }//O(1)

    @Override
    public boolean hasVertex(T v) {
        return vertices.containsKey(v);
    } //O(1)

    @Override
    public List<T> getVertexes() {
        return vertices.keySet().stream().toList(); //O(1)
    }

    @Override
    public void addEdge(T v, T w, int weight) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w) || weight <0) return;
        int i = vertices.get(v), j = vertices.get(w);
        costMatrix[i][j] = costMatrix[j][i] = weight; //Change costMatrix values in it.
        alpha++;
    } //O(1)

    @Override
    public void removeEdge(T v, T w) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w) || !hasEdge(v,w)) return;
        int i = vertices.get(v), j = vertices.get(w);
        costMatrix[i][j] = costMatrix[j][i] = -1; //Change costMatrix values in it.
        alpha--;
    } //O(1)


    @Override
    public boolean hasEdge(T v, T w) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w)) return false;
        int i = vertices.get(v), j = vertices.get(w);
        return costMatrix[i][j] == costMatrix[j][i] && costMatrix[i][j] >=0;
    } // O(1)

    @Override
    public int order() {
        return N;
    } //O(1)

    @Override
    public int alpha() {return alpha;}//O(1)

    @Override
    public List<T> getAdjacencyList(T v) {
        if(!vertices.containsKey(v)) return null;
        int i = vertices.get(v);
        List<T> adjacencyList = new ArrayList<>(N);
        List<T> vertexList = getVertexes();
        for (T w: vertexList) {
            int j = vertices.get(w);
            if(costMatrix[i][j] >0) adjacencyList.add(w);
        }
        return adjacencyList;
    } //O(N)

    private void resize(boolean upsize) {
        // To avoid creating completely new matrices, we have a fixed
        // array size, which is not fully used. Instead, we use more or
        // less of it as size increases or increases.

        int arraySize = costMatrix.length;
        int[][] result;

        if (upsize) {
            // If the size of the array is not enough, this method expands it.
            result = new int[arraySize * 2][arraySize * 2];
        } else {
            // If the size of the array is too large compared to the actual
            // portion used, we downsize it.
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

        // Now, since the matrix was modified, we need to create the hashmap again
        List<T> vertexes = vertices.keySet().stream().sorted(idxComp).toList();
        Map<T,Integer> newHM = new HashMap<>(upsize? arraySize * 2 : arraySize / 2);

        for (int i = 0; i < vertexes.size(); i++) {
            T vertex = vertexes.get(i);
            newHM.put(vertex, i);
        }

        vertices = newHM;
    }

    private class IndexComparator implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return Integer.compare(vertices.get(o1), vertices.get(o2));
        }
    }

}
