package org.dbunit;

import org.dbunit.database.IDatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Base testCase for database testing.<br>
 * Subclasses may override {@link #newDatabaseTester()} to plug-in a different implementation
 * of IDatabaseTester.<br> Default implementation uses a {@link PropertiesBasedJdbcDatabaseTester}.
 */
public abstract class JUnit5DBTestCase extends JUnit5DatabaseTestCase {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(JUnit5DBTestCase.class);

    public JUnit5DBTestCase() {
        super();
    }

    public JUnit5DBTestCase(String name) {
        super(name);
    }

    protected final IDatabaseConnection getConnection() throws Exception {
        logger.debug("getConnection() - start");

        final IDatabaseTester databaseTester = getDatabaseTester();
        assertNotNull( databaseTester, "DatabaseTester is not set" );
        IDatabaseConnection connection = databaseTester.getConnection();
        // Ensure that users have the possibility to configure the connection's configuration
        setUpDatabaseConfig(connection.getConfig());
        return connection;
    }

    /**
     * Creates a new IDatabaseTester.
     * Default implementation returns a {@link PropertiesBasedJdbcDatabaseTester}.
     */
    protected IDatabaseTester newDatabaseTester() throws Exception {
        return new PropertiesBasedJdbcDatabaseTester();
    }

}
