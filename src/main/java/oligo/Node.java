package oligo;

class Node {
    private int parent;
    private int overlap;

    Node(int p, int o) {
        parent = p;
        overlap = o;
    }

    public int getParent() {
        return parent;
    }

    public int getOverlap() {
        return overlap;
    }
}