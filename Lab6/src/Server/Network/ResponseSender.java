package Server.Network;

import Common.Network.Response;
import Common.Network.Serialization;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseSender {

    public static void send(DatagramChannel channel,
                            Response response,
                            SocketAddress address) throws Exception {

        byte[] bytes = Serialization.serialize(response);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        channel.send(buffer, address);
    }
}