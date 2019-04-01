package no.ntnu.tdt4240.g17.common.network;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.tdt4240.g17.common.network.game_messages.UpdateMessage;

/**
 * Has utility methods for testing.
 *
 * @author Kristian 'krissrex' Rekstad
 */
@Slf4j
public class MessageClassUtil {
    private MessageClassUtil() { }


    /**
     * This code doesn't work on Android, so it can only be used in tests :(
     * @return all files needed for kryo serialization. */
    public static List<Class> getMessageClasses() {
        // UpdateMessage was chosen arbitrarily. Need any class at the top package where all messages are.
        // Ever single class under this package and subpackages will be returned.
        final Class<UpdateMessage> messageClass = UpdateMessage.class;
        final String messageRootPackage = messageClass.getPackage().getName();
        final ClassLoader classLoader = messageClass.getClassLoader();

        final String url = messageRootPackage.replace(".", "/");
        log.debug("Scanning url {}", url);
        try {
            final Enumeration<URL> resources = classLoader.getResources(url);
            final ArrayList<File> files = new ArrayList<>();
            while (resources.hasMoreElements()) {
                final URL resource = resources.nextElement();
                final File file = new File(resource.getFile());
                if (!resource.getFile().contains("java/test")) {
                    // Exclude test sources
                    files.add(file);
                    log.debug("Found file: {}", resource.getFile());
                }
            }
            log.debug("Done scanning {}. Found {}", url, files);

            final List<Class> classes = files.stream()
                    .flatMap(file -> getClassesInDirectory(file, messageRootPackage).stream())
                    .sorted((classA, classB) -> classA.getCanonicalName().compareTo(classB.getCanonicalName()))
                    .collect(Collectors.toList());
            return classes;
        } catch (IOException e) {
            log.error("Failed to list resources of package {}", messageRootPackage, e);
        }

        return Collections.emptyList();
    }

    /**
     * Return all classes in a directory.
     * @param directory the directory to look in
     * @param packageName the package name for the class
     * @return list of classes in this directory and all subdirectories
     */
    private static List<Class> getClassesInDirectory(final File directory, final String packageName) {
        if (!directory.exists() || !directory.isDirectory()) {
            log.error("Not a directory, or does not exist: {}", directory.getAbsolutePath());
            return Collections.emptyList();
        }

        final File[] files = directory.listFiles();
        if (files == null) {
            log.error("Failed to list files in {}", directory.toString());
            return Collections.emptyList();
        }

        final List<Class> classes = Arrays.stream(files)
                .flatMap(file -> {
                    final String fileName = file.getName();
                    if (file.isDirectory()) {
                        if (fileName.contains(".")) {
                            log.error("package name {} has file {} that contains \".\"", packageName, fileName);
                            return Stream.empty();
                        }
                        String newPackage = packageName + "." + fileName;
                        return getClassesInDirectory(file, newPackage).stream();
                    } else if (fileName.endsWith(".class")) {
                        final String className = fileName.substring(0, fileName.length() - ".class".length());
                        final Class clazz;
                        try {
                            clazz = Class.forName(packageName + "." + className);
                            log.debug("Found class {}", clazz);
                            return Stream.of(clazz);
                        } catch (ClassNotFoundException e) {
                            log.error("Failed to load class {}.{}", packageName, className, e);
                            return Stream.empty();
                        }
                    } else {
                        log.debug("Rejected file \"{}\"", fileName);
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());

        return classes;
    }
}
