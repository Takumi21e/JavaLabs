package server.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class ConnectionReceiver {

    private final DatagramChannel channel;

    public ConnectionReceiver(int port) throws IOException {

        channel = DatagramChannel.open();

        channel.bind(new InetSocketAddress(port));

        channel.configureBlocking(false);
    }

    public DatagramChannel getChannel() {
        return channel;
    }
}