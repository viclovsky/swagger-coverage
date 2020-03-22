package com.github.viclovsky.swagger.coverage.core.rule.status;

import com.github.viclovsky.swagger.coverage.core.model.Branch;
import com.github.viclovsky.swagger.coverage.core.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.FullStatusBranchPredicate;
import com.github.viclovsky.swagger.coverage.core.rule.BranchRule;
import io.swagger.models.Operation;

import java.util.List;

import static java.util.Arrays.asList;

public class OnlyDeclaredHTTPStatuses extends BranchRule {

    @Override
    public List<Branch> createBranch(Operation operation) {
        Branch branch = new Branch(
                "Only declared status",
                ""
        );

        BranchPredicate predicate = new FullStatusBranchPredicate(operation.getResponses().keySet());
        branch.addPredicate(predicate);

        return asList(branch);
    }
}
