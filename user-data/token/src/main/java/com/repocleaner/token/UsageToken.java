package com.repocleaner.token;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsageToken {
    private String token;
    private List<TokenRestriction> restrictions;
}
