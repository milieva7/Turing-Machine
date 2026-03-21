package bg.tu_varna.sit.f24621691.project.app;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.cli.CommandLineInterface;
import bg.tu_varna.sit.f24621691.project.io.FileReader;
import bg.tu_varna.sit.f24621691.project.io.FileWriter;

public class Application {
    public static void main(String[] args) {
        //Създаваме ядрото
        MachineManager manager = new MachineManager();

        //Създаваме инструментите за вход/изход
        FileReader reader = new FileReader();
        FileWriter writer = new FileWriter();

        //Сглобяваме всичко в CLI
        CommandLineInterface cli = new CommandLineInterface(manager, reader, writer);

        cli.start();
    }
}