package no.ntnu.tdt4240.g17.server.game_arena;

import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.data.Arena;

/**
 * Created by Kristian 'krissrex' Rekstad on 5/1/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
class ArenaTmxReader {

    private HashMap<Arena, List<Vector2>> cache = new HashMap<>(2);

    /**
     * Get the tiles in a map.
     * @param arena the arena to read tiles form
     * @return a list of (x,y) positions of tiles' bottom left corner.
     */
    public List<Vector2> getTiles(final Arena arena) {
        List<Vector2> result = cache.get(arena);

        if (result == null) {
            result = new ArrayList<>();
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                final String filePath = ArenaUtil.getFilePathFor(arena);
                final File arenaFile = new File(getClass().getResource("/" + filePath).toURI());
                if (arenaFile.exists()) {
                    Document doc = dBuilder.parse(arenaFile);
                    doc.getDocumentElement().normalize();
                    final NodeList layers = doc.getElementsByTagName("layer");
                    for (int i = 0; i < layers.getLength(); i++) {
                        final Node item = layers.item(i);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) item;
                            final String name = element.getAttribute("name");
                            if ("map".equals(name)) {
                                int width = Integer.valueOf(element.getAttribute("width"));
                                int height = Integer.valueOf(element.getAttribute("height"));
                                String data = element.getElementsByTagName("data").item(0).getTextContent();
                                String[] cells = data.replace("\n", "").split(",");

                                for (int cellIndex = 0; cellIndex < cells.length; cellIndex++) {
                                    if ("0".equals(cells[cellIndex])) {
                                        continue;
                                    }
                                    int x = cellIndex % width;
                                    int y = height - (cellIndex / width) - 1;
                                    result.add(new Vector2(x, y));
                                }
                                cache.put(arena, result);
                            }
                        }
                    }
                } else {
                    throw new IllegalStateException("Missing arena file: " + filePath);
                }
            } catch (Exception ex) {
                log.error("Failed to parse xml for {}", arena.name(), ex);
            }
        }

        return result;
    }
}
