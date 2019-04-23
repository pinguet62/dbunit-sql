package fr.pinguet62.dbunit.sql.ext;

import org.apache.commons.io.IOUtils;
import org.dbunit.DBTestCase;
import org.dbunit.DatabaseTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Arrays.asList;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME;

/**
 * Real test case based on {@link DBTestCase}.
 */
public class IntegrationTest extends DBTestCase {

    public IntegrationTest() {
        System.setProperty(DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver");
        System.setProperty(DBUNIT_CONNECTION_URL, "jdbc:hsqldb:mem:test");
        System.setProperty(DBUNIT_USERNAME, "sa");
        System.setProperty(DBUNIT_PASSWORD, "");
    }

    /**
     * Add <b>DDL creation</b> operation before {@link DatabaseTestCase#getSetUpOperation() default}.
     */
    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        DatabaseOperation ddlOperation = new DatabaseOperation() {
            @Override
            public void execute(IDatabaseConnection connection, IDataSet dataSet) throws DatabaseUnitException, SQLException {
                try {
                    String ddlScript = IOUtils.toString(getClass().getResourceAsStream("/schema.sql"), defaultCharset());
                    IntegrationTest.this.getConnection().getConnection().createStatement().execute(ddlScript);
                } catch (Exception e) {
                    throw new RuntimeException("Error reading or executing DDL file", e);
                }
            }
        };
        return new CompositeOperation(ddlOperation, super.getSetUpOperation());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new SqlDataSet(getClass().getResourceAsStream("/dataset.sql"));
    }

    @Test
    public void test() throws Exception {
        ResultSet profileRS = getConnection().getConnection().createStatement().executeQuery("SELECT * FROM profile ORDER BY id");
        while (profileRS.next()) {
            assertTrue(asList("1st", "2nd").contains(profileRS.getString("id")));
            assertTrue(asList("first", "second").contains(profileRS.getString("key")));
        }

        ResultSet userRS = getConnection().getConnection().createStatement().executeQuery("SELECT * FROM user ORDER BY id");
        while (userRS.next()) {
            assertTrue(asList(1, 2).contains(userRS.getInt("id")));
            assertTrue(asList("AAA", "BBB").contains(userRS.getString("name")));
            assertTrue(asList("first@domain.org", "second@domain.org").contains(userRS.getString("email")));
            assertTrue(asList("1st", "2nd").contains(userRS.getString("profile_id")));
        }
    }

}
