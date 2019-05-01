package no.ntnu.tdt4240.g17.server.game_session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.GameMode;
import no.ntnu.tdt4240.g17.server.game_engine.GameEngine;
import no.ntnu.tdt4240.g17.server.game_engine.GameEngineFactory;

/**
 * A session of matchmade players in a game.
 *
 * @author Kristian 'krissrex' Rekstad
 */
public class Session {

    private static final AtomicInteger SESSIONS_CREATED_COUNTER = new AtomicInteger(0);

    private final int sessionId = SESSIONS_CREATED_COUNTER.incrementAndGet();

    @Getter
    private List<Player> players = new ArrayList<>();

    @Getter
    private GameEngine gameEngine;

    @Getter
    private Arena arena;

    @Getter
    private GameMode gameMode;
    private Thread engineThread;

    /** For fun and statistics.
     * @return how many session instances have been created. */
    public static int getSessionsCreatedCount() {
        return SESSIONS_CREATED_COUNTER.get();
    }

    /** test method, work in progress.
     * @param players the players in this session.
     * @return the session
     */
    public static Session create(final List<Player> players) {
        Arena arena = Arena.ARENA_2;

        final Session session = new Session();
        final GameEngineFactory gameEngineFactory = new GameEngineFactory();
        final GameEngine gameEngine = gameEngineFactory.create(arena, session);

        session.gameEngine = gameEngine;
        session.arena = arena;
        session.gameMode = GameMode.HEADHUNTER; // FIXME: Depends on matchmaking?
        session.players.addAll(players);

        session.engineThread = new Thread(gameEngine, "GameEngine " + gameEngine.getId());

        return session;
    }

    /** Create a new session.
     * @see #create(List) */
    public Session() {
    }

    /** Start the game enginge thread. */
    public void startEngine() {
        // TODO: 4/30/2019 Some work is needed for when a round is over, to send data and start new rounds etc.
        engineThread.start();
    }
}
