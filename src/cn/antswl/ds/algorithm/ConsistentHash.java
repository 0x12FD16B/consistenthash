package cn.antswl.ds.algorithm;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希实现
 *
 * @author David Liu
 */
public class ConsistentHash<T extends Node> {

    private final SortedMap<Long, VirtualNode<T>> hashRing = new TreeMap<>();

    private final HashAlgorithm hashAlgorithm;

    public ConsistentHash(Collection<T> pNodes, int vNodeCount) {
        this(pNodes, vNodeCount, new Sha256HashAlgorithm());
    }

    public ConsistentHash(Collection<T> pNodes, int vNodeCount, HashAlgorithm hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
        if (pNodes != null) {
            for (T pNode : pNodes) {
                appendVirtualNode(pNode, vNodeCount);
            }
        }
    }

    public void appendVirtualNode(T pNode, int vNodeCount) {
        if (vNodeCount < 0) throw new IllegalArgumentException("虚拟节点数量不能小于 0");
        int existingVNodeCount = this.getPhysicalNodeExistingVirtualNodeCount(pNode);
        for (int i = 0; i < vNodeCount; i++) {
            VirtualNode<T> virtualNode = new VirtualNode<>(pNode, i + existingVNodeCount);
            hashRing.put(hashAlgorithm.hash(virtualNode.getKey()), virtualNode);
        }
    }

    public void removePhysicalNode(T pNode) {
        if (pNode == null) return;
        Iterator<Long> iterator = hashRing.keySet().iterator();
        while (iterator.hasNext()) {
            Long key = iterator.next();
            VirtualNode<T> virtualNode = hashRing.get(key);
            if (virtualNode.isVirtualNodeOf(pNode)) {
                iterator.remove();
            }
        }
    }

    public T routeNode(String key) {
        if (hashRing.isEmpty()) {
            return null;
        }

        long hash = hashAlgorithm.hash(key);
        SortedMap<Long, VirtualNode<T>> tailMap = hashRing.tailMap(hash);
        long nodeHash = !tailMap.isEmpty() ? tailMap.firstKey() : hashRing.firstKey();
        return hashRing.get(nodeHash).getPhysicalNode();
    }

    private int getPhysicalNodeExistingVirtualNodeCount(T physicalNode) {
        int count = 0;
        for (VirtualNode<T> node : hashRing.values()) {
            if (node.isVirtualNodeOf(physicalNode)) {
                count++;
            }
        }
        return count;
    }
}