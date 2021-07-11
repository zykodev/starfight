package dev.zyko.starfight.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StarfightLogger {

    private Level level = Level.INFO;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public void log(Class<?> originClass, String message) {
        System.out.println("[" + this.sdf.format(new Date(System.currentTimeMillis())) + "] " + this.level.name() + "@" + originClass.getName() + ": " + message);
        this.level = Level.INFO;
    }

    public StarfightLogger setLevel(Level level) {
        this.level = level;
        return this;
    }

    public enum Level {
        INFO,
        WARN,
        ERROR,
        FATAL,
        DEBUG
    }

}
