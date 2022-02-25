function fn(){
    var proxyPort = karate.properties['proxy.port'];

    if(proxyPort){
        karate.configure('proxy', 'http://127.0.0.1:'+ proxyPort);
    }

    var setPathPattern = function(arg) {
        var SCO = Java.type("com.github.viclovsky.swagger.coverage.SwaggerCoverageOptions");
        SCO.setPath(arg);
    }

    var ignoreNextCall = function() {
        var SCO = Java.type("com.github.viclovsky.swagger.coverage.SwaggerCoverageOptions");
        SCO.setIgnoreCall(true);
    }

    return {
        scOptions : {
            setPathPattern : setPathPattern,
            ignoreNextCall : ignoreNextCall
        }
    }
}