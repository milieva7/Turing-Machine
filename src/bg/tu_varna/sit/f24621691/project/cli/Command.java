package bg.tu_varna.sit.f24621691.project.cli;

    //Пълен списък с команди.
public enum Command {
    //Системни команди
    OPEN, CLOSE, SAVE, SAVEAS, HELP, EXIT,

    //Команди за управление на машини
    LIST, PRINT, SAVETM, LOADTM, NEWTM,

    //Команди за модификация и симулация
    ADDSTATE, SETSTART, ADDACCEPT, ADDREJECT,
    ADDTRANS, REMOVETRANS, CHECKDET,
    INIT, STEP, RUN, STATUS, TAPE, RESET,
    ACCEPTS, TRACE, REPORT,

    UNKNOWN;

    public static Command fromString(String input) {
        if (input == null || input.isBlank())
            return UNKNOWN;

        //Премахваме тирета или специални символи, ако потребителят напише "save-as" вместо "saveas"
        String cleanInput = input.toUpperCase().replace(" ", "").replace("_", "");

        try {
            return Command.valueOf(cleanInput);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}