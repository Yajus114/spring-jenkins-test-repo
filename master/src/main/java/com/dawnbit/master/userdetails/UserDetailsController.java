package com.dawnbit.master.userdetails;

import com.dawnbit.entity.master.UserDetails;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api")
public class UserDetailsController {
    @Autowired
    UserDetailsService userDetailsService;

    /**
     * Saves user details for a given employee with specified permission groups.
     *
     * @param userDetailsDTO
     * @return UserDetails
     * @author DB-0113,name=Bharti Madaan,id=bharti.madaan@dawnbit.com,Ravi Kumar
     */
    @PostMapping("/save/userDetails")
    public ResponseEntity<Map<String, Object>> saveUserDetails(@RequestBody UserDetailsDTO userDetailsDTO) {
        final Map<String, Object> map = new HashMap<>();
        map.put("User Detail", userDetailsService.saveUserDetails(userDetailsDTO));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/get/allUserDetails")
    public List<UserDetails> getAllUser() {
        return userDetailsService.getAllUser();
    }

    @GetMapping("/get/userDetails")
    public ResponseEntity<UserDetails> getUserById(@RequestParam Long id) {
        return userDetailsService.getUserById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/checkEmailDuplicacy")
    public ResponseEntity<Map<String, Object>> checkEmailDuplicacy(@RequestParam String emailId, @RequestParam String orgId) throws Exception {
        List<UserDetails> users = this.userDetailsService.getUserBasedOnEmailId(emailId, orgId);

        Map<String, Object> map = new HashMap<>();
        if (users.isEmpty()) {
            map.put("userEmail", "validEmail");
        } else {
            map.put("userEmail", "duplicateEmail");
        }

        return ResponseEntity.ok(map);
    }

    @GetMapping("/checkUserIdDuplication")
    public boolean checkUserIdDuplication(@RequestParam String userId) {
        return userDetailsService.isUserIdExists(userId);
    }

    @PostMapping("/fetchUsersData")
    public ResponseEntity<Map<String, Object>> getUsersDetails(@RequestBody SearchModelDTO searchModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", this.userDetailsService.getUsersDetails(searchModel));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/fetchUsersLoginData")
    public ResponseEntity<Map<String, Object>> fetchUsersLoginData(@RequestBody SearchModelDTO searchModel) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", this.userDetailsService.fetchUsersLoginData(searchModel));
        return ResponseEntity.ok(map);
    }
}
