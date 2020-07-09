package cn.antswl.ds.algorithm;

/**
 * 服务器节点
 *
 * @author David Liu
 */
public class ServerNode implements Node {
    private final String zoneId;

    private final String ip;

    private final int port;

    public ServerNode(String zoneId, String ip, int port) {
        this.zoneId = zoneId;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String getKey() {
        return zoneId + "_" + ip + ":" + port;
    }
}
