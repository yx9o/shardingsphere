/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.test.it.sql.parser.internal.asserts.segment.assignment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.BinaryOperationExpression;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.CaseWhenExpression;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.ExpressionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.FunctionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.complex.CommonExpressionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.simple.LiteralExpressionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.simple.ParameterMarkerExpressionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.subquery.SubqueryExpressionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.item.ExpressionProjectionSegment;
import org.apache.shardingsphere.test.it.sql.parser.internal.asserts.SQLCaseAssertContext;
import org.apache.shardingsphere.test.it.sql.parser.internal.asserts.segment.column.ColumnAssert;
import org.apache.shardingsphere.test.it.sql.parser.internal.asserts.segment.expression.ExpressionAssert;
import org.apache.shardingsphere.test.it.sql.parser.internal.cases.parser.jaxb.segment.impl.assignment.ExpectedAssignmentValue;

/**
 * Assignment value assert.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AssignmentValueAssert {
    
    /**
     * Assert actual expression segment is correct with expected assignment value.
     *
     * @param assertContext assert context
     * @param actual actual expression segment
     * @param expected expected assignment value
     * @throws UnsupportedOperationException unsupported assertion segment exception               
     */
    public static void assertIs(final SQLCaseAssertContext assertContext, final ExpressionSegment actual, final ExpectedAssignmentValue expected) {
        if (actual instanceof ParameterMarkerExpressionSegment) {
            ExpressionAssert.assertParameterMarkerExpression(assertContext, (ParameterMarkerExpressionSegment) actual, expected.getParameterMarkerExpression());
            return;
        }
        if (actual instanceof LiteralExpressionSegment) {
            ExpressionAssert.assertLiteralExpression(assertContext, (LiteralExpressionSegment) actual, expected.getLiteralExpression());
            // FIXME should be CommonExpressionProjection, not ExpressionProjectionSegment
            return;
        }
        if (actual instanceof ExpressionProjectionSegment) {
            ExpressionAssert.assertCommonExpression(assertContext, (ExpressionProjectionSegment) actual, expected.getCommonExpression());
            return;
        }
        if (actual instanceof ColumnSegment) {
            ColumnAssert.assertIs(assertContext, (ColumnSegment) actual, expected.getColumn());
            return;
        }
        if (actual instanceof SubqueryExpressionSegment) {
            ExpressionAssert.assertSubqueryExpression(assertContext, (SubqueryExpressionSegment) actual, expected.getSubquery());
            return;
        }
        if (actual instanceof FunctionSegment) {
            ExpressionAssert.assertFunction(assertContext, (FunctionSegment) actual, expected.getFunction());
            return;
        }
        if (actual instanceof CommonExpressionSegment) {
            ExpressionAssert.assertCommonExpression(assertContext, (CommonExpressionSegment) actual, expected.getCommonExpression());
            return;
        }
        if (actual instanceof CaseWhenExpression) {
            ExpressionAssert.assertCaseWhenExpression(assertContext, (CaseWhenExpression) actual, expected.getCaseWhenExpression());
            return;
        }
        if (actual instanceof BinaryOperationExpression) {
            ExpressionAssert.assertBinaryOperationExpression(assertContext, (BinaryOperationExpression) actual, expected.getBinaryOperationExpression());
        }
    }
}
