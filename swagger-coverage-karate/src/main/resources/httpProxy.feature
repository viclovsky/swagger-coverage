@ignore
Feature: Http Proxy for Swagger-Coverage

Background:
    * print 'using oas3:', oas3
    * print 'working Dir:', workingDir

    * def initWriter = 
    """
        function(dir){
            var RequestWriter = Java.type('com.github.viclovsky.swagger.coverage.karate.RequestWriter');
            return new RequestWriter(dir);
        }
    """
    * def writer = callonce initWriter workingDir
    * def scOptions = Java.type("com.github.viclovsky.swagger.coverage.karate.SwaggerCoverageOptions");

Scenario: scOptions.getIgnoreCall()
    * print "Call ignored for Swagger Coverage Report."
    * karate.proceed(scOptions.getDestinationURL())
    * scOptions.reset()

Scenario:
    * karate.proceed(scOptions.getDestinationURL())
    
    * def pathParams = pathMatches(scOptions.getPathPattern()) ? pathParams : null
    
    * def multipart = karate.get('requestParts', null)
    * if (multipart != null) karate.remove("multipart", ".[*].value")

    * def reqJson = 
    """
        {
            baseUrl: '#(requestUrlBase)',
            path: '#(requestUri)',
            requestParams: '#(requestParams)',
            headerParams: '#(requestHeaders)',
            responseHeaders: '#(responseHeaders)',
            requestParts: '#(multipart)',
            pathParams: '#(pathParams)',
            method: '#(requestMethod)',
            statusCode: '#(responseStatus)',
            hasBody: '#(request != null)'
        }
    """

    * def coverageRequest = karate.toBean(reqJson, 'com.github.viclovsky.swagger.coverage.karate.Request')
    * eval writer.write(coverageRequest, oas3)
    * scOptions.reset()
