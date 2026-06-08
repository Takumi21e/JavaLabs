package server.network;

import common.network.Response;
import common.util.SerializationUtils;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseSender {

    public void send(
            DatagramChannel channel,
            Response response,
            SocketAddress address
    ) throws Exception {

        byte[] bytes = SerializationUtils.serialize(response);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        channel.send(buffer, address);
    }
}