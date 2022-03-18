# swagger-coverage for Karate
This module provides an integration of swagger-coverage for the [Karate Framework](https://github.com/karatelabs/karate). With the help of a single test runner, the coverage report can thus be generated automatically after the API tests.

## How it Works
Swagger-coverage for Karate uses the [server-side features](https://github.com/karatelabs/karate/tree/master/karate-netty#karate-netty) of the Karate framework to implement a proxy server which intercepts the HTTP calls, extracts the necessary information, and then redirects the call to the original destination. For this, a local mock server is started on a free port before running the tests to host the proxy. The port number is chosen at runtime and can be accessed by calling `karate.properties['proxy.port']`.

This approach has the advantage that the tests do not have to be changed and swagger-coverage can also be integrated into existing Karate projects with minimal effort. In addition, multiple Test Runners can be used in parallel. Since you usually don't want to create a report every time you run the tests (e.g. during active test implementation), you can decide individually when to use the Coverage Test Runner.

## Setup and How to Use
The Swagger Coverage Runner requires at least [Java](https://www.oracle.com/java/technologies/downloads/) 8 and the use of either JUnit 4 or JUnit 5.  

The `swagger-coverage-karate` artifact also includes the `swagger-coverage-commons` and the `swagger-coverage-commandline` modules.
To add the dependency to your project when using Maven, add the following to your pom.xml:
```xml
<dependency>
    <groupId>com.github.viclovsky</groupId>
    <artifactId>swagger-coverage-karate</artifactId>
    <version>${latest-swagger-coverage-version}</version>
</dependency>
```

If you use Gradle instead, add this to your build.gradle file:

```gradle
testImplementation "com.github.viclovsky:swagger-coverage-karate:${latest-swagger-coverage-version}"
```


### <a name="coverageDir"></a> Coverage Directory and Naming Conventions
The easiest way to provide the Test Runner with all the necessary information is to have a folder in the project where the required files are located. If these are named correctly, as in the following, it is only necessary to specify the path to this directory.

> The files can also be specifically set in the [options](#options). If not, the coverage directory is always checked for the files seen below.

```
api-test-coverage (can be any path and name)
    |
    +-- swagger-coverage-config.json
    +-- swagger-specification.json/yaml
```

If specified, the directory will function as the working directory for the swagger-coverage tool. Therefore, not only the `swagger-coverage-output` folder is then created in there, where the generated swagger models of the HTTP calls are stored, but also the final HTML coverage report.  
If not specified, the needed file paths must specifically be provided in the arguments of the Test Runner and everything else will be generated within the current working directory (the root of the project, most of the time).

### <a name="runner"></a> The SwaggerCoverageRunner
The `SwaggerCoverageRunner` extends the [Karate Runner](https://github.com/karatelabs/karate#junit-4-parallel-execution) class. Therefore, all options which are available there, can also be used with the coverage runner. This enables an easy migration, since it is only neccesary to add the additional builder methods to provide the runner with the needed information for creating the coverage report.

For example:
```java
import java.net.URI;
import com.github.viclovsky.swagger.coverage.karate.SwaggerCoverageRunner;
import com.intuit.karate.Results;
import org.junit.jupiter.api.Test;

public class CoverageReportRunner {

    @Test
    void testAll(){
        Results results = SwaggerCoverageRunner.path("classpath:some/path")
                .coverageDir("api-test-coverage")
                .swaggerSpec(URI.create("https://petstore3.swagger.io/api/v3/openapi.json"))
                .oas3()
                .outputJunitXml(true)
                .karateEnv("dev")
                .parallel(1);
    }
}
```

#### <a name="options"></a> Available Options
| Option | Description  |
|--------|--------------|
| `.coverageDir(String)` | set the working directory for the swagger-coverage tool. See [Coverage Directory](#coverageDir). |
| `.swaggerSpec(URI)` | specifically set the path to the Swagger/OpenAPI specification. Can also be a URL to a remote spec, useful for when swagger is hosted on the test environment.|
| `.swaggerCoverageConfig(String)` | specifically set the path to the [Config File](https://github.com/viclovsky/swagger-coverage#configuration-options). |
| `.swagger()`  |  use this when the specification uses the [Swagger/OpenAPI 2.0](https://swagger.io/specification/v2/) format. |
| `.oas3()` | use this when the specification uses the [OpenAPI 3.0](https://swagger.io/specification/) format.  |
| `.backupCoverageOutput(boolean)`  | backup the `swagger-coverage-output` folder, if one exists from a previous run. Default is set to `false`. |

### Swagger Coverage Options
There are a few options you may want to decide on more flexible instead of setting them once at the start of the tests. For that reason, the `scOptions` object is provided and can be used anywhere in the Karate-context. It holds the following functionalities:

#### `scOptions.setDestUrl(arg)`
This tells the proxy server where to send the HTTP calls to. Most of the time, it is sufficient to set this once in the `karate-config.js`. But if needed, it can be changed before every single call.

> Note that if your destination URL uses HTTPS, take a look at [The Problem with HTTPS and the Workaround](#https).

#### `scOptions.setPathPattern(arg)`
One of the conditions in the coverage report checks, whether a specified param was set in the test. In order for this to happen, the parameter name must be included in the swagger representation of the HTTP call. But unlike query parameters, for example, path parameters in Karate are not specified by their name but only by their value. That's why on the server side you can only get to this parameter name in a certain way. For this the path pattern in the style of `/some/path/{paramName}` is needed. To correctly use the aforementioned condition, this option can be used to specify the needed path pattern.

For example:

```gherkin
Feature: using the 'setPathPattern' option

Background:
    * url "http://petstore3.swagger.io"
    * eval scOptions.setDestUrl("https://petstore3.swagger.io")

Scenario:
    * eval scOptions.setPathPattern("/api/v3/pet/{petId}")
    Given path "api", "v3", "pet", 2
    When method GET
    Then status 200
```

#### `scOptions.ignoreNextCall()`
Sometimes you don't want a call to be included in the coverage report, for example when reusing feature files to create entities etc. This options tells the proxy server to simply send the call to the destination URL without extracting any information and thus ignoring it for the coverage report.

## <a name="https"></a> The Problem with HTTPS and the Workaround
As of now, the karate proxy server does not support HTTPS calls, as it is unable to unpack them (more information can be found in this [thread](https://github.com/karatelabs/karate/issues/640)). Therefore, the requests must be send as HTTP calls when using the [SwaggerCoverageRunner](#runner), and then the proxy can send them to the HTTPS destination. The following example shows, how a dynamic setup can be achieved in the `karate-config.json`:

```javaScript
function fn(){
    var env = karate.env;
    karate.log('karate.env system property was:', env);
    if (!env){
        env = 'dev';
    }
    
    var uri = "://petstore3.swagger.io";
    var protocol = "https";

    // always set the destination url to use https
    scOptions.setDestUrl(protocol + uri);

    // if the port is set, that means the proxy server is used. Then use the http protocol.
    if (karate.properties['proxy.port']){
        protocol = "http";
    }

    var baseUrl = protocol + uri;

    return {
        baseUrl : baseUrl
    }
}
```