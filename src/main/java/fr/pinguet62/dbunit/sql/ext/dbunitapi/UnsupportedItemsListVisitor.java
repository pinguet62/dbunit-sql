package fr.pinguet62.dbunit.sql.ext.dbunitapi;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;

/** Empty {@link ItemsListVisitor} who always {@code throw}s and {@link UnsupportedOperationException}. */
public class UnsupportedItemsListVisitor implements ItemsListVisitor {

    protected void notSupported(ItemsList itemsList) {
        throw new UnsupportedOperationException(itemsList.getClass().toString());
    }

    @Override
    public void visit(ExpressionList expressionList) {
        notSupported(expressionList);
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