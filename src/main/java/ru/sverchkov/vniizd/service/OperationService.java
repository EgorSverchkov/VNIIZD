package ru.sverchkov.vniizd.service;

import ru.sverchkov.vniizd.constants.SymbolBuffer;

public interface OperationService {
    Double operate(SymbolBuffer symbolBuffer);

    Double plusOrMinus(SymbolBuffer symbolBuffer);

    Double multOrDiv(SymbolBuffer symbolBuffer);

    Double numOrLeftBrackets(SymbolBuffer symbolBuffer);
}
