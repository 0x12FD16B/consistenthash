package cn.antswl.ds.algorithm;

/**
 * 虚拟节点
 *
 * @author David Liu
 */
public class VirtualNode<T extends Node> implements Node {

    final T physicalNode;

    final int vIndex;

    public VirtualNode(T physicalNode, int vIndex) {
        this.physicalNode = physicalNode;
        this.vIndex = vIndex;
    }

    public boolean isVirtualNodeOf(T physicalNode) {
        if (physicalNode == null) return false;
        return physicalNode.getKey().equals(this.physicalNode.getKey());
    }

    @Override
    public String getKey() {
        return this.physicalNode.getKey() + "_" + vIndex;
    }

    public T getPhysicalNode() {
        return physicalNode;
    }
}
