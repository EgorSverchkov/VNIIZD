package ru.sverchkov.vniizd.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private String result;
}
