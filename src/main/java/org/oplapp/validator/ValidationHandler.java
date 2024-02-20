package org.oplapp.validator;

import jakarta.validation.*;
import org.oplapp.exceptions.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * An instance of this class serves as a generic object validator.
 * @param <T> the given datatype
 */
@Component
public class ValidationHandler<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();


    /**
     * This method calls the actual validator, extracts error messages if violations occurred and
     * throws an InvalidInstanceException.
     * @param objToValidate the given object
     */
    public void handleValidation(final T objToValidate) {
        final Set<ConstraintViolation<T>> violations = validator.validate(objToValidate);
        if (!violations.isEmpty()) {
            final Set<String> errorMessages = violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new InvalidInstanceException(errorMessages);
        }
    }
}
