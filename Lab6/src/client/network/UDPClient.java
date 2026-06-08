package client.network;

import common.network.Request;
import common.network.Response;
import common.util.SerializationUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPClient {

    private final String host;
    private final int port;

    public UDPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Response sendRequest(Request request) {

        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(3000);

            byte[] requestBytes = SerializationUtils.serialize(request);

            InetAddress address = InetAddress.getByName(host);

            DatagramPacket requestPacket =
                    new DatagramPacket(
                            requestBytes,
                            requestBytes.length,
                            address,
                            port);

            socket.send(requestPacket);

            byte[] responseBuffer = new byte[65535];

            DatagramPacket responsePacket =
                    new DatagramPacket(
                            responseBuffer,
                            responseBuffer.length);

            socket.receive(responsePacket);

            byte[] responseBytes = new byte[responsePacket.getLength()];

            System.arraycopy(
                    responsePacket.getData(),
                    0,
                    responseBytes,
                    0,
                    responsePacket.getLength());

            return (Response) SerializationUtils.deserialize(responseBytes);

        } catch (SocketTimeoutException e) {

            return new Response(false, "Сервер не отвечает.");

        } catch (Exception e) {

            return new Response(false, "Ошибка связи: " + e.getMessage());
        }
    }
}