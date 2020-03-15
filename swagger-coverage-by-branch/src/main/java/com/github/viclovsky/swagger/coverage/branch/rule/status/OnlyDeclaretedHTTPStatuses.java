package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.SinglePredicateBranch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.FullStatusBranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;
import io.swagger.models.Operation;

import java.util.Arrays;
import java.util.List;

public class OnlyDeclaretedHTTPStatuses extends BranchRule {
    @Override
    public String getId() {
        return "only-declareted-status";
    }

    @Override
    public List<Branch> createBranch(Operation operation) {
        BranchPredicate predicate = new FullStatusBranchPredicate(operation.getResponses().keySet());
        Branch branch = new SinglePredicateBranch(
            "Only declared status",
            "",
            predicate
        );

        return Arrays.asList( branch);
    }
}
