## Configuration options

Swagger coverage report can be configured by json-file. 
You can control list of branch, which be generated and checked for results.

## General configuration options

General configuration options are placed in "general" section

**pathCaseIgnore** : *true/false*. Ignore case of operation path. For example, with true value, paths "/api/store/" and "/api/STORE" will be considered the same.
Default value if *false*. 

````
{
  ...

  "general": {
    "pathCaseIgnore": false
  },

   ...
}
````

## Rules configuration options
Options for different rules are placed in "rules" section. 
You can disable some rules or change their behavior.

#### Checking response http-status
This rule create branch for every status from *responses*-section of swagger specification.
Branch mark *covered* when report generator find specific status in results files.
Options for this rules are placed in *status* subsection in *rules* sections.

You can setup next options:

**enable** - *true/false*. You can disable this rule. Default value is *true*.

**filter** - *[val1,val2]*. Rule will ignore all status, which not in filter list.

**ignore** - *[val1,val2]*. Rule will ignore all status, which in ignore list.

```` 
{
  "rules" : {
    "status": {
      "enable": true,
      "ignore": ["400","500"],
      "filter": ["200"]
    },

    ....
  },
  
  ....
}
````

#### Checking the list of declared and received statuses
This rule create branch for comparing declared and received status. 
Branch marked as *covered* when result not contains any of undeclared status.
*Uncovered* state of this branch indicates missed status in original swagger-documentation 
or server errors.
Options for this rules are placed in *only-declareted-status* subsection in *rules* sections.


You can setup next options:

**enable** - *true/false*. You can disable this rule. Default value is *true*.

````
{
  "rules" : {

    ....

    "only-declareted-status" : {
      "enable" : true
    }
  },

   ....
}
````

## Result writer configuration
Options for report generation setting are placed in *writers* sections.

#### HTML report writer
Options for html-report placed in subsection *html* of *writers* sections.

You can setup next options:

**locale** - two latter language code. Now supported only *en/ru*.

**filename** - filename for html report.

````
{
  ....

  "writers": {
      "html": {
        "locale": "ru",
        "filename":"report.html"
      }
  }
}
````
