package io.github.henriquedarocha.batalhanaval.shared.exception;

import io.github.henriquedarocha.batalhanaval.modules.auth.exception.InvalidCredentialsException;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.GameNotFoundException;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.GameNotPlacingShipsException;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.InvalidFleetException;
import io.github.henriquedarocha.batalhanaval.modules.game.exception.UnsupportedGameModeException;
import io.github.henriquedarocha.batalhanaval.modules.player.exception.PlayerAlreadyExistsException;
import io.github.henriquedarocha.batalhanaval.modules.player.exception.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ProblemDetail handlePlayerAlreadyExists(PlayerAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Dados inválidos");

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        problem.setProperty("errors", errors);

        return problem;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(InvalidCredentialsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ProblemDetail handlePlayerNotFound(PlayerNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UnsupportedGameModeException.class)
    public ProblemDetail handleUnsupportedGameMode(UnsupportedGameModeException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidFleetException.class)
    public ProblemDetail handleInvalidFleet(InvalidFleetException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ProblemDetail handleGameNotFound(GameNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(GameNotPlacingShipsException.class)
    public ProblemDetail handleGameNotPlacingShips(GameNotPlacingShipsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadableBody(HttpMessageNotReadableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Parâmetro da URL inválido");
    }

}