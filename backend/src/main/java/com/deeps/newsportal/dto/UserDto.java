package com.deeps.newsportal.dto;

import java.util.List;

import com.deeps.newsportal.entity.Articles;

public class UserDto {

	private String id;
	private String fullName;
	private String email;
	private String createdAt;
	private String updatedAt;
	private String profilePic;
	private List<Articles> articles;

	public UserDto(String id, String fullName, String email, String createdAt, String updatedAt, String profilePic,
			List<Articles> articles) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.profilePic = profilePic;
		this.articles = articles;
	}

	public UserDto() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public List<Articles> getArticles() {
		return articles;
	}

	public void setArticles(List<Articles> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", fullName=" + fullName + ", email=" + email + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", profilePic=" + profilePic + ", articles=" + articles + "]";
	}

}
