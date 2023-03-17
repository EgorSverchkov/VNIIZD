package ru.sverchkov.vniizd.service;

import ru.sverchkov.vniizd.constants.Symbol;

import java.util.List;

public interface SymbolCheckService {
    List<Symbol> check(String requestString);
}
