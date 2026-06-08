package server.command.handlers;

import common.model.Product;
import common.network.Request;
import common.network.Response;
import server.collection.CollectionManager;
import java.util.stream.Collectors;

/**
 * Обработчик команды FILTER_BY_OWNER.
 * Фильтрует элементы по имени владельца.
 */
public class FilterByOwnerHandler implements CommandHandler {

    private final CollectionManager collectionManager;

    public FilterByOwnerHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "filter_by_owner";
    }

    @Override
    public String getDescription() {
        return "показать элементы соответствующего владельца";
    }

    /**
     * Выполняет команду filter_by_owner - фильтрует элементы по владельцу.
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
                        .filter(product -> product.getOwner().getName().equals(owner))
                        .sorted(Product::compareTo)
                        .map(Product::toString)
                        .collect(Collectors.joining("\n\n"));

        if (result.isBlank()) {
            return new Response(true, "Совпадений не найдено.");
        }
        
        return new Response(true, result);
    }
}
