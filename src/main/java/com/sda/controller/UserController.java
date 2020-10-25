package com.sda.controller;

import com.sda.dto.UpdateUserDto;
import com.sda.mappers.UserMapper;
import com.sda.model.User;
import com.sda.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());

        return "index";
    }

    @GetMapping("/{userId}")
    public String updateForm(Model model, @PathVariable("userId") Long id) {
        UpdateUserDto dto = UserMapper.INSTANCE.toDto(userService.findWithAddresses(id));
        model.addAttribute("dto", dto);
        return "updateUser";
    }

    @PostMapping
    public ModelAndView submitUpdateForm(@ModelAttribute("dto") UpdateUserDto dto) {
        User previousUser = userService.find(dto.getId());
        previousUser.setLogin(dto.getLogin());
        previousUser.setFirstName(dto.getFirstName());
        previousUser.setLastName(dto.getLastName());
        userService.save(previousUser);

        return new ModelAndView("redirect:/users");
    }
}
