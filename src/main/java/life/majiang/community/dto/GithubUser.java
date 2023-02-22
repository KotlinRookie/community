package life.majiang.community.dto;

public class GithubUser {
	private String name;
	private Long id;
	private String bio;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDio() {
		return bio;
	}
	public void setDio(String bio) {
		this.bio = bio;
	}
	@Override
	public String toString() {
		return "GithubUser [name=" + name + ", id=" + id + ", bio=" + bio + "]";
	}
	
}
