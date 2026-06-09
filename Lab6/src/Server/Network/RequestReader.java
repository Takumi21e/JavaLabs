package Server.Network;

import Common.Network.Request;
import Common.Network.Serialization;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestReader {

    public static RequestData read(DatagramChannel channel) throws Exception {

        ByteBuffer buffer = ByteBuffer.allocate(65535);
        SocketAddress clientAddress = channel.receive(buffer);

        if (clientAddress == null) {
            return null;
        }

        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Request request = (Request) Serialization.deserialize(bytes);

        return new RequestData(request, clientAddress);
    }
}