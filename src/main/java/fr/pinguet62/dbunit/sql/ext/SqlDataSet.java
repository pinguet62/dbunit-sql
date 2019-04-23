package fr.pinguet62.dbunit.sql.ext;

import org.apache.commons.io.IOUtils;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.IDataSetProducer;

import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.Charset.defaultCharset;

/**
 * {@link IDataSet} for SQL script file.
 * <p>
 * Use {@link IDataSetProducer} to read input data.
 *
 * @see SqlDataSetProducer
 */
public class SqlDataSet extends CachedDataSet {

    public SqlDataSet(InputStream inputStream) throws DataSetException, IOException {
        this(IOUtils.toString(inputStream, defaultCharset()));
    }

    public SqlDataSet(String script) throws DataSetException {
        super(new SqlDataSetProducer(script));
    }

}
