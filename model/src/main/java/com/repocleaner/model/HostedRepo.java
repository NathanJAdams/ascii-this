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
public class HostedRepo {
    private String userId;
    private String accountId;
    private boolean isActive;
    private String lastCleanedTimeHex;
    private String nextCleanTimeHex;
    private String masterBranch;
    private String repoName;
    private Timing timing;
    private Config config;
}
