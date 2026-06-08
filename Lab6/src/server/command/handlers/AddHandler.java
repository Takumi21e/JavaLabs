package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.collection.IdGenerator;

import java.util.Date;

public class AddHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public AddHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        Product product = request.getProduct();

        if (product == null) {
            return new Response(false, "Продукт не передан на сервер.");
        }

        product.setId(IdGenerator.generateId());
        product.setCreationDate(new Date());
        collectionManager.add(product);

        return new Response(true, "Элемент успешно добавлен.");
    }
}