package com.repocleaner.apigateway.schemas;

import com.repocleaner.apigateway.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileDiffSchema implements Schema {
    private final String fileNameA;
    private final String fileNameB;
    private final String[] a;
    private final String[] b;
    private final String[] common;
}
