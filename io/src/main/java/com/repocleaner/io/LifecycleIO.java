package com.repocleaner.io;

import com.repocleaner.model.FileStructure;

public interface LifecycleIO extends AutoCloseable {
    FileStructure getFileStructure();

    default void close() {
        getFileStructure().close();
    }
}
