package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;

import java.util.Optional;

public class UpdateHandler
        implements CommandHandler {

    private final CollectionManager collectionManager;

    public UpdateHandler(
            CollectionManager collectionManager
    ) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        Long id = request.getId();
        Product newProduct = request.getProduct();

        if (id == null) {
            return new Response(false, "Не указан id.");
        }
        if (newProduct == null) {
            return new Response(false, "Не передан Product.");
        }

        Optional<Product> optional = collectionManager.getById(id);
        if (optional.isEmpty()) {
            return new Response(false, "Элемент не найден.");
        }

        Product oldProduct = optional.get();
        newProduct.setId(oldProduct.getId());
        newProduct.setCreationDate(oldProduct.getCreationDate());

        collectionManager.getCollection().remove(oldProduct);
        collectionManager.add(newProduct);

        return new Response(true, "Элемент обновлен.");
    }
}