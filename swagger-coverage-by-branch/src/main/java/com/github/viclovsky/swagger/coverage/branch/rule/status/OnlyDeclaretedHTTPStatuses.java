package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.FullStatusBranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Operation;

import java.util.Arrays;
import java.util.List;

public class OnlyDeclaretedHTTPStatuses extends BranchRule {
    @Override
    public List<Branch> createBranch(Operation operation) {
        Branch branch = new Branch(
                "Only declared status",
                ""
        );

        BranchPredicate predicate = new FullStatusBranchPredicate(operation.getResponses().keySet());
        branch.addPredicate(predicate);

        return Arrays.asList( branch);
    }
}
