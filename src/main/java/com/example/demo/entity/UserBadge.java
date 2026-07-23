package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "User_Badges")
@IdClass(UserBadge.UserBadgeId.class)
public class UserBadge {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public UserBadge() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class UserBadgeId implements Serializable {
        private Long userId;
        private Long badgeId;

        public UserBadgeId() {
        }

        public UserBadgeId(Long userId, Long badgeId) {
            this.userId = userId;
            this.badgeId = badgeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserBadgeId that = (UserBadgeId) o;
            return Objects.equals(userId, that.userId) && Objects.equals(badgeId, that.badgeId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, badgeId);
        }
    }
}
