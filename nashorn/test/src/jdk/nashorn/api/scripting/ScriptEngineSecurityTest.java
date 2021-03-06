/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.nashorn.api.scripting;

import static org.testng.Assert.fail;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.testng.annotations.Test;

/**
 * jsr223 tests for security access checks.
 */
public class ScriptEngineSecurityTest {

    private void log(String msg) {
        org.testng.Reporter.log(msg, true);
    }

    @Test
    public void securityPackagesTest() {
        if (System.getSecurityManager() == null) {
            // pass vacuously
        }

        final ScriptEngineManager m = new ScriptEngineManager();
        final ScriptEngine e = m.getEngineByName("nashorn");
        try {
            e.eval("var v = Packages.sun.misc.Unsafe;");
            fail("should have thrown SecurityException");
        } catch (final Exception exp) {
            if (exp instanceof SecurityException) {
                log("got " + exp + " as expected");
            } else {
                fail(exp.getMessage());
            }
        }
    }

    @Test
    public void securityJavaTypeTest() {
        if (System.getSecurityManager() == null) {
            // pass vacuously
        }

        final ScriptEngineManager m = new ScriptEngineManager();
        final ScriptEngine e = m.getEngineByName("nashorn");
        try {
            e.eval("var v = Java.type('sun.misc.Unsafe');");
            fail("should have thrown SecurityException");
        } catch (final Exception exp) {
            if (exp instanceof SecurityException) {
                log("got " + exp + " as expected");
            } else {
                fail(exp.getMessage());
            }
        }
    }

    @Test
    public void securityClassForNameTest() {
        if (System.getSecurityManager() == null) {
            // pass vacuously
        }

        final ScriptEngineManager m = new ScriptEngineManager();
        final ScriptEngine e = m.getEngineByName("nashorn");
        try {
            e.eval("var v = java.lang.Class.forName('sun.misc.Unsafe');");
            fail("should have thrown SecurityException");
        } catch (final Exception exp) {
            if (exp instanceof SecurityException) {
                log("got " + exp + " as expected");
            } else {
                fail(exp.getMessage());
            }
        }
    }

    @Test
    public void securitySystemExit() {
        if (System.getSecurityManager() == null) {
            // pass vacuously
        }

        final ScriptEngineManager m = new ScriptEngineManager();
        final ScriptEngine e = m.getEngineByName("nashorn");
        try {
            e.eval("java.lang.System.exit(0);");
            fail("should have thrown SecurityException");
        } catch (final Exception exp) {
            if (exp instanceof SecurityException) {
                log("got " + exp + " as expected");
            } else {
                fail(exp.getMessage());
            }
        }
    }

    @Test
    public void securitySystemLoadLibrary() {
        if (System.getSecurityManager() == null) {
            // pass vacuously
        }

        final ScriptEngineManager m = new ScriptEngineManager();
        final ScriptEngine e = m.getEngineByName("nashorn");
        try {
            e.eval("java.lang.System.loadLibrary('foo');");
            fail("should have thrown SecurityException");
        } catch (final Exception exp) {
            if (exp instanceof SecurityException) {
                log("got " + exp + " as expected");
            } else {
                fail(exp.getMessage());
            }
        }
    }
}
