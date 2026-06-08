package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import java.util.stream.Collectors;

/**
 * Обработчик команды REMOVE_GREATER.
 * Удаляет все элементы, превышающие заданный.
 */
public class RemoveGreaterHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public RemoveGreaterHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "удалить все элементы, превышающие заданный";
    }

    /**
     * Выполняет команду remove_greater - удаляет все элементы,
     * чье значение больше заданного.
     *
     * @param request запрос с данными продукта для сравнения
     * @return ответ с ре��ультатом
     */
    @Override
    public Response execute(Request request) {

        try {

            Product product = request.getProduct();

            if (product == null) {
                return new Response(false, "Продукт не передан.");
            }

            int before = collectionManager.size();

            collectionManager.getCollection().removeIf(target -> target.compareTo(product) > 0);

            int after = collectionManager.size();

            return new Response(true, "Удалено элементов: " + (before - after));

        } catch (Exception e) {
            return new Response(false, "Ошибка при удалении: " + e.getMessage());
        }
    }
}
