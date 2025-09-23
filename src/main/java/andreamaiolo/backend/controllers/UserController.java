package andreamaiolo.backend.controllers;

import andreamaiolo.backend.entities.User;
import andreamaiolo.backend.payloads.UserPayload;
import andreamaiolo.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "15") int pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(pageNumber, pageSize, sortBy);
    }

    @PutMapping("/me")
    public User updateOwnProfile(@AuthenticationPrincipal User currentUser,
                                 @RequestBody @Validated UserPayload payload) {
        return this.userService.findAndUpdate(currentUser.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteOwnProfile(@AuthenticationPrincipal User currentUser) {
        this.userService.findAndDelete(currentUser.getId());
    }
}
