import java.util.*;

// Undirected graph of campus locations using an adjacency list
public class CampusGraph {

    private Map<String, List<String>> adjList;

    public CampusGraph() {
        adjList = new LinkedHashMap<>();
    }

    // returns the stored name of a location (case-insensitive match), or null
    public String findLocation(String name) {
        for (String location : adjList.keySet()) {
            if (location.equalsIgnoreCase(name.trim())) {
                return location;
            }
        }
        return null;
    }

    public boolean hasLocation(String name) {
        return findLocation(name) != null;
    }

    public boolean addLocation(String name) {
        if (hasLocation(name)) return false;
        adjList.put(name.trim(), new ArrayList<>());
        return true;
    }

    public boolean removeLocation(String name) {
        String location = findLocation(name);
        if (location == null) return false;

        // remove this location from all neighbour lists first
        for (String neighbour : adjList.get(location)) {
            adjList.get(neighbour).remove(location);
        }
        adjList.remove(location);
        return true;
    }

    public boolean hasConnection(String from, String to) {
        String a = findLocation(from);
        String b = findLocation(to);
        if (a == null || b == null) return false;
        return adjList.get(a).contains(b);
    }

    public boolean addConnection(String from, String to) {
        String a = findLocation(from);
        String b = findLocation(to);
        if (a == null || b == null || a.equals(b) || adjList.get(a).contains(b)) {
            return false;
        }
        adjList.get(a).add(b);
        adjList.get(b).add(a);
        return true;
    }

    public boolean removeConnection(String from, String to) {
        if (!hasConnection(from, to)) return false;
        String a = findLocation(from);
        String b = findLocation(to);
        adjList.get(a).remove(b);
        adjList.get(b).remove(a);
        return true;
    }

    public List<String> getNeighbours(String name) {
        String location = findLocation(name);
        if (location == null) return null;
        return adjList.get(location);
    }

    public void display() {
        for (String location : adjList.keySet()) {
            List<String> neighbours = adjList.get(location);
            System.out.println(location + " -> " + (neighbours.isEmpty() ? "(no connections)" : String.join(", ", neighbours)));
        }
    }

    public List<String> bfs(String startName) {
        List<String> order = new ArrayList<>();
        String start = findLocation(startName);
        if (start == null) return order;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            order.add(current);
            for (String neighbour : adjList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return order;
    }

    public List<String> dfs(String startName) {
        List<String> order = new ArrayList<>();
        String start = findLocation(startName);
        if (start == null) return order;

        dfsRec(start, new HashSet<>(), order);
        return order;
    }

    private void dfsRec(String current, Set<String> visited, List<String> order) {
        visited.add(current);
        order.add(current);
        for (String neighbour : adjList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsRec(neighbour, visited, order);
            }
        }
    }

    public boolean isEmpty() {
        return adjList.isEmpty();
    }

    public int size() {
        return adjList.size();
    }
}
