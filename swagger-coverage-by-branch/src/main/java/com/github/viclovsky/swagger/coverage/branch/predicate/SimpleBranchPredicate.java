package com.github.viclovsky.swagger.coverage.branch.predicate;

import io.swagger.models.Response;
import io.swagger.models.parameters.Parameter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleBranchPredicate extends BranchPredicate{

    protected boolean isEmpty;
    protected String name;
    protected String in;

    public SimpleBranchPredicate(boolean isEmpty, String name, String in) {
        this.isEmpty = isEmpty;
        this.name = name;
        this.in = in;
    }

    @Override
    public boolean check(List<Parameter> params, Map<String, Response> responses) {
        Optional<Parameter> p = params.stream().filter(ParameterUtils.equalsParam(name, in)).findFirst();
        return (isEmpty() ^ p.isPresent());
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

    public String getName() {
        return name;
    }

    public void setParamName(String paramName) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BranchPredicate{" +
                "isEmpty=" + isEmpty +
                ", paramName='" + name + '\'' +
                '}';
    }

}
