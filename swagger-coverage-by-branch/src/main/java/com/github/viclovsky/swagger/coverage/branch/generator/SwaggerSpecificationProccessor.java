package com.github.viclovsky.swagger.coverage.branch.generator;

import com.github.viclovsky.swagger.coverage.branch.model.BranchOperationCoverage;
import com.github.viclovsky.swagger.coverage.branch.model.OperationsHolder;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;

import java.util.Map;
import java.util.TreeMap;

public class SwaggerSpecificationProccessor {

    public static OperationsHolder extractOperation(Swagger swagger){
        OperationsHolder operations = new OperationsHolder();

        swagger.getPaths().keySet().forEach(path
                -> swagger.getPaths().get(path).getOperationMap().forEach((httpMethod, operation)
                        -> {

                operations.addOperation(String.format("%s %s", path, httpMethod), operation);

                }
        ));
        return operations;
    }

}
