package com.ezpzapis.commons.rest.dsl.filter;

import com.ezpzapis.commons.util.TypeValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

@Log4j2
public class RestFilterDSLMongoVisitor<T> extends RestFilterDSLBaseVisitor<Criteria> {

    private Class<T> clazz;

    public RestFilterDSLMongoVisitor(Class<T> clazz) {
        this.clazz = clazz;
    }

    private static final int LEFT = 0, RIGHT = 1;

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public Criteria visitQueryRoot(RestFilterDSLParser.QueryRootContext ctx) {
        return visit(ctx.getChild(0));
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public Criteria visitParenthesizedExpression(RestFilterDSLParser.ParenthesizedExpressionContext ctx) {
        Criteria result = super.visit(ctx.logical_expression());
        return result;
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public Criteria visitComparisonExpression(RestFilterDSLParser.ComparisonExpressionContext ctx) {

        Criteria criteria = Criteria.where(ctx.fieldName().getText());

        if(TypeValidator.validate(clazz, ctx.fieldName().getText(), ctx.fieldValue().getText())) {

            switch (ctx.comparison_operator().getText()) {
                case "==":
                    criteria = criteria.is(removeEnclosingQuotes(ctx.fieldValue().getText()));
                    break;
                case "!=":
                    criteria = criteria.ne(ctx.fieldValue().getText());
                    break;
                case ">":
                    criteria = criteria.gt(ctx.fieldValue().getText());
                    break;
                case ">=":
                    criteria = criteria.gte(ctx.fieldValue().getText());
                    break;
                case "<":
                    criteria = criteria.lt(ctx.fieldValue().getText());
                    break;
                case "<=":
                    criteria = criteria.lte(ctx.fieldValue().getText());
                    break;
                default:
                    throw new IllegalArgumentException(String.format("'%s' is not a supported operator", ctx.comparison_operator().getText()));
            }
        }
        return criteria;
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public Criteria visitAndExpression(RestFilterDSLParser.AndExpressionContext ctx) {
        return new Criteria().andOperator(
                (Criteria)visit(ctx.logical_expression(LEFT)), (Criteria)visit(ctx.logical_expression(RIGHT)));
    }

    /**
     * @param ctx the parse tree
     * @return
     */
    @Override
    public Criteria visitOrExpression(RestFilterDSLParser.OrExpressionContext ctx) {
        return new Criteria().orOperator(
                (Criteria)visit(ctx.logical_expression(LEFT)), (Criteria)visit(ctx.logical_expression(RIGHT))
        );
    }

    private String removeEnclosingQuotes(String str) {
        if (StringUtils.isNotBlank(str) && (str.startsWith("'") || str.startsWith("\""))) {
            log.info("%s has quotes", str);
            return str.substring(1, str.length() - 1);
        } else {
            return str;
        }
    }
}
