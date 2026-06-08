package client;

import client.command.ClientCommandParser;
import client.console.ConsoleManager;
import client.console.InputManager;
import client.network.UDPClient;
import common.network.Request;
import common.network.Response;

/**
 * Главный класс клиентского приложения.
 * 
 * Отвечает за:
 * - инициализацию компонентов клиента
 * - чтение команд из консоли
 * - парсинг и отправку команд на сервер
 * - вывод результатов
 */
public class ClientMain {

    public static void main(String[] args) {

        ConsoleManager console = new ConsoleManager();
        InputManager inputManager = new InputManager(console.getScanner());
        ClientCommandParser parser = new ClientCommandParser(inputManager);
        UDPClient client = new UDPClient("localhost", 5555);
        
        System.out.println("Клиент запущен. Введите 'help' для справки или 'exit' для выхода.\n");

        while (true) {

            System.out.print("> ");
            String line = console.readCommand();

            if (line.isBlank()) {
                continue;
            }

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("До свидания!");
                break;
            }

            Request request = parser.parse(line);

            if (request == null) {
                System.out.println("Неизвестная команда. Введите 'help' для справки.\n");
                continue;
            }

            try {
                Response response = client.sendRequest(request);
                
                if (response != null) {
                    System.out.println(response.getMessage());
                } else {
                    System.out.println("Ошибка: пустой ответ от сервера.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка при отправке команды: " + e.getMessage());
            }
            
            System.out.println();
        }

        System.out.println("Клиент завершён.");
    }
}
