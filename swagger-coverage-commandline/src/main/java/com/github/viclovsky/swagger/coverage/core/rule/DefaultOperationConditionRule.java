package com.github.viclovsky.swagger.coverage.core.rule;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import io.swagger.models.Operation;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class DefaultOperationConditionRule extends ConditionRule {

    @Override
    public List<Condition> createCondition(Operation operation) {
        Condition condition = new Condition("Was called", "");
        condition.addPredicate(new ConditionPredicate() {
            @Override
            public boolean check(List<Parameter> params, Map<String, Response> responses) {
                return true;
            }

            @Override
            public boolean postCheck() {
                return false;
            }

            @Override
            public boolean hasPostCheck() {
                return false;
            }

            @Override
            public String getReason() {
                return null;
            }
        });
        return asList(condition);
    }
}
