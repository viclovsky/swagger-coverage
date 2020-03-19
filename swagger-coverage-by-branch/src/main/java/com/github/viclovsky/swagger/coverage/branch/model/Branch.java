package com.github.viclovsky.swagger.coverage.branch.model;

import com.github.viclovsky.swagger.coverage.branch.predicate.BranchPredicate;

import java.util.ArrayList;
import java.util.List;

public class Branch {

    protected String name;
    protected String description;
    protected List<String> reasons = new ArrayList<>();
    protected boolean covered;
    protected List<BranchPredicate> predicateList;

    public Branch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void postCheck(){
        predicateList.stream().filter(BranchPredicate::hasPostCheck).forEach(branchPredicate -> {
            covered = covered && branchPredicate.postCheck();
            reasons.add(branchPredicate.getReason());
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

    public List<BranchPredicate> getPredicateList() {
        return predicateList;
    }

    public void setPredicateList(List<BranchPredicate> predicateList) {
        this.predicateList = predicateList;
    }

    public void addPredicate(BranchPredicate predicate){
        if(predicateList == null) {
            predicateList = new ArrayList<>();
        }

        predicateList.add(predicate);
    }

    public List<String> getReasons() {
        return reasons;
    }

    public Branch setReasons(List<String> reasons) {
        this.reasons = reasons;
        return this;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", covered=" + covered +
                ", predicateList=" + predicateList +
                '}';
    }
}
