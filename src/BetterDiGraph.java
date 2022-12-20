/**
 * Implementation of an editable directed graph using Hashmaps.
 *
 * @author Shota Bennett
 */

import java.util.NoSuchElementException;
import java.util.LinkedList;
import java.util.HashMap;


public class BetterDiGraph implements EditableDiGraph {

    // Represents a graph using HashMap to maintain O(1) performance for adding and removing vertices

    private HashMap<Integer, LinkedList<Integer>> map;

    //ctors
    public BetterDiGraph() {
        map = new HashMap<>();
    }

    public BetterDiGraph(int vertexCount) {
        map = new HashMap<>();
        for(int i = 0; i < vertexCount; i++) {
            addVertex(i);
        }
    }

    @Override
    public void addEdge(int source, int destination) {

        // Adds vertices if they do not exist. Does not add edge if it already exists.
        if(!(map.containsKey(source)))
            addVertex(source);
        if(!(map.containsKey(destination)))
            addVertex(destination);
        if(map.get(source).contains(destination))
            return;

        // Adds edge from source to destination.
        map.get(source).add(destination);
    }

    @Override
    public void addVertex(int vertex) {
        //Does not add existing vertices.
        if(containsVertex(vertex))
            return;
        map.put(vertex, new LinkedList<>());
    }

    @Override
    public Iterable<Integer> getAdj(int vertex) {
        //Returns adjacency list for vertex
        return map.get(vertex);
    }

    @Override
    public int getEdgeCount() {
        int count = 0;
        for(Integer value : map.keySet())
            count += map.get(value).size();
        return count;
    }

    @Override
    public int getIndegree(int vertexKey) throws NoSuchElementException {
        //Counts how many nodes point to parameter node.
        int count = 0;
        if(!containsVertex(vertexKey))
            throw new NoSuchElementException("Vertex does not exist.");
        for(Integer value : map.keySet()) {
            if(map.get(value).contains(vertexKey))
                count++;
        }
        return count;
    }

    @Override
    public int getVertexCount() {
        return map.keySet().size();
    }

    @Override
    public void removeEdge(int source, int destination) {
        //Guard clauses: Checks vertices.
        if(!map.containsKey(source))
            return;
        if(!map.containsKey(destination))
            return;

        //Remove edge.
        map.get(source).remove(destination);
    }

    @Override
    public void removeVertex(int vertexKey) {
        //Check if graph contains key and removes.
        if(!map.containsKey(vertexKey))
            return;
        map.remove(vertexKey);

        //Remove from adjacency lists as well.
        for(Integer value : map.keySet()) {
            map.get(value).remove(Integer.valueOf(vertexKey));
        }
    }

    @Override
    public Iterable<Integer> vertices() {
        return map.keySet();
    }

    @Override
    public boolean isEmpty() {
        return map.size() == 0;
    }

    @Override
    public boolean containsVertex(int key) {
        return map.containsKey(key);
    }
}
