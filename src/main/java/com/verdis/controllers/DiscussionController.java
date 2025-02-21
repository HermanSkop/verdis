package com.verdis.controllers;

import com.verdis.dtos.AccountDto;
import com.verdis.dtos.CreateDiscussionDto;
import com.verdis.dtos.DiscussionDto;
import com.verdis.dtos.DiscussionPreviewDto;
import com.verdis.services.AccountService;
import com.verdis.services.DiscussionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscussionController {
    private final DiscussionService discussionService;
    private final AccountService accountService;

    public DiscussionController(DiscussionService discussionService, AccountService accountService) {
        this.discussionService = discussionService;
        this.accountService = accountService;
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

    @PostMapping("/discussion/{id}/comment")
    public String comment(@PathVariable("id") Long id, @RequestParam("content") String content, HttpSession session) {
        discussionService.createComment(id, content, (AccountDto) session.getAttribute("user"));
        return "redirect:/discussion/" + id;
    }

    @GetMapping("/discussion/create")
    public String createDiscussion(Model model) {
        model.addAttribute("discussion", new DiscussionDto());
        return "createDiscussion";
    }

    @PostMapping("/discussion/create")
    public String createDiscussion(CreateDiscussionDto createDiscussionDto, HttpSession session) {
        createDiscussionDto.setAuthor((AccountDto) session.getAttribute("user"));
        discussionService.createDiscussion(createDiscussionDto);
        return "redirect:/";
    }

}
