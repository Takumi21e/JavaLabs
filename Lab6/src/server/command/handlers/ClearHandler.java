package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

public class ClearHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public ClearHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        collectionManager.getCollection().clear();
        return new Response(true, "Коллекция очищена.");
    }
}