package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

public class RemoveGreaterHandler
        implements CommandHandler {

    private final CollectionManager collectionManager;

    public RemoveGreaterHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        Product reference = request.getProduct();

        if (reference == null) {
            return new Response(false, "Продукт не передан.");
        }

        int before = collectionManager.getCollection().size();
        collectionManager.getCollection().removeIf(product -> product.compareTo(reference) > 0);
        int removed = before - collectionManager.getCollection().size();

        return new Response(true, "Удалено элементов: " + removed);
    }
}