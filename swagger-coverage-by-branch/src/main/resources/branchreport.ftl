<#ftl output_format="HTML">

<#global i18=messages>

<#-- @ftlvariable ftlvariable name="data" type="com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults" -->
<#import "ui.ftl" as ui/>
<#import "sections/summary.ftl" as summary />
<#import "sections/generation.ftl" as generation />
<#import "details/operation.ftl" as operations />
<#import "details/branch.ftl" as branch />
<#import "details/tag.ftl" as tag />

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
                    <a class="nav-link" href="#summary">${i18["menu.summary"]}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#details">${i18["menu.operations"]}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#tag-section">${i18["menu.tags"]}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#branchs">${i18["menu.branch"]}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#system">${i18["menu.generation"]}</a>
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
                    <h1 class="title" id="summary">${i18["menu.summary"]}</h1>
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
                    <h2 class="title" id="details">${i18["menu.operations"]}</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <ul class="nav nav-pills nav-fill" id="detail-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="branch-tab" data-toggle="tab" href="#branch" role="tab"
                               aria-controls="branch" aria-selected="true">
                                ${i18["operations.all"]}: ${data.operations?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="full-tab" data-toggle="tab" href="#full" role="tab"
                               aria-controls="full" aria-selected="true">
                                ${i18["operations.full"]}: ${data.coverageOperationMap.full?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="party-tab" data-toggle="tab" href="#party" role="tab"
                               aria-controls="party" aria-selected="true">
                                ${i18["operations.partial"]}: ${data.coverageOperationMap.party?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="empty-tab" data-toggle="tab" href="#empty" role="tab"
                               aria-controls="empty" aria-selected="true">
                                ${i18["operations.empty"]}: ${data.coverageOperationMap.empty?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="zero-tab" data-toggle="tab" href="#zero" role="tab"
                               aria-controls="zero" aria-selected="true">
                                ${i18["operations.no_call"]}: ${data.zeroCall?size}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="missed-tab" data-toggle="tab" href="#missed" role="tab"
                               aria-controls="missed" aria-selected="false">
                                ${i18["operations.missed"]}: ${data.missed?size}
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
                            <@branch.list
                                coverage=data.coverageOperationMap.full + data.coverageOperationMap.party + data.coverageOperationMap.empty
                                operations=data.operations
                                prefix="branch"/>
                        </div>
                        <div class="tab-pane fade" id="full" role="tabpanel" aria-labelledby="full-tab">
                            <@branch.list coverage=data.coverageOperationMap.full operations=data.operations prefix="full"/>
                        </div>
                        <div class="tab-pane fade" id="party" role="tabpanel" aria-labelledby="party-tab">
                            <@branch.list coverage=data.coverageOperationMap.party operations=data.operations prefix="party"/>
                        </div>
                        <div class="tab-pane fade" id="empty" role="tabpanel" aria-labelledby="empty-tab">
                            <@branch.list coverage=data.coverageOperationMap.empty operations=data.operations prefix="empty"/>
                        </div>
                        <div class="tab-pane fade" id="zero" role="tabpanel" aria-labelledby="zero-tab">
                            <@branch.list coverage=data.zeroCall operations=data.operations prefix="zero"/>
                        </div>
                        <div class="tab-pane fade" id="missed" role="tabpanel" aria-labelledby="missed-tab">
                            <@operations.list coverage=data.missed prefix="missed"/>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section id="tag-section">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="tags">${i18["menu.tags"]}</h2>
                </div>
            </div>
            <@tag.list tags=data.tagCoverageMap operations=data.operations/>
        </section>

        <section id="branchs">
            <div class="row">
                <div class="col-12">
                    <h2 class="title" id="branchs">${i18["menu.branch"]}</h2>
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
                                        <#assign nameKey = "predicate.${key}.name">
                                        <#assign descriptionKey = "predicate.${key}.description">
                                        <p><strong>${i18[nameKey]}</strong></p>
                                        <small>${i18[descriptionKey]}</small>
                                    </div>
                                    <div class="col-4">
                                        <@ui.progress
                                            full=value.allCount
                                            current=value.coveredCount
                                            postfix=i18["details.branchprogress.postfix"]
                                        />
                                    </div>
                                </div>
                            </div>
                            <div id="branches-by-type-${key?index}" class="collapse" aria-labelledby="headingOne">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12">
                                            <ul class="nav nav-pills nav-fill" id="branch-tabs-${key?index}" role="tablist">
                                                <li class="nav-item">
                                                    <a class="nav-link active" id="tab-branch-covered-${key?index}" data-toggle="tab" href="#branch-covered-${key?index}" role="tab"
                                                       aria-controls="branch-covered-${key?index}" aria-selected="true">
                                                        ${i18["summary.branches.covered"]}: ${value.coveredOperation?size}
                                                    </a>
                                                </li>
                                                <li class="nav-item">
                                                    <a class="nav-link" id="tab-branch-uncovered-${key?index}" data-toggle="tab" href="#branch-uncovered-${key?index}" role="tab"
                                                       aria-controls="branch-uncovered-${key?index}" aria-selected="true">
                                                        ${i18["summary.branches.uncovered"]}: ${value.uncoveredOperation?size}
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="tab-content" id="details-content-${key?index}">
                                                <div class="tab-pane fade show active" id="branch-covered-${key?index}" role="tabpanel" aria-labelledby="tab-branch-covered-${key?index}">
                                                    <table class="table table-sm">
                                                        <thead>
                                                        <tr>
                                                            <th scope="col">${i18["details.branch.operation"]}</th>
                                                            <th scope="col">${i18["details.branch.branchname"]}e</th>
                                                            <th scope="col">${i18["details.branch.details"]}</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <#list value.coveredOperation as branchItem>
                                                            <tr class="table-success">
                                                                <td>
                                                                    <span>
                                                                        <i class="fas fa-check"></i>
                                                                    </span>
                                                                    &nbsp;${branchItem.operation}
                                                                </td>
                                                                <td>${branchItem.branch.name}</td>
                                                                <td>${branchItem.branch.reason?no_esc}</td>
                                                            </tr>
                                                        </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="tab-pane fade" id="branch-uncovered-${key?index}" role="tabpanel" aria-labelledby="tab-branch-uncovered-${key?index}">
                                                    <table class="table table-sm">
                                                        <thead>
                                                        <tr>
                                                            <th scope="col">${i18["details.branch.operation"]}</th>
                                                            <th scope="col">${i18["details.branch.branchname"]}e</th>
                                                            <th scope="col">${i18["details.branch.details"]}</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <#list value.uncoveredOperation as branchItem>
                                                            <tr class="table-danger">
                                                                <td>
                                                                    <span>
                                                                        <i class="fas fa-bug"></i>
                                                                    </span>
                                                                    &nbsp;${branchItem.operation}
                                                                </td>
                                                                <td>${branchItem.branch.name}</td>
                                                                <td>${branchItem.branch.reason?no_esc}</td>
                                                            </tr>
                                                        </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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
                    <h2 class="title" id="system">${i18["menu.generation"]}</h2>
                </div>
            </div>
            <@generation.data statistic=data.generationStatistics/>
            <div class="row">
                <div class="col-12">
                    <h4>${i18["generation.configuration"]}</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="alert alert-secondary" role="alert">
                        <pre>${data.prettyConfiguration} </pre>
                    </div>
                </div>
            </div>
        </section>

        <footer class="page-footer font-small mdb-color lighten-3 pt-4">
            <div class="footer-copyright text-center py-3"></div>
        </footer>

    </div>
</main>

</body>