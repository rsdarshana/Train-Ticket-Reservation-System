package com.trainsystem.controller;

import com.trainsystem.model.User;
import com.trainsystem.model.UserProfile;
import com.trainsystem.services.ProfileService;
import com.trainsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping
    public String viewProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("profiles", profileService.getUserProfiles(user));
        model.addAttribute("newProfile", new UserProfile());
        return "profile/view";
    }

    @PostMapping("/add")
    public String addProfile(@AuthenticationPrincipal UserDetails userDetails,
                           @ModelAttribute UserProfile newProfile) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        newProfile.setUser(user);
        profileService.saveProfile(newProfile);
        return "redirect:/profile";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable Long id,
                             Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        UserProfile profile = profileService.getProfileByIdAndUser(id, user).orElseThrow();
        model.addAttribute("profile", profile);
        return "profile/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable Long id,
                              @ModelAttribute UserProfile updatedProfile) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        UserProfile existingProfile = profileService.getProfileByIdAndUser(id, user).orElseThrow();
        profileService.updateProfile(existingProfile, updatedProfile);
        return "redirect:/profile";
    }

    @PostMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteProfile(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long id) {
        try {
            User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
            profileService.deleteProfile(id, user);
            return ResponseEntity.ok("Profile deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting profile: " + e.getMessage());
        }
    }
}