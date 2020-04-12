<#import "ui.ftl" as ui/>

<#macro conditionList list>
    <div class="card-body">
        <table class="table table-sm">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Condition</th>
                <th scope="col">Details</th>
            </tr>
            </thead>
            <tbody>
            <#list list as condition>
                <#assign trStyle = "table-danger">

                <#if condition.covered>
                    <#assign trStyle = "table-success">
                </#if>
                <tr class="${trStyle}">
                    <td>
                        <#if condition.covered>
                            <@ui.success text=""/>
                        <#else>
                            <@ui.danger text=""/>
                        </#if>
                    </td>
                    <td>${condition.name}</td>
                    <td>${condition.getReason()}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>