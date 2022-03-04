function fn(){
    var proxyPort = karate.properties['proxy.port'];

    if(proxyPort){
        karate.configure('proxy', 'http://127.0.0.1:'+ proxyPort);
    }

    var setDestUrl = function(arg) {
        var SCO = Java.type("com.github.viclovsky.swagger.coverage.karate.SwaggerCoverageOptions");
        SCO.setDestinationURL(arg);
    }

    var setPathPattern = function(arg) {
        var SCO = Java.type("com.github.viclovsky.swagger.coverage.karate.SwaggerCoverageOptions");
        SCO.setPathPattern(arg);
    }

    var ignoreNextCall = function() {
        var SCO = Java.type("com.github.viclovsky.swagger.coverage.karate.SwaggerCoverageOptions");
        SCO.setIgnoreCall(true);
    }

    return {
        scOptions : {
            setDestUrl : setDestUrl,
            setPathPattern : setPathPattern,
            ignoreNextCall : ignoreNextCall
        }
    }
}