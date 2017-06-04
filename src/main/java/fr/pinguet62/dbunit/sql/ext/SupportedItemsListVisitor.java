package fr.pinguet62.dbunit.sql.ext;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * {@link ItemsListVisitor} with only supported {@link ItemsList}.
 * <p>
 * Supported {@link ItemsList} call {@link SupportedExpressionVisitor} to input parameter.<br>
 * Unsupported {@link ItemsList} throw an {@link UnsupportedOperationException}.
 */
public class SupportedItemsListVisitor implements ItemsListVisitor {

    private final List<Object> values;

    public SupportedItemsListVisitor(List<Object> values) {
        this.values = values;
    }

    protected void notSupported(ItemsList itemsList) {
        throw new UnsupportedOperationException(itemsList.getClass().toString());
    }

    @Override
    public void visit(ExpressionList expressionList) {
        for (Expression expression : expressionList.getExpressions()) {
            SupportedExpressionVisitor expressionDeParser = new SupportedExpressionVisitor(values);
            expression.accept(expressionDeParser);
        }
    }

    @Override
    public void visit(MultiExpressionList multiExprList) {
        notSupported(multiExprList);
    }

    @Override
    public void visit(SubSelect subSelect) {
        notSupported(subSelect);
    }

}