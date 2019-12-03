# swagger-coverage
Swagger-coverage gives a full picture about coverage of API regression tests built on OAS 2 (Swagger). By saying coverage we mean not a broad theme functionality, but presence (or absence) of calls defined by API methods, parameters and return codes which corresponds specification of API.

## How it work
Producing coverage report consists of two parts. Firstly, during test execution, filters/interceptors save information of calls in swagger format in specific folder on executing tests.
The next stage is to compare saved results with current API specification and builds report.  

## How it used and examples
Just add filter into test client SwaggerCoverageRestAssured. For instance, as presented below:

```java
.filter(new SwaggerCoverageRestAssured())
```
 swagger-coverage can be used via jar or binary file after running tests via swagger-coverage 

Examples: 

Important remark: swagger-coverage works fine with clients which were generated via swagger. Because all methods/parameters which will be saved are 100% compatible current API specification. 


## Output formats
For a moment it is available such output formats:
* Json swagger format with all methods etc. which isn’t covered. It is possible to build html from swagger json for visualisation.  
* Default output in simple format: list of fully covered http methods, partially covered and not covered at all.

## Requirements 
For a moment swagger-coverage  is compatible only with OpenApi specification v2. It is possible that swagger-coverage will support other versions, for instance,  swagger v1.


