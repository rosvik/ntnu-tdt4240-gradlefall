package no.ntnu.tdt4240.g17.server.network;

import com.esotericsoftware.kryonet.Connection;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/22/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class PlayerConnection extends Connection {

    private String id = null;
    private PlayerState state = PlayerState.UNIDENTIFIED;


}
