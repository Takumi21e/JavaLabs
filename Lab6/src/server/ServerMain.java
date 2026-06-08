package server;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.command.CommandExecutor;
import server.file.FileManager;
import server.network.ConnectionReceiver;
import server.network.RequestData;
import server.network.RequestReader;
import server.network.ResponseSender;

public class ServerMain {

    public static void main(String[] args) {

        try {

            String fileName = System.getenv("FILE_PATH");
            if (fileName == null || fileName.isBlank()) {
                fileName = "lab6_data.csv";
                System.out.println("Переменная окружения FILE_PATH не задана. " + "Используется lab6_data.csv");
            } else {
                System.out.println("Файл коллекции: " + fileName);
            }

            FileManager fileManager = new FileManager(fileName);
            CollectionManager collectionManager = new CollectionManager(fileManager.load());
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {

                                System.out.println("Сохранение коллекции...");
                                fileManager.save(collectionManager.getCollection());
                                System.out.println("Коллекция сохранена.");

                            }));

            CommandExecutor commandExecutor = new CommandExecutor(collectionManager);
            ConnectionReceiver receiver = new ConnectionReceiver(5555);
            RequestReader requestReader = new RequestReader();
            ResponseSender responseSender = new ResponseSender();
            System.out.println("Сервер запущен на порту 5555");

            while (true) {

                RequestData data = requestReader.read(receiver.getChannel());
                if (data == null) {continue;
                }

                Request request = data.getRequest();
                System.out.println("Получена команда: " + request.getCommandType());
                Response response = commandExecutor.execute(request);
                responseSender.send(
                        receiver.getChannel(),
                        response,
                        data.getClientAddress());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}