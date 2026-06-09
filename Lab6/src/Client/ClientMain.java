package Client;

import Client.Managers.ScannerManager;
import Client.Managers.InputManager;
import Common.Network.Request;

/**
 * Главный класс клиентского приложения.
 * Отвечает за:
 * - инициализацию компонентов клиента
 * - чтение команд из консоли
 * - парсинг и отправку команд на сервер
 * - вывод результатов
 */
public class ClientMain {

    public static void main(String[] args) {

        ScannerManager scannerManager = new ScannerManager();
        InputManager inputManager = new InputManager(scannerManager.getScanner());
        RequestMaker requestMaker = new RequestMaker(inputManager);

        System.out.println("Клиент запущен. Введите 'help' для справки или 'exit' для выхода.");

        while (true) {

            String line = scannerManager.readCommand();
            if (line.isBlank()) {
                continue;
            }
            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Клиент завершён.");
                break;
            }

            Request request = requestMaker.make(line);
            if (request == null) {
                System.out.println("Неизвестная команда. Введите 'help' для справки.");
                continue;
            }
            if (request.getCommandName().equals("undefined")) {
                continue;
            }

            try {
                RequestSender.sendRequest("localhost", 5555, request);

            } catch (Exception e) {
                System.out.println("Ошибка при отправке команды: " + e.getMessage());
            }
        }
    }
}
