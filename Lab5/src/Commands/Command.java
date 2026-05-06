package Commands;

/**
 * Интерфейс команды
 */
public interface Command {
    String getName();            // имя команды
    String getDescription();     // описание (для help)
    void execute(String[] args); // выполнение
}