package com.example.LeftMenuBar.backcode;

/**
 * Created by MaDy on 18/03/2022 / 20:44
 * <p>
 * This was by help from of a youtuber!
 * <p>
 * Helped for image shapes, firebase, database, connection, storage
 * <p>
 * From the logic of it will work on the main project!
 **/
public class Posts {


    private String imagePost, postText, userProfileImage, usernameID;
    String date;


    public Posts(String date, String imagePost, String postText, String userProfileImage, String usernameID) {
        this.date = date;
        this.imagePost = imagePost;
        this.postText = postText;
        this.userProfileImage = userProfileImage;
        this.usernameID = usernameID;
    }

    public Posts() {
    }

    public String getDate() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public String getImagePost() {
        return imagePost;
    }

    public void setImagePost(String imagePost) {
        this.imagePost = imagePost;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getUsernameID() {
        return usernameID;
    }

    public void setUsernameID(String usernameID) {
        this.usernameID = usernameID;
    }
}
