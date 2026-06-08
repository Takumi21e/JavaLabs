package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.file.FileManager;

/**
 * Обработчик команды SAVE.
 * Сохраняет коллекцию в файл (доступна только на сервере).
 */
public class SaveHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public SaveHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл (только сервер)";
    }

    /**
     * Выполняет команду save - сохраняет коллекцию в файл.
     *
     * @param request запрос
     * @return ответ с результатом
     */
    @Override
    public Response execute(Request request) {

        try {

            String fileName = System.getenv("FILE_PATH");
            if (fileName == null || fileName.isBlank()) {
                fileName = "lab6_data.csv";
            }

            FileManager fileManager = new FileManager(fileName);
            fileManager.save(collectionManager.getCollection());

            return new Response(true, "Коллекция сохранена в файл.");

        } catch (Exception e) {
            return new Response(false, "Ошибка при сохранении: " + e.getMessage());
        }
    }
}
