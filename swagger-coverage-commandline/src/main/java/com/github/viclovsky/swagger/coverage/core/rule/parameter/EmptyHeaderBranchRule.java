package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Branch;
import com.github.viclovsky.swagger.coverage.core.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultBranchPredicate;
import io.swagger.models.parameters.HeaderParameter;
import io.swagger.models.parameters.Parameter;

import java.util.List;

import static java.util.Arrays.asList;

public class EmptyHeaderBranchRule extends ParameterRule {

    @Override
    public List<Branch> processParameter(Parameter parameter) {
        if (parameter instanceof HeaderParameter) {
            Branch branch = new Branch(String.format("Empty header «%s»", parameter.getName()),
                    ""
            );

            BranchPredicate predicate = new DefaultBranchPredicate(true, parameter.getName(), parameter.getIn());
            branch.addPredicate(predicate);

            return asList(branch);
        }

        return null;
    }
}
