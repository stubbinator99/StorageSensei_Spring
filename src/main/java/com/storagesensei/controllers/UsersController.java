package com.storagesensei.controllers;

import com.storagesensei.db.UserRepository;
import com.storagesensei.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
  @Autowired
  private UserRepository userRepo;

  @Autowired
  PasswordEncoder passwordEncoder;

  /*@PostMapping("/login")
  public @ResponseBody String login(@RequestParam User user) {

  }*/

  @RequestMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("user", new User());

    return "signup_form";
  }

  @PostMapping("/register_user")
  public String registerUser(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    userRepo.save(user);

    return "register_success";
  }

}
