package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.generator.SwaggerSpecificationProcessor;
import com.github.viclovsky.swagger.coverage.core.model.Branch;
import com.github.viclovsky.swagger.coverage.core.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.NotOnlyParameterListValueBranchPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.List;

import static java.util.Arrays.asList;

public class NotOnlyEnumValuesBranchRule extends ParameterRule {

    @Override
    public List<Branch> processParameter(Parameter parameter) {
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

            return asList(branch);
        }

        return null;
    }
}
