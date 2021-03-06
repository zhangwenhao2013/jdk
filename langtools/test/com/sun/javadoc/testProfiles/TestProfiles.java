/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
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

/*
 * @test
 * @bug      8006124 8009684
 * @summary  Test javadoc support for profiles.
 * @author   Bhavesh Patel
 * @library  ../lib/
 * @build    JavadocTester TestProfiles
 * @run main TestProfiles
 */
public class TestProfiles extends JavadocTester {

    //Test information.
    private static final String BUG_ID = "8006124-8009684";
    private static final String PROFILE_BUG_ID = BUG_ID + "-1";
    private static final String PACKAGE_BUG_ID = BUG_ID + "-2";
    //Javadoc arguments.
    private static final String[] ARGS1 = new String[]{
        "-d", PROFILE_BUG_ID, "-sourcepath", SRC_DIR, "-Xprofilespath", SRC_DIR + FS
        + "profile-rtjar-includes.txt", "pkg1", "pkg2", "pkg3", "pkg4", "pkg5"
    };
    private static final String[] ARGS2 = new String[]{
        "-d", PACKAGE_BUG_ID, "-sourcepath", SRC_DIR, "pkg1", "pkg2", "pkg3", "pkg4", "pkg5"
    };
    //Input for string tests for profiles.
    private static final String[][] PROFILES_TEST = {
        // Tests for profile-overview-frame.html listing all profiles.
        {PROFILE_BUG_ID + FS + "profile-overview-frame.html",
            "<span><a href=\"overview-frame.html\" "
            + "target=\"packageListFrame\">All Packages</a></span>"
        },
        {PROFILE_BUG_ID + FS + "profile-overview-frame.html",
            "<li><a href=\"compact1-frame.html\" target=\"packageListFrame\">"
            + "compact1</a></li>"
        },
        // Tests for profileName-frame.html listing all packages in a profile.
        {PROFILE_BUG_ID + FS + "compact2-frame.html",
            "<span><a href=\"overview-frame.html\" target=\"packageListFrame\">"
            + "All Packages</a></span><span><a href=\"profile-overview-frame.html\" "
            + "target=\"packageListFrame\">All Profiles</a></span>"
        },
        {PROFILE_BUG_ID + FS + "compact2-frame.html",
            "<li><a href=\"pkg4/compact2-package-frame.html\" "
            + "target=\"packageFrame\">pkg4</a></li>"
        },
        // Test for profileName-package-frame.html listing all types in a
        // package of a profile.
        {PROFILE_BUG_ID + FS + "pkg2" + FS + "compact2-package-frame.html",
            "<a href=\"../compact2-summary.html\" target=\"classFrame\">"
            + "compact2</a> - <a href=\"../pkg2/compact2-package-summary.html\" "
            + "target=\"classFrame\">pkg2</a>"
        },
        // Tests for profileName-summary.html listing the summary for a profile.
        {PROFILE_BUG_ID + FS + "compact2-summary.html",
            "<li><a href=\"compact1-summary.html\">Prev Profile</a></li>" + NL
            + "<li><a href=\"compact3-summary.html\">Next Profile</a></li>"
        },
        {PROFILE_BUG_ID + FS + "compact2-summary.html",
            "<h1 title=\"Profile\" class=\"title\">Profile&nbsp;compact2</h1>"
        },
        {PROFILE_BUG_ID + FS + "compact2-summary.html",
            "<h3><a href=\"pkg2/compact2-package-summary.html\" "
            + "target=\"classFrame\">pkg2</a></h3>"
        },
        // Tests for profileName-package-summary.html listing the summary for a
        // package in a profile.
        {PROFILE_BUG_ID + FS + "pkg5" + FS + "compact3-package-summary.html",
            "<li><a href=\"../pkg4/compact3-package-summary.html\">Prev Package"
            + "</a></li>"
        },
        {PROFILE_BUG_ID + FS + "pkg5" + FS + "compact3-package-summary.html",
            "<div class=\"subTitle\">compact3</div>"
        },
        //Test for "overview-frame.html" showing the "All Profiles" link.
        {PROFILE_BUG_ID + FS + "overview-frame.html",
            "<span><a href=\"profile-overview-frame.html\" "
            + "target=\"packageListFrame\">All Profiles</a></span>"
        },
        //Test for "className.html" showing the profile information for the type.
        {PROFILE_BUG_ID + FS + "pkg2" + FS + "Class1Pkg2.html",
            "<div class=\"subTitle\">compact1, compact2, compact3</div>"
        },
        {PROFILE_BUG_ID + FS + "index.html",
            "<frame src=\"overview-frame.html\" name=\"packageListFrame\" " +
            "title=\"All Packages\">"
        }
    };
    private static final String[][] PROFILES_NEGATED_TEST = {
        {PROFILE_BUG_ID + FS + "pkg3" + FS + "Class2Pkg3.html",
            "<div class=\"subTitle\">compact1"
        },
        {PROFILE_BUG_ID + FS + "pkg3" + FS + "Interface1Pkg3.html",
            "<div class=\"subTitle\">compact1"
        },
        {PROFILE_BUG_ID + FS + "pkg4" + FS + "compact2-package-frame.html",
            "<li><a href=\"Anno1Pkg4.html\" title=\"annotation in pkg4\" "
            + "target=\"classFrame\">Anno1Pkg4</a></li>"
        }
    };
    private static final String[][] PACKAGES_TEST = {
        {PACKAGE_BUG_ID + FS + "overview-frame.html",
            "<h2 title=\"Packages\">Packages</h2>"
        },
        {PACKAGE_BUG_ID + FS + "pkg4" + FS + "package-frame.html",
            "<h1 class=\"bar\"><a href=\"../pkg4/package-summary.html\" "
            + "target=\"classFrame\">pkg4</a></h1>"
        },
        {PACKAGE_BUG_ID + FS + "pkg4" + FS + "package-summary.html",
            "<div class=\"header\">" + NL + "<h1 title=\"Package\" "
            + "class=\"title\">Package&nbsp;pkg4</h1>" + NL + "</div>"
        }
    };
    private static final String[][] PACKAGES_NEGATED_TEST = {
        {PACKAGE_BUG_ID + FS + "profile-overview-frame.html",
            "<span><a href=\"overview-frame.html\" "
            + "target=\"packageListFrame\">All Packages</a></span>"
        },
        {PACKAGE_BUG_ID + FS + "compact2-frame.html",
            "<span><a href=\"overview-frame.html\" target=\"packageListFrame\">"
            + "All Packages</a></span><span><a href=\"profile-overview-frame.html\" "
            + "target=\"packageListFrame\">All Profiles</a></span>"
        },
        {PACKAGE_BUG_ID + FS + "pkg2" + FS + "compact2-package-frame.html",
            "<a href=\"../compact2-summary.html\" target=\"classFrame\">"
            + "compact2</a> - <a href=\"../pkg2/compact2-package-summary.html\" "
            + "target=\"classFrame\">pkg2</a>"
        },
        {PACKAGE_BUG_ID + FS + "compact2-summary.html",
            "<h1 title=\"Profile\" class=\"title\">Profile&nbsp;compact2</h1>"
        },
        {PACKAGE_BUG_ID + FS + "pkg5" + FS + "compact3-package-summary.html",
            "<div class=\"subTitle\">compact3</div>"
        },
        {PACKAGE_BUG_ID + FS + "overview-frame.html",
            "<span><a href=\"profile-overview-frame.html\" "
            + "target=\"packageListFrame\">All Profiles</a></span>"
        },
        {PACKAGE_BUG_ID + FS + "pkg2" + FS + "Class1Pkg2.html",
            "<div class=\"subTitle\">compact1, compact2, compact3</div>"
        }
    };

    /**
     * The entry point of the test.
     *
     * @param args the array of command line arguments.
     */
    public static void main(String[] args) {
        TestProfiles tester = new TestProfiles();
        run(tester, ARGS1, PROFILES_TEST, PROFILES_NEGATED_TEST);
        run(tester, ARGS2, PACKAGES_TEST, PACKAGES_NEGATED_TEST);
        tester.printSummary();
    }

    /**
     * {@inheritDoc}
     */
    public String getBugId() {
        return BUG_ID;
    }

    /**
     * {@inheritDoc}
     */
    public String getBugName() {
        return getClass().getName();
    }
}
