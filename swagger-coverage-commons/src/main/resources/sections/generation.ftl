<#macro data statistic>
    <div class="row">
        <div class="col-sm">
            <div class="col-12">
                ${i18["generation.parsed_file_count"]}: ${statistic.resultFileCount}<br>
                ${i18["generation.time"]}: ${statistic.generationTime} ms<br>
                ${i18["generation.result_file_created_interval"]}: ${statistic.fileResultDateInterval}<br>
                ${i18["generation.report.date"]}: ${statistic.generateDate}<br>
            </div>
        </div>
    </div>
</#macro>