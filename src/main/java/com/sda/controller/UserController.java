package com.sda.controller;

import com.sda.dto.CreateOrUpdateUserDto;
import com.sda.mappers.UserMapper;
import com.sda.model.User;
import com.sda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void allowEmptyDateBinding( WebDataBinder binder )
    {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
    }

    @GetMapping
    public String index(Model model, @ModelAttribute("successMsg") String successMsg) {
        log.info("....");
        String username = ((DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getAttribute("name");
        model.addAttribute("loggedUser", username);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("successMsg", successMsg);

        return "index";
    }

    @GetMapping("/{userId}")
    public String updateForm(Model model, @PathVariable("userId") Long id) {
        CreateOrUpdateUserDto dto = UserMapper.INSTANCE.toDto(userService.findWithAddresses(id));
        model.addAttribute("dto", dto);
        return "updateUser";
    }

    @PostMapping
    public ModelAndView submitUpdateForm(@ModelAttribute("dto") CreateOrUpdateUserDto dto) {
        userService.save(UserMapper.INSTANCE.toEntity(dto));

        return new ModelAndView("redirect:/users");
    }

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("dto", new CreateOrUpdateUserDto());

        return "addUser";
    }

    @PostMapping("/create")
    public ModelAndView addUser(@ModelAttribute("dto") CreateOrUpdateUserDto dto,
                          Model model) {
        final User user = userService.save(UserMapper.INSTANCE.toEntity(dto));
        model.addAttribute("successMsg", String.format("User with login: %s was successfully created",
                user.getLogin()));

        return new ModelAndView("redirect:/users", model.asMap());
    }
}
