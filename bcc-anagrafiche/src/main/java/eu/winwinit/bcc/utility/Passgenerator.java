package eu.winwinit.bcc.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Passgenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();       
		System.out.println("admin --> "+bCryptPasswordEncoder.encode("admin"));
		System.out.println("user --> "+bCryptPasswordEncoder.encode("user"));
		System.out.println("newuser --> "+bCryptPasswordEncoder.encode("newuser"));
		System.out.println("inactive --> "+bCryptPasswordEncoder.encode("inactive"));
	}
}
