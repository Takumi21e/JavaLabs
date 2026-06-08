package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

import java.util.stream.Collectors;

public class FilterGreaterThanPartNumberHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public FilterGreaterThanPartNumberHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        String partNumber = request.getStringArgument();

        String result = collectionManager
                        .getCollection()
                        .stream()
                        .filter(product -> product.getPartNumber() != null)
                        .filter(product -> product.getPartNumber().compareTo(partNumber) > 0)
                        .map(Product::toString)
                        .collect(Collectors.joining("\n\n"));

        if (result.isBlank()) {
            return new Response(true, "Элементы не найдены.");
        }

        return new Response(true, result);
    }
}