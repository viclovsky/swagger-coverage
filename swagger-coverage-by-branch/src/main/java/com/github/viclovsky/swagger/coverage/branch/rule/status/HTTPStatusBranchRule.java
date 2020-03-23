package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.SinglePredicateBranch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.StatusBranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;

import java.util.List;


public class HTTPStatusBranchRule extends StatusBranchRule {

    @Override
    public String getId() {
        return "status";
    }

    @Override
    public Branch processStatus(String status) {
        if (skeep(status)) {
            return null;
        }

        BranchPredicate predicate = new StatusBranchPredicate(status);
        Branch branch = new SinglePredicateBranch(
            "HTTP status " + status,
            "",
            predicate
        );
        return branch;
    }

    protected boolean skeep(String status){
        if (this.options == null) {
            return false;
        }
        if (
            this.options.getFilter() != null
            && !this.options.getFilter().isEmpty()
            && !this.options.getFilter().contains(status)
        ) {
            return true;
        }

        if (
            this.options.getIgnore() != null
            && !this.options.getIgnore().isEmpty()
            && this.options.getIgnore().contains(status)
        ) {
            return true;
        }

        return false;
    }
}
