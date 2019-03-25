package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Connection;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/22/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class PlayerConnection extends Connection {

    /** The globally unqiue connection id. */
    @Getter @Setter @Nullable
    private String id = null;

    /** Player state.*/
    @Getter @Setter
    private PlayerState state = PlayerState.UNIDENTIFIED;

}
