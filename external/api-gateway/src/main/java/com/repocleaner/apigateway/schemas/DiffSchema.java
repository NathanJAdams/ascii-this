package com.repocleaner.apigateway.schemas;

import com.repocleaner.apigateway.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiffSchema implements Schema {
    private final FileDiffSchema[] fileDiffs;
}
