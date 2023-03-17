package ru.sverchkov.vniizd.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sverchkov.vniizd.constants.Symbol;
import ru.sverchkov.vniizd.constants.SymbolType;
import ru.sverchkov.vniizd.exception.BadRequestException;
import ru.sverchkov.vniizd.service.SymbolCheckService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SymbolCheckServiceImpl implements SymbolCheckService {
    private static final Character ZERO_CHARACTER = '0';
    private static final Character NINE_CHARACTER = '9';
    private static final Character WHITE_SPACE_CHARACTER = ' ';
    private static final String BAD_REQUEST_MESSAGE = "Вы ввели не правильный запрос" +
            ". Проверьте корректность введеных данных. ";
    private static final String END_STRING = "";


    @Override
    public List<Symbol> check(String requestString) {
        log.info("Start checking request: {}",requestString);
        ArrayList<Symbol> symbols = new ArrayList<>();
        int index = 0;
        log.info("Index is: {}", index);
        while (index< requestString.length()) {
            char charAtIndex = requestString.charAt(index);
            log.info("Char at index: {} equals is: {}",index,charAtIndex);
            switch (charAtIndex) {
                case '(':
                    log.info(charAtIndex + " equals is '('");
                    symbols.add(new Symbol(SymbolType.LEFT_BRACKET, charAtIndex));
                    index++;
                    continue;
                case ')':
                    log.info(charAtIndex + " equals is ')'");
                    symbols.add(new Symbol(SymbolType.RIGHT_BRACKET, charAtIndex));
                    index++;
                    continue;
                case '+':
                    log.info(charAtIndex + " equals is '+'");
                    symbols.add(new Symbol(SymbolType.PLUS_SYM, charAtIndex));
                    index++;
                    continue;
                case '-':
                    log.info(charAtIndex + " equals is '-'");
                    symbols.add(new Symbol(SymbolType.MINUS_SYM, charAtIndex));
                    index++;
                    continue;
                case '*':
                    log.info(charAtIndex + " equals is '*'");
                    symbols.add(new Symbol(SymbolType.MULT_SYM, charAtIndex));
                    index++;
                    continue;
                case '/':
                    log.info(charAtIndex + " equals is '/'");
                    symbols.add(new Symbol(SymbolType.DIV_SYM, charAtIndex));
                    index++;
                    continue;
                default:
                    if (charAtIndex <= NINE_CHARACTER && charAtIndex >= ZERO_CHARACTER || charAtIndex == ',' || charAtIndex == '.') {
                        StringBuilder resultString = new StringBuilder();
                        do {
                            resultString.append(charAtIndex);
                            log.info("Result number append: {}", resultString);
                            index++;
                            if (index >= requestString.length()) {
                                break;
                            }
                            charAtIndex = requestString.charAt(index);
                        } while (charAtIndex <= NINE_CHARACTER && charAtIndex >= ZERO_CHARACTER || charAtIndex == ',' || charAtIndex == '.');
                        symbols.add(new Symbol(SymbolType.NUMBER,String.format( "%.2f",Double
                                .parseDouble(resultString.toString().replaceAll(",", ".")))
                                .replaceAll(",", ".")));
                        log.info("Symbol add in symbols list");
                    } else {
                        if (charAtIndex != WHITE_SPACE_CHARACTER) {
                            log.info("Bad char is: {}", charAtIndex);
                            throw new BadRequestException(BAD_REQUEST_MESSAGE);
                        }
                        index++;
                    }
            }
        }
        symbols.add(new Symbol(SymbolType.END_STR, END_STRING));
        log.info("Symbols is: {}", symbols);
        return symbols;
    }
}
