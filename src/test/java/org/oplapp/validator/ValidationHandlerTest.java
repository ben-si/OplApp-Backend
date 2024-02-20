package org.oplapp.validator;

import jakarta.validation.constraints.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.oplapp.exceptions.*;

import org.springframework.test.context.junit.jupiter.*;


import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ValidationHandlerTest {

    @InjectMocks
    private ValidationHandler<InstanceToValidate> validationHandler;

    @Test
    void handleValidation_ShouldThrowAnInvalidInstanceExceptionWhenViolationsExist() {
        // Arrange
        final InstanceToValidate instanceToValidate = new InstanceToValidate("a", 10_000, -1, null);

        // Act and Assert
        assertThrows(InvalidInstanceException.class, () -> validationHandler.handleValidation(instanceToValidate));
    }


    private record InstanceToValidate(@Size(min = 2) String property1, @Min(value = 0) @Max(value = 9999) int property2,
                                      @PositiveOrZero int property3, @NotNull String property4) {
    }
}
