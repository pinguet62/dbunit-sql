package fr.pinguet62.dbunit.sql.ext;

import java.util.List;

import fr.pinguet62.dbunit.sql.ext.dbunitapi.UnsupportedExpressionVisitor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;

/**
 * {@link ExpressionVisitor} with only supported {@link Expression}.
 * <p>
 * Each value is {@link List#add(Object) added} to input parameter.
 */
public class SupportedExpressionVisitor extends UnsupportedExpressionVisitor {

    /** Contains each value of statement. */
    private final List<Object> values;

    /**
     * @param values
     *            The {@link #values}.
     */
    public SupportedExpressionVisitor(List<Object> values) {
        this.values = values;
    }

    @Override
    public void visit(LongValue longValue) {
        values.add(longValue.getValue());
    }

    @Override
    public void visit(StringValue stringValue) {
        values.add(stringValue.getValue());
    }

}