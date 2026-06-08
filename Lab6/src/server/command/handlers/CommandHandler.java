package server.command.handlers;

import common.network.Request;
import common.network.Response;

/**
 * Интерфейс обработчика команды.
 *
 * Определяет структуру всех обработчиков команд:
 * - имя команды
 * - описание команды
 * - выполнение команды
 */
public interface CommandHandler {
    /**
     * Получить имя команды.
     *
     * @return имя команды
     */
    String getName();

    /**
     * Получить описание команды.
     *
     * @return описание команды
     */
    String getDescription();

    /**
     * Выполнить команду.
     *
     * @param request запрос с параметрами команды
     * @return ответ сервера
     */
    Response execute(Request request);
}
