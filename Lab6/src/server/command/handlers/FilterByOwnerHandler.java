package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

import java.util.stream.Collectors;

public class FilterByOwnerHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public FilterByOwnerHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        String owner = request.getStringArgument();

        String result = collectionManager
                        .getCollection()
                        .stream()
                        .filter(product -> product.getOwner().getName().equals(owner))
                        .map(Product::toString)
                        .collect(Collectors.joining("\n\n"));

        if (result.isBlank()) {
            return new Response(true, "Совпадений не найдено.");
        }
        return new Response(true, result);
    }
}
