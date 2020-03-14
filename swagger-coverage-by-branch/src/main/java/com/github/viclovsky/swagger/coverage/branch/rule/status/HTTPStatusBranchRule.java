package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.SinglePredicateBranch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.StatusBranchPredicate;


public class HTTPStatusBranchRule extends StatusBranchRule {

    @Override
    public Branch processStatus(String status) {
        BranchPredicate predicate = new StatusBranchPredicate(status);
        Branch branch = new SinglePredicateBranch(
            "HTTP status " + status,
            "",
            predicate
        );
        return branch;
    }
}
