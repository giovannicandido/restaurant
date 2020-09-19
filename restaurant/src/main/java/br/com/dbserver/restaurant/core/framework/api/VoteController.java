package br.com.dbserver.restaurant.core.framework.api;

import java.security.Principal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbserver.restaurant.core.application.usercase.VoteUseCase;

@RestController
@RequestMapping("${v1Api}/vote")
public class VoteController {
    private final VoteUseCase voteUseCase;

    public VoteController(VoteUseCase voteUseCase) {
        this.voteUseCase = voteUseCase;
    }

    @PostMapping
    public void postVote(@RequestBody Long restaurantId, Principal principal) {
        voteUseCase.vote(restaurantId, principal.getName());
    }
}
