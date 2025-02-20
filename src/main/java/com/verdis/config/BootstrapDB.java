package com.verdis.config;

import com.verdis.config.security.PasswordEncoder;
import com.verdis.models.Comment;
import com.verdis.models.Discussion;
import com.verdis.models.account.Admin;
import com.verdis.models.account.User;
import com.verdis.repositories.AdminRepository;
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
    private final AdminRepository adminRepository;

    @Autowired
    public BootstrapDB(UserRepository userRepository, CommentRepository commentRepository, DiscussionRepository discussionRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.discussionRepository = discussionRepository;
        this.adminRepository = adminRepository;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        if (userRepository.count() == 0) {
            User user1 = User.builder().username("user").password(PasswordEncoder.encryptPassword("User1234")).email("exampleuser@mail.com")
                    .archivedDiscussions(new ArrayList<>()).comments(new ArrayList<>()).build();
            Admin admin = Admin.builder().username("admin").password(PasswordEncoder.encryptPassword("Admin1234")).email("exampleadmin@mail.com")
                    .archivedDiscussions(new ArrayList<>()).build();

            Discussion discussion1 = Discussion.builder().title("Discussion 1").content("This is a discussion.")
                    .author(user1).build();
            Comment comment1 = Comment.builder().content("This is a comment.").discussion(discussion1).author(user1).build();

            Discussion discussion2 = Discussion.builder().title("Discussion 2").content("This is another discussion.")
                    .author(admin).archivedBy(admin).build();
            Comment comment2 = Comment.builder().content("This is another comment.").discussion(discussion2).author(user1).build();

            discussion1.setComments(List.of(comment1));
            user1.setComments(List.of(comment1));

            userRepository.save(user1);
            adminRepository.save(admin);
            discussionRepository.save(discussion1);
            discussionRepository.save(discussion2);
            commentRepository.save(comment1);

            System.out.println("Database initialized with sample data.");
        }
    }
}
