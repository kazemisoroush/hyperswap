package logger;

/**
 * Single class which is responsible for logging in whole project.
 */
public class Logger {

    /**
     * Logging type constant.
     */
    private static LogType TYPE = LogType.CONSOLE;

    /**
     * Log string with arguments.
     *
     * @param log       to be logged.
     * @param arguments to put inside the string.
     */
    public static void log(String log, Object... arguments) {
        if (! log.contains("%s")) {
            log(log);

            return;
        }

        String string = String.format(log, arguments);

        // let other method be responsible for logging...
        log(string);
    }

    /**
     * Log method which logs the object.
     *
     * @param string to log.
     */
    public static void log(Object string) {
        if (TYPE == LogType.CONSOLE) {
            // log the thing to the application console...
            System.out.println(string);
        } else if (TYPE == LogType.FILE) {
            // ...
        } else {
            // ...
        }
    }
}
