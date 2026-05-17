package bg.tu_varna.sit.f24621691.project.app;

import bg.tu_varna.sit.f24621691.project.cli.CommandLineInterface;

/**
 * Главен клас на приложението.
 * Създава командния интерфейс и стартира работата на симулатора.
 */
public class Application {

    /**
     * Главен метод, от който започва изпълнението на програмата.
     *
     * @param args аргументи от командния ред
     */
    public static void main(String[] args) {

        CommandLineInterface cli = new CommandLineInterface();

        cli.start();
    }
}