<#ftl output_format="HTML">
<#-- @ftlvariable ftlvariable name="data" type="com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults" -->
<#import "operationData.ftl" as operationData/>
<#import "ui.ftl" as ui/>

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

<#macro branchdetails coverage prefix>
    <div class="accordion" id="${prefix}-accordion">
        <#list coverage as key, value>
            <div class="card">
                <div class="card-header">
                    <div class="row"
                         data-toggle="collapse"
                         data-target="#${prefix}-${key?index}"
                         aria-expanded="true"
                         aria-controls="collapseOne">
                        <div class="col-4">
                            ${key}
                        </div>
                        <div class="col-2">
                            ${value.processCount} calls
                        </div>
                        <div class="col-2">
                            ${value.coveredBrancheCount}/${value.allBrancheCount}
                        </div>
                        <div class="col-2">
                            <#if value.branches?size gt 0 >
                                (${100*value.coveredBrancheCount/(value.allBrancheCount)}%)
                            <#else>
                                (--)
                            </#if>
                        </div>
                        <div class="col-2">
                            <#if value.branches?size gt 0 >
                                <@ui.progress current = value.coveredBrancheCount full = value.allBrancheCount />
                            </#if>
                        </div>
                    </div>
                </div>
                <div id="${prefix}-${key?index}" class="collapse" aria-labelledby="headingOne">
                    <@operationData.branchList list=value.branches />
                </div>
            </div>
        </#list>
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
    <style>
        .title {
            margin-top: 55px;
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
                    <h1 class="title" id="summary">Swagger Coverage</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <div class="alert alert-success" role="alert">
                        Full coverage: ${data.fullOperationCount * 100 / data.allOperationCount}%
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-warning" role="alert">
                        Partial coverage: ${data.partOperationCount * 100 / data.allOperationCount}%
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-danger" role="alert">
                        Empty coverage: ${data.emptyOperationCount * 100 / data.allOperationCount}%
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <div class="alert alert-primary" role="alert">
                        All: ${data.allOperationCount}
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-secondary" role="alert">
                        No calls: ${data.zeroCall?size}
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-secondary" role="alert">
                        Missed: ${data.missed?size}
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-2">
                    Branches covered:
                </div>
                <div class="col-2">
                    ${data.coveredBrancheCount} / ${data.allBrancheCount}
                </div>
                <div class="col-2">
                    <#if data.allBrancheCount gt 0 >
                        (${100*data.coveredBrancheCount/(data.allBrancheCount)}%)
                    <#else>
                        (--)
                    </#if>
                </div>
                <div class="col-6">
                    <#if data.allBrancheCount gt 0 >
                        <@ui.progress current = data.coveredBrancheCount full = data.allBrancheCount />
                    </#if>
                </div>
            </div>
        </section>
        <section id="details">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="details">Coverage Details</h2>
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
                                Full: ${data.full?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="party-tab" data-toggle="tab" href="#party" role="tab"
                               aria-controls="party" aria-selected="true">
                                Partial: ${data.party?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="empty-tab" data-toggle="tab" href="#empty" role="tab"
                               aria-controls="empty" aria-selected="true">
                                Empty: ${data.empty?size}
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
                            <@branchdetails coverage=data.operations  prefix="branch"/>
                        </div>
                        <div class="tab-pane fade" id="full" role="tabpanel" aria-labelledby="full-tab">
                            <@branchdetails coverage=data.full  prefix="full"/>
                        </div>
                        <div class="tab-pane fade" id="party" role="tabpanel" aria-labelledby="party-tab">
                            <@branchdetails coverage=data.party  prefix="party"/>
                        </div>
                        <div class="tab-pane fade" id="empty" role="tabpanel" aria-labelledby="empty-tab">
                            <@branchdetails coverage=data.empty  prefix="empty"/>
                        </div>
                        <div class="tab-pane fade" id="zero" role="tabpanel" aria-labelledby="zero-tab">
                            <@branchdetails coverage=data.zeroCall  prefix="zero"/>
                        </div>
                        <div class="tab-pane fade" id="missed" role="tabpanel" aria-labelledby="missed-tab">
                            <@details coverage=data.missed prefix="missed"/>
                        </div>
                    </div>
                </div>
            </div>
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
                                    <div class="col-5">
                                        ${key}
                                    </div>
                                    <div class="col-2">
                                        ${value.coveredCount}/${value.allCount}
                                    </div>
                                    <div class="col-2">
                                        <#if value.allCount gt 0 >
                                            (${100*value.coveredCount/(value.allCount)}%)
                                        <#else>
                                            (--)
                                        </#if>
                                    </div>
                                    <div class="col-2">
                                        <#if value.allCount gt 0 >
                                            <@ui.progress current = value.coveredCount full = value.allCount />
                                        </#if>
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
                                                <td><@ui.success text=operation/></td>
                                                <td>${branch.name}</td>
                                                <td>${branch.reason?no_esc}</td>
                                            </tr>
                                        </#list>
                                        <#list value.uncoveredOperation as operation,branch>
                                            <tr class="table-danger">
                                                <td><@ui.danger text=operation/></td>
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
            <div class="row">
                <div class="col-12">
                    Parsed result files: ${data.generationStatistics.resultFileCount}<br>
                    Generation time: ${data.generationStatistics.generationTime} ms<br>
                    Result file create interval: ${data.generationStatistics.fileResultDateInterval}<br>
                    Generation report date: ${data.generationStatistics.generateDate}<br>
                </div>
            </div>
        </section>

        <footer class="page-footer font-small mdb-color lighten-3 pt-4">
            <div class="footer-copyright text-center py-3"></div>
        </footer>

    </div>
</main>

</body>