package ru.sverchkov.vniizd.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sverchkov.vniizd.constants.SymbolBuffer;
import ru.sverchkov.vniizd.dto.RequestDto;
import ru.sverchkov.vniizd.service.MainService;
import ru.sverchkov.vniizd.service.OperationService;
import ru.sverchkov.vniizd.service.SymbolCheckService;
import ru.sverchkov.vniizd.service.ValidateService;

@Component
@Slf4j
public class MainServiceImpl implements MainService {
    private final SymbolCheckService symbolCheckService;
    private final OperationService operationService;
    private final ValidateService validateService;


    public MainServiceImpl(SymbolCheckService symbolCheckService, OperationService operationService, ValidateService validateService) {
        this.symbolCheckService = symbolCheckService;
        this.operationService = operationService;
        this.validateService = validateService;
    }

    @Override
    public Double getResult(RequestDto requestDto) {
        validateService.validateRequest(requestDto);
        log.info("Start calculating in mainService");
        Double operate = operationService
                .operate(new SymbolBuffer(symbolCheckService.check(requestDto.getRequestString())));
        validateService.validateResult(operate);
        return operate;
    }
}
