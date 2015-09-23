package com.example.astrohackathon.model;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by RoMY on 10/11/2014.
 */
public class User {
    String id;
    String email;
    String first_name;
    String last_name;
    String token;
    String avatar;

    public User() {

    }

    public User(JSONObject json) {
        try {
            if(json.has("id")) {
                this.id = json.getString("id");
            }

            if(json.has("email")) {
                this.email = json.getString("email");

            }

            if(json.has("first_name")) {
                this.first_name = json.getString("first_name");

            }

            if(json.has("last_name")) {
                this.last_name = json.getString("last_name");

            }

            if(json.has("token")) {
                this.token = json.getString("token");

            }

            if(json.has("avatar")) {
                this.avatar = json.getString("avatar");

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
