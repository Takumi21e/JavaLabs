package Server;

import Common.Network.*;
import Server.Managers.*;
import Server.Network.*;

public class ServerMain {

    public static void main(String[] args) {

        try {

            String fileName = System.getenv("FILE_PATH");
            if (fileName == null || fileName.isBlank()) {
                fileName = "lab6_data.csv";
                System.out.println("Переменная окружения FILE_PATH не задана. Используется lab6_data.csv");
            } else {
                System.out.println("Выбран файл коллекции: " + fileName);
            }

            FileManager fileManager = new FileManager(fileName);
            CollectionManager collectionManager = new CollectionManager(fileManager.load());

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                                System.out.println("Сохранение коллекции...");
                                fileManager.save(collectionManager.getCollection());
                            }));

            CommandManager commandManager = new CommandManager(collectionManager);
            ConnectionReceiver receiver = new ConnectionReceiver(5555);
            System.out.println("Сервер запущен на порту: " + receiver.getPort());

            while (true) {

                RequestData data = RequestReader.read(receiver.getChannel());

                if (data == null) {
                    continue;
                }

                Request request = data.getRequest();
                System.out.println("Получена команда: " + request.getCommandName());
                Response response = commandManager.execute(request);
                ResponseSender.send(
                        receiver.getChannel(),
                        response,
                        data.getClientAddress());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}