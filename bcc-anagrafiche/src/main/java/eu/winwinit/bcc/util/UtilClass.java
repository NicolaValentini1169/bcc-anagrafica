package eu.winwinit.bcc.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class UtilClass {

	
	public static Set<String> fromGrantedAuthorityToStringSet(Set<GrantedAuthority> grantedAuthoritySet) {
		Set<String> rolesSetString = new HashSet<>();
		for (GrantedAuthority grantedAuthority: grantedAuthoritySet) {
			 rolesSetString.add(grantedAuthority.toString());
		}
		return rolesSetString;
	}
}
