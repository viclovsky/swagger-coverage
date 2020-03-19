package com.github.viclovsky.swagger.coverage.branch.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.Map;

public class SimpleBranchPredicate extends BranchPredicate{

    protected boolean isEmpty;
    protected String paramName;

    public SimpleBranchPredicate(boolean isEmpty, String paramName) {
        this.isEmpty = isEmpty;
        this.paramName = paramName;
    }

    @Override
    public boolean check(Map<String, Parameter> params, Map<String, Response> responses) {
        return (isEmpty() ^ params.containsKey(getParamName()));
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String toString() {
        return "BranchPredicate{" +
                "isEmpty=" + isEmpty +
                ", paramName='" + paramName + '\'' +
                '}';
    }

}
