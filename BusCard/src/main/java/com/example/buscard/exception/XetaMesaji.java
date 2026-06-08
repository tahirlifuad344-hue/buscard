package com.example.buscard.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XetaMesaji {
    private Integer status;
    private String mesaj;
    private LocalDateTime zaman;
}
