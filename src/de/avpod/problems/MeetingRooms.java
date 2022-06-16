package de.avpod.problems;

import de.avpod.datastructures.IntervalTree;
import de.avpod.datastructures.TreeVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MeetingRooms {

    public static void main(String[] args) {
        MeetingRooms mr = new MeetingRooms();
        System.out.println(mr.minMeetingRooms(new int[][]{
                {0,30},{15,20},{5,10},{-5, 15}, {0,20}
        }));
    }
    public int minMeetingRooms(int[][] intervals) {
        IntervalTree tree = new IntervalTree();
        for (int[] interval : intervals) {
            tree.add(interval);
        }

        AtomicInteger maxOverlaps = new AtomicInteger(0);
        final List<IntervalTree.IntervalNode> currentIntersection = new ArrayList<>();
        TreeVisitor<IntervalTree.IntervalNode> visitor = new TreeVisitor<>() {


            @Override
            public void visit(final IntervalTree.IntervalNode node) {
                int overlapCount = 0;
                ListIterator<IntervalTree.IntervalNode> iter = currentIntersection.listIterator();
                while (iter.hasNext()) {
                    IntervalTree.IntervalNode previousNode = iter.next();
                    if (previousNode.isOverlap(node)) {
                        overlapCount++;
                    } else {
                        iter.remove();
                    }
                }
                maxOverlaps.set(Math.max(maxOverlaps.get(), overlapCount));
                currentIntersection.add(node);
            }

            @Override
            public void onNextLevel() {

            }

            @Override
            public void onPrevLevel() {

            }

        };
        tree.traverseInOrder(visitor);

        return maxOverlaps.get();
    }

    private int hashCountSolution(int[][] intervals) {
        int max = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int[] interval : intervals) {
            for (int minute = interval[0]; minute < interval[1]; minute++) {
                Integer count = counts.get(minute);
                if (count == null) {
                    count = 0;
                }
                count++;
                max = Math.max(count, max);
                counts.put(minute, count);
            }
        }
        return max;
    }
}
