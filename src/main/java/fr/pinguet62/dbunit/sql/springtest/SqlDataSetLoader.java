package fr.pinguet62.dbunit.sql.springtest;

import java.io.InputStream;

import org.dbunit.dataset.IDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

import fr.pinguet62.dbunit.sql.ext.SqlDataSet;

public class SqlDataSetLoader extends AbstractDataSetLoader {

    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        try (InputStream inputStream = resource.getURL().openStream()) {
            return new SqlDataSet(inputStream);
        }
    }

}