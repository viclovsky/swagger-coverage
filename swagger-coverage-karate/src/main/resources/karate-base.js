function fn(){
    var proxyPort = karate.properties['proxy.port'];
    if(proxyPort){
        karate.configure('proxy', 'http://127.0.0.1:'+ proxyPort);
    }
}