package de.avpod.datastructures;

import java.util.Map;
import java.util.TreeMap;

class SnapshotArray {
    int version;
    int length;
    //array of: version -> value
    final TreeMap<Integer, Integer>[] versionedArray;

    public SnapshotArray(int length) {
        this.length = length;
        versionedArray = new TreeMap[length];
    }

    public void set(int index, int val) {
        if(versionedArray[index] == null) {
            versionedArray[index] = new TreeMap<>();
        }
        versionedArray[index].put(version, val);
    }

    public int snap() {
        version++;
        return version - 1;
    }

    public int get(int index, int snap_id) {
        if (snap_id > version) {
            throw new IllegalArgumentException("Snapshot not yet created");
        }
        if(versionedArray[index] == null) {
            return 0;
        }

        Map.Entry<Integer, Integer> entry = versionedArray[index].floorEntry(snap_id);
        return entry == null ? 0 : entry.getValue();
    }

}
