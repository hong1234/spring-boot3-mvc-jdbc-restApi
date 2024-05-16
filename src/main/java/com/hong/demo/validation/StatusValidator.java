package com.hong.demo.validation;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

// import java.util.regex.Matcher;
// import java.util.regex.Pattern;
// import java.util.regex.PatternSyntaxException;

public class StatusValidator implements ConstraintValidator<StatusValidation, String> {
    public boolean isValid(String colorName, ConstraintValidatorContext cxt) {
        List list = Arrays.asList(new String[]{"Low","Medium","High"});
        return list.contains(colorName);
    }
}


// public class ColorValidator implements ConstraintValidator<ColorValidation, Enum<?>> {

//     private Pattern pattern;

//     @Override
//     public void initialize(ColorValidation constraintAnnotation) {
//         try {
//             pattern = Pattern.compile(constraintAnnotation.regexp());
//         } catch (PatternSyntaxException e) {
//             throw new IllegalArgumentException("Given regex is invalid", e);
//         }
//     }

//     @Override
//     public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
//         if (value == null) {
//             return true;
//         }

//         Matcher m = pattern.matcher(value.name());
//         return m.matches();
//     }
// }
