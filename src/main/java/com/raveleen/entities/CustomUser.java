package com.raveleen.entities;

import com.raveleen.entities.enums.UserRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Святослав on 31.12.2016.
 */
@Entity
public class CustomUser {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 16)
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(length = 35)
    private String email;
    @Column(length = 18)
    private String phone;
    @Column(length = 250)
    private String info;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_relations",
            joinColumns = @JoinColumn(name = "followed_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "follower_ID", referencedColumnName = "id"))
    private List<CustomUser> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<CustomUser> following = new ArrayList<>();

    @ManyToMany(mappedBy = "likedBy")
    private List<Post> likedPosts = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "id")
    private List<Dialog> dialogs = new ArrayList<>();

    public CustomUser(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public CustomUser(String login, String password, UserRole role, String email, String phone) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public CustomUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /*
    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }*/

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public List<CustomUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<CustomUser> followers) {
        this.followers = followers;
    }

    public List<CustomUser> getFollowing() {
        return following;
    }

    public void setFollowing(List<CustomUser> following) {
        this.following = following;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public void addFollower(CustomUser customUser) {
        followers.add(customUser);
    }

    public void deleteFollower(CustomUser customUser) {
        followers.remove(customUser);
    }

    public int getNumberOfFollowers() {
        return followers.size();
    }

    public int getNumberOfFollowings() {
        return following.size();
    }

    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }
}
