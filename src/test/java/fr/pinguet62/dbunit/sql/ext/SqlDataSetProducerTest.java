package fr.pinguet62.dbunit.sql.ext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.stream.IDataSetConsumer;
import org.dbunit.dataset.stream.IDataSetProducer;
import org.junit.Assert;
import org.junit.Test;

import fr.pinguet62.dbunit.sql.ext.dbunitapi.UnsupportedExpressionVisitor;
import net.sf.jsqlparser.expression.ExpressionVisitor;

/** @see SqlDataSetProducer */
public class SqlDataSetProducerTest {

    /**
     * User {@link IDataSetConsumer} on {@link SqlDataSetProducer}'s consumer.<br>
     * This {@link IDataSetConsumer} can contain {@link Assert} methods to check the good implementation of
     * {@link IDataSetProducer#produce()}.
     *
     * @see UnsupportedExpressionVisitor
     */
    private void run(String script, IDataSetConsumer consumer) {
        IDataSetProducer producer = new SqlDataSetProducer(script);
        try {
            producer.setConsumer(consumer);
            producer.produce();
        } catch (DataSetException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Process supported SQL request.
     *
     * @see SupportedExpressionVisitor
     */
    private void runUnsupported(String script) {
        IDataSetProducer producer = new SqlDataSetProducer(script);
        try {
            producer.setConsumer(new IDataSetConsumerAdapter());
            producer.produce();
            fail("Must fail: " + script);
        } catch (DataSetException e) {
            fail(e.getMessage());
        } catch (UnsupportedOperationException e) {
            // ok
        }
    }

    /**
     * A missing key doesn't insert the corresponding value.
     *
     * @see SqlDataSetProducer#produce()
     */
    @Test
    public void test_missingKey() {
        run("insert into profile (id) values ('1st', 'first');", new IDataSetConsumerAdapter() {
            @Override
            public void row(Object[] values) throws DataSetException {
                assertEquals(2, values.length);
                assertEquals("1st", values[0]);
                assertEquals("first", values[1]);
            }

            @Override
            public void startTable(ITableMetaData metaData) throws DataSetException {
                assertEquals(1, metaData.getColumns().length);
                assertEquals("id", metaData.getColumns()[0].getColumnName());
            }
        });
    }

    /**
     * A missing value throws an error.
     *
     * @see SqlDataSetProducer#produce()
     */
    @Test
    public void test_missingValue() {
        run("insert into profile (id, key) values ('1st');", new IDataSetConsumerAdapter() {
            @Override
            public void row(Object[] values) throws DataSetException {
                assertEquals(1, values.length);
                assertEquals("1st", values[0]);
            }

            @Override
            public void startTable(ITableMetaData metaData) throws DataSetException {
                assertEquals(2, metaData.getColumns().length);
                assertEquals("id", metaData.getColumns()[0].getColumnName());
                assertEquals("key", metaData.getColumns()[1].getColumnName());
            }
        });
    }

    /**
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Addition)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.CastExpression)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Concat)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Division)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.DoubleValue)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.Function)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.HexValue)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Modulo)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Multiplication)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.NullValue)
     * @see ExpressionVisitor#visit(net.sf.jsqlparser.expression.operators.arithmetic.Subtraction)
     */
    @Test
    public void test_unsupported() {
        runUnsupported("insert into profile (Addition) values (1 + 2);");
        runUnsupported("insert into profile (BitwiseAnd) values (1 & 2);");
        runUnsupported("insert into profile (BitwiseOr) values (1 | 2);");
        runUnsupported("insert into profile (BitwiseXor) values (1 ^ 2);");
        runUnsupported("insert into profile (CastExpression) values (CAST('25122017' as datetime));");
        runUnsupported("insert into profile (Concat) values ('foo' || 'bar');");
        runUnsupported("insert into profile (Division) values (1 / 2);");
        runUnsupported("insert into profile (DoubleValue) values (1.23);");
        runUnsupported("insert into profile (Function) values (FCT());");
        runUnsupported("insert into profile (HexValue) values (0x123);");
        runUnsupported("insert into profile (Modulo) values (1 % 2);");
        runUnsupported("insert into profile (Multiplication) values (1 * 2);");
        runUnsupported("insert into profile (NullValue) values (NULL);");
        runUnsupported("insert into profile (Substraction) values (1 - 2);");

    }

}