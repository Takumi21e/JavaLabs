package command;

import manager.CommandManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ExecuteScript implements Command {

    private final CommandManager commandManager;
    private final Set<String> executingScripts = new HashSet<>();

    public ExecuteScript(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String getName() {
        return "execute_script";
    }
    @Override
    public String getDescription() {
        return "выполнить скрипт из файла";
    }
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Ошибка: укажите имя файла.");
            return;
        }

        String fileName = args[1];
        if (executingScripts.contains(fileName)) {
            System.out.println("Рекурсия! Скрипт уже выполняется.");
            return;
        }

        executingScripts.add(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                System.out.println("> " + line); // полезно для отладки
                commandManager.execute(line);
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения скрипта: " + e.getMessage());
        } finally {
            executingScripts.remove(fileName);
        }
    }
}