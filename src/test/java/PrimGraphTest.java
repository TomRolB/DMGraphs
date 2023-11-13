import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrimGraphTest {

    private final PrimGraph<String> pg;

    public PrimGraphTest() {
        Comparator<String> cmp = Comparator.naturalOrder();
        pg = new PrimGraph<>(cmp);
    }

    @Test
    void addVertex() {
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        assertTrue(pg.hasVertex("a"));
        assertTrue(pg.hasVertex("b"));
        assertTrue(pg.hasVertex("c"));
        List<String> vertexes = pg.getVertexes();
        assertEquals(3, pg.getVertexes().size());
        String[] trueVertexes = {"a", "b", "c"};
        for (int i = 0;  i < 3; i++) {
            assertEquals(trueVertexes[i], vertexes.get(i));
        }
    }

    @Test
    void removeVertex() {
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        pg.addVertex("i");
        pg.removeVertex("b");
        assertEquals(8, pg.getVertexes().size());
        Assertions.assertFalse(pg.hasVertex("b"));
        pg.removeVertex("i");
        assertEquals(7, pg.getVertexes().size());
        Assertions.assertFalse(pg.hasVertex("i"));
        pg.addVertex("b");
        assertEquals(8, pg.getVertexes().size());
        String[] lyrics = {"a", "c", "d", "e", "f", "g", "h", "b"};
        for (int i = 0; i < 8; i++) {
            assertTrue(pg.hasVertex(lyrics[i]));
        }
    }

    @Test
    void addEdge() {
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        pg.addVertex("i");
        pg.addVertex("j");
        pg.addEdge("a", "b", 30);
        pg.addEdge("b", "c", 10);
        pg.addEdge("j", "i", 20);
        pg.addEdge("a", "i", 20);
        assertTrue(pg.hasEdge("a", "c"));
        assertTrue(pg.hasEdge("b", "c"));
        assertTrue(pg.hasEdge("a", "i"));
    }

    @Test
    void removeEdge() {
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        pg.addVertex("i");
        pg.addVertex("j");
        pg.addEdge("a", "b", 30);
        pg.addEdge("b", "c", 10);
        pg.addEdge("j", "i", 20);
        pg.addEdge("a", "i", 20);
        pg.removeEdge("a", "b");
        Assertions.assertFalse(pg.hasEdge("a", "b"));
        pg.removeEdge("j", "i");
        Assertions.assertFalse(pg.hasEdge("j", "i"));
    }

    @Test
    void order() {
        assertEquals(0, pg.order());
        pg.addVertex("a");
        pg.removeVertex("a");
        assertEquals(0, pg.order());
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        assertEquals(4, pg.order());
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        assertEquals(8, pg.order());
        pg.addVertex("i");
        pg.addVertex("j");
        pg.removeVertex("a");
        assertEquals(9, pg.order());
        pg.removeVertex("d");
        pg.removeVertex("e");
        pg.removeVertex("i");
        assertEquals(6, pg.order());
    }

    @Test
    void alpha() {
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        pg.addVertex("i");
        pg.addVertex("j");
        assertEquals(0, pg.alpha());
        pg.addEdge("a", "b", 30);
        pg.removeEdge("a", "b");
        assertEquals(0, pg.alpha());
        pg.addEdge("a", "b", 22);
        pg.addEdge("b", "c", 10);
        assertEquals(2, pg.alpha());
        pg.addEdge("j", "i", 20);
        pg.addEdge("a", "i", 20);
        assertEquals(4, pg.alpha());
    }

    @Test
    void getAdjacencyList() {
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        pg.addVertex("i");
        pg.addVertex("j");
        pg.addEdge("a", "b", 30);
        pg.addEdge("b", "c", 10);
        pg.addEdge("j", "i", 20);
        pg.addEdge("a", "i", 20);
        pg.addEdge("c", "i", 20);
        pg.addEdge("b", "i", 20);
        List<String> al1 = pg.getAdjacencyList("i");
        assertEquals(4, al1.size());
        for (int i = 0;  i < 4; i++) {
            assertTrue(pg.hasEdge("i", al1.get(i)));
        }
        List<String> al2 = pg.getAdjacencyList("a");
        assertEquals(2, al2.size());
        for (int i = 0;  i < 2; i++) {
            assertTrue(pg.hasEdge("a", al2.get(i)));
        }
    }

    @Test
    void primTest() {
        pg.addVertex("1");
        pg.addVertex("2");
        pg.addVertex("3");
        pg.addVertex("4");
        pg.addVertex("5");
        pg.addVertex("6");
        pg.addVertex("1");

    }

    @Test
    void getMST(){
        //PPT Example
        pg.addVertex("1");
        pg.addVertex("2");
        pg.addVertex("3");
        pg.addVertex("4");
        pg.addVertex("5");
        pg.addVertex("6");

        pg.addEdge("1","2", 5);
        pg.addEdge("1","3", 6);
        pg.addEdge("1","4", 1);
        pg.addEdge("2","4", 5);
        pg.addEdge("2","6", 2);
        pg.addEdge("3","4", 5);
        pg.addEdge("3","5", 3);
        pg.addEdge("4","5", 6);
        pg.addEdge("4","6", 4);
        pg.addEdge("5","6", 6);
        Graph<String> MST = pg.prim();
        assertEquals(pg.order(), MST.order());
        assertTrue(MST.hasEdge("1","4"));
        assertTrue(MST.hasEdge("2","6"));
        assertTrue(MST.hasEdge("4","6"));
        assertTrue(MST.hasEdge("4","3"));
        assertTrue(MST.hasEdge("3","5"));
    }

}