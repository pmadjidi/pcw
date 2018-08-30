package com.payam.test.counters;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
class NamedCounters {
    private static ConcurrentHashMap<String, Integer> concurrentHashMapCounters = null;

    public NamedCounters() {
        concurrentHashMapCounters = new ConcurrentHashMap<String, Integer>();
    }

    //atomic
    public Integer add(String key) {
        return concurrentHashMapCounters.computeIfAbsent(key, k -> 0);
    }

    //atomic
    public Integer get(String key) {
        return concurrentHashMapCounters.computeIfPresent(key, (k, v) -> v);
    }


    //atomic
    public Integer inc(String key) {
        return concurrentHashMapCounters.computeIfPresent(key, (k, v) -> v + 1);
    }

    //List operations, safe but does not mirror possible modifications from other threads
    // does not throw modification execptions ....
    public Map<String, String> scan() {
        Map<String, String> snapShot = new HashMap<String, String>();
        concurrentHashMapCounters.forEach((k, v) -> snapShot.put(k, v.toString()));
        return snapShot;
    }

    public void printCounters() {
        scan().forEach((k, v) -> System.out.print("Key: " + k + " Value:" + v + "\n"));
    }

}