package com.bolad.polls.controller;


import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bolad.polls.model.Poll;
import com.bolad.polls.payload.ApiResponse;
import com.bolad.polls.payload.PollRequest;
import com.bolad.polls.payload.PollResponse;
import com.bolad.polls.repository.PollRepository;
import com.bolad.polls.repository.UserRepository;
import com.bolad.polls.repository.VoteRepository;
import com.bolad.polls.security.CurrentUser;
import com.bolad.polls.security.UserPrincipal;
import com.bolad.polls.service.PollService;
import com.bolad.polls.util.AppConstants;

@RestController
@RequestMapping("/api/polls")
public class PollController {
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PollService pollService;
	
	private static final Logger logger = LoggerFactory.getLogger(PollController.class);
	
	//Get a paginated list of polls sorted by their creation time
	@GetMapping
	public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
												@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
												@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size)
	{
		return pollService.getAllPolls(currentUser, page, size);
	
	}
	
	//Create a poll
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequest pollRequest) {
		Poll poll = pollService.createPoll(pollRequest);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{pollId}")
				.buildAndExpand(poll.getId()).toUri();
		
		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "Poll Created Successfully"));
	}
	
	//Get a poll by pollId
	@GetMapping("/{pollId}")
	public PollResponse getPollById(@CurrentUser UserPrincipal currentUser,
									@PathVariable Long pollId) {
		return pollService.getPollById(pollId, currentUser);
	}
	
	//Vote for a choicce in a poll
	@PostMapping("/{pollId}/votes")
	@PreAuthorize("hasRole('USER')")
	public PollResponse castVote(@CurrentUser UserPrincipal currentUser,
							@PathVariable Long pollId,
							@Valid @RequestBody VoteRequest voteRequest) {
		return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
	}
	
	
}
