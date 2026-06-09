package Client;

import Client.Managers.InputManager;
import Common.Network.Request;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RequestMaker {

    private final InputManager inputManager;
    private static final Set<String> executingScripts = new HashSet<>();

    public RequestMaker(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    // Управление рекурсией скриптов
    public static boolean tryStartScript(String path) {
        return executingScripts.add(path);
    }

    public static void endScript(String path) {
        executingScripts.remove(path);
    }

    public Request make(String line) {
        String[] parts = line.trim().split("\\s+");
        String command = parts[0].toLowerCase();

        switch (command) {
            case "help":
                return new Request("help");
            case "info":
                return new Request("info");
            case "show":
                return new Request("show");
            case "clear":
                return new Request("clear");
            case "add":
                Request reqAdd = new Request("add");
                reqAdd.setProduct(inputManager.readProduct());
                return reqAdd;
            case "remove_by_id":
                if (parts.length != 2) {
                    System.out.println("Некорректный ввод: remove_by_id <id>");
                    return new Request("undefined");
                }
                try {
                    long id = Long.parseLong(parts[1]);
                    Request reqRemove = new Request("remove_by_id");
                    reqRemove.setId(id);
                    return reqRemove;          // ИСПРАВЛЕНО
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: id должен быть числом.");
                    return new Request("undefined");
                }
            case "update":
                if (parts.length != 2) {
                    System.out.println("Некорректный ввод: update <id>");
                    return new Request("undefined");
                }
                try {
                    long id = Long.parseLong(parts[1]);
                    Request reqUpdate = new Request("update");
                    reqUpdate.setId(id);
                    reqUpdate.setProduct(inputManager.readProduct());
                    return reqUpdate;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: id должен быть числом.");
                    return new Request("undefined");
                }
            case "filter_by_owner":
                if (parts.length != 2) {
                    System.out.println("Некорректный ввод: filter_by_owner <owner>");
                    return new Request("undefined");
                }
                Request reqOwner = new Request("filter_by_owner");
                reqOwner.setStringArgument(parts[1]);
                return reqOwner;
            case "filter_greater_than_part_number":
                if (parts.length != 2) {
                    System.out.println("Некорректный ввод: filter_greater_than_part_number <partNumber>");
                    return new Request("undefined");
                }
                Request reqPart = new Request("filter_greater_than_part_number");
                reqPart.setStringArgument(parts[1]);
                return reqPart;
            case "shuffle":
                return new Request("shuffle");
            case "add_if_max":
                Request reqMax = new Request("add_if_max");
                reqMax.setProduct(inputManager.readProduct());
                return reqMax;
            case "remove_greater":
                Request reqGreater = new Request("remove_greater");
                reqGreater.setProduct(inputManager.readProduct());
                return reqGreater;
            case "filter_less_than_owner":
                if (parts.length != 2) {
                    System.out.println("Некорректный ввод: filter_less_than_owner <owner>");
                    return new Request("undefined");
                }
                Request reqLess = new Request("filter_less_than_owner");
                reqLess.setStringArgument(parts[1]);
                return reqLess;
            case "save":
                System.out.println("Команда 'save' доступна только серверу.");
                return new Request("undefined");
            case "execute_script":
                if (parts.length != 2) {
                    System.out.println("Некорректный ввод: execute_script <filename>");
                    return new Request("undefined");
                }
                File file = new File(parts[1]);
                if (!file.exists()) {
                    System.out.println("Файл не найден: " + parts[1]);
                    return new Request("undefined");
                }
                try {
                    String scriptPath = file.getCanonicalPath();
                    Request reqScript = new Request("execute_script");
                    reqScript.setStringArgument(scriptPath);
                    return reqScript;   // Будет обработан локально в ClientMain
                } catch (IOException e) {
                    System.out.println("Ошибка пути: " + e.getMessage());
                    return new Request("undefined");
                }
            default:
                return null;
        }
    }
}