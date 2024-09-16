package br.com.alura.adopet.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String to;
    private String from;
    private String subject;
    private String msg;
}
