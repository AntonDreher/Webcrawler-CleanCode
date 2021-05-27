package com.cleancode.webcrawler.testutil;

import java.security.Permission;

/*
This class is used for testing and prevents System.exit(status) to actually terminate the program,
instead throwing an Exception containing the status.
 */
public class NoExitSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
    }

    @Override
    public void checkExit(int status) {
        super.checkExit(status);
        throw new ExitException(status);
    }
}
