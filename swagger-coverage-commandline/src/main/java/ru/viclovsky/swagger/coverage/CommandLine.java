package ru.viclovsky.swagger.coverage;

import com.beust.jcommander.Parameter;
import com.google.common.collect.Lists;

import java.util.List;

public class CommandLine {

    protected static final String PROGRAM_NAME = "swagger-coverage";

    @Parameter
    public List<String> parameters = Lists.newArrayList();

    public static void main(final String[] args) {
        //todo: parse args and start command
        System.out.println("hello world");
    }
}
