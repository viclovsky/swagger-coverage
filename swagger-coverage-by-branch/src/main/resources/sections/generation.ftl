<#macro data statistic>
    <div class="row">
        <div class="col-12">
            Parsed result files: ${statistic.resultFileCount}<br>
            Generation time: ${statistic.generationTime} ms<br>
            Result file create interval: ${statistic.fileResultDateInterval}<br>
            Generation report date: ${statistic.generateDate}<br>
        </div>
    </div>
</#macro>