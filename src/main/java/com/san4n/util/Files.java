package com.san4n.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Files {

    private Files(){}

    public static long getDirectorySizeJava8(Path path) {

        long size = 0;

        if(!java.nio.file.Files.exists(path))
            return size;


        // need close Files.walk
        try (Stream<Path> walk = java.nio.file.Files.walk(path)) {

            size = walk
                    .filter(java.nio.file.Files::isRegularFile)
                    .mapToLong(p -> {
                        // ugly, can pretty it with an extract method
                        try {
                            return java.nio.file.Files.size(p);
                        } catch (IOException e) {
                            System.out.printf("Failed to get size of %s%n%s", p, e);
                            return 0L;
                        }
                    })
                    .sum();

        } catch (IOException e) {
        }

        return size;

    }

}
