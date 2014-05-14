package org.celllife.stock.application.security;

import org.celllife.stock.domain.user.User;
import org.celllife.stock.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MsisdnUserDetailsService implements UserDetailsService {
	
	//private static Logger log = LoggerFactory.getLogger(MsisdnUserDetailsService.class);
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findOneByMsisdn(username);
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user with msisdn "+username);
		}
		return new MsisdnUserDetails(user);
	}

}
