package com.github.viclovsky.swagger.coverage.core.rule.tag;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultTagConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.rule.core.ConditionRule;
import io.swagger.v3.oas.models.Operation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterAllByTagRule extends ConditionRule {

    @Override
    public String getId() {
        return "filterAllByTag";
    }

    public List<Condition> createCondition(Operation operation) {
        if (!isRuleEnabled()) {
            return null;
        }
        List<String> tags = operation.getTags();
        if (tags == null || tags.isEmpty()) {
            return processNoTag();
        }

        return shouldProcess(tags) ? generateCondition() : null;
    }

    public List<Condition> generateCondition() {
        Condition condition = new SinglePredicateCondition(getPredicateName(), "",
                new DefaultTagConditionPredicate(null));
        condition.setCovered(true);
        return Arrays.asList(condition);
    }

    public List<Condition> processNoTag() {
        return (includesEnabled()) ? null : generateCondition();
    }

    public boolean isRuleEnabled() {
        if (this.options == null) {
            return false;
        }
        return options.isEnable() && (includesEnabled() || excludesEnabled());
    }

    private boolean shouldProcess(List<String> tags) {
        boolean foundInclusion = false;
        for (String tag : tags) {
            if (isExcluded(tag)) {
                return false;
            }
            if (isIncluded(tag)) {
                foundInclusion = true;
            }
        }
        return includesEnabled() == foundInclusion;
    }

    private String getPredicateName() {
        StringBuilder sb = new StringBuilder("Filter all ");
        if (includesEnabled()) {
            String includedTags = options.getFilter().stream()
                    .filter(tag -> !isExcluded(tag))
                    .collect(Collectors.joining(","));
            sb.append(" by tag(s) ").append(includedTags);
        }
        if (excludesEnabled()) {
            sb.append(" excluding tag(s) ");
            sb.append(options.getIgnore().stream().collect(Collectors.joining(",")));
        }
        return sb.toString();
    }
}
