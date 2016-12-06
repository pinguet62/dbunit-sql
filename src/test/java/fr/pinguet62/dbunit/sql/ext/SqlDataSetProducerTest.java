package fr.pinguet62.dbunit.sql.ext;

import static org.junit.Assert.assertEquals;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.IDataSetConsumer;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.junit.Assert;
import org.junit.Test;

import fr.pinguet62.dbunit.sql.ext.SqlDataSetProducer;

/**
 * Empty {@link IDataSetConsumer} implementation.<br>
 * {@link Override} necessary methods to check the good {@link IDataSetProducer} behavior.
 */
abstract class DataSetConsumerChecker implements IDataSetConsumer {

    @Override
    public void startDataSet() throws DataSetException {}

    @Override
    public void endDataSet() throws DataSetException {}

    @Override
    public void endTable() throws DataSetException {}

}

/** @see SqlDataSetProducer */
public class SqlDataSetProducerTest {

    /**
     * A missing key doesn't insert the corresponding value.
     * 
     * @see SqlDataSetProducer#produce()
     */
    @Test
    public void test_missingKey() throws DataSetException {
        run("insert into profile (id) values ('1st', 'first');", new DataSetConsumerChecker() {
            @Override
            public void startTable(ITableMetaData metaData) throws DataSetException {
                assertEquals(1, metaData.getColumns().length);
                assertEquals("id", metaData.getColumns()[0].getColumnName());
            }

            @Override
            public void row(Object[] values) throws DataSetException {
                assertEquals(2, values.length);
                assertEquals("1st", values[0]);
                assertEquals("first", values[1]);
            }
        });
    }

    /**
     * A missing value throws an error.
     * 
     * @see SqlDataSetProducer#produce()
     */
    @Test
    public void test_missingValue() throws DataSetException {
        run("insert into profile (id, key) values ('1st');", new DataSetConsumerChecker() {
            @Override
            public void startTable(ITableMetaData metaData) throws DataSetException {
                assertEquals(2, metaData.getColumns().length);
                assertEquals("id", metaData.getColumns()[0].getColumnName());
                assertEquals("key", metaData.getColumns()[1].getColumnName());
            }

            @Override
            public void row(Object[] values) throws DataSetException {
                assertEquals(1, values.length);
                assertEquals("1st", values[0]);
            }
        });
    }

    /**
     * User {@link DataSetConsumerChecker} on {@link SqlDataSetProducer}'s consumer.<br>
     * This {@link DataSetConsumerChecker} must contains {@link Assert} methods to check the good implementation of
     * {@link IDataSetProducer#produce()}.
     * 
     * @see IDataSetProducer#produce()
     */
    private void run(String script, IDataSetConsumer checker) throws DataSetException {
        IDataSetProducer producer = new SqlDataSetProducer(script);
        producer.setConsumer(checker);
        producer.produce();
    }

}