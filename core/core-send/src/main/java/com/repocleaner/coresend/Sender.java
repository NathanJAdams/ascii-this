package com.repocleaner.coresend;

import com.repocleaner.io.external.SendIO;
import com.repocleaner.util.RepoCleanerException;

public class Sender {
    public static void send(SendIO sendIO) throws RepoCleanerException {
        sendIO.send();
    }
}
