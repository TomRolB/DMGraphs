import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

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
        fillGraph(size);
    }
    public PrimGraph(@NotNull Comparator<T> comparator, int size){
        this.comparator = comparator;
        this.size = size;
        this.costMatrix = new int[size][size];
        fillGraph(size);
    }

    private void fillGraph(int size) {
        for (int i = 0; i < size; i++) {
            Arrays.fill(costMatrix[i], 2147483647 );
        }
    }
    //Edges should have values greater than or equal to 0, so we use 2147483647 to represent that there is no edge between v and w
    private Map<T,Integer> vertices = new HashMap<>();//Map with pairs of vertices and indices
    private int N = 0; //Vertices amount
    private int alpha = 0;//Edges amount
    private int fixPosition = 0;//Position we will be shifting whenever adding a vertex

    @Override
    public void addVertex(T x) {
        if (x == null) return;
        vertices.put(x,fixPosition++);
        N++;
        if (fixPosition == costMatrix.length) resize(true);
    }// amortized O(1). Whenever needs to do the resize() it becomes an O(N^2) algorithm

    @Override
    public void removeVertex(T x) {
        if(x == null) return;
        if(!vertices.containsKey(x)) return;
        removeAllEdges(x);
        vertices.remove(x); N--;
        if (fixPosition == costMatrix.length / 4) resize(false);
    }//amortized O(N). Whenever needs to do the resize() it becomes an O(N^2) algorithm

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
        costMatrix[i][j] = costMatrix[j][i] = 2147483647; //Change costMatrix values in it.
        alpha--;
    } //O(1)


    @Override
    public boolean hasEdge(T v, T w) {
        if(!vertices.containsKey(v) || !vertices.containsKey(w)) return false;
        int i = vertices.get(v), j = vertices.get(w);
        return costMatrix[i][j] == costMatrix[j][i] && costMatrix[i][j] >=0 && costMatrix[i][j] != 2147483647;
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
            if (costMatrix[0][i] == 2147483647) continue;
            currentCol = 0;

            for (int j = 0; j < size; j++) {
                if (costMatrix[i][j] == 2147483647) continue;
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

    private void removeAllEdges(T x) {
        int i = vertices.get(x);
        for (int j = 0; j < costMatrix.length; j++) {
            costMatrix[i][j] = costMatrix[j][i] = 2147483647;
        }
    }

    public PrimGraph<T> prim () {
        PrimGraph<T> newGraph = new PrimGraph<>(this.comparator, costMatrix.length);
        Map<Integer, T> reversedHashMap = vertices.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));
        for (int i= 0; i < N; i++) {
            newGraph.addVertex(reversedHashMap.get(i));
        }
        HashSet<T> v = new HashSet<>(List.copyOf(getVertexes()));
        HashSet<T> x = new HashSet<>();
        int index = 0;
        while (!v.isEmpty()) {
            T chosenVertex = reversedHashMap.get(index);
            v.remove(chosenVertex);
            x.add(chosenVertex);
            int min = 99999;
            int departure = 0;
            int arrival = 0;
            for (T node : x) {
                int row = vertices.get(node);
                for (int i = 0; i < N; i++) {
                    if (costMatrix[row][i] < min && i != row && !x.contains(reversedHashMap.get(i))) {
                        departure = row;
                        arrival = i;
                        min = costMatrix[row][i];
                        index = i;
                    }
                }
            }
            newGraph.addEdge(reversedHashMap.get(arrival), reversedHashMap.get(departure), min);
        }
        return newGraph;
    }

    private class IndexComparator implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return Integer.compare(vertices.get(o1), vertices.get(o2));
        }
    }

}
