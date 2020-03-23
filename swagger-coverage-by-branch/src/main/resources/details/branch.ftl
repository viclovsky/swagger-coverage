<#import "operation.ftl" as operation />

<#macro list coverage operations prefix>
    <div class="accordion" id="${prefix}-accordion">
        <#list coverage as key>
            <@operation.details
            name=key
            operationResult=operations[key]
            target=prefix + "-" + key?counter
            />
        </#list>
    </div>
</#macro>