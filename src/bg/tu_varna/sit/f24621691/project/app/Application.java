package bg.tu_varna.sit.f24621691.project.app;

import bg.tu_varna.sit.f24621691.project.core.MachineManager;
import bg.tu_varna.sit.f24621691.project.cli.CommandLineInterface;

public class Application {
    public static void main(String[] args) {

            MachineManager manager = new MachineManager();

            CommandLineInterface cli = new CommandLineInterface(manager);

            cli.start();
        }
    }
