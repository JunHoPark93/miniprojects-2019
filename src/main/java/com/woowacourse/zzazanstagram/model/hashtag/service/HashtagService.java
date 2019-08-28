package com.woowacourse.zzazanstagram.model.hashtag.service;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.hashtag.domain.ArticleHashtag;
import com.woowacourse.zzazanstagram.model.hashtag.domain.Hashtag;
import com.woowacourse.zzazanstagram.model.hashtag.repository.ArticleHashtagRepository;
import com.woowacourse.zzazanstagram.model.hashtag.repository.HashtagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashtagService {
    private final ArticleHashtagRepository articleHashtagRepository;
    private final HashtagRepository hashtagRepository;

    public HashtagService(final ArticleHashtagRepository articleHashtagRepository, final HashtagRepository hashtagRepository) {
        this.articleHashtagRepository = articleHashtagRepository;
        this.hashtagRepository = hashtagRepository;
    }

    public List<ArticleHashtag> save(Article article) {
        List<ArticleHashtag> articleHashtags = extractHashTagsFrom(article);
        return articleHashtagRepository.saveAll(articleHashtags);
    }

    private List<ArticleHashtag> extractHashTagsFrom(Article article) {
        return Collections.unmodifiableList(
                article.extractTagKeywords()
                        .stream()
                        .map(h -> hashtagRepository.findByKeyword(h.getKeyword())
                                .map(hashtag -> new ArticleHashtag(article, hashtag))
                                .orElseGet(() -> {
                                    Hashtag hashtag = hashtagRepository.save(h);
                                    return new ArticleHashtag(article, hashtag);
                                })
                        )
                        .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public List<ArticleHashtag> findAllByHashtag(String keyword) {
        Hashtag hashtag = hashtagRepository.findByKeyword(keyword)
                .orElseThrow(() -> new HashTagException("해당 해시태그에 대한 게시글이 존재하지 않습니다."));

        return articleHashtagRepository.findAllByHashtagOrderByArticleCreatedDateDesc(hashtag);
    }
}
