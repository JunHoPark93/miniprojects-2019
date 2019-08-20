package com.woowacourse.zzazanstagram.model.article.domain;

import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Image;
import com.woowacourse.zzazanstagram.model.comment.domain.Comment;
import com.woowacourse.zzazanstagram.model.common.BaseEntity;
import com.woowacourse.zzazanstagram.model.member.domain.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Article extends BaseEntity {
    private Image image;
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false, foreignKey = @ForeignKey(name = "fk_article_to_member"))
    private Member author;

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Comment> comments;

    protected Article() {
    }

    public Article (final Image image, final Contents contents, final Member author) {
        this.image = image;
        this.contents = contents;
        this.author = author;
        this.comments = new ArrayList<>();
    }

    public Image getImage() {
        return image;
    }

    public Contents getContents() {
        return contents;
    }

    public String getImageValue() {
        return image.getUrl();
    }

    public String getContentsValue() {
        return contents.getContents();
    }

    public Member getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComments(Comment comment) {
        comments.add(comment);
    }


}