package ru.sverchkov.vniizd.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sverchkov.vniizd.exception.BadRequestException;
import ru.sverchkov.vniizd.service.FunctionalInterfaceForPowAndSin;
import ru.sverchkov.vniizd.service.GetPowAndSinFunctionService;

import java.util.HashMap;

@Component
@Slf4j
public class GetPowAndSinFunctionServiceImpl implements GetPowAndSinFunctionService {
    private static final String BAD_REQUEST_MESSAGE = "Произошла ошибка, проверьте корректность введенных данных";
    private static final Double TWO_POW = 2D;

    @Override
    public HashMap<String, FunctionalInterfaceForPowAndSin> getFunction() {
        log.info("Start function pow");
        HashMap<String, FunctionalInterfaceForPowAndSin> result = new HashMap<>();
        result.put("pow", number -> {
            if(number == null || number.isInfinite() || number.isNaN()){
                throw new BadRequestException(BAD_REQUEST_MESSAGE);
            }
            log.info("Pow number in getFunction is: {}", Math.pow(number, TWO_POW));
            return Math.pow(number, TWO_POW);
        });
        result.put("sin", number -> {
            if(number == null || number.isInfinite() || number.isNaN()){
                throw new BadRequestException(BAD_REQUEST_MESSAGE);
            }
            log.info("Number: {} in sin is: {}",number, Math.toRadians(number));
            log.info("Sinus number in getFunction is: {}", Math.sin(Math.toRadians(number)));
            return Double.parseDouble(String.format("%.4f",Math.sin(Math.toRadians(number)))
                    .replaceAll(",","."));
        });
        log.info("Result in pow is: {}", result);
        return result;
    }
}
