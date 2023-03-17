package ru.sverchkov.vniizd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sverchkov.vniizd.dto.ExceptionResponse;
import ru.sverchkov.vniizd.dto.RequestDto;
import ru.sverchkov.vniizd.dto.ResponseDto;
import ru.sverchkov.vniizd.exception.BadRequestException;
import ru.sverchkov.vniizd.service.MainService;

@RestController
@Slf4j
public class MainController {
    private static final String POST_CALCULATE = "/api/v1/calculate";
    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @PostMapping(POST_CALCULATE)
    public ResponseEntity<ResponseDto> postCalculate(@RequestBody RequestDto requestString){
        log.info("Start calculating this request : {}", requestString);
        return ResponseEntity.ok(ResponseDto.builder().result(mainService.getResult(requestString).toString()).build());
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> badRequestExceptionHandler(BadRequestException exception){
        return ResponseEntity.badRequest().body(ExceptionResponse.builder()
                        .message(exception.getMessage())
                .build());
    }
}
