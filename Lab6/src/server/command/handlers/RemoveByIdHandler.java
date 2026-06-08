package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

public class RemoveByIdHandler
        implements CommandHandler {

    private final CollectionManager collectionManager;

    public RemoveByIdHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        Long id = request.getId();
        if (id == null) {
            return new Response(false, "Не указан id.");
        }

        boolean removed = collectionManager.removeById(id);
        if (removed) {
            return new Response(true, "Элемент удалён.");
        }
        return new Response(false, "Элемент с таким id не найден.");
    }
}