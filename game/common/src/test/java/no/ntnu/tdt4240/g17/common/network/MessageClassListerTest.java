package no.ntnu.tdt4240.g17.common.network;

import org.junit.jupiter.api.Test;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Kristian 'krissrex' Rekstad on 3/26/2019.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
class MessageClassListerTest {


    @Test
    void shouldReturnFiles() {
        // Given

        // When
        final List<Class> knownMessageClasses = MessageClassUtil.getMessageClasses(); // Found by scanning directory.
        final List<Class> classes = MessageClassLister.getMessageClasses();
        final String packageName = "no.ntnu.tdt4240.g17.common.network.game_messages.";

        // Then

        /*
        // If tests fail, this can print all classes that it found. You can paste that list into the class.
        // Should use code generation, but too much work.
        log.debug("Known classes: \n{}", knownMessageClasses
                .stream()
                .map(clazz -> {
                    return clazz.getName().replace(packageName, "").replace("data.", "") + ".class";
                })
                .sorted()
                .reduce((s, s2) -> s + ",\n" + s2)
                .orElse("<NONE!>")
        );*/

        assertThat(classes, is(not(empty())));
        assertThat(classes, containsInAnyOrder(knownMessageClasses.toArray()));
    }
}