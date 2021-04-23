./gradlew clean -Dskip.tests build
rm -r -f swagger-coverage-commandline-1.0-SNAPSHOT
unzip swagger-coverage-commandline/build/distributions/swagger-coverage-commandline-1.0-SNAPSHOT.zip
./swagger-coverage-commandline-1.0-SNAPSHOT/bin/swagger-coverage-commandline -s swagger-coverage-commandline/src/test/resources/v3/petstory.yaml -i swagger-coverage-commandline/src/test/resources/v3/swagger-coverage-output -c swagger-coverage-commandline/src/test/resources/configuration.json