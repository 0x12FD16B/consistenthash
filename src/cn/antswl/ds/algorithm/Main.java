package cn.antswl.ds.algorithm;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // 虚拟节点数量
        for (int i = 1; i < 1000; i++) {
            int times = 1000000;
            List<ServerNode> servers = Arrays.asList(
                    new ServerNode("zone1", "192.168.0.1", 6789),
                    new ServerNode("zone1", "192.168.0.2", 6789),
                    new ServerNode("zone1", "192.168.0.3", 6789),
                    new ServerNode("zone1", "192.168.0.4", 6789),
                    new ServerNode("zone1", "192.168.0.5", 6789),
                    new ServerNode("zone1", "192.168.0.6", 6789),
                    new ServerNode("zone1", "192.168.0.7", 6789),
                    new ServerNode("zone1", "192.168.0.8", 6789),
                    new ServerNode("zone1", "192.168.0.9", 6789),
                    new ServerNode("zone1", "192.168.0.10", 6789));
            ConsistentHash<ServerNode> consistentHash = new ConsistentHash<>(servers, i);
            Map<String, Integer> routedCount = new LinkedHashMap<>();
            routedCount.put("zone1_192.168.0.1:6789", 0);
            routedCount.put("zone1_192.168.0.2:6789", 0);
            routedCount.put("zone1_192.168.0.3:6789", 0);
            routedCount.put("zone1_192.168.0.4:6789", 0);
            routedCount.put("zone1_192.168.0.5:6789", 0);
            routedCount.put("zone1_192.168.0.6:6789", 0);
            routedCount.put("zone1_192.168.0.7:6789", 0);
            routedCount.put("zone1_192.168.0.8:6789", 0);
            routedCount.put("zone1_192.168.0.9:6789", 0);
            routedCount.put("zone1_192.168.0.10:6789", 0);
            for (int j = 0; j < times; j++) {
                ServerNode routeNode = consistentHash.routeNode(UUID.randomUUID().toString());
                routedCount.put(routeNode.getKey(), routedCount.get(routeNode.getKey()) + 1);
            }
            System.out.println(i + "\t" + getStandardDeviation(routedCount.values().toArray(new Integer[0])));
        }
    }

    private static double getStandardDeviation(Integer[] array) {
        int m = array.length;
        double sum = 0;
        for (Integer i : array) {
            sum += i;
        }
        double dAve = sum / m;
        double dVar = 0;
        for (Integer i : array) {
            dVar += (i - dAve) * (i - dAve);
        }
        return Math.sqrt(dVar / m);
    }
}
