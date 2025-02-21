package com.verdis.services;

import com.verdis.config.AppConfig;
import com.verdis.dtos.AccountDto;
import com.verdis.dtos.CreateDiscussionDto;
import com.verdis.dtos.DiscussionDto;
import com.verdis.mappers.DiscussionMapper;
import com.verdis.dtos.DiscussionPreviewDto;
import com.verdis.models.Comment;
import com.verdis.models.Discussion;
import com.verdis.models.account.Account;
import com.verdis.models.account.Admin;
import com.verdis.repositories.DiscussionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionMapper discussionMapper;
    private final AccountService accountService;

    public DiscussionService(DiscussionRepository discussionRepository, DiscussionMapper discussionMapper, AccountService accountService) {
        this.discussionRepository = discussionRepository;
        this.discussionMapper = discussionMapper;
        this.accountService = accountService;
    }

    public Page<DiscussionPreviewDto> getDiscussions(int page) {
        Pageable pageable = PageRequest.of(page, AppConfig.PAGE_SIZE, Sort.Direction.DESC, "updatedDateTime");
        return discussionRepository.findAll(pageable).map(discussionMapper::toPreviewDto);
    }

    public DiscussionDto getDiscussion(Long id) {
        return discussionRepository.findById(id).map(discussionMapper::toDto).orElseThrow(() -> new IllegalArgumentException("Discussion not found"));
    }

    public void createDiscussion(CreateDiscussionDto createDiscussionDto) {
        Account author = accountService.getAccount(createDiscussionDto.getAuthor().getId());
        Discussion discussion = discussionMapper.fromCreateDto(createDiscussionDto);
        discussion.setAuthor(author);
        discussionRepository.save(discussion);
    }

    public void createComment(Long id, String content, AccountDto user) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Discussion not found"));
        Account author = accountService.getAccount(user.getId());
        List<Comment> comments = discussion.getComments();
        comments.add(Comment.builder().content(content).author(author).discussion(discussion).build());
        discussion.setComments(comments);
        discussionRepository.save(discussion);
    }

    public void archiveDiscussion(Long id, AccountDto user) {
        Discussion discussion = discussionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Discussion not found"));
        Account archiver = accountService.getAccount(user.getId());
        if (!discussion.getAuthor().equals(archiver) && !(archiver instanceof Admin))
            throw new IllegalArgumentException("Only the author or an admin can archive a discussion");


        discussion.setArchivedBy(archiver);
        discussionRepository.save(discussion);
    }

    public void deleteComment(Long discussionId, Long commentId, AccountDto user) {
        Discussion discussion = discussionRepository.findById(discussionId).orElseThrow(() -> new IllegalArgumentException("Discussion not found"));
        Account deleter = accountService.getAccount(user.getId());
        if (!(deleter instanceof Admin))
            throw new IllegalArgumentException("Only an admin can delete a comment");

        List<Comment> comments = discussion.getComments();
        comments.removeIf(comment -> comment.getId().equals(commentId));
        discussion.setComments(comments);
        discussionRepository.save(discussion);
    }
}
