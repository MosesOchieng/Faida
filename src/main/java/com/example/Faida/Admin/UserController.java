import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final OtpService otpService; // Implement your own OTP service

    public UserController(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");

        // Retrieve user by phone number
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid phone number");
        }

        User user = userOptional.get();

        // Generate OTP and send it to the user's phone number
        String otp = otpService.generateOTP();
        otpService.sendOTP(phoneNumber, otp);

        // Update user with the new OTP
        user.setOtp(otp);
        userRepository.save(user);

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOTP(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String otp = request.get("otp");

        // Retrieve user by phone number
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid phone number");
        }

        User user = userOptional.get();

        // Check if OTP matches
        if (otp.equals(user.getOtp())) {
            // OTP is valid, allow the user to log in
            return ResponseEntity.ok("OTP verification successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
}
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        // Return the dashboard data to the frontend
        return ResponseEntity.ok("Welcome to the dashboard!");
    }
}