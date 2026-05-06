import Commands.*;
import Managers.*;
import Managers.InputManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Получаем имя файла из переменной окружения
        String fileName = System.getenv("FILE_PATH");

        // Инициализация основных компонентов
        Scanner scanner = new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();
        InputManager inputManager = new InputManager(scanner);
        commandManager.setInputManager(inputManager);
        FileManager fileManager = new FileManager(fileName);

        // Загрузка данных из файла
        collectionManager.getAll().addAll(fileManager.load());

        // Создаём exit отдельно
        Exit exitCommand = new Exit();

        // Регистрация команд
        commandManager.register(new Help(commandManager));
        commandManager.register(new Show(collectionManager));
        commandManager.register(new Add(collectionManager, commandManager));
        commandManager.register(new Info(collectionManager));
        commandManager.register(new RemoveById(collectionManager));
        commandManager.register(new Clear(collectionManager));
        commandManager.register(new Update(collectionManager, commandManager));
        commandManager.register(new AddIfMax(collectionManager, commandManager));
        commandManager.register(new RemoveGreater(collectionManager, commandManager));
        commandManager.register(new FilterByOwner(collectionManager));
        commandManager.register(new Save(collectionManager, fileManager));
        commandManager.register(new ExecuteScript(commandManager));
        commandManager.register(new FilterGreaterThanPartNumber(collectionManager));
        commandManager.register(new Shuffle(collectionManager));
        commandManager.register(new FilterLessThanOwner(collectionManager));
        commandManager.register(exitCommand);

        // Основной цикл
        System.out.println("Программа запущена.");

        while (true) {
            try {
                System.out.print("Введите команду> ");
                String input = scanner.nextLine();

                commandManager.execute(input);

                if (exitCommand.shouldExit()) {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }
}