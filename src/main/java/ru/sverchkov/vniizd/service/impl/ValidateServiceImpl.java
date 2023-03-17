package ru.sverchkov.vniizd.service.impl;

import org.springframework.stereotype.Component;
import ru.sverchkov.vniizd.dto.RequestDto;
import ru.sverchkov.vniizd.exception.BadRequestException;
import ru.sverchkov.vniizd.service.ValidateService;

@Component
public class ValidateServiceImpl implements ValidateService {
    private static final String BAD_REQUEST_ZERO_EXCEPTION = "На ноль делить нельзя";
    private static final String BAD_REQUEST_EMPTY_EXCEPTION = "Вы ничего не отправили";
    private static final String EXCEPTION = "Произошла ошибка повторите попытку";

    @Override
    public void validateResult(Double result) {
        if (Double.isInfinite(result)) {
            throw new BadRequestException(BAD_REQUEST_ZERO_EXCEPTION);
        }
        if(Double.isNaN(result)) {
            throw new BadRequestException(EXCEPTION);
        }
    }

    @Override
    public void validateRequest(RequestDto requestDto) {
        if (requestDto == null || requestDto.getRequestString() == null) {
            throw new BadRequestException(BAD_REQUEST_EMPTY_EXCEPTION);
        }
    }
}
