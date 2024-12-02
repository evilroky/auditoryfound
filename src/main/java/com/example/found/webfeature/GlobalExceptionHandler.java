package com.example.found.webfeature;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Обработка ошибки, если переданы некорректные параметры (например, текст вместо числа)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
        model.addAttribute("error", "Введите корректные числовые значения для номера аудитории.");
        return "index"; // Возвращаемся на index.html с сообщением об ошибке
    }

    // Обработка всех остальных исключений
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
        return "index"; // Возвращаемся на index.html с общим сообщением об ошибке
    }

    // Обработка ошибки, если переданы некорректные параметры (например, текст вместо числа)
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(Exception ex, Model model) {
        model.addAttribute("error", "Введите корректные числовые значения для номера аудитории.");
        return "index";
    }
}
