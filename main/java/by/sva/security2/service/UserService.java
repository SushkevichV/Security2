package by.sva.security2.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.sva.security2.entity.Role;
import by.sva.security2.entity.User;
import by.sva.security2.repository.RoleRepository;
import by.sva.security2.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@PersistenceContext
	private EntityManager em; // позволяет делать SQL-запросы напрямую
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*
	//public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		//this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}
	*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			throw new  UsernameNotFoundException("User not found");
		}
		return user;
	}
	
	public User findUserById(long id) {
		Optional<User> userFromDb = userRepository.findById(id);
		return userFromDb.orElse(new User());
	}
	
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public boolean addUser(User user) {
		User userFromDb = userRepository.findByUsername(user.getUsername());
		if(userFromDb != null) {
			return false;
		}
		user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
		//user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return true;
	}
	
	public boolean deleteUser(long id) {
		if(userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public List<User> usergtList(long idMin) {
		// выполнение SQL-запроса с помощью EntityManager
		return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class) //получить пользователей с id большим заданного
				.setParameter("paramId", idMin).getResultList();
	}

}
