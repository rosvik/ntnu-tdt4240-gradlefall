package no.ntnu.tdt4240.g17.server.availability;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/22/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public interface FailureListener {
    /** How severe a failure was. */
    enum Severity {
        /** Minor severity. The program may continue to function. */
        MINOR,

        /** Major severity. The program may continue to function,
         * but with problems or disabled functionality.
         */
        MAJOR,

        /** Fatal severity. The program has to shut down. */
        FATAL }

    /** Report that a failure happened.
     *
     * @param severity How severe the error was
     * @param exception An exception to get a stack trace and message.
     *                  You can create a new one with {@link RuntimeException}.
     */
    void reportFailure(Severity severity, Throwable exception);
}
