package ru.sverchkov.vniizd.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sverchkov.vniizd.dto.RequestDto;
import ru.sverchkov.vniizd.exception.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainServiceImplTest {
    private final GetPowAndSinFunctionServiceImpl getPowFunctionService = new GetPowAndSinFunctionServiceImpl();
    private final SymbolCheckServiceImpl symbolCheckService = new SymbolCheckServiceImpl(getPowFunctionService);
    private final OperationServiceImpl operationService = new OperationServiceImpl(getPowFunctionService);
    private final ValidateServiceImpl validateService = new ValidateServiceImpl();
    private final MainServiceImpl mainService = new MainServiceImpl(symbolCheckService
            , operationService, validateService);

    @Test
    void callGetResult_InputRightRequest_WaitRightAnswer(){
        String testRequestString = "1 + 2";
        Double testRightAnswer = 3.0;
        RequestDto testRequestDto = new RequestDto();
        testRequestDto.setRequestString(testRequestString);

        assertEquals(testRightAnswer, mainService.getResult(testRequestDto));
    }

    @Test
    void callGetResult_InputWrongRequest_WaitBadRequestException(){
        String testRequestString = "1 a 2";
        RequestDto testRequestDto = new RequestDto();
        testRequestDto.setRequestString(testRequestString);

        BadRequestException badRequestWithWrongDataException = assertThrows(BadRequestException.class
                , () -> mainService.getResult(testRequestDto));

        assertEquals("Произошла ошибка, проверьте корректность введенных данных"
                , badRequestWithWrongDataException.getMessage());
    }

    @Test
    void callGetResult_InputNothing_WaitBadRequestException(){
        BadRequestException badRequestWithNothingException = assertThrows(BadRequestException.class
                , () -> mainService.getResult(null));

        assertEquals("Вы ничего не отправили", badRequestWithNothingException.getMessage());

    }

    @Test
    void callGetResult_InputRequestDtoWithNothingRequestString_WaitBadRequestException(){
        RequestDto testRequestDto = new RequestDto();
        testRequestDto.setRequestString(null);

        BadRequestException badRequestRequestDtoWithNothingRequestStringException = assertThrows
                (BadRequestException.class, () -> mainService.getResult(testRequestDto));

        assertEquals("Вы ничего не отправили"
                , badRequestRequestDtoWithNothingRequestStringException.getMessage());
    }

}