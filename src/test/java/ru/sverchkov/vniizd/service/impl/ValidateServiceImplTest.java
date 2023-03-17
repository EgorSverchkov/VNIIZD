package ru.sverchkov.vniizd.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sverchkov.vniizd.dto.RequestDto;
import ru.sverchkov.vniizd.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidateServiceImplTest {
    private final ValidateServiceImpl validateService = new ValidateServiceImpl();

    @Test
    void testValidateResult_InputInfinity_WaitBadRequestException(){
        Double testDoubleNegativeInfinity = Double.NEGATIVE_INFINITY;
        Double testDoublePositiveInfinity = Double.POSITIVE_INFINITY;

        BadRequestException badRequestExceptionNegativeException = assertThrows(BadRequestException.class
                , () -> validateService.validateResult(testDoubleNegativeInfinity));
        BadRequestException badRequestExceptionPositiveException = assertThrows(BadRequestException.class
                , () -> validateService.validateResult(testDoublePositiveInfinity));

        assertEquals("На ноль делить нельзя", badRequestExceptionPositiveException.getMessage());
        assertEquals("На ноль делить нельзя", badRequestExceptionNegativeException.getMessage());

    }

    @Test
    void testValidateResult_InputNan_WaitBadRequestException(){
        Double testDoubleNaN = Double.NaN;

        BadRequestException badRequestNaNException = assertThrows(BadRequestException.class
                , () -> validateService.validateResult(testDoubleNaN));

        assertEquals("Произошла ошибка повторите попытку", badRequestNaNException.getMessage());

    }

    @Test
    void testValidateRequest_InputNull_WaitBadRequestException(){
        RequestDto testRequestDtoEqualsNull = null;

        BadRequestException badRequestNullException = assertThrows(BadRequestException.class
                , () -> validateService.validateRequest(testRequestDtoEqualsNull));

        assertEquals("Вы ничего не отправили", badRequestNullException.getMessage());
    }

    @Test
    void testValidateRequest_InputRequestDtoWithNullRequestString_WaitBadRequestException(){
        RequestDto testRequestDtoWithRequestStringEqualsNull = new RequestDto();
        testRequestDtoWithRequestStringEqualsNull.setRequestString(null);

        BadRequestException badRequestWithRequestStringNullException = assertThrows(BadRequestException.class
                , () -> validateService.validateRequest(testRequestDtoWithRequestStringEqualsNull));

        assertEquals("Вы ничего не отправили", badRequestWithRequestStringNullException.getMessage());
    }

    @Test
    void testValidateResult_InputFive_WaitWorkWithoutBadRequestException(){
        Double testFive = 5D;

        assertDoesNotThrow(() ->validateService.validateResult(testFive));
    }

    @Test
    void testValidateRequest_InputRequestDtoWithAnyRequestString_WaitWorkWithoutBadRequestException(){
        RequestDto testRequestDtoWithAnyRequestString = new RequestDto();
        testRequestDtoWithAnyRequestString.setRequestString("(1 + 1) * 2");

        assertDoesNotThrow(() -> validateService.validateRequest(testRequestDtoWithAnyRequestString));
    }
}