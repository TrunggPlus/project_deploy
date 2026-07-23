package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Answer_Bookmarks")
@IdClass(AnswerBookmark.AnswerBookmarkId.class)
public class AnswerBookmark {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "answer_id", nullable = false)
    private Long answerId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public AnswerBookmark() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class AnswerBookmarkId implements Serializable {
        private Long userId;
        private Long answerId;

        public AnswerBookmarkId() {
        }

        public AnswerBookmarkId(Long userId, Long answerId) {
            this.userId = userId;
            this.answerId = answerId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AnswerBookmarkId that = (AnswerBookmarkId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(answerId, that.answerId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, answerId);
        }
    }
}
