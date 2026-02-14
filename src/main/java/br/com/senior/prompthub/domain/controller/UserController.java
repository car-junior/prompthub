package br.com.senior.prompthub.domain.controller;

import br.com.senior.prompthub.core.controller.BaseCrudController;
import br.com.senior.prompthub.core.dto.PageParams;
import br.com.senior.prompthub.core.dto.PageResult;
import br.com.senior.prompthub.core.service.modelmapper.ModelMapperService;
import br.com.senior.prompthub.domain.dto.user.request.UserCreateRequest;
import br.com.senior.prompthub.domain.dto.user.response.UserResponse;
import br.com.senior.prompthub.domain.entity.User;
import br.com.senior.prompthub.domain.enums.EntityStatus;
import br.com.senior.prompthub.domain.service.user.UserService;
import br.com.senior.prompthub.domain.spec.user.UserSearch;
import br.com.senior.prompthub.domain.spec.user.UserSpecification;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public ResponseEntity<PageResult<UserResponse>> getAllUsers(PageParams pageParams, UserSearch search) {
        return crudController.getAllSpec(pageParams, userSpecification, search).asPageDto(UserResponse.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return crudController.getById(id).asDto(UserResponse.class);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest userCreate) {
        return crudController.create(userCreate).asDto(UserResponse.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserCreateRequest userCreate) {
        return crudController.update(id, userCreate).asDto(UserResponse.class);
    }

    @PatchMapping("/{id}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestParam EntityStatus status) {
        userService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
