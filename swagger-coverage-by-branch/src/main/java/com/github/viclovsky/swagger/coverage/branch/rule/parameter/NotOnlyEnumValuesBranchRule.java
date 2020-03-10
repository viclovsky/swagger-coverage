package com.github.viclovsky.swagger.coverage.branch.rule.parameter;

import com.github.viclovsky.swagger.coverage.branch.generator.SwaggerSpecificationProccessor;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.NotOnlyParameterListValueBranchPredicate;
import io.swagger.models.parameters.Parameter;

import java.util.List;

public class NotOnlyEnumValuesBranchRule extends ParameterRule {


    @Override
    public Branch processParameter(Parameter parameter) {
        List<String> enumValues = SwaggerSpecificationProccessor.extractEnum(parameter);

        if (enumValues != null && !enumValues.isEmpty()) {
            Branch branch = new Branch(
                    parameter.getIn() + " {" + parameter.getName() + "} contains values not only from enum " + enumValues,
                    ""
            );

            BranchPredicate predicate = new NotOnlyParameterListValueBranchPredicate(parameter.getName(),enumValues);
            branch.addPredicate(predicate);

            return branch;
        }

        return null;
    }
}
