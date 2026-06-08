package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

/**
 * Обработчик команды CLEAR.
 * Очищает коллекцию.
 */
public class ClearHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public ClearHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    /**
     * Выполняет команду clear - очищает коллекцию.
     *
     * @param request запрос
     * @return ответ с результатом
     */
    @Override
    public Response execute(Request request) {
        collectionManager.clear();
        return new Response(true, "Коллекция очищена.");
    }
}
