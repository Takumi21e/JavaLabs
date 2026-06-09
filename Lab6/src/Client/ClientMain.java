package Client;

import Client.Managers.InputManager;
import Client.Managers.ScannerManager;
import Common.Network.Request;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        ScannerManager scannerManager = new ScannerManager();
        InputManager inputManager = new InputManager(scannerManager.getScanner());
        RequestMaker requestMaker = new RequestMaker(inputManager);

        System.out.println("Клиент запущен. Введите 'help' для справки или 'exit' для выхода.");

        while (true) {
            String line = scannerManager.readCommand();
            if (line.isBlank()) continue;
            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Клиент завершён.");
                break;
            }

            Request request = requestMaker.make(line);
            if (request == null) {
                System.out.println("Неизвестная команда. Введите 'help'.");
                continue;
            }
            if (request.getCommandName().equals("undefined")) {
                continue;
            }
            // Локальная обработка execute_script
            if (request.getCommandName().equals("execute_script")) {
                runScript(request.getStringArgument());
                continue;
            }

            try {
                RequestSender.sendRequest("localhost", 5555, request);
            } catch (Exception e) {
                System.out.println("Ошибка отправки: " + e.getMessage());
            }
        }
    }

    private static void runScript(String scriptPath) {
        if (!RequestMaker.tryStartScript(scriptPath)) {
            System.out.println("Рекурсия! Скрипт уже выполняется: " + scriptPath);
            return;
        }

        try (Scanner fileScanner = new Scanner(new File(scriptPath))) {
            InputManager fileInputManager = new InputManager(fileScanner);
            RequestMaker fileRequestMaker = new RequestMaker(fileInputManager);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                Request cmdRequest = fileRequestMaker.make(line);
                if (cmdRequest == null) {
                    System.out.println("Неизвестная команда в скрипте: " + line);
                    continue;
                }
                if (cmdRequest.getCommandName().equals("undefined")) continue;

                // Вложенный execute_script
                if (cmdRequest.getCommandName().equals("execute_script")) {
                    runScript(cmdRequest.getStringArgument());
                    continue;
                }

                // exit в скрипте
                if (cmdRequest.getCommandName().equals("exit")) {
                    System.out.println("Команда exit в скрипте. Клиент завершается.");
                    System.exit(0);
                }

                RequestSender.sendRequest("localhost", 5555, cmdRequest);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл скрипта не найден: " + scriptPath);
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка в скрипте: не хватает данных для команды.");
        } catch (Exception e) {
            System.out.println("Ошибка выполнения скрипта: " + e.getMessage());
        } finally {
            RequestMaker.endScript(scriptPath);
        }
    }
}