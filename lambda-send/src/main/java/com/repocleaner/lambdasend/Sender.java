package com.repocleaner.lambdasend;

import com.repocleaner.core.io.SendIO;
import com.repocleaner.core.util.RepoCleanerException;

public class Sender {
    public void send(SendIO sendIO) throws RepoCleanerException {
        sendIO.send();
    }
}
