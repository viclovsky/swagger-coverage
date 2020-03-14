<#ftl output_format="HTML">
<#-- @ftlvariable ftlvariable name="data" type="com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults" -->
<#import "operationData.ftl" as operationData/>
<#import "ui.ftl" as ui/>
<#import "sections/summary.ftl" as summary />
<#import "sections/generation.ftl" as generation>

<#macro details coverage prefix>
    <div class="accordion" id="${prefix}-accordion">
        <#list coverage as key, value>
            <div class="card">
                <div class="card-header">
                    <div class="row"
                         data-toggle="collapse"
                         data-target="#${prefix}-${key?index}"
                         aria-expanded="true"
                         aria-controls="collapseOne">
                        <div class="col-6">
                            ${key}
                        </div>
                        <div class="col-3">
                            parameters:
                        </div>
                        <div class="col-3">
                            statuses:

                        </div>
                    </div>
                </div>
                <div id="${prefix}-${key?index}" class="collapse" aria-labelledby="headingOne">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-2">
                                Parameters:
                            </div>
                            <div class="col-10">

                            </div>
                        </div>
                        <br/>
                        <div class="row">
                            <div class="col-2">
                                Statuses:
                            </div>
                            <div class="col-10">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
        <#if coverage?size == 0>
            No data...
        </#if>
    </div>
</#macro>

<#macro branchdetails coverage operations prefix>
    <div class="accordion" id="${prefix}-accordion">
        <#list coverage as key>
            <@operationDetails
            name=key
            operationResult=operations[key]
            target=prefix + "-" + key?counter
            />
        </#list>
    </div>
</#macro>

<#macro taglist tags operations>
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
                            ${tagCoverage.operations?size} operations
                        </div>
                        <div class="col-2">
                            ${tagCoverage.callCounts} calls
                        </div>
                        <div class="col-4">
                            <@ui.progress
                            full=tagCoverage.branchCounter.all
                            current=tagCoverage.branchCounter.covered
                            postfix="branches covered"
                            />
                        </div>
                    </div>
                </div>
                <div id="tag-${tag}" class="collapse" aria-labelledby="headingOne">
                    <div class="card-body">
                        <@ui.coverageBadget counter=tagCoverage.coverageCounter />
                        <#list tagCoverage.operations as operation >
                            <@operationDetails
                                name=operation
                                operationResult=operations[operation]
                                target="tag-" + tag + "-" + operation?counter
                            />
                        </#list>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</#macro>

<#macro operationDetails name operationResult target>
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
                        <span><strong>${name}</strong></span>
                    </p>
                    <span><small>${operationResult.description}</small></span>
                </div>
                <div class="col-2">
                    ${operationResult.processCount} calls
                </div>
                <div class="col-4">
                    <@ui.progress
                        full=operationResult.allBrancheCount
                        current=operationResult.coveredBrancheCount
                        postfix="branches covered"
                    />
                </div>
            </div>
        </div>
        <div id="${target}" class="collapse" aria-labelledby="headingOne">
            <@operationData.branchList list=operationResult.branches />
        </div>
    </div>
</#macro>

