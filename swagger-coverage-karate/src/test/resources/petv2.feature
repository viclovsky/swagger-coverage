Feature: Petstore v2

Background:
    * def baseUrl = karate.properties["baseUrl"]
    * url baseUrl
    * eval scOptions.setDestUrl(baseUrl)

Scenario: Basic Test
    * eval scOptions.setPathPattern("/pet/{id}")
    Given path "pet", 1
    When method GET
    Then status 200