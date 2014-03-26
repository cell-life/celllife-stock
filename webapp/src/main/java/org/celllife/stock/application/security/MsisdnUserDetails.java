package org.celllife.stock.application.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MsisdnUserDetails extends User {
	
	//private static Logger log = LoggerFactory.getLogger(MsisdnUserDetails.class);

	private static final long serialVersionUID = 655111608903564631L;
	private String salt;
	
	private static List<SimpleGrantedAuthority> defaultAuths = new ArrayList<SimpleGrantedAuthority>();
	static {
		defaultAuths.add(new SimpleGrantedAuthority("STOCK"));
	}

	public MsisdnUserDetails(org.celllife.stock.domain.user.User user) {
		super(user.getMsisdn(), user.getEncryptedPassword(), defaultAuths);
		this.salt = user.getSalt();
	}

	public String getSalt() {
		return salt;
	}
}
