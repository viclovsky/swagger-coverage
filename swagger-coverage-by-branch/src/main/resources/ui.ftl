<#macro success text>
    <svg class="bi bi-check-circle text-success" width="20px"
         height="20px"
         viewBox="0 0 20 20" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd"
              d="M17.354 4.646a.5.5 0 010 .708l-7 7a.5.5 0 01-.708 0l-3-3a.5.5 0 11.708-.708L10 11.293l6.646-6.647a.5.5 0 01.708 0z"
              clip-rule="evenodd"></path>
        <path fill-rule="evenodd"
              d="M10 4.5a5.5 5.5 0 105.5 5.5.5.5 0 011 0 6.5 6.5 0 11-3.25-5.63.5.5 0 11-.5.865A5.472 5.472 0 0010 4.5z"
              clip-rule="evenodd"></path>
    </svg>
    ${text}
</#macro>
<#macro danger text>
    <svg class="bi bi-alert-circle text-danger" width="20px"
         height="20px"
         viewBox="0 0 20 20" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd"
              d="M10 17a7 7 0 100-14 7 7 0 000 14zm0 1a8 8 0 100-16 8 8 0 000 16z"
              clip-rule="evenodd"></path>
        <path d="M9.002 13a1 1 0 112 0 1 1 0 01-2 0zM9.1 6.995a.905.905 0 111.8 0l-.35 3.507a.553.553 0 01-1.1 0L9.1 6.995z"></path>
    </svg>
    ${text}
</#macro>
<#macro question text>
    <svg class="bi bi-question alert-light" width="20px"
         height="20px"
         viewBox="0 0 20 20" fill="currentColor"
         xmlns="http://www.w3.org/2000/svg">
        <path fill-rule="evenodd"
              d="M10 17a7 7 0 100-14 7 7 0 000 14zm8-7a8 8 0 11-16 0 8 8 0 0116 0z"
              clip-rule="evenodd"></path>
        <path d="M7.25 8.033h1.32c0-.781.458-1.384 1.36-1.384.685 0 1.313.343 1.313 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.007.463h1.307v-.355c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.326 0-2.786.647-2.754 2.533zm1.562 5.516c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94z"></path>
    </svg>
    ${text}
</#macro>
<#macro progress current full>
    <#assign percentValue = 100*current/(full)>
    <#assign bgStyle = "bg-danger">

    <#if percentValue gt 33>
        <#assign bgStyle = "bg-warning">
    </#if>

    <#if percentValue gt 66>
        <#assign bgStyle = "bg-success">
    </#if>

    <div class="progress">
        <div
                class="progress-bar ${bgStyle}" role="progressbar"
                style="width: ${percentValue}%"
                aria-valuenow="${current}"
                aria-valuemin="0"
                aria-valuemax="${full}">
        </div>
    </div>
</#macro>