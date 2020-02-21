<#ftl output_format="HTML">
<#-- @ftlvariable ftlvariable name="data" type="com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults" -->
<#macro success text>
    <svg class="bi bi-check-circle text-success" width="24px"
         height="24px"
         viewBox="0 0 20 20" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd"
              d="M17.354 4.646a.5.5 0 010 .708l-7 7a.5.5 0 01-.708 0l-3-3a.5.5 0 11.708-.708L10 11.293l6.646-6.647a.5.5 0 01.708 0z"
              clip-rule="evenodd"></path>
        <path fill-rule="evenodd"
              d="M10 4.5a5.5 5.5 0 105.5 5.5.5.5 0 011 0 6.5 6.5 0 11-3.25-5.63.5.5 0 11-.5.865A5.472 5.472 0 0010 4.5z"
              clip-rule="evenodd"></path>
    </svg>
    ${text}
</#macro>
<#macro danger text>
    <svg class="bi bi-alert-circle text-danger" width="20px"
         height="20px"
         viewBox="0 0 20 20" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd"
              d="M10 17a7 7 0 100-14 7 7 0 000 14zm0 1a8 8 0 100-16 8 8 0 000 16z"
              clip-rule="evenodd"></path>
        <path d="M9.002 13a1 1 0 112 0 1 1 0 01-2 0zM9.1 6.995a.905.905 0 111.8 0l-.35 3.507a.553.553 0 01-1.1 0L9.1 6.995z"></path>
    </svg>
    ${text}
</#macro>
<#macro question text>
    <svg class="bi bi-question alert-light" width="20px"
         height="20px"
         viewBox="0 0 20 20" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd"
              d="M10 17a7 7 0 100-14 7 7 0 000 14zm8-7a8 8 0 11-16 0 8 8 0 0116 0z"
              clip-rule="evenodd"></path>
        <path d="M7.25 8.033h1.32c0-.781.458-1.384 1.36-1.384.685 0 1.313.343 1.313 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.007.463h1.307v-.355c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.326 0-2.786.647-2.754 2.533zm1.562 5.516c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94z"></path>
    </svg>
    ${text}
</#macro>
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
                            <#if value.coveredParams?size gt 0>
                                <@success text=value.coveredParams?size/>
                            </#if>
                            <#if value.params?size gt 0 >
                                <@danger text=value.params?size/>
                            </#if>
                            <#if value.ignoredParams?size gt 0 >
                                <@question text=value.ignoredParams?size/>
                            </#if>
                        </div>
                        <div class="col-3">
                            statuses:
                            <#if value.coveredStatusCodes?size gt 0>
                                <@success text=value.coveredStatusCodes?size/>
                            </#if>
                            <#if value.statusCodes?size gt 0 >
                                <@danger text=value.statusCodes?size/>
                            </#if>
                            <#if value.ignoredStatusCodes?size gt 0>
                                <@question text=value.ignoredStatusCodes?size/>
                            </#if>
                        </div>
                    </div>
                </div>
                <div id="${prefix}-${key?index}" class="collapse" aria-labelledby="headingOne"
                     data-parent="#${prefix}-accordion">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-2">
                                Parameters:
                            </div>
                            <div class="col-10">
                                <#list value.coveredParams as param>
                                    <span>
                                        <@success text = param/>
                                    </span>
                                </#list>
                                <#list value.params as param>
                                    <span>
                                         <@danger text = param/>
                                    </span>
                                </#list>
                                <#list value.ignoredParams as param>
                                    <span>
                                         <@question text = param/>
                                    </span>
                                </#list>
                            </div>
                        </div>
                        <br/>
                        <div class="row">
                            <div class="col-2">
                                Statuses:
                            </div>
                            <div class="col-10">
                                <#list value.coveredStatusCodes as status>
                                    <span>
                                        <@success text = status/>
                                    </span>
                                </#list>
                                <#list value.statusCodes as status>
                                    <span>
                                        <@danger text = status/>
                                    </span>
                                </#list>
                                <#list value.ignoredStatusCodes as status>
                                    <span>
                                         <@question text = status/>
                                    </span>
                                </#list>
                            </div>
                        </div>
                    </div>
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
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div class="content">
    <div class="container">
        <section id="summary">
            <div class="row">
                <div class="col-12">
                    <h1 class="title">Swagger Coverage</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <div class="alert alert-primary" role="alert">
                        All: ${data.statistics.all}
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-success" role="alert">
                        Full: ${data.statistics.full * 100 / data.statistics.all}%
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-warning" role="alert">
                        Partial: ${data.statistics.partial * 100 / data.statistics.all}%
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-danger" role="alert">
                        Empty: ${data.statistics.empty * 100 / data.statistics.all}%
                    </div>
                </div>
                <div class="col-sm">
                    <div class="alert alert-secondary" role="alert">
                        Missed: ${data.statistics.missed}
                    </div>
                </div>
            </div>
        </section>
        <section id="details">
            <div class="row">
                <div class="col-12">
                    <h2 class="title">Coverage Details</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <ul class="nav nav-pills nav-fill" id="detail-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" id="all-tab" data-toggle="tab" href="#all" role="tab"
                               aria-controls="all" aria-selected="true">
                                All: ${data.statistics.all}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="full-tab" data-toggle="tab" href="#full" role="tab"
                               aria-controls="full" aria-selected="false">
                                Full: ${data.statistics.full}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="partial-tab" data-toggle="tab" href="#partial" role="tab"
                               aria-controls="partial" aria-selected="false">
                                Partial: ${data.statistics.partial}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="empty-tab" data-toggle="tab" href="#empty" role="tab"
                               aria-controls="empty" aria-selected="false">
                                Empty: ${data.statistics.empty}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="missed-tab" data-toggle="tab" href="#missed" role="tab"
                               aria-controls="missed" aria-selected="false">
                                Missed: ${data.statistics.missed}
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-12">
                    <div class="tab-content" id="details-content">
                        <div class="tab-pane fade show active" id="all" role="tabpanel" aria-labelledby="all-tab">
                            <@details coverage=data.fullCoverage prefix="all-full"/>
                            <@details coverage=data.partialCoverage prefix="all-partial"/>
                            <@details coverage=data.emptyCoverage prefix="all-empty"/>
                        </div>
                        <div class="tab-pane fade" id="full" role="tabpanel" aria-labelledby="full-tab">
                            <@details coverage=data.fullCoverage  prefix="full"/>
                        </div>
                        <div class="tab-pane fade" id="partial" role="tabpanel" aria-labelledby="partial-tab">
                            <@details coverage=data.partialCoverage prefix="partial"/>
                        </div>
                        <div class="tab-pane fade" id="empty" role="tabpanel" aria-labelledby="empty-tab">
                            <@details coverage=data.emptyCoverage prefix="empty"/>
                        </div>
                        <div class="tab-pane fade" id="missed" role="tabpanel" aria-labelledby="missed-tab">
                            <@details coverage=data.missedCoverage prefix="missed"/>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>

</body>