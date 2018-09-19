package com.repocleaner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LifecycleRequest {
    private String id;
    private String token;
    private Initiator initiator;
    private Config config;
    private Source source;
    private Sink sink;
}
