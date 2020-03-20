package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.NotOnlyParameterListValueBranchPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.List;

public class NotOnlyEnumValuesBranchRule extends ParameterRule {

    @Override
    public Branch processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProcessor.extractEnum(parameter);

        if (enumValues != null && !enumValues.isEmpty()) {
            Branch branch = new Branch(
                    String.format("%s «%s» contains values not only from enum %s", parameter.getIn(),
                            parameter.getName(), enumValues),
                    ""
            );

            BranchPredicate predicate = new NotOnlyParameterListValueBranchPredicate(parameter.getName(),
                    parameter.getIn(), enumValues);
            branch.addPredicate(predicate);

            return branch;
        }

        return null;
    }
}
