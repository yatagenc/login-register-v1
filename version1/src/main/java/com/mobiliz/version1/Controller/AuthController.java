package com.mobiliz.version1.Controller;

import com.mobiliz.version1.Model.User;
import com.mobiliz.version1.Repository.UserRepository;
import com.mobiliz.version1.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // This should map to register.html
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // This should map to login.html
    }

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        return "welcome";  // This should map to welcome.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            User registeredUser = userService.registerUser(
                    null, user.getName(), user.getEmail(), user.getPassword());
            model.addAttribute("user", registeredUser);
            return "redirect:/welcome";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Registration failed");
            return "redirect:/register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, HttpServletRequest request) {
        if (userService.authenticateUser(name, password)) {
            request.getSession().setAttribute("user", name);
            return "redirect:/welcome";
        } else {
            return "redirect:/login?error";
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
    }
}

