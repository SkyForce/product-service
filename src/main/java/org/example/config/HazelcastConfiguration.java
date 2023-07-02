package org.example.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    @Value("${hazelcast.map.name}")
    private String hazelcastMapName;

    @Value("${hazelcast.map.size}")
    private int hazelcastMapSize;

    @Value("${hazelcast.map.ttl.sec}")
    private int hazelcastMapTtlSec;

    @Bean
    HazelcastInstance productsHazelcastInstance() {
        Config config = new Config();
        MapConfig mapConfig = new MapConfig(hazelcastMapName);
        mapConfig.setEvictionConfig(new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setSize(hazelcastMapSize)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE));
        mapConfig.setTimeToLiveSeconds(hazelcastMapTtlSec);
        config.addMapConfig(mapConfig);
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public IMap<String, String> productsCache(HazelcastInstance productsHazelcastInstance) {
        return productsHazelcastInstance.getMap(hazelcastMapName);
    }
}
