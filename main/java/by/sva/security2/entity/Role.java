package by.sva.security2.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
	@Id
	private long id;
	private String name;
	
	@Transient
	@ManyToMany(mappedBy = "roles") //должно соответствовать имени поля в классе User
	private Set<User> users;
	
	public Role() {
		
	}
	
	public Role(long id) {
		this.id = id;
	}
	
	public Role(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String getAuthority() {
		return getName();
	}

}
