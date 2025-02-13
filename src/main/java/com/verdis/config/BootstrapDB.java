package com.verdis.config;

import com.verdis.models.Comment;
import com.verdis.models.Discussion;
import com.verdis.models.account.User;
import com.verdis.repositories.CommentRepository;
import com.verdis.repositories.DiscussionRepository;
import com.verdis.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapDB {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final DiscussionRepository discussionRepository;

    @Autowired
    public BootstrapDB(UserRepository userRepository, CommentRepository commentRepository, DiscussionRepository discussionRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.discussionRepository = discussionRepository;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        if (userRepository.count() == 0) {
            User user1 = User.builder().username("admin").password("Admin123").email("exampleadmin@mail.com")
                    .archivedDiscussions(new ArrayList<>()).comments(new ArrayList<>()).build();
            User user2 = User.builder().username("user").password("User123").email("exampleuser@mail.com")
                    .archivedDiscussions(new ArrayList<>()).comments(new ArrayList<>()).build();

            Discussion discussion1 = Discussion.builder().title("Discussion 1").content("This is a discussion.")
                    .author(user1).build();
            Comment comment1 = Comment.builder().content("This is a comment.").discussion(discussion1).user(user1).build();

            discussion1.setComments(List.of(comment1));
            user1.setComments(List.of(comment1));

            userRepository.save(user1);
            userRepository.save(user2);
            discussionRepository.save(discussion1);
            commentRepository.save(comment1);

            System.out.println("Database initialized with sample data.");
        }
    }
}
