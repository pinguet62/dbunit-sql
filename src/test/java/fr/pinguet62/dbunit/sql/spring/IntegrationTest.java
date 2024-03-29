package fr.pinguet62.dbunit.sql.spring;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import fr.pinguet62.dbunit.sql.springtest.SqlDataSetLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DatabaseConfig.class)
// DbUnit
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = SqlDataSetLoader.class)
@DatabaseSetup("/dataset.sql")
class IntegrationTest {

    @Autowired
    DataSource dataSource;

    @Test
    void test() throws SQLException {
        ResultSet profileRS = dataSource.getConnection().createStatement().executeQuery("SELECT * FROM profile ORDER BY id");
        while (profileRS.next()) {
            assertTrue(asList("1st", "2nd").contains(profileRS.getString("id")));
            assertTrue(asList("first", "second").contains(profileRS.getString("key")));
        }

        ResultSet userRS = dataSource.getConnection().createStatement().executeQuery("SELECT * FROM user ORDER BY id");
        while (userRS.next()) {
            assertTrue(asList(1, 2).contains(userRS.getInt("id")));
            assertTrue(asList("AAA", "BBB").contains(userRS.getString("name")));
            assertTrue(asList("first@domain.org", "second@domain.org").contains(userRS.getString("email")));
            assertTrue(asList("1st", "2nd").contains(userRS.getString("profile_id")));
        }
    }

}
