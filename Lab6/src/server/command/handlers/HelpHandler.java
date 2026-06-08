package server.command.handlers;

import common.network.Request;
import common.network.Response;

/**
 * Обработчик команды HELP.
 * Выводит справку по доступным командам.
 */
public class HelpHandler implements CommandHandler {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    /**
     * Выполняет команду help - выводит список всех доступных команд.
     *
     * @param request запрос
     * @return ответ со справкой
     */
    @Override
    public Response execute(Request request) {
        String help = "Доступные команды:\n" +
                "  help - вывести справку по командам\n" +
                "  info - информация о коллекции\n" +
                "  show - вывести все элементы коллекции (отсортированы)\n" +
                "  add - добавить новый элемент\n" +
                "  update id - обновить элемент по id\n" +
                "  remove_by_id id - удалить элемент по id\n" +
                "  clear - очистить коллекцию\n" +
                "  add_if_max - добавить, если это максимальный элемент\n" +
                "  remove_greater - удалить все элементы, превышающие заданный\n" +
                "  shuffle - перемешать элементы коллекции\n" +
                "  filter_by_owner owner - фильтровать по владельцу\n" +
                "  filter_less_than_owner owner - элементы с владельцем меньше заданного\n" +
                "  filter_greater_than_part_number partNumber - элементы с номером партии больше заданного\n" +
                "  save - сохранить коллекцию в файл (только сервер)";

        return new Response(true, help);
    }
}
