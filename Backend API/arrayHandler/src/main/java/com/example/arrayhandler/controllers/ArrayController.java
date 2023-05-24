package com.example.arrayhandler.controllers;

import com.example.arrayhandler.models.ArrayRequest;
import com.example.arrayhandler.models.ArrayResponse;
import com.example.arrayhandler.models.ResponseError;
import com.example.arrayhandler.models.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArrayController {

    @PostMapping("/arrays")
    public ResponseEntity<?> performArrayOperation(@RequestBody ArrayRequest request) {
        if (request.getNumbers() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please provide numbers!"));
        }

        if (request.getWhat() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Please provide what to do with the numbers!"));
        }

        int[] numbers = request.getNumbers();
        String operation = request.getWhat();
        int result;

        switch (operation) {
            case "sum":
                result = sum(numbers);
                break;
            case "multiply":
                result = multiply(numbers);
                break;
            case "double":
                return ResponseEntity.ok(new ArrayResponse(doubleArrayElements(numbers)));
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseError("Invalid operation specified!"));
        }

        return ResponseEntity.ok(new ResultResponse(result));
    }

    private int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    private int multiply(int[] numbers) {
        int product = 1;
        for (int number : numbers) {
            product *= number;
        }
        return product;
    }

    private int[] doubleArrayElements(int[] numbers) {
        int[] doubledArray = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            doubledArray[i] = numbers[i] * 2;
        }
        return doubledArray;
    }
}

