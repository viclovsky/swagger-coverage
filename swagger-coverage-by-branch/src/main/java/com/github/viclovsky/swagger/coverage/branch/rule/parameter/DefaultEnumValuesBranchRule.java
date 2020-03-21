package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.DefaultParameterValueBranchPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

public class DefaultEnumValuesBranchRule extends ParameterRule {

    @Override
    public List<Branch> processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProcessor.extractEnum(parameter);
        List<Branch> branches = new ArrayList<>();

        if (enumValues != null && !enumValues.isEmpty()) {

            enumValues.forEach(e -> {
                Branch branch = new Branch(
                        String.format("%s «%s» contains value from enum «%s»", parameter.getIn(), parameter.getName(), e),
                        ""
                );

                BranchPredicate predicate = new DefaultParameterValueBranchPredicate(parameter.getName(), parameter.getIn(), e);
                branch.addPredicate(predicate);
                branches.add(branch);
            });
            return branches;
        }

        return null;
    }
}
