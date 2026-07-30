package com.example.demo.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DevQueryAdminStaticTools {

    private final JdbcTemplate jdbcTemplate;

    public DevQueryAdminStaticTools(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // --- 1. Users Module ---

    @Tool(description = "Retrieves the total number of registered users and the number of new users who registered today. Useful for providing an overview of forum growth and user acquisition to the admin.")
    public Object getGeneralUserStats() {
        try {
            String sql = "SELECT COUNT(*) AS TotalUsers, " +
                    "SUM(CASE WHEN CAST(created_at AS DATE) = CURRENT_DATE() THEN 1 ELSE 0 END) AS NewUsersToday "
                    +
                    "FROM Users";
            return jdbcTemplate.queryForMap(sql);
        } catch (DataAccessException e) {
            return "Error retrieving general user stats. Please notify the database administrator.";
        }
    }

    @Tool(description = "Retrieves a list of the top 10 most recently banned users from the system, including the ban reason and timestamp. Useful for tracking moderation actions and penalized users.")
    public Object getRecentBannedUsers() {
        try {
            String sql = "SELECT user_id AS UserId, username AS Username, status " +
                    "FROM Users " +
                    "WHERE status = 'banned' " +
                    "ORDER BY created_at DESC LIMIT 10";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving recent banned users. Please check the database connection.";
        }
    }

    // --- 2. Tags Module ---

    @Tool(description = "Retrieves the top 10 most frequently used programming tags in the forum. Useful for understanding what topics and technologies are currently popular or trending among developers.")
    public Object getTrendingTags() {
        try {
            String sql = "SELECT tag_id AS TagId, tag_name AS TagName " +
                    "FROM Tags " +
                    "LIMIT 10";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving trending tags from the database.";
        }
    }

    // --- 3. Reports Module ---

    @Tool(description = "Retrieves a list of unresolved user reports (e.g., spam, community guideline violations) that currently require administrator attention and resolution.")
    public Object getPendingReports() {
        try {
            String sql = "SELECT report_id AS ReportId, reporter_id AS ReporterId, reported_item_id AS ReportedItemId, report_type AS ReportType, reason AS Reason, created_at AS CreatedAt " +
                    "FROM Report " +
                    "WHERE status = 'Pending' " +
                    "ORDER BY created_at ASC";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving pending reports. The reports table might be inaccessible.";
        }
    }

    // --- 4. Rules Module ---

    @Tool(description = "Retrieves the current list of active forum rules and their corresponding violation penalties. Useful for verifying community guidelines when moderating or answering questions.")
    public Object getForumRules() {
        try {
            String sql = "SELECT rule_id AS RuleId, title AS Title, content AS Description " +
                    "FROM Rule " +
                    "ORDER BY rule_id ASC";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving forum rules from the database.";
        }
    }

    // --- 5. Badges Module ---

    @Tool(description = "Retrieves a list of all available gamification badges in the system along with their criteria and required points. Useful for explaining to users how to earn specific achievements.")
    public Object getSystemBadges() {
        try {
            String sql = "SELECT badge_id AS BadgeId, name AS Name, description AS Description, required_reputation AS RequiredPoints " +
                    "FROM Badges " +
                    "ORDER BY required_reputation ASC";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving system badges. Gamification data is currently unavailable.";
        }
    }

    // --- 6. Blogs Module ---

    @Tool(description = "Retrieves the top 5 most recently published blog posts authored by administrators or moderators. Useful for reviewing recent official announcements or articles.")
    public Object getRecentAdminBlogs() {
        try {
            String sql = "SELECT blog_id AS BlogId, title AS Title, author_id AS AuthorId, created_at AS PublishedAt " +
                    "FROM Blog " +
                    "ORDER BY created_at DESC LIMIT 5";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving recent admin blogs.";
        }
    }

    // --- 7. Feedbacks Module ---

    @Tool(description = "Retrieves a list of pending user feedbacks or feature requests submitted by community members that have not yet been addressed by the administration team.")
    public Object getUnresolvedFeedbacks() {
        try {
            String sql = "SELECT feedback_id AS FeedbackId, user_id AS UserId, content AS Content, created_at AS SubmittedAt " +
                    "FROM Feedback " +
                    "ORDER BY created_at ASC";
            return jdbcTemplate.queryForList(sql);
        } catch (DataAccessException e) {
            return "Error retrieving unresolved feedbacks from the database.";
        }
    }
}
