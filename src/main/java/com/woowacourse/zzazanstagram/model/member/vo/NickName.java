package com.woowacourse.zzazanstagram.model.member.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NickName {
    private static final String NICK_NAME_REGEX = "[A-Za-zㄱ-ㅎㅏ-ㅣ가-힣]{2,10}";

    @Column(name = "nickname")
    private String nickName;

    private NickName(final String name) {
        this.nickName = validateName(name);
    }

    private NickName() {
    }

    public static NickName of(final String name) {
        return new NickName(name);
    }

    private String validateName(final String name) {
        if (isMismatch(name)) {
            throw new IllegalArgumentException("이름은 2자 이상 10자 이하입니다.");
        }
        return name;
    }

    private boolean isMismatch(String name) {
        return !name.matches(NICK_NAME_REGEX);
    }

    public NickName updateName(String name) {
        return new NickName(name);
    }

    public String getNickName() {
        return nickName;
    }
}
