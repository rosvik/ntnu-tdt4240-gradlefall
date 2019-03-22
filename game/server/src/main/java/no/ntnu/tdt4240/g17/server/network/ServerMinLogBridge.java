package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.minlog.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Bridges {@link com.esotericsoftware.minlog.Log} with {@link org.slf4j.LoggerFactory}.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public final class ServerMinLogBridge {
    /**
     * Utility class.
     */
    private ServerMinLogBridge() {
    }

    private static final AtomicBoolean IS_BRIDGED = new AtomicBoolean(false);

    private static final HashMap<String, Logger> LOGGERS = new HashMap<>();

    /**
     * Bridge kryonets Minlog to SLF4J.
     * Safe to call many times.
     */
    public static void bridgeMinlogToSlf4j() {
        if (IS_BRIDGED.getAndSet(true)) {
            return;
        }

        Log.set(Log.LEVEL_TRACE);
        Log.setLogger(new Log.Logger() {
            @Override
            public void log(final int level, final String category, final String message, final Throwable ex) {
                String name = "com.esotericsoftware.kryonet";
                if (category != null) {
                    name += "." + category;
                }

                final Logger logger = LOGGERS.computeIfAbsent(name, LoggerFactory::getLogger);

                switch (level) {
                    case Log.LEVEL_TRACE:
                        logger.trace(message, ex);
                        break;
                    case Log.LEVEL_ERROR:
                        logger.error(message, ex);
                        break;
                    case Log.LEVEL_WARN:
                        logger.warn(message, ex);
                        break;
                    case Log.LEVEL_INFO:
                        logger.info(message, ex);
                        break;
                    case Log.LEVEL_DEBUG:
                        logger.debug(message, ex);
                        break;
                    case Log.LEVEL_NONE:
                        // Fallthrough
                    default:
                        break;
                }
            }
        });
    }
}
