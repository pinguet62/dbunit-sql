package fr.pinguet62.dbunit.sql.ext;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.IDataSetConsumer;

public class IDataSetConsumerAdapter implements IDataSetConsumer {

    @Override
    public void endDataSet() {
    }

    @Override
    public void endTable() {
    }

    @Override
    public void row(Object[] values) {
    }

    @Override
    public void startDataSet() {
    }

    @Override
    public void startTable(ITableMetaData metaData) throws DataSetException {
    }

}
