<#import "../ui.ftl" as ui/>

<#macro operations operationCoveredMap>
    <div class="row">
        <div class="col-12">
            <h4>${i18["summary.operations"]}</h4>
        </div>
    </div>
    <@ui.coverageBadget counter=operationCoveredMap.counter />
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
            <h4>${i18["summary.branches"]}</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                <div class="row">
                    <div class="col-4">
                        <strong>${i18["summary.branches.total"]}</strong>
                    </div>
                    <div class="col-4">
                        <strong>${counter.all}</strong>
                    </div>
                    <div class="col-4">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-success" role="alert">
                <div class="row">
                    <div class="col-4">
                        <strong>${i18["summary.branches.covered"]}</strong>
                    </div>
                    <div class="col-4">
                        <strong> ${counter.covered * 100 / counter.all}%</strong>
                    </div>
                    <div class="col-4">
                        ${counter.covered} of ${counter.all}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-danger" role="alert">
                <div class="row">
                    <div class="col-4">
                        <strong>${i18["summary.branches.uncovered"]}</strong>
                    </div>
                    <div class="col-4">
                        <strong> ${(counter.all-counter.covered) * 100 / counter.all}%</strong>
                    </div>
                    <div class="col-4">
                        ${counter.all-counter.covered} of ${counter.all}
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
    <@ui.coverageBadget counter=tagCounter />
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