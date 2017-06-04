package fr.pinguet62.dbunit.sql.ext;

import java.util.ArrayList;
import java.util.List;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.stream.IDataSetConsumer;
import org.dbunit.dataset.stream.IDataSetProducer;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.insert.Insert;

/** {@link IDataSetProducer} who <b>parse SQL script</b> and initializes table/columns/values for DbUnit. */
public class SqlDataSetProducer implements IDataSetProducer {

    private IDataSetConsumer consumer;

    private final String script;

    public SqlDataSetProducer(String script) {
        this.script = script;
    }

    @Override
    public void produce() throws DataSetException {
        consumer.startDataSet();

        // Parse statements
        Statements statements;
        try {
            statements = CCJSqlParserUtil.parseStatements(script);
        } catch (JSQLParserException e) {
            throw new DataSetException("Error parsing script", e);
        }

        for (Statement statement : statements.getStatements()) {
            if (!(statement instanceof Insert))
                throw new DataSetException("Only INSERT statement are supported: " + statement);
            Insert insert = (Insert) statement;

            // Columns
            List<net.sf.jsqlparser.schema.Column> readColumns = insert.getColumns();
            int nbColumns = readColumns.size();
            if (nbColumns == 0)
                throw new DataSetException("The columns name must be written into script");
            Column[] columns = new Column[nbColumns];
            for (int c = 0; c < nbColumns; c++)
                columns[c] = new Column(readColumns.get(c).getColumnName(), DataType.UNKNOWN);

            // Table
            String tableName = insert.getTable().getName();
            ITableMetaData metaData = new DefaultTableMetaData(tableName, columns);

            consumer.startTable(metaData);

            // Value
            ItemsList itemsList = insert.getItemsList();
            final List<Object> tmpItems = new ArrayList<>();
            itemsList.accept(new SupportedItemsListVisitor(tmpItems));
            Object[] row = tmpItems.toArray();
            consumer.row(row);

            consumer.endTable();
        }

        consumer.endDataSet();
    }

    @Override
    public void setConsumer(IDataSetConsumer consumer) throws DataSetException {
        this.consumer = consumer;
    }

}