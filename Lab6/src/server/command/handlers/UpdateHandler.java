package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import java.util.Date;

/**
 * Обработчик команды UPDATE.
 * Обновляет элемент в коллекции по ID.
 */
public class UpdateHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public UpdateHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "обновить элемент коллекции по ID";
    }

    /**
     * Выполняет команду update - обновляет элемент по ID.
     *
     * @param request запрос с ID и данными нового продукта
     * @return ответ с результатом
     */
    @Override
    public Response execute(Request request) {

        try {

            Long id = request.getId();
            Product newProduct = request.getProduct();

            if (id == null || id <= 0) {
                return new Response(false, "Некорректный ID.");
            }

            if (newProduct == null) {
                return new Response(false, "Продукт не передан.");
            }

            Product existingProduct = collectionManager.getById(id).orElse(null);

            if (existingProduct == null) {
                return new Response(true, "Элемент с таким ID не найден.");
            }

            // Обновляем данные, но сохраняем ID и дату создания
            newProduct.setId(id);
            newProduct.setCreationDate(existingProduct.getCreationDate());

            collectionManager.removeById(id);
            collectionManager.add(newProduct);

            return new Response(true, "Элемент успешно обновлен.");

        } catch (Exception e) {
            return new Response(false, "Ошибка при обновлении: " + e.getMessage());
        }
    }
}
