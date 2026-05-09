package com.estore.customer.controller;

import com.estore.customer.entity.Profile;
import com.estore.customer.entity.User;
import com.estore.customer.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getProfile(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user.getProfile()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long id,
                                           @RequestBody Profile updatedProfile) {
        return userRepository.findById(id).map(user -> {
            Profile profile = user.getProfile();
            if (profile == null) profile = new Profile();
            profile.setPhone(updatedProfile.getPhone());
            profile.setAddress(updatedProfile.getAddress());
            profile.setCity(updatedProfile.getCity());
            profile.setCountry(updatedProfile.getCountry());
            user.setProfile(profile);
            userRepository.save(user);
            return ResponseEntity.ok(profile);
        }).orElse(ResponseEntity.notFound().build());
    }
}
