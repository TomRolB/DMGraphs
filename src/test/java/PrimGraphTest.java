import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

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
        Assertions.assertTrue(pg.hasVertex("a"));
        Assertions.assertTrue(pg.hasVertex("b"));
        Assertions.assertTrue(pg.hasVertex("c"));
        List<String> vertexes = pg.getVertexes();
        Assertions.assertEquals(3, pg.getVertexes().size());
        String[] trueVertexes = {"a", "b", "c"};
        for (int i = 0;  i < 3; i++) {
            Assertions.assertEquals(trueVertexes[i], vertexes.get(i));
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
        Assertions.assertEquals(8, pg.getVertexes().size());
        Assertions.assertFalse(pg.hasVertex("b"));
        pg.removeVertex("i");
        Assertions.assertEquals(7, pg.getVertexes().size());
        Assertions.assertFalse(pg.hasVertex("i"));
        pg.addVertex("b");
        Assertions.assertEquals(8, pg.getVertexes().size());
        String[] lyrics = {"a", "c", "d", "e", "f", "g", "h", "b"};
        for (int i = 0; i < 8; i++) {
            Assertions.assertTrue(pg.hasVertex(lyrics[i]));
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
        Assertions.assertTrue(pg.hasEdge("a", "c"));
        Assertions.assertTrue(pg.hasEdge("b", "c"));
        Assertions.assertTrue(pg.hasEdge("a", "i"));
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
        Assertions.assertEquals(0, pg.order());
        pg.addVertex("a");
        pg.removeVertex("a");
        Assertions.assertEquals(0, pg.order());
        pg.addVertex("a");
        pg.addVertex("b");
        pg.addVertex("c");
        pg.addVertex("d");
        Assertions.assertEquals(4, pg.order());
        pg.addVertex("e");
        pg.addVertex("f");
        pg.addVertex("g");
        pg.addVertex("h");
        Assertions.assertEquals(8, pg.order());
        pg.addVertex("i");
        pg.addVertex("j");
        pg.removeVertex("a");
        Assertions.assertEquals(9, pg.order());
        pg.removeVertex("d");
        pg.removeVertex("e");
        pg.removeVertex("i");
        Assertions.assertEquals(6, pg.order());
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
        Assertions.assertEquals(0, pg.alpha());
        pg.addEdge("a", "b", 30);
        pg.removeEdge("a", "b");
        Assertions.assertEquals(0, pg.alpha());
        pg.addEdge("a", "b", 22);
        pg.addEdge("b", "c", 10);
        Assertions.assertEquals(2, pg.alpha());
        pg.addEdge("j", "i", 20);
        pg.addEdge("a", "i", 20);
        Assertions.assertEquals(4, pg.alpha());
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
        Assertions.assertEquals(4, al1.size());
        for (int i = 0;  i < 4; i++) {
            Assertions.assertTrue(pg.hasEdge("i", al1.get(i)));
        }
        List<String> al2 = pg.getAdjacencyList("a");
        Assertions.assertEquals(2, al2.size());
        for (int i = 0;  i < 2; i++) {
            Assertions.assertTrue(pg.hasEdge("a", al2.get(i)));
        }
    }
}