import command.*;
import manager.*;
import util.InputManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // 1. Получаем имя файла из переменной окружения
        String fileName = System.getenv("FILE_PATH");

        // 2. Инициализация основных компонентов
        Scanner scanner = new Scanner(System.in);
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();
        InputManager inputManager = new InputManager(scanner);
        FileManager fileManager = new FileManager(fileName);

        // 3. Загрузка данных из файла
        collectionManager.getAll().addAll(fileManager.load());

        // 4. Создаём exit отдельно (нужно проверять флаг)
        Exit exitCommand = new Exit();

        // 5. Регистрация команд
        commandManager.register(new Help(commandManager));
        commandManager.register(new Show(collectionManager));
        commandManager.register(new Add(collectionManager, inputManager));
        commandManager.register(new Info(collectionManager));
        commandManager.register(new RemoveById(collectionManager));
        commandManager.register(new Clear(collectionManager));
        commandManager.register(new Update(collectionManager, inputManager));
        commandManager.register(new AddIfMax(collectionManager, inputManager));
        commandManager.register(new RemoveGreater(collectionManager, inputManager));
        commandManager.register(new FilterByOwner(collectionManager));
        commandManager.register(new Save(collectionManager, fileManager));
        commandManager.register(new ExecuteScript(commandManager));
        commandManager.register(new FilterGreaterThanPartNumber(collectionManager));
        commandManager.register(new Shuffle(collectionManager));
        commandManager.register(new FilterLessThanOwner(collectionManager));
        commandManager.register(exitCommand);

        // 6. Основной цикл
        System.out.println("Программа запущена. Введите команду (help для списка команд):");

        while (true) {
            try {
                System.out.print("> ");
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