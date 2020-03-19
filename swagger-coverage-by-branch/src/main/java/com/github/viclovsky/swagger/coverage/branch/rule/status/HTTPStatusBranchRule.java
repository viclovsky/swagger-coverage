package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.StatusBranchPredicate;


public class HTTPStatusBranchRule extends StatusBranchRule {

    @Override
    public Branch processStatus(String status) {
        Branch branch = new Branch(
                "HTTP status " + status,
                ""
        );

        BranchPredicate predicate = new StatusBranchPredicate(status);
        branch.addPredicate(predicate);
        return branch;
    }
}
