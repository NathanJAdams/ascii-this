package com.repocleaner.model.receive;

import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.model.user.Config;
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
}
