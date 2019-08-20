package com.woowacourse.zzazanstagram.model.member.dto;

public class MemberResponse {
    private Long id;
    private String nickName;
    private String name;
    private String email;
    private String profileImage;

    public MemberResponse(Long id, String nickName, String name, String email, String profileImage) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
