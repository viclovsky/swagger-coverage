package com.github.viclovsky.swagger.coverage.core.rule;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import io.swagger.models.Operation;

import java.util.List;

public abstract class ConditionRule {
    public abstract List<Condition> createCondition(Operation operation);
}
