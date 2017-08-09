package source.test;

import source.utils.index;

public class Entity {
	@index(1)
	private String id;
	@index(2)
	private String name;
	@index(3)
	private String age;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Entity(String id, String name, String age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
}
