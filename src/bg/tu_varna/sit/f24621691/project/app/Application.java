package bg.tu_varna.sit.f24621691.project.app;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.cli.CommandLineInterface;
import bg.tu_varna.sit.f24621691.project.io.FileServiceReader;
import bg.tu_varna.sit.f24621691.project.io.FileServiceWriter;

public class Application {
    public static void main(String[] args) {
        //Създаваме ядрото
        MachineManager manager = new MachineManager();

        //Създаваме инструментите за вход/изход
        FileServiceReader reader = new FileServiceReader();
        FileServiceWriter writer = new FileServiceWriter();

        //Сглобяваме всичко в CLI
        CommandLineInterface cli = new CommandLineInterface(manager, reader, writer);

        cli.start();
    }
}