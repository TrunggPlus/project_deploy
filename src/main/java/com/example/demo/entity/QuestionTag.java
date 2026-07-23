package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Question_Tags")
@IdClass(QuestionTag.QuestionTagId.class)
public class QuestionTag {

    @Id
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Id
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    public QuestionTag() {
    }

    public QuestionTag(Long questionId, Long tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public static class QuestionTagId implements Serializable {
        private Long questionId;
        private Long tagId;

        public QuestionTagId() {
        }

        public QuestionTagId(Long questionId, Long tagId) {
            this.questionId = questionId;
            this.tagId = tagId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QuestionTagId that = (QuestionTagId) o;
            return Objects.equals(questionId, that.questionId) && Objects.equals(tagId, that.tagId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(questionId, tagId);
        }
    }
}
