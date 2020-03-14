<#import "../ui.ftl" as ui/>

<#macro operations operationCoveredMap>
    <div class="row">
        <div class="col-12">
            <h4>Operation coverage summary</h4>
        </div>
    </div>
    <@ui.coverageBadget counter=operationCoveredMap.counter />
</#macro>

<#macro calls data>
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                All operation: ${data.operations?size}
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-secondary" role="alert">
                Operation without calls: ${data.zeroCall?size}
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-secondary" role="alert">
                Missed request: ${data.missed?size}
            </div>
        </div>
    </div>
</#macro>

<#macro branchs counter>
    <div class="row">
        <div class="col-12">
            <h4>Branches coverage summary</h4>
        </div>
    </div>
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                <div class="row">
                    <div class="col-4">
                        <strong>Total</strong>
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
                        <strong>Covered</strong>
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
                        <strong>Uncovered</strong>
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
            <h4>Tags coverage summary</h4>
        </div>
    </div>
    <@ui.coverageBadget counter=tagCounter />
    <div class="row">
        <div class="col-sm">
            <div class="alert alert-primary" role="alert">
                All tags: ${tagCounter.all}
            </div>
        </div>
        <div class="col-sm">
            <div class="alert alert-secondary" role="alert">
                Tag without calls: ${tagsDetail?values?filter(x -> x.callCounts = 0)?size}
            </div>
        </div>
        <div class="col-sm"></div>
    </div>
</#macro>