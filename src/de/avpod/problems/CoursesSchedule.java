package de.avpod.problems;

public class CoursesSchedule {

    public static void main(String[] args) {

    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
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
