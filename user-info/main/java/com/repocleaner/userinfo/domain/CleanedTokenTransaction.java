package com.repocleaner.userinfo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleanedTokenTransaction extends TokenTransaction {
    private String commitLink;
}
