package web.controller;

import web.model.User;
import web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired()
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/id")
    public String getUserById(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user";
    }

    @GetMapping("/new")
    public String getUserCreationForm(User user) {
        return "create";
    }

    @PostMapping("/new")
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        } else {
            userService.addUser(user);
            return "redirect:/";
        }
    }

    @PostMapping("/")
    public String deleteUserById(@RequestParam(value = "id", required = false) long id) {
        userService.removeUser(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String getUserUpdateForm(@RequestParam(value = "id", required = false) long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUserData(@RequestParam ("id") long id, User user) {
        user.setUserId(id);
        userService.updateUser(user);
        return "redirect:/";
    }
}