<head>
    <meta charset="utf-8">
    <title>Swagger Coverage</title>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/0b83173bdb.js" crossorigin="anonymous"></script>
    <style>
        .title {
            margin-top: 60px;
        }

        .progress {
            position: relative;
        }

        .progress span {
            position: absolute;
            display: block;
            width: 100%;
            color: black;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container">
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#summary">Summary</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#details">Operation details</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#tag-section">Tag details</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#branchs">Branch details</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#system">Generation info</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<main role="main" class="container">
    <div class="container">
        <section id="summary">
            <div class="row">
                <div class="col-12">
                    <h1 class="title" id="summary">Summary</h1>
                </div>
            </div>
            <@summary.operations operationCoveredMap=data.coverageOperationMap />
            <@summary.calls data=data />
            <@summary.tags tagsDetail=data.tagCoverageMap tagCounter=data.tagCounter />
            <@summary.branchs counter=data.branchCounter />
        </section>

        <section id="details">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="details">Operation details</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <ul class="nav nav-pills nav-fill" id="detail-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="branch-tab" data-toggle="tab" href="#branch" role="tab"
                               aria-controls="branch" aria-selected="true">
                                All: ${data.operations?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="full-tab" data-toggle="tab" href="#full" role="tab"
                               aria-controls="full" aria-selected="true">
                                Full: ${data.coverageOperationMap.full?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="party-tab" data-toggle="tab" href="#party" role="tab"
                               aria-controls="party" aria-selected="true">
                                Partial: ${data.coverageOperationMap.party?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="empty-tab" data-toggle="tab" href="#empty" role="tab"
                               aria-controls="empty" aria-selected="true">
                                Empty: ${data.coverageOperationMap.empty?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="zero-tab" data-toggle="tab" href="#zero" role="tab"
                               aria-controls="zero" aria-selected="true">
                                No calls: ${data.zeroCall?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="missed-tab" data-toggle="tab" href="#missed" role="tab"
                               aria-controls="missed" aria-selected="false">
                                Missed: ${data.missed?size}
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-12">
                    <div class="tab-content" id="details-content">
                        <div class="tab-pane fade show active" id="branch" role="tabpanel" aria-labelledby="branch-tab">
                            <@branchdetails
                                coverage=data.coverageOperationMap.full + data.coverageOperationMap.party + data.coverageOperationMap.empty
                                operations=data.operations
                                prefix="branch"/>
                        </div>
                        <div class="tab-pane fade" id="full" role="tabpanel" aria-labelledby="full-tab">
                            <@branchdetails coverage=data.coverageOperationMap.full operations=data.operations prefix="full"/>
                        </div>
                        <div class="tab-pane fade" id="party" role="tabpanel" aria-labelledby="party-tab">
                            <@branchdetails coverage=data.coverageOperationMap.party operations=data.operations prefix="party"/>
                        </div>
                        <div class="tab-pane fade" id="empty" role="tabpanel" aria-labelledby="empty-tab">
                            <@branchdetails coverage=data.coverageOperationMap.empty operations=data.operations prefix="empty"/>
                        </div>
                        <div class="tab-pane fade" id="zero" role="tabpanel" aria-labelledby="zero-tab">
                            <@branchdetails coverage=data.zeroCall operations=data.operations prefix="zero"/>
                        </div>
                        <div class="tab-pane fade" id="missed" role="tabpanel" aria-labelledby="missed-tab">
                            <@details coverage=data.missed prefix="missed"/>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section id="tag-section">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="tags">Tag details</h2>
                </div>
            </div>
            <@taglist tags=data.tagCoverageMap operations=data.operations/>
        </section>

        <section id="branchs">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="branchs">Branches</h2>
                </div>
            </div>
            <div class="row">
                <div class="accordion col-12" id="branches-by-type-accordion">
                    <#list data.branchStatisticsMap as key, value>
                        <div class="card">
                            <div class="card-header">
                                <div class="row"
                                     data-toggle="collapse"
                                     data-target="#branches-by-type-${key?index}"
                                     aria-expanded="true"
                                     aria-controls="collapseOne">
                                    <div class="col-8">
                                        ${key}
                                    </div>
                                    <div class="col-4">
                                        <@ui.progress
                                            full=value.allCount
                                            current=value.coveredCount
                                            postfix="branches covered"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div id="branches-by-type-${key?index}" class="collapse" aria-labelledby="headingOne">
                                <div class="card-body">
                                    <table class="table table-sm">
                                        <thead>
                                        <tr>
                                            <th scope="col">Operation name</th>
                                            <th scope="col">Branch name</th>
                                            <th scope="col">Details</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list value.coveredOperation as operation,branch>
                                            <tr class="table-success">
                                                <td>
                                                    <span>
                                                        <i class="fas fa-check"></i>
                                                    </span>
                                                    &nbsp;${operation}
                                                </td>
                                                <td>${branch.name}</td>
                                                <td>${branch.reason?no_esc}</td>
                                            </tr>
                                        </#list>
                                        <#list value.uncoveredOperation as operation,branch>
                                            <tr class="table-danger">
                                                <td>
                                                    <span>
                                                        <i class="fas fa-bug"></i>
                                                    </span>
                                                    &nbsp;${operation}
                                                </td>
                                                <td>${branch.name}</td>
                                                <td>${branch.reason?no_esc}</td>
                                            </tr>
                                        </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </section>

        <section id="system">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="system">Generation info</h2>
                </div>
            </div>
            <@generation.data statistic=data.generationStatistics/>
        </section>

        <footer class="page-footer font-small mdb-color lighten-3 pt-4">
            <div class="footer-copyright text-center py-3"></div>
        </footer>

    </div>
</main>

</body>