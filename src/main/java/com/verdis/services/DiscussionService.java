package com.verdis.services;

import com.verdis.config.AppConfig;
import com.verdis.dtos.DiscussionDto;
import com.verdis.mappers.DiscussionMapper;
import com.verdis.dtos.DiscussionPreviewDto;
import com.verdis.repositories.DiscussionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionMapper discussionMapper;

    public DiscussionService(DiscussionRepository discussionRepository, DiscussionMapper discussionMapper) {
        this.discussionRepository = discussionRepository;
        this.discussionMapper = discussionMapper;
    }

    public Page<DiscussionPreviewDto> getDiscussions(int page) {
        Pageable pageable = PageRequest.of(page, AppConfig.PAGE_SIZE);
        return discussionRepository.findAll(pageable).map(discussionMapper::toPreviewDto);
    }

    public DiscussionDto getDiscussion(Long id) {
        return discussionRepository.findById(id).map(discussionMapper::toDto).orElse(null);
    }
}
