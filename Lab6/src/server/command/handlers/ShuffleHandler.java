package server.command.handlers;

import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

import java.util.Collections;

public class ShuffleHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public ShuffleHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        Collections.shuffle(collectionManager.getCollection());
        return new Response(true, "Коллекция перемешана.");
    }
}