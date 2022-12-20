/**
 * Program for generating kanji component dependency order via topological sort.
 * 
 * @author Shota Bennett, Acuna
 * @version 1.0
 */
import java.io.*;
import java.util.HashMap;

public class BennettMain {
    
    /**
     * Entry point for testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Initializers
        HashMap<Integer, Character> nodeMap = new HashMap<>();
        BetterDiGraph nodes = new BetterDiGraph();
        IntuitiveTopological sortedGraph;

        //Map kanji values to integer Keys
        hashKanji(nodeMap);

        //Print unsorted list from data-kanji.txt
        System.out.println("ORIGINAL:");
        for(Integer key : nodeMap.keySet()) {
            nodes.addVertex(key);
            String value = nodeMap.get(key).toString();
            System.out.print(value);
        }
        System.out.println();

        //Add edges to graph
        edgeFromFile(nodes);

        //Provide topological sort of graph and print.
        //Unchecked NullPointerException thrown if graph has cycles.
        sortedGraph = new IntuitiveTopological(nodes);
        System.out.println("SORTED:");
        Iterable<Integer> list = sortedGraph.order();
        if(list == null)
            throw new IllegalArgumentException("Cannot provide topological sort: graph contains cycles.");
        for(Integer iter : list) {
            System.out.print(nodeMap.get(iter));;
        }
    }

    public static void hashKanji(HashMap<Integer, Character> map) {
        try(BufferedReader indexReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data-kanji.txt")), "UTF8"));) {
            // skip first line
            String line = indexReader.readLine();

            //read all lines, Hash kanji.
            line = indexReader.readLine();
            while(line != null) {
                if(Character.compare(line.charAt(0), '#') != 0){
                    String[] splitLine = line.split("\t");
                    Integer integerKey = Integer.parseInt(splitLine[0]);
                    Character characterValue = splitLine[1].charAt(0);
                    map.put(integerKey, characterValue);
                }
                line = indexReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void edgeFromFile(BetterDiGraph graph) {
        int source, destination;
        try(BufferedReader edgeReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("data-components.txt")), "UTF8"));) {
            // skip first line
            String line = edgeReader.readLine();

            //read all lines, add edges to BetterDiGraph object.
            line = edgeReader.readLine();
            while(line != null) {
                if(Character.compare(line.charAt(0), '#') != 0){
                    String[] splitLineEdge = line.split("\t");
                    source = Integer.parseInt(splitLineEdge[0]);
                    destination = Integer.parseInt(splitLineEdge[1]);
                    graph.addEdge(source, destination);
                }
                line = edgeReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}