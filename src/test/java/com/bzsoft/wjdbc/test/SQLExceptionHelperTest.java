// VJDBC - Virtual JDBC
// Written by Michael Link
// Website: http://vjdbc.sourceforge.net

package com.bzsoft.wjdbc.test;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;

import com.bzsoft.wjdbc.util.SQLExceptionHelper;


import junit.framework.TestCase;

public class SQLExceptionHelperTest extends TestCase {

    public void testDerivedSQLException() throws Exception
    {
        SQLException originalEx = new SQLException();
        Exception otherEx = new UnsupportedOperationException("Bla");

        SQLException wex2 = SQLExceptionHelper.wrap(originalEx);
        assertSame(originalEx, wex2);

        SQLException wex3 = SQLExceptionHelper.wrap(otherEx);
        assertNotSame(otherEx, wex3);
    }
}
