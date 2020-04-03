package com.github.viclovsky.swagger.coverage.core.model;

import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SinglePredicateCondition extends Condition {

    private static final Logger log = LoggerFactory.getLogger(SinglePredicateCondition.class);


    protected ConditionPredicate predicate;

    public SinglePredicateCondition(String name, String description) {
        super(name, description);
    }

    public SinglePredicateCondition(String name, String description, ConditionPredicate predicate) {
        super(name, description);
        this.predicate = predicate;
    }

    @Override
    public void postCheck(){
        this.covered = predicate.postCheck();
    }

    @Override
    public boolean isHasPostCheck() {
        return predicate.hasPostCheck();
    }

    @Override
    public boolean isNeedCheck() {
        return this.covered == false || predicate.hasPostCheck();
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
        this.covered = predicate.check(params,responses);
        return this.covered;
    }

    @Override
    public String getReason() {
        if (predicate.getReason() != null){
            return predicate.getReason();
        }

        return "";
    }

    @Override
    public String getType() {
        return predicate.getClass().getSimpleName();
    }

}
