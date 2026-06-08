package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.collection.IdGenerator;

import java.util.Comparator;
import java.util.Date;

public class AddIfMaxHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public AddIfMaxHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        Product product = request.getProduct();

        if (product == null) {
            return new Response(false, "Продукт не передан.");
        }

        Product maxProduct = collectionManager
                        .getCollection()
                        .stream()
                        .max(Product::compareTo)
                        .orElse(null);

        boolean shouldAdd = maxProduct == null || product.compareTo(maxProduct) > 0;

        if (!shouldAdd) {
            return new Response(true, "Элемент не является максимальным.");
        }

        product.setId(IdGenerator.generateId());
        product.setCreationDate(new Date());
        collectionManager.add(product);

        return new Response(true, "Элемент успешно добавлен.");
    }
}