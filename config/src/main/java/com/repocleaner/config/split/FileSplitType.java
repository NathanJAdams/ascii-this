package com.repocleaner.config.split;

import com.repocleaner.model.config.SplitType;

public class FileSplitType implements SplitType {
    public String getType() {
        return "file";
    }
}
