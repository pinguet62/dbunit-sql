package fr.pinguet62.dbunit.sql.ext;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.ArrayExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.CollateExpression;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NextValExpression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.IntegerDivision;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.FullTextSearch;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.expression.operators.relational.SimilarToExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;

/**
 * {@link ExpressionVisitor} with only supported {@link Expression}.
 * <p>
 * Supported {@link Expression} value are {@link List#add(Object) added} to input parameter.<br>
 * Unsupported {@link Expression} throw an {@link UnsupportedOperationException}.
 */
public class SupportedExpressionVisitor implements ExpressionVisitor {

    /**
     * Contains each value of statement.
     */
    private final List<Object> values;

    /**
     * @param values The {@link #values}.
     */
    public SupportedExpressionVisitor(List<Object> values) {
        this.values = values;
    }

    @Override
    public void visit(StringValue stringValue) {
        values.add(stringValue.getValue());
    }

    @Override
    public void visit(LongValue longValue) {
        values.add(longValue.getValue());
    }

    @Override
    public void visit(Addition addition) {
        notSupported(addition);
    }

    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        notSupported(allComparisonExpression);
    }

    @Override
    public void visit(AnalyticExpression aexpr) {
        notSupported(aexpr);
    }

    @Override
    public void visit(AndExpression andExpression) {
        notSupported(andExpression);
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        notSupported(anyComparisonExpression);
    }

    @Override
    public void visit(Between between) {
        notSupported(between);
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        notSupported(bitwiseAnd);
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        notSupported(bitwiseOr);
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        notSupported(bitwiseXor);
    }

    @Override
    public void visit(CaseExpression caseExpression) {
        notSupported(caseExpression);
    }

    @Override
    public void visit(CastExpression cast) {
        notSupported(cast);
    }

    @Override
    public void visit(Column tableColumn) {
        notSupported(tableColumn);
    }

    @Override
    public void visit(Concat concat) {
        notSupported(concat);
    }

    @Override
    public void visit(DateTimeLiteralExpression literal) {
        notSupported(literal);
    }

    @Override
    public void visit(DateValue dateValue) {
        notSupported(dateValue);
    }

    @Override
    public void visit(Division division) {
        notSupported(division);
    }

    @Override
    public void visit(IntegerDivision division) {
        notSupported(division);
    }

    @Override
    public void visit(DoubleValue doubleValue) {
        notSupported(doubleValue);
    }

    @Override
    public void visit(EqualsTo equalsTo) {
        notSupported(equalsTo);
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        notSupported(existsExpression);
    }

    @Override
    public void visit(ExtractExpression eexpr) {
        notSupported(eexpr);
    }

    @Override
    public void visit(Function function) {
        notSupported(function);
    }

    @Override
    public void visit(GreaterThan greaterThan) {
        notSupported(greaterThan);
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        notSupported(greaterThanEquals);
    }

    @Override
    public void visit(HexValue hexValue) {
        notSupported(hexValue);
    }

    @Override
    public void visit(InExpression inExpression) {
        notSupported(inExpression);
    }

    @Override
    public void visit(FullTextSearch fullTextSearch) {
        notSupported(fullTextSearch);
    }

    @Override
    public void visit(IntervalExpression iexpr) {
        notSupported(iexpr);
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
        notSupported(isNullExpression);
    }

    @Override
    public void visit(IsBooleanExpression isBooleanExpression) {
        notSupported(isBooleanExpression);
    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        notSupported(jdbcNamedParameter);
    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {
        notSupported(jdbcParameter);
    }

    @Override
    public void visit(JsonExpression jsonExpr) {
        notSupported(jsonExpr);
    }

    @Override
    public void visit(JsonOperator jsonExpr) {
        notSupported(jsonExpr);
    }

    @Override
    public void visit(KeepExpression aexpr) {
        notSupported(aexpr);
    }

    @Override
    public void visit(LikeExpression likeExpression) {
        notSupported(likeExpression);
    }

    @Override
    public void visit(Matches matches) {
        notSupported(matches);
    }

    @Override
    public void visit(MinorThan minorThan) {
        notSupported(minorThan);
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        notSupported(minorThanEquals);
    }

    @Override
    public void visit(Modulo modulo) {
        notSupported(modulo);
    }

    @Override
    public void visit(Multiplication multiplication) {
        notSupported(multiplication);
    }

    @Override
    public void visit(MySQLGroupConcat groupConcat) {
        notSupported(groupConcat);
    }

    @Override
    public void visit(ValueListExpression valueList) {
        notSupported(valueList);
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        notSupported(notEqualsTo);
    }

    @Override
    public void visit(NotExpression aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(NextValExpression aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(CollateExpression aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(SimilarToExpression aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(ArrayExpression aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(BitwiseRightShift aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(BitwiseLeftShift aThis) {
        notSupported(aThis);
    }

    @Override
    public void visit(NullValue nullValue) {
        notSupported(nullValue);
    }

    @Override
    public void visit(NumericBind bind) {
        notSupported(bind);
    }

    @Override
    public void visit(OracleHierarchicalExpression oexpr) {
        notSupported(oexpr);
    }

    @Override
    public void visit(OracleHint hint) {
        notSupported(hint);
    }

    @Override
    public void visit(OrExpression orExpression) {
        notSupported(orExpression);
    }

    @Override
    public void visit(Parenthesis parenthesis) {
        notSupported(parenthesis);
    }

    @Override
    public void visit(RegExpMatchOperator rexpr) {
        notSupported(rexpr);
    }

    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
        notSupported(regExpMySQLOperator);
    }

    @Override
    public void visit(RowConstructor rowConstructor) {
        notSupported(rowConstructor);
    }

    @Override
    public void visit(SignedExpression signedExpression) {
        notSupported(signedExpression);
    }

    @Override
    public void visit(SubSelect subSelect) {
        notSupported(subSelect);
    }

    @Override
    public void visit(Subtraction subtraction) {
        notSupported(subtraction);
    }

    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {
        notSupported(timeKeyExpression);
    }

    @Override
    public void visit(TimestampValue timestampValue) {
        notSupported(timestampValue);
    }

    @Override
    public void visit(TimeValue timeValue) {
        notSupported(timeValue);
    }

    @Override
    public void visit(UserVariable var) {
        notSupported(var);
    }

    @Override
    public void visit(WhenClause whenClause) {
        notSupported(whenClause);
    }

    private void notSupported(Expression expression) {
        throw new UnsupportedOperationException(expression.getClass().toString());
    }

}
