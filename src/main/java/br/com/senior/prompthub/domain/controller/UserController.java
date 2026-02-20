package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.user.UserInput;
import br.com.senior.prompthub.domain.dto.user.UserOutput;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.enums.GlobalRole;
import br.com.senior.prompthub.domain.service.user.UserService;
import br.com.senior.prompthub.domain.spec.user.UserSearch;
import br.com.senior.prompthub.domain.spec.user.UserSpecification;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserSpecification userSpecification;
    private final BaseCrudController<User, Long> crudController;

    public UserController(UserService userService,
                          ModelMapperService<User> userModelMapperService,
                          UserSpecification userSpecification) {
        this.userSpecification = userSpecification;
        this.userService = userService;
        this.crudController = new BaseCrudController<>(userService, userModelMapperService, User.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<PageResult<UserOutput>> getAllUsers(PageParams pageParams, UserSearch search) {
        return crudController.getAllSpec(pageParams, userSpecification, search).asPageDto(UserOutput.class);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserOutput> getUserById(@PathVariable Long id) {
        return crudController.getById(id).asDto(UserOutput.class);
    }

    // Apenas ADMIN pode criar usuários via endpoint administrativo
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserOutput> createUser(@Valid @RequestBody UserInput userCreate) {
        return crudController.create(userCreate).asDto(UserOutput.class);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserOutput> updateUser(@PathVariable Long id, @Valid @RequestBody UserInput userCreate) {
        return crudController.update(id, userCreate).asDto(UserOutput.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam EntityStatus status) {
        userService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/change-role")
    public ResponseEntity<Void> changeRole(@PathVariable Long id, @RequestParam GlobalRole role) {
        userService.changeRole(id, role);
        return ResponseEntity.noContent().build();
    }
}
