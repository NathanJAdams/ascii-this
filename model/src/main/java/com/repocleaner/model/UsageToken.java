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
public class UsageToken {
    private String hash;
    private String expiryTimeHex;
    private String notificationEndPoint;
    private Config config;
}
