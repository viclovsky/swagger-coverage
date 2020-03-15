<#import "operation.ftl" as operation />
<#import "../ui.ftl" as ui/>

<#macro list tags operations>
    <div class="accordion" id="tags-accordion">
        <#list tags as tag, tagCoverage>
            <div class="card">
                <div class="card-header">
                    <div class="row"
                         data-toggle="collapse"
                         data-target="#tag-${tag}"
                         aria-expanded="true"
                         aria-controls="collapseOne">
                        <div class="col-4">
                            <strong>${tagCoverage.description}</strong>
                        </div>
                        <div class="col-2">
                            ${tagCoverage.operations?size} ${i18["details.tag.operations"]}
                        </div>
                        <div class="col-2">
                            ${tagCoverage.callCounts} ${i18["details.operation.calls"]}
                        </div>
                        <div class="col-4">
                            <@ui.progress
                            full=tagCoverage.branchCounter.all
                            current=tagCoverage.branchCounter.covered
                            postfix=i18["details.branchprogress.postfix"]
                            />
                        </div>
                    </div>
                </div>
                <div id="tag-${tag}" class="collapse" aria-labelledby="headingOne">
                    <div class="card-body">
                        <@ui.coverageBadget counter=tagCoverage.coverageCounter />
                        <#list tagCoverage.operations as op>
                            <@operation.details
                            name=op
                            operationResult=operations[op]
                            target="tag-" + tag + "-" + op?counter
                            />
                        </#list>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</#macro>