package com.sda.rest;

import com.sda.dto.CreateOrUpdateUserDto;
import com.sda.mappers.UserMapper;
import com.sda.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 */
@RestController
@RequestMapping("/rest/users")
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}")
    public @ResponseBody CreateOrUpdateUserDto find(@PathVariable("id") Long id) {
        return UserMapper.INSTANCE.toDto(userService.find(id));
    }

    // GET /rest/users?firstName=wdwhegewg&lastName=wiuwerh
    @GetMapping
    public @ResponseBody List<CreateOrUpdateUserDto> find(@RequestParam String firstName,
                                                          @RequestParam String lastName) {

        return null;
    }
}
