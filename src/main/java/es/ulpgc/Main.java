package es.ulpgc;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        TreeIndexer index = new TreeIndexer(hazelcastInstance);
        index.clearData();

        index.indexGenerator();
    }
}