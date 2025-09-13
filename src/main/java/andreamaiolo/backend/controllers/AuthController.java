package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.exceptions.ValidationException;
import andreamaiolo.backend.payloads.LoginPayload;
import andreamaiolo.backend.payloads.LoginRespDTO;
import andreamaiolo.backend.payloads.UserPayload;
import andreamaiolo.backend.payloads.UserRespDTO;
import andreamaiolo.backend.services.AuthService;
import andreamaiolo.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody @Validated LoginPayload payload) {
        String token = authService.checkAndCreateToken(payload);
        return new LoginRespDTO(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO save(@RequestBody @Validated UserPayload payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getDefaultMessage()));
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList());
        } else {
            User newUser = this.userService.saveUser(payload);
            return new UserRespDTO(newUser.getId());
        }

    }
}
