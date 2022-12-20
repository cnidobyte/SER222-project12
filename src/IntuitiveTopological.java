/**
 * Class implementing a type of topological sort.
 *
 * @author Shota Bennett
 */

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

public class IntuitiveTopological implements TopologicalSort {
    //Initializers
    private HashMap<Integer, Boolean> marked;
    private HashMap<Integer, Boolean> onStack;
    private boolean cycle;
    private BetterDiGraph graph;
    private LinkedList<Integer> list;

    //Constructors
    public IntuitiveTopological(BetterDiGraph graph) {
        onStack = new HashMap<>();
        marked = new HashMap<>();
        this.graph = graph;
        for(Integer key : graph.vertices()) {
            marked.put(key, false);
            onStack.put(key, false);
        }
        for(Integer key : graph.vertices()) {
            if(!marked.get(key))
                depthFirstSearch(graph, key);
        }
    }

    @Override
    public Iterable<Integer> order(){
        //return topological sort.
        list = new LinkedList<>();

        //check for cycles, return null list.
        if(!isDAG())
            return null;

        //Provide topological order list, removing from graph. Destructive.
        while(!graph.isEmpty()) {
            Integer removeIndex = 0;
            for(Iterator<Integer> iterator = graph.vertices().iterator(); iterator.hasNext();) {
                Integer integer = iterator.next();
                if(graph.getIndegree(integer) == 0) {
                    list.add(integer);
                    iterator.remove();
                }
            }
        }
        return list;
    }

    @Override
    public boolean isDAG() {
        return !cycle;
    }

    private void depthFirstSearch(BetterDiGraph graph, int iterator) {
        //Simple depth first search to check for cycles.
        onStack.put(iterator, true);
        marked.put(iterator, true);
        for(int w : graph.getAdj(iterator)) {
            if(!marked.get(w)) {
                depthFirstSearch(graph, w);
            }
            else if(onStack.get(w)) {
                cycle = true;
            }
        }
        onStack.put(iterator, false);
    }
}
