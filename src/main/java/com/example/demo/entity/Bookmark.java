package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Bookmarks")
@IdClass(Bookmark.BookmarkId.class)
public class Bookmark {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "collection_id")
    private Integer collectionId;

    public Bookmark() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public static class BookmarkId implements Serializable {
        private Long userId;
        private Long questionId;

        public BookmarkId() {
        }

        public BookmarkId(Long userId, Long questionId) {
            this.userId = userId;
            this.questionId = questionId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BookmarkId that = (BookmarkId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(questionId, that.questionId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, questionId);
        }
    }
}
