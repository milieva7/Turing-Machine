package bg.tu_varna.sit.f24621691.project.cli;

public enum Command {
    //Системни команди
    OPEN, CLOSE, SAVE, SAVEAS, HELP, EXIT,

    //Команди за управление на машини
    LIST, PRINT, SAVETM, LOADTM, NEWTM,

    // Команди за модификация и симулация
    ADDSTATE, SETSTART, ADDACCEPT, ADDREJECT,
    ADDTRANS, REMOVETRANS, CHECKDET,
    INIT, STEP, RUN, STATUS, TAPE, RESET,
    ACCEPTS, TRACE, REPORT,

    UNKNOWN;

    public static Command fromString(String input) {
        if (input == null || input.isBlank()) {
            return UNKNOWN;
        }


        String cleanInput = input.trim()
                .toUpperCase()
                .replace("-", "") // за "save-as"
                .replace("_", "") // за "save_as"
                .replace(" ", ""); // за "save as"


        try {
            return Command.valueOf(cleanInput);
        } catch (IllegalArgumentException e) {

            return UNKNOWN;
        }
    }
}