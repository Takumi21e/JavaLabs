package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

/**
 * Обработчик команды REMOVE_BY_ID.
 * Удаляет элемент из коллекции по ID.
 */
public class RemoveByIdHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public RemoveByIdHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по ID";
    }

    /**
     * Выполняет команду remove_by_id - удаляет элемент по ID.
     *
     * @param request запрос с ID элемента
     * @return ответ с результатом
     */
    @Override
    public Response execute(Request request) {

        try {

            Long id = request.getId();

            if (id == null || id <= 0) {
                return new Response(false, "Некорректный ID.");
            }

            boolean removed = collectionManager.removeById(id);

            if (removed) {
                return new Response(true, "Элемент успешно удален.");
            } else {
                return new Response(true, "Элемент с таким ID не найден.");
            }

        } catch (Exception e) {
            return new Response(false, "Ошибка при удалении: " + e.getMessage());
        }
    }
}
