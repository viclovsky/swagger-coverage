<#ftl output_format="HTML">

<#global i18=messages>
<#global operationMap=data.flatOperations>

<#-- @ftlvariable ftlvariable name="data" type="com.github.viclovsky.swagger.coverage.model.SwaggerCoverageResults" -->

<head>
    <meta charset="utf-8">
    <title>Swagger Coverage: CUSTOM_TEST_REPORT</title>
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
            <a class="navbar-brand" href="#">${data.info.getTitle()!} ${data.info.getVersion()!}</a>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#tag-section">${i18["menu.tags"]}</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<main role="main" class="container">
    <div class="container">
        <section id="condition-section">
            <div class="row">
                <div class="accordion col-12" id="conditions-by-type-accordion">
                    <#list data.conditionStatisticsMap as key, value>
                        <div class="card">
                            <div class="card-header">
                                <div class="row"
                                     data-toggle="collapse"
                                     data-target="#conditions-by-type-${key?index}"
                                     aria-expanded="true"
                                     aria-controls="collapseOne">
                                    <div class="col-8">
                                        <#assign nameKey = "predicate.${key}.name">
                                        <#assign descriptionKey = "predicate.${key}.description">
                                        <p><strong>${i18[nameKey]!nameKey}</strong></p>
                                        <small>${i18[descriptionKey]!descriptionKey}</small>
                                    </div>
                                </div>
                            </div>
                            <div id="conditions-by-type-${key?index}" class="collapse" aria-labelledby="headingOne">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="tab-content" id="details-content-${key?index}">
                                                <div class="tab-pane fade show active" id="condition-covered-${key?index}" role="tabpanel" aria-labelledby="tab-condition-covered-${key?index}">
                                                    <table class="table table-sm">
                                                        <thead>
                                                        <tr>
                                                            <th scope="col">${i18["details.condition.conditionname"]}e</th>
                                                            <th scope="col">${i18["details.condition.details"]}</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <#list value.coveredOperation as conditionItem>
                                                            <tr class="table-success">
                                                                <td>
                                                                    <span>
                                                                        <i class="fas fa-check"></i>
                                                                    </span>
                                                                    &nbsp;${conditionItem.operation}
                                                                </td>
                                                                <td>${conditionItem.condition.name}</td>
                                                                <td>${conditionItem.condition.reason?no_esc}</td>
                                                            </tr>
                                                        </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="tab-pane fade" id="condition-uncovered-${key?index}" role="tabpanel" aria-labelledby="tab-condition-uncovered-${key?index}">
                                                    <table class="table table-sm">
                                                        <thead>
                                                        <tr>
                                                            <th scope="col">${i18["details.condition.operation"]}</th>
                                                            <th scope="col">${i18["details.condition.conditionname"]}e</th>
                                                            <th scope="col">${i18["details.condition.details"]}</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <#list value.uncoveredOperation as conditionItem>
                                                            <tr class="table-danger">
                                                                <td>
                                                                    <span>
                                                                        <i class="fas fa-bug"></i>
                                                                    </span>
                                                                    &nbsp;${conditionItem.operation}
                                                                </td>
                                                                <td>${conditionItem.condition.name}</td>
                                                                <td>${conditionItem.condition.reason?no_esc}</td>
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

        <section id="system-section">
            <div class="row">
                <div class="col-12">
                    <div class="col-sm">
                        <div class="alert alert-secondary" role="alert">
                            <pre>${data.prettyConfiguration} </pre>
                        </div>
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
