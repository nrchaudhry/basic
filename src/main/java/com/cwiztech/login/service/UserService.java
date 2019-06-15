package com.cwiztech.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwiztech.login.model.LoginUser;
import com.cwiztech.login.repository.loginUserRepository;

@Service("userDetailsService")
public class UserService implements UserDetailsService {
	@Autowired
	private loginUserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		LoginUser loginuser = userRepository.getUser(username);
		if (loginuser != null) {
            return loginuser;
        }
        throw new UsernameNotFoundException(username);
	}
}