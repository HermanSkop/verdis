package com.verdis.controllers;

import com.verdis.dtos.DiscussionPreviewDto;
import com.verdis.services.DiscussionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscussionController {
    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @GetMapping("/")
    public String discussions(Model model, @RequestParam(defaultValue = "0", name = "page") int page) {
        Page<DiscussionPreviewDto> discussionPage = discussionService.getDiscussions(page);

        model.addAttribute("discussions", discussionPage.getContent());
        model.addAttribute("discussionPage", discussionPage);

        return "discussions";
    }

    @GetMapping("/discussion/{id}")
    public String discussion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("discussion", discussionService.getDiscussion(id));
        return "discussion";
    }


}
