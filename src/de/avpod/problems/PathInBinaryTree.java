package de.avpod.problems;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class PathInBinaryTree {

    public String getDirections(TreeNode root, int startValue, int destValue) {
        final LinkedList<PathNode> fromPath = new LinkedList<>();
        final LinkedList<PathNode> toPath = new LinkedList<>();

        final PathNode rootNode = new PathNode(root, Direction.up);
        findPath(rootNode, startValue, fromPath);
        findPath(rootNode, destValue, toPath);
        fromPath.addFirst(rootNode);
        toPath.addFirst(rootNode);
        return mergePaths(fromPath, toPath);
    }

    private String mergePaths(final LinkedList<PathNode> fromPath,
                              final LinkedList<PathNode> toPath) {
        final int startValue = fromPath.getLast().val.val;
        final int destValue = toPath.getLast().val.val;

        PathNode lastCommonNode = null;

        while (!fromPath.isEmpty() && !toPath.isEmpty()) {
            PathNode from = fromPath.getFirst();
            PathNode to = toPath.getFirst();

            if(from.val == to.val) {
                lastCommonNode = from;
                fromPath.removeFirst();
                toPath.removeFirst();
            } else {
                break;
            }
        }

        if(lastCommonNode.val.val == startValue) {
            return printPath(toPath);
        }

        if(lastCommonNode.val.val == destValue) {
            return printInversedPath(fromPath);
        }

        return printInversedPath(fromPath) + printPath(toPath);
    }

    private String printInversedPath(final LinkedList<PathNode> toPath) {
        return toPath.stream().map(pathNode -> Direction.up.toString()).collect(Collectors.joining());
    }

    private String printPath(final LinkedList<PathNode> toPath) {
        return toPath.stream().map(pathNode -> pathNode.direction.toString()).collect(Collectors.joining());
    }

    private boolean findPath(final PathNode curNode, final int goal, LinkedList<PathNode> path) {
        if (curNode.val.val == goal) {
            return true;
        }

        if (curNode.val.left == null && curNode.val.right == null) {
            return false;
        }

        if (curNode.val.left != null) {
            final PathNode nextNode = new PathNode(curNode.val.left, Direction.left);
            path.add(nextNode);
            final boolean pathFound = findPath(nextNode, goal, path);
            if (pathFound) {
                return true;
            }
            path.removeLast();
        }

        if (curNode.val.right != null) {
            final PathNode nextNode = new PathNode(curNode.val.right, Direction.right);
            path.add(nextNode);
            final boolean pathFound = findPath(nextNode, goal, path);
            if (pathFound) {
                return true;
            }
            path.removeLast();
        }

        return false;
    }

    public static class PathNode {
        TreeNode val;
        Direction direction;

        public PathNode(final TreeNode val, final Direction direction) {
            this.val = val;
            this.direction = direction;
        }
    }

    public String getDirectionsNaiv(TreeNode root, int startValue, int destValue) {
        Map<GraphNode, List<GraphEdge>> graph = new HashMap<>();

        graph.put(new GraphNode(root), new ArrayList<>());
        fillGraph(root, graph.keySet().iterator().next(), graph);

        GraphNode startNode = GraphNode.fakeNode(startValue);
        GraphNode endNode = GraphNode.fakeNode(destValue);
        List<GraphEdge> path = bfs(startNode, endNode, graph);

        return path.stream().map(e -> e.direction.toString()).collect(Collectors.joining());
    }

    private List<GraphEdge> bfs(final GraphNode startNode,
                                final GraphNode endNode,
                                final Map<GraphNode, List<GraphEdge>> graph) {
        Queue<List<GraphEdge>> queue = new LinkedList<>();
        List<GraphEdge> startEdges = graph.get(startNode);
        startEdges.forEach(edge -> {
            final List<GraphEdge> iter = new LinkedList<>();
            iter.add(edge);
            queue.add(iter);
        });

        while (!queue.isEmpty()) {
            List<GraphEdge> curPath = queue.poll();
            GraphEdge curEdge = curPath.get(curPath.size() - 1);

            if (curEdge.to.equals(endNode)) {
                return curPath;
            }

            for (GraphEdge nextEdge : graph.get(curEdge.to)) {
                if (curEdge.from.equals(nextEdge.to)) {
                    //do not revisit node
                    continue;
                }

                if ((curEdge.direction == Direction.left || curEdge.direction == Direction.right) && nextEdge.direction == Direction.up) {
                    //do not reverse down direction to avoid loops
                    continue;
                }


                List<GraphEdge> nextPath = new LinkedList<>(curPath);
                nextPath.add(nextEdge);
                queue.add(nextPath);
            }
        }

        return Collections.emptyList();
    }

    private void fillGraph(final TreeNode node,
                           final GraphNode graphNode,
                           final Map<GraphNode, List<GraphEdge>> graph) {
        final List<GraphEdge> edges = graph.get(graphNode);

        if (node.left != null) {
            final GraphNode leftNode = new GraphNode(node.left);
            edges.add(new GraphEdge(graphNode, leftNode, Direction.left));

            final List<GraphEdge> leftEdges = new ArrayList<>();
            leftEdges.add(new GraphEdge(leftNode, graphNode, Direction.up));
            graph.put(leftNode, leftEdges);

            fillGraph(node.left, leftNode, graph);

        }

        if (node.right != null) {
            final GraphNode rightNode = new GraphNode(node.right);
            edges.add(new GraphEdge(graphNode, rightNode, Direction.right));

            final List<GraphEdge> rightEdges = new ArrayList<>();
            rightEdges.add(new GraphEdge(rightNode, graphNode, Direction.up));
            graph.put(rightNode, rightEdges);

            fillGraph(node.right, rightNode, graph);

        }
    }


    public enum Direction {
        left("L"),
        right("R"),
        up("U");

        private final String text;

        Direction(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static class GraphNode {
        TreeNode value;

        public GraphNode(final TreeNode value) {
            this.value = value;
        }

        public static GraphNode fakeNode(final int value) {
            return new GraphNode(new TreeNode(value));
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final GraphNode graphNode = (GraphNode) o;
            return value.val == graphNode.value.val;
        }

        @Override
        public int hashCode() {
            return value.val;
        }
    }

    public static class GraphEdge {
        GraphNode from;
        GraphNode to;
        Direction direction;

        public GraphEdge(final GraphNode from, final GraphNode to, final Direction direction) {
            this.from = from;
            this.to = to;
            this.direction = direction;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
