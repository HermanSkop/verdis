package com.verdis.services;

import com.verdis.config.AppConfig;
import com.verdis.dtos.CreateDiscussionDto;
import com.verdis.dtos.DiscussionDto;
import com.verdis.mappers.DiscussionMapper;
import com.verdis.dtos.DiscussionPreviewDto;
import com.verdis.models.Discussion;
import com.verdis.models.account.Account;
import com.verdis.repositories.DiscussionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Pageable pageable = PageRequest.of(page, AppConfig.PAGE_SIZE);
        return discussionRepository.findAll(pageable).map(discussionMapper::toPreviewDto);
    }

    public DiscussionDto getDiscussion(Long id) {
        return discussionRepository.findById(id).map(discussionMapper::toDto).orElse(null);
    }

    public void createDiscussion(CreateDiscussionDto createDiscussionDto) {
        Account author = accountService.getAccount(createDiscussionDto.getAuthor().getId());
        Discussion discussion = discussionMapper.fromCreateDto(createDiscussionDto);
        discussion.setAuthor(author);
        discussionRepository.save(discussion);
    }
}
