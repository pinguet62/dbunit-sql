package fr.pinguet62.dbunit.sql.ext;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.IDataSetConsumer;

public class IDataSetConsumerAdapter implements IDataSetConsumer {

    @Override
    public void endDataSet() throws DataSetException {
    }

    @Override
    public void endTable() throws DataSetException {
    }

    @Override
    public void row(Object[] values) throws DataSetException {
    }

    @Override
    public void startDataSet() throws DataSetException {
    }

    @Override
    public void startTable(ITableMetaData metaData) throws DataSetException {
    }

}