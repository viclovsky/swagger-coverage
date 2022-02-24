function fn(){
    var proxyPort = karate.properties['proxy.port'];

    

    if(proxyPort){
        karate.configure('proxy', 'http://127.0.0.1:'+ proxyPort);
    }

    var setNumber = function(arg) {
        var CR = Java.type("com.github.viclovsky.swagger.coverage.CrossRef");
        CR.INSTANCE.setNum(arg);
    } 

    var setPath = function(arg) {
        var CR = Java.type("com.github.viclovsky.swagger.coverage.CrossRef");
        CR.INSTANCE.setPath(arg);
    }

    return {
        setNumber: setNumber,
        setPath : setPath
    }
}