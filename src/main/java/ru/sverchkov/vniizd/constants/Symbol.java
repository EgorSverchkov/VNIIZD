package ru.sverchkov.vniizd.constants;

import lombok.Data;

import java.util.Objects;

@Data
public class Symbol {
    private SymbolType symbolType;
    private String value;

    public Symbol(SymbolType symbolType, String value) {
        this.symbolType = symbolType;
        this.value = value;
    }

    public Symbol(SymbolType symbolType, Character value) {
        this.symbolType = symbolType;
        this.value = value.toString();
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbolType=" + symbolType +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return symbolType == symbol.symbolType && Objects.equals(value, symbol.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbolType, value);
    }
}
