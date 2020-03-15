package com.github.viclovsky.swagger.coverage.branch.rule.status;

import com.github.viclovsky.swagger.coverage.branch.configuration.options.RuleConfigurationOptions;
import com.github.viclovsky.swagger.coverage.branch.model.Branch;
import com.github.viclovsky.swagger.coverage.branch.model.SinglePredicateBranch;
import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.predicate.StatusBranchPredicate;
import com.github.viclovsky.swagger.coverage.branch.rule.core.BranchRule;

import java.util.List;


public class HTTPStatusBranchRule extends StatusBranchRule {

    protected List<String> filter;
    protected List<String> ignore;

    @Override
    public String getId() {
        return "status";
    }

    @Override
    public Branch processStatus(String status) {
        if (
            filter != null
            && !filter.isEmpty()
            && !filter.contains(status)
        ) {
            return null;
        }

        if (
            ignore != null
            && !ignore.isEmpty()
            && ignore.contains(status)
        ) {
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

    @Override
    public BranchRule configure(RuleConfigurationOptions options) {
        this.filter = options.getFilter();
        this.ignore = options.getIgnore();
        return super.configure(options);
    }
}
