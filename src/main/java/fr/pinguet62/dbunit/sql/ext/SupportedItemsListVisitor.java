package fr.pinguet62.dbunit.sql.ext;

import java.util.List;

import fr.pinguet62.dbunit.sql.ext.dbunitapi.UnsupportedItemsListVisitor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

public class SupportedItemsListVisitor extends UnsupportedItemsListVisitor {

    private final List<Object> values;

    public SupportedItemsListVisitor(List<Object> values) {
        this.values = values;
    }

    @Override
    public void visit(ExpressionList expressionList) {
        for (Expression expression : expressionList.getExpressions()) {
            SupportedExpressionVisitor expressionDeParser = new SupportedExpressionVisitor(values);
            expression.accept(expressionDeParser);
        }
    }

}