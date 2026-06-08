package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import java.util.stream.Collectors;

/**
 * Обработчик команды FILTER_LESS_THAN_OWNER.
 * Фильтрует элементы, чей владелец лексикографически меньше заданного.
 */
public class FilterLessThanOwnerHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public FilterLessThanOwnerHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "filter_less_than_owner";
    }

    @Override
    public String getDescription() {
        return "показать элементы, владелец которых меньше заданного";
    }

    /**
     * Выполняет команду filter_less_than_owner - фильтрует элементы,
     * чей владелец лексикографически меньше заданного.
     *
     * @param request запрос с именем владельца
     * @return ответ с отфильтрованными элементами
     */
    @Override
    public Response execute(Request request) {

        String owner = request.getStringArgument();

        if (owner == null || owner.isBlank()) {
            return new Response(false, "Имя владельца не передано.");
        }

        String result = collectionManager
                        .getCollection()
                        .stream()
                        .filter(product -> product.getOwner().getName().compareTo(owner) < 0)
                        .sorted(Product::compareTo)
                        .map(Product::toString)
                        .collect(Collectors.joining("\n\n"));

        if (result.isBlank()) {
            return new Response(true, "Совпадений не найдено.");
        }

        return new Response(true, result);
    }
}
