package client;

import client.command.ClientCommandParser;
import client.console.ConsoleManager;
import client.console.InputManager;
import client.network.UDPClient;
import common.network.Request;
import common.network.Response;

public class ClientMain {

    public static void main(String[] args) {

        ConsoleManager console = new ConsoleManager();
        InputManager inputManager = new InputManager(console.getScanner());
        ClientCommandParser parser = new ClientCommandParser(inputManager);
        UDPClient client = new UDPClient("localhost", 5555);
        System.out.println("Клиент запущен.");

        while (true) {

            String line = console.readCommand();

            if (line.isBlank()) {
                continue;
            }

            if (line.equalsIgnoreCase("exit")) {
                break;
            }

            Request request = parser.parse(line);

            if (request == null) {
                System.out.println("Неизвестная команда.");
                continue;
            }

            Response response = client.sendRequest(request);

            System.out.println(response.getMessage());
        }

        System.out.println("Клиент завершён.");
    }
}