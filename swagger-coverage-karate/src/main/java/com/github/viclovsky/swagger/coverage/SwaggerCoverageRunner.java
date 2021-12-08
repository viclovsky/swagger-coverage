package com.github.viclovsky.swagger.coverage;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.github.viclovsky.swagger.coverage.core.generator.Generator;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.core.MockServer;
import com.intuit.karate.job.JobConfig;

public class SwaggerCoverageRunner extends Runner {

    private static MockServer mockServer;

    private static int startProxy(Map<String, Object> args){
        mockServer = MockServer
                    .feature("classpath:httpProxy.feature")
                    .args(args)
                    .http(0).build();

        return mockServer.getPort();
    }

    private static void stopProxy(){
        if (mockServer != null){
            mockServer.stop();
        }
    }

    public static class SwaggerCoverageBuilder extends Builder<SwaggerCoverageBuilder> {
        final String SPECIFICATION_NAME = "swagger-specification";
        final String CONFIG_NAME = "swagger-coverage-config.json";

        Boolean oas3;
        String destUrl;
        String specificationPath;
        String configPath;
        String inputPath;
        String coverageDir;

        private void prepareTests(){
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("oas3", oas3);
            args.put("destUrl", destUrl);
            args.put("workingDir", coverageDir);
            int proxyPort = startProxy(args);
            systemProperty("proxy.port", proxyPort + "");
        }
        
        private void GenerateReport(){
            if (coverageDir == null) coverageDir = "";

            Generator generator = new Generator()
                .setInputPath(Path.of(coverageDir, SwaggerCoverageConstants.OUTPUT_DIRECTORY));
            
            if (specificationPath != null) {
                generator.setSpecPath(Path.of(coverageDir, specificationPath));
            }
            else{
                File specFile = Optional.of(Path.of(coverageDir, SPECIFICATION_NAME + ".json").toFile())
                        .filter((file) -> file.exists())
                        .or(()-> Optional.of(Path.of(coverageDir, SPECIFICATION_NAME + ".yaml").toFile()))
                        .filter((file) -> file.exists())
                        .orElseThrow(() -> new NoSuchElementException());

                generator.setSpecPath(specFile.toPath()); 
            }

            if (configPath != null){
                generator.setConfigurationPath(Path.of(coverageDir, configPath));
            }
            else{
                File configFile = Path.of(coverageDir, CONFIG_NAME).toFile();
                if (configFile.exists()){
                    generator.setConfigurationPath(configFile.toPath());
                }
            }

            generator.run();
        }

        public SwaggerCoverageBuilder swagger(){
            oas3 = false;
            return this;
        }

        public SwaggerCoverageBuilder oas3(){
            oas3 = true;
            return this;
        }

        public SwaggerCoverageBuilder destUrl(String destUrl){
            this.destUrl = destUrl;
            return this;
        }

        public SwaggerCoverageBuilder inputDir(String path){
            inputPath = path;
            return this;
        }

        public SwaggerCoverageBuilder swaggerSpec(String path){
            specificationPath = path;
            return this;
        }

        public SwaggerCoverageBuilder swaggerCoverageConfig(String configPath){
            this.configPath = configPath;
            return this; 
        }

        public SwaggerCoverageBuilder coverageDir(String coverageDir){
            this.coverageDir = coverageDir;
            return this;
        }

        @Override
        public Results parallel(int threadCount){
            prepareTests();
            Results results = super.parallel(threadCount);
            GenerateReport();
            stopProxy();
            return results;
        }

        @Override
        public Results jobManager(JobConfig value){
            prepareTests();
            Results results = super.jobManager(value);
            GenerateReport();
            stopProxy();
            return results;
        }
    }

    
    public static SwaggerCoverageBuilder path(String... paths) {
        SwaggerCoverageBuilder builder = new SwaggerCoverageBuilder();
        return builder.path(paths);
    }

    public static SwaggerCoverageBuilder path(List<String> paths) {
        SwaggerCoverageBuilder builder = new SwaggerCoverageBuilder();
        return builder.path(paths);
    }

    public static SwaggerCoverageBuilder builder() {
        return new SwaggerCoverageBuilder();
    }
}
