package algorithms;
import java.util.*;

public class ShortestPath {

    private int[][] map;

    public static class Node {

        public int x;
        public int y;
        public Node parent;
        public Double g = 0.0;
        public Double h = 0.0;
        public Double f = 0.0;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ") ";
        }
    }

    public ShortestPath(int[][] map) {
        this.map = map;
    }

    protected boolean isGoal(Node node, Node end) {
        return (node.x == end.x) && (node.y == end.y);
    }

    protected Double g(Node from, Node to) {

        if (from.x == to.x && from.y == to.y) {
            return 0.0;
        }

        if (map[to.y][to.x] == 1) {
            return 1.0;
        }

        return Double.MAX_VALUE;
    }

    protected List<Node> generateSuccessors(Node node) {
        List<Node> ret = new LinkedList<Node>();
        int x = node.x;
        int y = node.y;
        if (y < map.length - 1 && map[y + 1][x] == 1) {
            ret.add(new Node(x, y + 1));
        }

        if (x < map[0].length - 1 && map[y][x + 1] == 1) {
            ret.add(new Node(x + 1, y));
        }

        if (y > 0 && map[y - 1][x] == 1) {
            ret.add(new Node(x, y - 1));
        }

        if (x > 0 && map[y][x - 1] == 1) {
            ret.add(new Node(x - 1, y));
        }
        return ret;
    }

    public Double g(Node node) {
        Double g = 0.0;
        while (node != null) {
            g += node.g;
            node = node.parent;
        }
        return g;
    }

    protected Double h(Node from, Node to) {
        /* Use the Manhattan distance heuristic.  */
        return new Double(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
    }

    protected Double f(Node from, Node to) {
        return g(from) + h(from, to);
    }

    public List<Node> compute(Node start, Node end) {
        List<Node> openset = new ArrayList<Node>();
        openset.add(start);

        start.g = 0.0;
        start.h = h(start, end);
        start.f = start.g + start.h;

        List<Node> closedset = new ArrayList<Node>();

        while (!openset.isEmpty()) {
            Node x = getBestNode(openset, end);
            if (isGoal(x, end)) {
                List<Node> list = new ArrayList<Node>();
                list.add(0, x);
                while (x != null) {
                    list.add(0, x.parent);
                    x = x.parent;
                }
                return list;

            }

            openset.remove(x);
            closedset.add(x);

            for (Node y : generateSuccessors(x)) {
                if (contains(closedset, y)) {
                    continue;
                }

                Double try_g_score = g(x) + h(x, y);
                boolean best_try;
                if (!contains(openset, y)) {
                    openset.add(y);
                    best_try = true;
                } else if (try_g_score < g(y)) {
                	best_try = true;
                } else {
                	best_try = false;
                }

                if (best_try) {
                    y.parent = x;
                    y.g = try_g_score;
                    y.h = h(y, end);
                    y.f = g(y) + h(y, end);
                }
            }
        }


        return null;

    }

    protected boolean equal(Node node, Node end) {
        return (node.x == end.x) && (node.y == end.y);
    }

    protected boolean contains(List<Node> list, Node n) {
        for (Node x : list) {
            if (equal(x, n)) {
                return true;
            }
        }
        return false;
    }

    private Node getBestNode(List<Node> openset, Node end) {

        Node ret = openset.get(0);
        Double retf = f(ret, end);
        for (Node n : openset) {
            if (f(n, end) < retf) {
                ret = n;
                retf = f(n, end);
            }
        }
        return ret;
    }

}
