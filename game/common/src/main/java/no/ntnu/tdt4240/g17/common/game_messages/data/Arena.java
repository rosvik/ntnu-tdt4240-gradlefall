package no.ntnu.tdt4240.g17.common.game_messages.data;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

/**
 * Represents all available arenas.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public enum Arena {
    /** */
    ARENA_1("map2.tmx");

    /** The filename for a arena tmx-file. */
    @Getter
    private final String arenaTmxFilePath;

    /**
     * @param arenaTmxFilePath the file for a arena tilemap.
     */
    Arena(@NotNull final String arenaTmxFilePath) {
        this.arenaTmxFilePath = arenaTmxFilePath;
    }


}
