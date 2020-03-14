<#import "ui.ftl" as ui/>

<#macro branchList list>
    <div class="card-body">
        <table class="table table-sm">
            <thead>
            <tr>
                <th scope="col">Branch name</th>
                <th scope="col">Details</th>
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