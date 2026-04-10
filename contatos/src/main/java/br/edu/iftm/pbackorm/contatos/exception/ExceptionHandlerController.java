package br.edu.iftm.pbackorm.contatos.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.iftm.pbackorm.contatos.dto.ErroDTO;

@RestControllerAdvice
public class ExceptionHandlerController {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> tratarBadRequest(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(e -> e.getField() + ": " + e.getDefaultMessage())
                        .toList();

        ErroDTO erro = ErroDTO.builder()
                .dataHora(LocalDateTime.now())
                .erros(erros)
                .mensagem("Erro de Validação")
                .build();

        return ResponseEntity.badRequest().body(erro);
    }
}
