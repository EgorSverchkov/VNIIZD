package ru.sverchkov.vniizd.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sverchkov.vniizd.constants.Symbol;
import ru.sverchkov.vniizd.constants.SymbolBuffer;
import ru.sverchkov.vniizd.constants.SymbolType;
import ru.sverchkov.vniizd.exception.BadRequestException;
import ru.sverchkov.vniizd.service.GetPowAndSinFunctionService;
import ru.sverchkov.vniizd.service.OperationService;


@Component
@Slf4j
public class OperationServiceImpl implements OperationService {
    private static final Integer ZERO = 0;
    private static final String BAD_REQUEST_EXCEPTION_MESSAGE
            = "Произошла ошибка, проверьте корректность введенных данных";

    private final GetPowAndSinFunctionService getPowAndSinFunctionService;

    public OperationServiceImpl(GetPowAndSinFunctionService getPowAndSinFunctionService) {
        this.getPowAndSinFunctionService = getPowAndSinFunctionService;
    }

    @Override
    public Double operate(SymbolBuffer symbolBuffer) {
        log.info("Start operating: {}", symbolBuffer.toString());
        Symbol symbol = symbolBuffer.nextSymbol();
        if (symbol.getSymbolType() == SymbolType.END_STR) {
            log.info("Result is: {}", ZERO);
            return Double.valueOf(ZERO);
        } else {
            symbolBuffer.backSymbol();
            return plusOrMinus(symbolBuffer);
        }
    }

    @Override
    public Double plusOrMinus(SymbolBuffer symbolBuffer) {
        log.info("PlusOrMinus method start with: {}", symbolBuffer.toString());
        Double value = multOrDiv(symbolBuffer);
        log.info("Value in PlusOrMinus is: {}", value);
        while (true) {
            Symbol symbol = symbolBuffer.nextSymbol();
            log.info("Symbol in plusOrMinus is: {}", symbol.getValue());
            switch (symbol.getSymbolType()) {
                case PLUS_SYM:
                    log.info("It's plus symbol");
                    value += multOrDiv(symbolBuffer);
                    break;
                case MINUS_SYM:
                    log.info("It's minus symbol");
                    value -= multOrDiv(symbolBuffer);
                    break;
                case END_STR:
                case RIGHT_BRACKET:
                    log.info("It's plus symbol");
                    symbolBuffer.backSymbol();
                    return value;
                default:
                    log.error("Error in plusOrMinus with: {}", symbol.getValue());
                    throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public Double multOrDiv(SymbolBuffer symbolBuffer) {
        log.info("MultOrDiv method start with: {}", symbolBuffer.toString());
        Double value = numOrLeftBrackets(symbolBuffer);
        log.info("Value in multOrDiv is: {}", value);
        while (true) {
            Symbol symbol = symbolBuffer.nextSymbol();
            log.info("Symbol in multOrDiv is: {}", symbol.getValue());
            switch (symbol.getSymbolType()) {
                case MULT_SYM:
                    log.info("It's multiply symbol");
                    value *= numOrLeftBrackets(symbolBuffer);
                    break;
                case DIV_SYM:
                    log.info("It's division symbol");
                    value /= numOrLeftBrackets(symbolBuffer);
                    break;
                case END_STR:
                case RIGHT_BRACKET:
                case PLUS_SYM:
                case NUMBER:
                case MINUS_SYM:
                    log.info("It's minus symbol");
                    symbolBuffer.backSymbol();
                    return value;
                default:
                    log.error("Error in multOrDiv with: {}", symbol.getValue());
                    throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE + symbol.getValue());
            }
        }
    }

    @Override
    public Double numOrLeftBrackets(SymbolBuffer symbolBuffer) {
        log.info("NumOrLeftBrackets method start with: {}", symbolBuffer.toString());
        Symbol symbol = symbolBuffer.nextSymbol();
        switch (symbol.getSymbolType()) {
            case FUNC_NAME:
                log.info("Its function in numOrLeftBrackets method");
                symbolBuffer.backSymbol();
                return powAndSinFunc(symbolBuffer);
            case MINUS_SYM:
                log.info("Its minus in numOrLeftBrackets method");
                double value = numOrLeftBrackets(symbolBuffer);
                return -value;
            case NUMBER:
                log.info("Its number in numOrLeftBrackets method");
                return Double.parseDouble(symbol.getValue());
            case LEFT_BRACKET:
                log.info("Its left brackets in numOrLeftBrackets method");
                Double result = plusOrMinus(symbolBuffer);
                symbol = symbolBuffer.nextSymbol();
                if (symbol.getSymbolType() != SymbolType.RIGHT_BRACKET) {
                    log.info("Error in numOrLeftBrackets when compare with: {}", symbol.getValue());
                    throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE + symbol.getValue());
                }
                return result;
            default:
                log.info("Error in numOrLeftBrackets with: {}", symbol.getValue());
                throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE + symbol.getValue());
        }
    }

    @Override
    public Double powAndSinFunc(SymbolBuffer symbolBuffer) {
        String value = symbolBuffer.nextSymbol().getValue();
        log.info("Starting pow number:  {}", value);
        Symbol symbol = symbolBuffer.nextSymbol();
        log.info("Symbol in pow method is: {}", symbol);
        if (symbol.getSymbolType() != SymbolType.LEFT_BRACKET) {
            throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE);
        }

        double powNumber;
        symbol = symbolBuffer.nextSymbol();
        boolean unar = false;
        log.info("Symbol in middle of pow method is: {}", symbol);
        if (symbol.getSymbolType() == SymbolType.MINUS_SYM) {
            symbol = symbolBuffer.nextSymbol();
            unar = true;
        }
        if (symbol.getSymbolType() != SymbolType.RIGHT_BRACKET) {
            if (symbol.getSymbolType() == SymbolType.FUNC_NAME) {
                symbolBuffer.backSymbol();
                powNumber = powAndSinFunc(symbolBuffer);
            } else {
                powNumber = Double.parseDouble(symbol.getValue());
            }
        } else {
            throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE);
        }
        log.info("Pow number is: {}", powNumber);
        Symbol symbolNext = symbolBuffer.nextSymbol();
        if (symbolNext.getSymbolType() == SymbolType.END_STR) {
            throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE);
        }

        if (unar) {
            return getPowAndSinFunctionService.getFunction().get(value).use(-powNumber);
        } else {
            return getPowAndSinFunctionService.getFunction().get(value).use(powNumber);
        }
    }


}
