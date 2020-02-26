./gradlew clean build
rm -r -f swagger-coverage-commandline-1.0-SNAPSHOT
unzip swagger-coverage-commandline/build/distributions/swagger-coverage-commandline-1.0-SNAPSHOT.zip
./swagger-coverage-commandline-1.0-SNAPSHOT/bin/swagger-coverage-commandline -s swagger-coverage-commandline/src/test/resources/petstory.json -i swagger-coverage-commandline/src/test/resources/swagger-coverage-output --ignoreHeaders api_key