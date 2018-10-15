package com.repocleaner.json;

import com.repocleaner.json.customisers.InitiatorGsonCustomiser;
import com.repocleaner.json.customisers.SinkGsonCustomiser;
import com.repocleaner.json.customisers.SourceGsonCustomiser;
import com.repocleaner.json.customisers.TransformationInfoGsonCustomiser;
import com.repocleaner.util.json.JsonUtil;

public class JsonInfo {
    public static final JsonUtil JSON_UTIL = new JsonUtil(
            new InitiatorGsonCustomiser(),
            new SinkGsonCustomiser(),
            new SourceGsonCustomiser(),
            new TransformationInfoGsonCustomiser());
}
