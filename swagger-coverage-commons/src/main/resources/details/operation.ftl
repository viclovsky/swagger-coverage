<#import "../ui.ftl" as ui/>

<#macro list coverage prefix>
    <div class="accordion" id="${prefix}-accordion">
        <#list coverage as key, value>
            <div class="card">
                <div class="card-header">
                    <div class="row"
                         data-toggle="collapse"
                         data-target="#${prefix}-${key?index}"
                         aria-expanded="true"
                         aria-controls="collapseOne">
                        <div class="col-9">
                            ${key}
                        </div>
                        <div class="col-3">
                            ${i18["details.operation.status"]}: ${value.responses?keys?join(",")}
                        </div>
                    </div>
                </div>
                <div id="${prefix}-${key?index}" class="collapse" aria-labelledby="headingOne">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-12">
                                ${i18["details.operation.parameters"]}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <table class="table table-sm">
                                    <thead>
                                    <tr>
                                        <th scope="col">${i18["details.operation.parameter.type"]}</th>
                                        <th scope="col">${i18["details.operation.parameter.name"]}</th>
                                        <th scope="col">${i18["details.operation.parameter.value"]}</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list value.parameters as p>
                                            <tr>
                                                <td>${p.in}</td>
                                                <td>${p.getName()}</td>
                                                <td>${p.getVendorExtensions()["x-example"]}</td>
                                            </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
        <#if coverage?size == 0>
            ${i18["details.operation.no_data"]}
        </#if>
    </div>
</#macro>

<#macro details name operationResult target>
    <div class="card">
        <div class="card-header">
            <div class="row"
                 data-toggle="collapse"
                 data-target="#${target}"
                 aria-expanded="true"
                 aria-controls="collapseOne">
                <div class="col-6">
                    <p>
                        <@ui.coverageStateBadget operationResult=operationResult />

                        <button type="button" class="btn btn-sm">${operationResult.operationKey.httpMethod}</button>

                        <span><strong>${operationResult.operationKey.path}</strong></span>
                    </p>
                    <span><small>${operationResult.description}</small></span>
                </div>
                <div class="col-2">
                    ${operationResult.processCount} ${i18["details.operation.calls"]}
                </div>
                <div class="col-4">
                    <@ui.progress
                    full=operationResult.allBrancheCount
                    current=operationResult.coveredBrancheCount
                    postfix=i18["details.branchprogress.postfix"]
                    />
                </div>
            </div>
        </div>
        <div id="${target}" class="collapse" aria-labelledby="headingOne">
            <@branchList list=operationResult.branches />
        </div>
    </div>
</#macro>

<#macro branchList list>
    <div class="card-body">
        <table class="table table-sm">
            <thead>
            <tr>
                <th scope="col">${i18["details.branchlist.name"]}</th>
                <th scope="col">${i18["details.branchlist.details"]}</th>
            </tr>
            </thead>
            <tbody>
            <#list list as branch>
                <#assign trStyle = "table-danger">

                <#if branch.covered>
                    <#assign trStyle = "table-success">
                </#if>
                <tr class="${trStyle}">
                    <td>
                        <#if branch.covered>
                            <span>
                                <i class="fas fa-check"></i>
                            </span>
                        <#else>
                            <span>
                                <i class="fas fa-bug"></i>
                            </span>
                        </#if>
                        &nbsp;${branch.name}
                    </td>
                    <td>${branch.reason}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>