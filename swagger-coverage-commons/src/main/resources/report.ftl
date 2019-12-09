<html>
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
                            <a class="nav-link disabled" id="home-tab" data-toggle="tab" href="#home" role="tab"
                               aria-controls="home" aria-selected="true">
                                All: ${data.statistics.all}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab"
                               aria-controls="home" aria-selected="true">
                                Full: ${data.statistics.full}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
                               aria-controls="profile" aria-selected="false">
                                Partial: ${data.statistics.partial}
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab"
                               aria-controls="contact" aria-selected="false">
                                Empty: ${data.statistics.empty}
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-12">
                    <div class="tab-content" id="details-content">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <ul class="list-group list-group-flush">
                                <#list data.fullCoverage as key, value>
                                    <li class="list-group-item">
                                        <div class="row">
                                            <div class="col-6">
                                                ${key}
                                            </div>
                                            <div class="col-3">
                                                parameters:
                                                <svg class="bi bi-check-circle text-success" width="24px" height="24px"
                                                     viewBox="0 0 20 20" fill="currentColor"
                                                     xmlns="http://www.w3.org/2000/svg">
                                                    <path fill-rule="evenodd"
                                                          d="M17.354 4.646a.5.5 0 010 .708l-7 7a.5.5 0 01-.708 0l-3-3a.5.5 0 11.708-.708L10 11.293l6.646-6.647a.5.5 0 01.708 0z"
                                                          clip-rule="evenodd"></path>
                                                    <path fill-rule="evenodd"
                                                          d="M10 4.5a5.5 5.5 0 105.5 5.5.5.5 0 011 0 6.5 6.5 0 11-3.25-5.63.5.5 0 11-.5.865A5.472 5.472 0 0010 4.5z"
                                                          clip-rule="evenodd"></path>
                                                </svg>
                                                ${value.coveredParams?size}
                                            </div>
                                            <div class="col-3">
                                                statuses:
                                                <svg class="bi bi-check-circle text-success" width="24px" height="24px"
                                                     viewBox="0 0 20 20" fill="currentColor"
                                                     xmlns="http://www.w3.org/2000/svg">
                                                    <path fill-rule="evenodd"
                                                          d="M17.354 4.646a.5.5 0 010 .708l-7 7a.5.5 0 01-.708 0l-3-3a.5.5 0 11.708-.708L10 11.293l6.646-6.647a.5.5 0 01.708 0z"
                                                          clip-rule="evenodd"></path>
                                                    <path fill-rule="evenodd"
                                                          d="M10 4.5a5.5 5.5 0 105.5 5.5.5.5 0 011 0 6.5 6.5 0 11-3.25-5.63.5.5 0 11-.5.865A5.472 5.472 0 0010 4.5z"
                                                          clip-rule="evenodd"></path>
                                                </svg>
                                                ${value.coveredStatusCodes?size}
                                            </div>
                                        </div>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                        <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                            <div class="accordion" id="accordionExample">
                                <#list data.partialCoverage as key, value>
                                    <div class="card">
                                        <div class="card-header">
                                            <div class="row"
                                                 data-toggle="collapse"
                                                 data-target="#empty-${key?index}"
                                                 aria-expanded="true"
                                                 aria-controls="collapseOne">
                                                <div class="col-6">
                                                    ${key}
                                                </div>
                                                <div class="col-3">
                                                    parameters:
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
                                                    ${value.coveredParams?size}
                                                    <svg class="bi bi-alert-circle text-danger" width="20px"
                                                         height="20px"
                                                         viewBox="0 0 20 20" fill="currentColor"
                                                         xmlns="http://www.w3.org/2000/svg">
                                                        <path fill-rule="evenodd"
                                                              d="M10 17a7 7 0 100-14 7 7 0 000 14zm0 1a8 8 0 100-16 8 8 0 000 16z"
                                                              clip-rule="evenodd"></path>
                                                        <path d="M9.002 13a1 1 0 112 0 1 1 0 01-2 0zM9.1 6.995a.905.905 0 111.8 0l-.35 3.507a.553.553 0 01-1.1 0L9.1 6.995z"></path>
                                                    </svg>
                                                    ${value.params?size}
                                                </div>
                                                <div class="col-3">
                                                    statuses:
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
                                                    ${value.coveredStatusCodes?size}
                                                    <svg class="bi bi-alert-circle text-danger" width="20px"
                                                         height="20px"
                                                         viewBox="0 0 20 20" fill="currentColor"
                                                         xmlns="http://www.w3.org/2000/svg">
                                                        <path fill-rule="evenodd"
                                                              d="M10 17a7 7 0 100-14 7 7 0 000 14zm0 1a8 8 0 100-16 8 8 0 000 16z"
                                                              clip-rule="evenodd"></path>
                                                        <path d="M9.002 13a1 1 0 112 0 1 1 0 01-2 0zM9.1 6.995a.905.905 0 111.8 0l-.35 3.507a.553.553 0 01-1.1 0L9.1 6.995z"></path>
                                                    </svg>
                                                    ${value.statusCodes?size},
                                                </div>
                                            </div>
                                        </div>
                                        <div id="empty-${key?index}" class="collapse" aria-labelledby="headingOne"
                                             data-parent="#accordionExample">
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="col-2">
                                                        Parameters:
                                                    </div>
                                                    <div class="col-10">
                                                        <#list value.coveredParams as param>
                                                            <span>
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
                                                            ${param}
                                                            </span>
                                                        </#list>
                                                        <#list value.params as param>
                                                            <span>
                                                            <svg class="bi bi-alert-circle text-danger" width="20px"
                                                                 height="20px"
                                                                 viewBox="0 0 20 20" fill="currentColor"
                                                                 xmlns="http://www.w3.org/2000/svg">
                                                                <path fill-rule="evenodd"
                                                                      d="M10 17a7 7 0 100-14 7 7 0 000 14zm0 1a8 8 0 100-16 8 8 0 000 16z"
                                                                      clip-rule="evenodd"></path>
                                                                <path d="M9.002 13a1 1 0 112 0 1 1 0 01-2 0zM9.1 6.995a.905.905 0 111.8 0l-.35 3.507a.553.553 0 01-1.1 0L9.1 6.995z"></path>
                                                            </svg>
                                                            ${param}
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
                                                            ${status}
                                                            </span>
                                                        </#list>
                                                        <#list value.statusCodes as status>
                                                            <span>
                                                            <svg class="bi bi-alert-circle text-danger" width="20px"
                                                                 height="20px"
                                                                 viewBox="0 0 20 20" fill="currentColor"
                                                                 xmlns="http://www.w3.org/2000/svg">
                                                                <path fill-rule="evenodd"
                                                                      d="M10 17a7 7 0 100-14 7 7 0 000 14zm0 1a8 8 0 100-16 8 8 0 000 16z"
                                                                      clip-rule="evenodd"></path>
                                                                <path d="M9.002 13a1 1 0 112 0 1 1 0 01-2 0zM9.1 6.995a.905.905 0 111.8 0l-.35 3.507a.553.553 0 01-1.1 0L9.1 6.995z"></path>
                                                            </svg>
                                                            ${status}
                                                            </span>
                                                        </#list>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
                            <ul class="list-group list-group-flush">
                                <#list data.emptyCoverage as key, value>
                                    <li class="list-group-item">
                                        ${key}
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>

</body>
</html>
