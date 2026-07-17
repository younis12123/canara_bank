package com.serviceimpl;
import com.dao.UserRepository;
import com.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepo ;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Users user = userRepo.findByUserName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + userName);
        }

        return User.builder().username(user.getUserName()).password(user.getPassword()).build();
    }
}
