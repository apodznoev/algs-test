package de.avpod.datastructures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class SnapshotArray {
    int version;
    int length;
    //index -> desc sorted list of versions, where it was overwritten
    final Map<Integer, LinkedList<Integer>> indexHistory;
    //array of: version -> value
    final Map<Integer, Integer>[] versionedArray;

    public SnapshotArray(int length) {
        this.length = length;
        versionedArray = new Map[length];
        indexHistory = new HashMap<>(length);

        for (int i = 0; i < length; i++) {
            final LinkedList<Integer> versionList = new LinkedList<>();
            versionList.add(0);
            this.indexHistory.put(i, versionList);
            final Map<Integer, Integer> versionMap = new HashMap<>();
            versionMap.put(0, 0);
            versionedArray[i] = versionMap;
        }
    }

    public void set(int index, int val) {
        checkIndex(index);
        versionedArray[index].put(version, val);
        final LinkedList<Integer> versionOverride = indexHistory.get(index);
        if (versionOverride.getFirst() == version) {
            return;
        }

        versionOverride.addFirst(version);
    }

    public int snap() {
        version++;
        return version - 1;
    }

    public int get(int index, int snap_id) {
        checkIndex(index);
        if (snap_id > version) {
            throw new IllegalArgumentException("Snapshot not yet created");
        }
        final LinkedList<Integer> versionOverride = indexHistory.get(index);

        for (final int overwrittenInVersion : versionOverride) {
            if (overwrittenInVersion <= snap_id) {
                return versionedArray[index].get(overwrittenInVersion);
            }
        }

        throw new IllegalStateException("Cannot find value of index:" + index + " for snapshot:" + snap_id);
    }

    private void checkIndex(final int index) {
        if (index >= length || index < 0) {
            throw new IndexOutOfBoundsException(index);
        }
    }
}
