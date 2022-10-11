package by.sva.security2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.sva.security2.entity.User;

public interface UserRepository extends JpaRepository<User, Long> { // тип возвращаемого объекта и тип его id
	
	User findByUsername(String username);

}
