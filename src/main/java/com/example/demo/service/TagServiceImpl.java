package com.example.demo.service;

import com.example.demo.dto.TagDTO;
import com.example.demo.dto.QuestionViewDTO;
import com.example.demo.entity.Tag;
import com.example.demo.entity.TagFollow;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.TagFollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;
    private final TagFollowRepository tagFollowRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, QuestionRepository questionRepository, TagFollowRepository tagFollowRepository) {
        this.tagRepository = tagRepository;
        this.questionRepository = questionRepository;
        this.tagFollowRepository = tagFollowRepository;
    }

    @Override
    public List<TagDTO> searchAndSortTags(String keyword, String sort) {
        List<Object[]> results = tagRepository.searchAndSortTagsNative(keyword, sort);
        List<TagDTO> dtos = new ArrayList<>();
        for (Object[] row : results) {
            dtos.add(mapRowToTagDTO(row));
        }
        return dtos;
    }

    @Override
    public TagDTO getTagById(Long id) {
        List<Object[]> results = tagRepository.findTagByIdNative(id);
        if (results != null && !results.isEmpty()) {
            return mapRowToTagDTO(results.get(0));
        }
        return null;
    }

    @Override
    public List<QuestionViewDTO> getQuestionsByTag(Long tagId, String filter, int page, int pageSize) {
        List<Object[]> results = questionRepository.findQuestionsByTagNative(tagId, filter, org.springframework.data.domain.PageRequest.of(page - 1, pageSize));
        List<QuestionViewDTO> questions = new ArrayList<>();
        for (Object[] row : results) {
            Long qId = ((Number) row[0]).longValue();
            String title = (String) row[1];
            String body = (String) row[2];
            Integer score = ((Number) row[3]).intValue();
            Integer viewCount = ((Number) row[4]).intValue();
            Timestamp createdAt = null;
            if (row[5] instanceof Timestamp ts) {
                createdAt = ts;
            } else if (row[5] instanceof java.time.LocalDateTime ldt) {
                createdAt = Timestamp.valueOf(ldt);
            } else if (row[5] instanceof java.util.Date d) {
                createdAt = new Timestamp(d.getTime());
            }

            Timestamp updatedAt = null;
            if (row[6] instanceof Timestamp ts) {
                updatedAt = ts;
            } else if (row[6] instanceof java.time.LocalDateTime ldt) {
                updatedAt = Timestamp.valueOf(ldt);
            } else if (row[6] instanceof java.util.Date d) {
                updatedAt = new Timestamp(d.getTime());
            }

            Boolean isClosed = false;
            if (row[7] instanceof Boolean b) {
                isClosed = b;
            } else if (row[7] instanceof Number n) {
                isClosed = n.intValue() != 0;
            }

            String authorName = (String) row[8];
            String authorAvatar = (String) row[9];
            Integer answerCount = row[10] != null ? ((Number) row[10]).intValue() : 0;

            List<String> tags = questionRepository.findTagNamesByQuestionIdNative(qId);

            questions.add(new QuestionViewDTO(qId, title, body, score, viewCount, createdAt, updatedAt, isClosed, authorName, authorAvatar, answerCount, tags));
        }
        return questions;
    }

    @Override
    public int countQuestionsByTag(Long tagId, String filter) {
        return questionRepository.countQuestionsByTagNative(tagId, filter);
    }

    @Override
    public void followOrUnfollowTag(Long userId, Long tagId, String action) {
        Optional<TagFollow> followOpt = tagFollowRepository.findByUserIdAndTagId(userId, tagId);
        if ("follow".equalsIgnoreCase(action)) {
            if (followOpt.isEmpty()) {
                tagFollowRepository.save(new TagFollow(userId, tagId));
            }
        } else if ("unfollow".equalsIgnoreCase(action)) {
            followOpt.ifPresent(tagFollowRepository::delete);
        }
    }

    @Override
    public boolean isFollowing(Long userId, Long tagId) {
        return tagFollowRepository.existsByUserIdAndTagId(userId, tagId);
    }

    private TagDTO mapRowToTagDTO(Object[] row) {
        Long id = ((Number) row[0]).longValue();
        String tagName = (String) row[1];
        String description = (String) row[2];
        Boolean isActive = false;
        if (row[3] instanceof Boolean b) {
            isActive = b;
        } else if (row[3] instanceof Number n) {
            isActive = n.intValue() != 0;
        }
        Integer questionCount = row[4] != null ? ((Number) row[4]).intValue() : 0;
        Integer followerCount = row[5] != null ? ((Number) row[5]).intValue() : 0;

        return new TagDTO(id, tagName, description, isActive, questionCount, followerCount);
    }

    @Override
    public void createTag(String tagName, String description) {
        String cleanTagName = tagName.trim().toLowerCase();
        Optional<Tag> existing = tagRepository.findByTagNameIgnoreCase(cleanTagName);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Tag already exists");
        }
        Tag tag = new Tag();
        tag.setTagName(cleanTagName);
        tag.setDescription(description != null ? description.trim() : "");
        tag.setIsActive(true);
        tagRepository.save(tag);
    }
}
