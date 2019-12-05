package ru.viclovsky.swagger.coverage.core.filter;

import io.swagger.models.Operation;
import org.apache.log4j.Logger;
import ru.viclovsky.swagger.coverage.model.OperationCoverage;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.apache.http.HttpStatus.SC_OK;

public class StatusOkFilter implements SwaggerCoverageFilter {

    private final static Logger LOGGER = Logger.getLogger(StatusOkFilter.class);

    @Override
    public void apply(OperationCoverage operation) {
        Operation op = operation.getOperation();

        if (Objects.nonNull(op.getResponses())) {
           Set<String> statusCodes = op.getResponses().keySet().stream()
                    .filter(not200Ok()).collect(Collectors.toSet());

            statusCodes.forEach(s -> {
                LOGGER.debug(String.format("Remove status code: [%s]", s));
                op.getResponses().remove(s);
                operation.addIgnoredStatusCode(s);
            });
        }
    }

    private static Predicate<String> not200Ok() {
        return status -> !status.matches(valueOf(SC_OK));
    }
}
