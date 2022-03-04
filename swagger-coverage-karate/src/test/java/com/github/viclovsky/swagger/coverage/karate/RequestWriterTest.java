package com.github.viclovsky.swagger.coverage.karate;

import static com.github.viclovsky.swagger.coverage.SwaggerCoverageConstants.OUTPUT_DIRECTORY;
import static java.util.Optional.ofNullable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.endsWith;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intuit.karate.JsonUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RequestWriterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Request request = new Request();

    @Before
    public void setUp() {
        try {
            File file = ofNullable(getClass().getClassLoader().getResource("request.json"))
                    .map(resource -> new File(resource.getFile()))
                    .orElseThrow(() -> new IllegalArgumentException("Unable to read file: request.json"));
            String requestJson = Files.readString(file.toPath());
            request = JsonUtils.fromJson(requestJson, Request.class);
        } catch (Exception e) {
            throw new RuntimeException("Can't load request json from resources folder!");
        }
    }

    @Test
    public void shouldWriteSwaggerJson() throws IOException {

        Path workingDir = folder.newFolder().toPath();
        RequestWriter writer = new RequestWriter(workingDir.toString());
        writer.write(request, false);

        assertThat(getPaths(workingDir.resolve(OUTPUT_DIRECTORY)), iterableWithSize(1));
        assertThat(getPaths(workingDir.resolve(OUTPUT_DIRECTORY)).get(0).toString(), endsWith(".json"));
    }

    @Test
    public void shouldWriteOas3Yaml() throws IOException {
        Path workingDir = folder.newFolder().toPath();
        RequestWriter writer = new RequestWriter(workingDir.toString());
        writer.write(request, true);

        assertThat(getPaths(workingDir.resolve(OUTPUT_DIRECTORY)), iterableWithSize(1));
        assertThat(getPaths(workingDir.resolve(OUTPUT_DIRECTORY)).get(0).toString(), endsWith(".yaml"));
    }

    private List<Path> getPaths(Path path) {
        try (Stream<Path> paths = Files.walk(path)) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("can't walk files in swagger output directory");
        }
    }
}
