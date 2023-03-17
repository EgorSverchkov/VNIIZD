package ru.sverchkov.vniizd.constants;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SymbolBuffer {
    private int index;
    private final List<Symbol> symbols;

    public SymbolBuffer(List<Symbol> symbols) {
        log.info("SymbolBuffer create");
        this.symbols = symbols;
    }

    public Symbol nextSymbol() {
        log.info("Next symbol called");
        return symbols.get(index++);
    }

    public void backSymbol() {
        log.info("Back symbol called");
        index--;
    }

    @Override
    public String toString() {
        return "SymbolBuffer{" +
                "index=" + index +
                ", symbols=" + symbols +
                '}';
    }
}
