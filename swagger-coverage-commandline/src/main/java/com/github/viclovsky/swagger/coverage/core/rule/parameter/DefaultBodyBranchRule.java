package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Branch;
import com.github.viclovsky.swagger.coverage.core.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultBodyBranchPredicate;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;

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
