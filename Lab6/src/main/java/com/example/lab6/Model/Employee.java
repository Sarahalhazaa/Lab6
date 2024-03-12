package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Employee {

    @NotEmpty
    @Size(min = 3)
    private String ID;

    @NotEmpty
    @Size(min = 5)
   @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^05\\d{8}$")
    private String phoneNumber;

    @NotNull
    @Positive
    @Min(26)
    private Integer age;

    @NotEmpty
    @Pattern(regexp = "^(supervisor|coordinator)$")
    private String position;

    private boolean onLeave = false;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate hireDate;

    @NotNull
   @Positive
    private int annualLeave;


}
