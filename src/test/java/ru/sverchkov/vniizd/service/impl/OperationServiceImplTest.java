package ru.sverchkov.vniizd.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sverchkov.vniizd.constants.Symbol;
import ru.sverchkov.vniizd.constants.SymbolBuffer;
import ru.sverchkov.vniizd.constants.SymbolType;
import ru.sverchkov.vniizd.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OperationServiceImplTest {
    private final OperationServiceImpl operationService = new OperationServiceImpl();


    @Test
    void callOperate_InputRightSymbolBuffer_WaitRightDouble(){
        Double testRightDouble = 0.5;

        List<Symbol> testRightSymbols = new ArrayList<>();
        testRightSymbols.add(new Symbol(SymbolType.NUMBER, "1"));
        testRightSymbols.add(new Symbol(SymbolType.DIV_SYM, "/"));
        testRightSymbols.add(new Symbol(SymbolType.NUMBER, "2"));
        testRightSymbols.add(new Symbol(SymbolType.END_STR, ""));

        Assertions.assertEquals(testRightDouble, operationService.operate(new SymbolBuffer(testRightSymbols)));
    }

    @Test
    void callOperate_InputWrongSymbolBuffer_Wait_BadRequestException(){
        List<Symbol> testWrongSymbols = new ArrayList<>();
        testWrongSymbols.add(new Symbol(SymbolType.NUMBER, "1"));
        testWrongSymbols.add(new Symbol(SymbolType.NUMBER, "2"));
        testWrongSymbols.add(new Symbol(SymbolType.END_STR, ""));

        BadRequestException badRequestWrongSymbolsException = Assertions.assertThrows(BadRequestException.class
                , () -> operationService.operate(new SymbolBuffer(testWrongSymbols)));

        Assertions.assertEquals("Неизвестный символ: 2", badRequestWrongSymbolsException.getMessage());

    }

}