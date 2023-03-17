package ru.sverchkov.vniizd.service;

import ru.sverchkov.vniizd.dto.RequestDto;

public interface ValidateService {
    void validateResult(Double result);
    void validateRequest(RequestDto requestDto);
}
