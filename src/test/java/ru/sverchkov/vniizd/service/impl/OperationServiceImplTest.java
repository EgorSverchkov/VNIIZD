package ru.sverchkov.vniizd.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sverchkov.vniizd.constants.Symbol;
import ru.sverchkov.vniizd.constants.SymbolBuffer;
import ru.sverchkov.vniizd.constants.SymbolType;
import ru.sverchkov.vniizd.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OperationServiceImplTest {
    private final GetPowAndSinFunctionServiceImpl getPowFunctionService = new GetPowAndSinFunctionServiceImpl();
    private final OperationServiceImpl operationService = new OperationServiceImpl(getPowFunctionService);


    @Test
    void callOperate_InputRightSymbolBuffer_WaitRightDouble() {
        Double testRightDouble = 0.5;

        List<Symbol> testRightSymbols = new ArrayList<>();
        testRightSymbols.add(new Symbol(SymbolType.NUMBER, "1"));
        testRightSymbols.add(new Symbol(SymbolType.DIV_SYM, "/"));
        testRightSymbols.add(new Symbol(SymbolType.NUMBER, "2"));
        testRightSymbols.add(new Symbol(SymbolType.END_STR, ""));

        assertEquals(testRightDouble, operationService.operate(new SymbolBuffer(testRightSymbols)));
    }

    @Test
    void callOperate_InputWrongSymbolBuffer_WaitBadRequestException() {
        List<Symbol> testWrongSymbols = new ArrayList<>();
        testWrongSymbols.add(new Symbol(SymbolType.NUMBER, "1"));
        testWrongSymbols.add(new Symbol(SymbolType.NUMBER, "2"));
        testWrongSymbols.add(new Symbol(SymbolType.END_STR, ""));

        BadRequestException badRequestWrongSymbolsException = assertThrows(BadRequestException.class
                , () -> operationService.operate(new SymbolBuffer(testWrongSymbols)));

        assertEquals("Произошла ошибка, проверьте корректность введенных данных", badRequestWrongSymbolsException.getMessage());

    }

    @Test
    void callOperate_InputRightSymbolBufferWithFunctionPowTwo_WaitNumberFour() {
        Double rightAnswerNumber = 4D;

        List<Symbol> testSymbolsWithPowTwo = new ArrayList<>();
        testSymbolsWithPowTwo.add(new Symbol(SymbolType.FUNC_NAME, "pow"));
        testSymbolsWithPowTwo.add(new Symbol(SymbolType.LEFT_BRACKET, "("));
        testSymbolsWithPowTwo.add(new Symbol(SymbolType.NUMBER, "2"));
        testSymbolsWithPowTwo.add(new Symbol(SymbolType.RIGHT_BRACKET, ")"));
        testSymbolsWithPowTwo.add(new Symbol(SymbolType.END_STR, ""));

        assertEquals(rightAnswerNumber, operationService.operate(new SymbolBuffer(testSymbolsWithPowTwo)));
    }

    @Test
    void callOperate_InputRightSymbolBufferWithFunctionSinThirty_WaitNumberZeroPointFive() {
        Double rightAnswerNumber = 0.5D;

        List<Symbol> testSymbolsWithSinThirty = new ArrayList<>();
        testSymbolsWithSinThirty.add(new Symbol(SymbolType.FUNC_NAME, "sin"));
        testSymbolsWithSinThirty.add(new Symbol(SymbolType.LEFT_BRACKET, "("));
        testSymbolsWithSinThirty.add(new Symbol(SymbolType.NUMBER, "30"));
        testSymbolsWithSinThirty.add(new Symbol(SymbolType.RIGHT_BRACKET, ")"));
        testSymbolsWithSinThirty.add(new Symbol(SymbolType.END_STR, ""));

        assertEquals(rightAnswerNumber, operationService.operate(new SymbolBuffer(testSymbolsWithSinThirty)));
    }

}