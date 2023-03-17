package ru.sverchkov.vniizd.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sverchkov.vniizd.constants.Symbol;
import ru.sverchkov.vniizd.constants.SymbolType;
import ru.sverchkov.vniizd.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SymbolCheckServiceImplTest {
    private final SymbolCheckServiceImpl symbolCheckService = new SymbolCheckServiceImpl();


    @Test
    void callCheck_InputRandomString_Wait_BadRequestException(){
        String testRandomString = "fsdfsdtnre(09f9dsf99)(9009)((";

        BadRequestException badRequestException = assertThrows(BadRequestException.class
                , () -> symbolCheckService.check(testRandomString));

        assertEquals("Вы ввели не правильный запрос. Проверьте корректность введеных данных. "
                , badRequestException.getMessage());
    }

    @Test
    void callCheck_InputRightString_Wait_EqualsList(){
        String testRequestString = "1 / 2";
        List<Symbol> testRightSymbols = new ArrayList<>();
        testRightSymbols.add(new Symbol(SymbolType.NUMBER, "1"));
        testRightSymbols.add(new Symbol(SymbolType.DIV_SYM, "/"));
        testRightSymbols.add(new Symbol(SymbolType.NUMBER, "2"));
        testRightSymbols.add(new Symbol(SymbolType.END_STR, ""));

        assertEquals(testRightSymbols,symbolCheckService.check(testRequestString));
    }


}