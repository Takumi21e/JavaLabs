package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

public class ShowHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public ShowHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        if (collectionManager.getCollection().isEmpty()) {
            return new Response(true, "Коллекция пуста.");
        }

        StringBuilder builder = new StringBuilder();
        for (Product product : collectionManager.getCollection()) {
            builder.append(product).append("\n");
        }
        return new Response(true, builder.toString());
    }
}