package com.github.viclovsky.swagger.coverage.core.model;

import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;

import java.util.ArrayList;
import java.util.List;

public class Condition {

    private String name;
    private String description;
    private List<String> reasons = new ArrayList<>();
    private boolean covered;
    private List<ConditionPredicate> predicateList;

    public Condition(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void postCheck() {
        predicateList.stream().filter(ConditionPredicate::hasPostCheck).forEach(conditionPredicate -> {
            covered = covered && conditionPredicate.postCheck();
            reasons.add(conditionPredicate.getReason());
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public List<ConditionPredicate> getPredicateList() {
        return predicateList;
    }

    public void setPredicateList(List<ConditionPredicate> predicateList) {
        this.predicateList = predicateList;
    }

    public void addPredicate(ConditionPredicate predicate){
        if(predicateList == null) {
            predicateList = new ArrayList<>();
        }

        predicateList.add(predicate);
    }

    public List<String> getReasons() {
        return reasons;
    }

    public Condition setReasons(List<String> reasons) {
        this.reasons = reasons;
        return this;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", covered=" + covered +
                ", predicateList=" + predicateList +
                '}';
    }
}
