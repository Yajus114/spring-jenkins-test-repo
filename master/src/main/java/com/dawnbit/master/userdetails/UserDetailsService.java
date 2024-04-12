package com.dawnbit.master.userdetails;

import com.dawnbit.entity.master.UserDetails;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.UserDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserDetailsService {
    ResponseEntity<UserDetails> saveUserDetails(UserDetailsDTO userDetailsDTO);

    List<UserDetails> getAllUser();

    Optional<UserDetails> getUserById(Long id);

    List<UserDetails> getUserBasedOnEmailId(String emailId, String orgId) throws Exception;

    Page<?> getUsersDetails(SearchModelDTO searchModel);

    Page<?> fetchUsersLoginData(SearchModelDTO searchModel);

    boolean isUserIdExists(String userId);
}
