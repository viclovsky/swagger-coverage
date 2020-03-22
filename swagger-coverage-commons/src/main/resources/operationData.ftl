<#import "ui.ftl" as ui/>

<#macro branchList list>
    <div class="card-body">
        <table class="table table-sm">
            <thead>
            <tr>
                <th scope="col">#</th>
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
                            <@ui.success text=""/>
                        <#else>
                            <@ui.danger text=""/>
                        </#if>
                    </td>
                    <td>${branch.name}</td>
                    <td>
                        <#list branch.reasons as reason>
                            ${(reason)!}
                        </#list>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>