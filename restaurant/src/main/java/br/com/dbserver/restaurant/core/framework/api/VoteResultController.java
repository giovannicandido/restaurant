package br.com.dbserver.restaurant.core.framework.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbserver.restaurant.core.application.dto.VoteResultListDto;
import br.com.dbserver.restaurant.core.application.usercase.ListVoteResultUseCase;
import br.com.dbserver.restaurant.core.domain.service.VoteResultService;

@RestController
@RequestMapping("${v1Api}/vote-result")
public class VoteResultController {
    private final ListVoteResultUseCase listVoteResultUseCase;
    private final VoteResultService voteResultService;

    public VoteResultController(ListVoteResultUseCase listVoteResultUseCase, VoteResultService voteResultService) {
        this.listVoteResultUseCase = listVoteResultUseCase;
        this.voteResultService = voteResultService;
    }

    @GetMapping
    public List<VoteResultListDto> getAllThisWeek() {
        return listVoteResultUseCase.list();
    }

    @PostMapping("/process")
    public void processResult() {
        voteResultService.processVoteOfThisDay();
    }
}
