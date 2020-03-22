package com.github.viclovsky.swagger.coverage.core.rule.status;


import com.github.viclovsky.swagger.coverage.core.model.Branch;
import com.github.viclovsky.swagger.coverage.core.predicate.BranchPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultStatusBranchPredicate;

import java.util.List;

import static java.util.Arrays.asList;

public class DefaultHTTPStatusBranchRule extends StatusBranchRule {

    @Override
    public List<Branch> processStatus(String status) {
        Branch branch = new Branch(
                "HTTP status " + status,
                ""
        );

        BranchPredicate predicate = new DefaultStatusBranchPredicate(status);
        branch.addPredicate(predicate);
        return asList(branch);
    }
}
