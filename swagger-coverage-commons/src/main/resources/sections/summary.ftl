<#import "../ui.ftl" as ui/>

<#macro operations operationCoveredMap>
    <div class="row">
        <div class="col-12">
            <h4>${i18["summary.operations"]}</h4>
        </div>
    </div>
    <@ui.coverageBadget counter=operationCoveredMap.counter/>
</#macro>

<#macro calls data>
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                ${i18["summary.operations.all"]}: ${data.operations?size}
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-secondary" role="alert">
                ${i18["summary.operations.no_call"]}: ${data.zeroCall?size}
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-secondary" role="alert">
                ${i18["summary.operations.missed"]}: ${data.missed?size}
            </div>
        </div>
    </div>
</#macro>

<#macro branchs counter>
    <div class="row">
        <div class="col-12">
            <h4>${i18["summary.conditions"]}</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                <div class="row">
                    <div class="col-12">
                        <strong>${i18["summary.conditions.total"]}: ${counter.all}</strong>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-success" role="alert">
                <div class="row">
                    <div class="col-12">
                        <strong>${i18["summary.conditions.covered"]}: ${counter.covered * 100 / counter.all}% (${counter.covered}/${counter.all})</strong>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-danger" role="alert">
                <div class="row">
                    <div class="col-12">
                        <strong>${i18["summary.conditions.uncovered"]}:  ${(counter.all-counter.covered) * 100 / counter.all}% (${counter.all-counter.covered}/${counter.all})</strong>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <#if counter.all gt 0 >
                <@ui.progressbar current = counter.covered full = counter.all height=50/>
            </#if>
        </div>
    </div>
</#macro>

<#macro tags tagsDetail tagCounter>
    <div class="row">
        <div class="col-12">
            <h4>${i18["summary.tags"]}</h4>
        </div>
    </div>
    <@ui.coverageBadget counter=tagCounter/>
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                ${i18["summary.tags.all"]}: ${tagCounter.all}
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-secondary" role="alert">
                ${i18["summary.tags.no_call"]}: ${tagsDetail?values?filter(x -> x.callCounts = 0)?size}
            </div>
        </div>
        <div class="col-sm"></div>
    </div>
</#macro>