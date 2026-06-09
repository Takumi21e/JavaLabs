package Server.Commands;

import Common.Network.Request;
import Common.Network.Response;
import Server.Managers.CollectionManager;
import Server.Managers.FileManager;

/**
 * Обработчик команды SAVE.
 * Сохраняет коллекцию в файл (доступна только на сервере).
 */
public class Save implements Command {

    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
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

            return new Response("Коллекция сохранена в файл.");

        } catch (Exception e) {
            return new Response("Ошибка при сохранении: " + e.getMessage());
        }
    }
}
