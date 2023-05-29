@GetMapping("/{userId}")
public ResponseEntity<?> getUser(@PathVariable Long userId) {
    // Retrieve user by ID
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    User user = userOptional.get();
    return ResponseEntity.ok(user);
}

@PostMapping("/add")
public ResponseEntity<?> addUser(@RequestBody User user) {
    // Set user as active by default
    user.setActive(true);

    // Save the user in the database
    userRepository.save(user);

    return ResponseEntity.ok("User added successfully");
}

@PutMapping("/activate/{userId}")
public ResponseEntity<?> activateUser(@PathVariable Long userId) {
    // Retrieve user by ID
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    User user = userOptional.get();

    // Activate the user
    user.setActive(true);
    userRepository.save(user);

    return ResponseEntity.ok("User activated successfully");
}

@PutMapping("/deactivate/{userId}")
public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {
    // Retrieve user by ID
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    User user = userOptional.get();

    // Deactivate the user
    user.setActive(false);
    userRepository.save(user);

    return ResponseEntity.ok("User deactivated successfully");
}
