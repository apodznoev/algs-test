package de.avpod.problems;

import de.avpod.datastructures.Graph;
import de.avpod.datastructures.GraphNode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class CoursesSchedule {

    public static void main(String[] args) {
        int numCorses = Integer.parseInt(args[0]);
        String[] arrays = args[1].replace("[[","").replace("]]", "").split("],\\[");
        int[][] prerequisites = new int[arrays.length][2];
        for(int i =0; i < arrays.length; i++) {
            prerequisites[i] = new int[]{
                    Integer.parseInt(arrays[i].split(",")[0]),
                    Integer.parseInt(arrays[i].split(",")[1])
            };
        }

        CoursesSchedule cs = new CoursesSchedule();
        System.out.println(Arrays.toString(cs.findOrder(numCorses, prerequisites)));
    }
//  1 - 0 - 3      4
//        \  \ /  /
//           2 - 5 - 6

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Graph graph = constructGraph(numCourses, prerequisites);
        if (graph.startNodes.isEmpty()) {
            return new int[0];
        }

        LinkedList<GraphNode> result = new LinkedList<>();

        for (GraphNode curNode : graph.startNodes) {
            boolean hasCycle = buildPaths(curNode, new LinkedList<>(), result);
            if (hasCycle) {
                return new int[0];
            }
        }

        return result.size() < numCourses ? new int[]{} : result.stream().mapToInt(gn -> gn.value).toArray();
    }

    private boolean buildPaths(final GraphNode curNode,
                               LinkedList<GraphNode> currentPath,
                               LinkedList<GraphNode> topologicalSort) {
        if (currentPath.contains(curNode)) {
            return true;
        }

        if (curNode.marked) {
            return false;
        }

        currentPath.add(curNode);

        for (GraphNode nextNode : curNode.children) {
            boolean cycleFound = buildPaths(nextNode, currentPath, topologicalSort);
            if (cycleFound) {
                return true;
            }
        }

        curNode.marked = true;
        topologicalSort.addFirst(curNode);
        currentPath.removeLast();

        return false;
    }

    // 4 <-- requires 2 <-- requires 1 <-- requires 5
    //   <-- requires 3
// node4, children 2,3
//if starting 1 -> build [1,5]
//find 2 -> build [2, 1], merge [2,1] with [1,5]
//merge rest, response is [4, 3, 2, 1, 5]
    private Graph constructGraph(final int size, final int[][] prerequisites) {
        Set<GraphNode> startVertices = new HashSet<>();
        Graph graph = new Graph();
        graph.startNodes = startVertices;
        graph.nodes = new GraphNode[size];

        for (int i = 0; i < size; i++) {
            GraphNode node = new GraphNode();
            node.value = i;
            node.children = new LinkedList<>();
            startVertices.add(node);
            graph.nodes[i] = node;
        }

        for (int j = 0; j < prerequisites.length; j++) {
            int enables = prerequisites[j][0];
            int required = prerequisites[j][1];
            graph.nodes[required].children.add(graph.nodes[enables]);
            startVertices.remove(graph.nodes[enables]);
        }

        return graph;
    }


    public boolean canFinishBruteForce(int numCourses, int[][] prerequisites) {
        return tryAccomplish(numCourses, prerequisites, new boolean[numCourses], numCourses);
    }

    private boolean tryAccomplish(final int numCourses,
                                  final int[][] prerequisites,
                                  final boolean[] taken,
                                  final int remaining) {
        int coursesToTake = remaining;
        for (int i = 0; i < numCourses; i++) {
            if (taken[i]) {
                continue;
            }

            boolean wasCompleted = checkPrerequisites(i, taken, prerequisites);
            if (wasCompleted) {
                taken[i] = true;
                coursesToTake--;
            }
        }

        if (coursesToTake == remaining) {
            return false;
        }

        if (coursesToTake == 0) {
            return true;
        }

        return tryAccomplish(numCourses, prerequisites, taken, coursesToTake);
    }

    private boolean checkPrerequisites(final int courseNum, final boolean[] taken, final int[][] prerequisites) {
        for (int j = 0; j < prerequisites.length; j++) {
            int[] prerequisite = prerequisites[j];
            if (prerequisite[0] == courseNum && !taken[prerequisite[1]]) {
                return false;
            }
        }

        return true;
    }
}
