package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.DefaultBodyBranchPredicate;
import com.google.common.collect.Lists;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class DefaultBodyBranchRule extends ParameterRule {

    @Override
    public List<Branch> processParameter(Parameter parameter) {
        if (parameter instanceof BodyParameter) {
            Branch branch = new Branch(
                    "Not empty «body» request",
                    ""
            );

            BranchPredicate predicate = new DefaultBodyBranchPredicate();
            branch.addPredicate(predicate);

            return asList(branch);
        }

        return null;
    }
}
