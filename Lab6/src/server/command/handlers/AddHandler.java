package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import server.collection.IdGenerator;
import java.util.Date;

/**
 * Обработчик команды ADD.
 * Добавляет новый элемент в коллекцию.
 */
public class AddHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public AddHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    /**
     * Выполняет команду add - добавляет новый элемент в коллекцию.
     *
     * @param request запрос с данными продукта
     * @return ответ с результатом
     */
    @Override
    public Response execute(Request request) {

        try {

            Product product = request.getProduct();

            if (product == null) {
                return new Response(false, "Продукт не передан.");
            }

            product.setId(IdGenerator.generateId());
            product.setCreationDate(new Date());

            collectionManager.add(product);

            return new Response(true, "Элемент успешно добавлен.");

        } catch (Exception e) {
            return new Response(false, "Ошибка при добавлении: " + e.getMessage());
        }
    }
}
