package server.network;

import common.network.Request;
import common.util.SerializationUtils;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestReader {

    public RequestData read(DatagramChannel channel) throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(65535);

        SocketAddress clientAddress = channel.receive(buffer);

        if (clientAddress == null) {
            return null;
        }

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Request request = (Request) SerializationUtils.deserialize(bytes);

        return new RequestData(request, clientAddress);
    }
}