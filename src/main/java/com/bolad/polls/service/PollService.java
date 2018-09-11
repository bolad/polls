package com.bolad.polls.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bolad.polls.model.Poll;
import com.bolad.polls.model.User;
import com.bolad.polls.payload.PagedResponse;
import com.bolad.polls.payload.PollResponse;
import com.bolad.polls.repository.PollRepository;
import com.bolad.polls.repository.UserRepository;
import com.bolad.polls.repository.VoteRepository;
import com.bolad.polls.security.UserPrincipal;
import com.bolad.polls.util.ModelMapper;

/*
 * Both the controllers PollController and UserController use the PollService class to get the list of 
 * polls formatted in the form of PollResponse payloads that is returned to the clients.
 */
@Service
public class PollService {
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PollService.class);
	
	public PagedResponse<PollResponse> getAllPolls(UserPrincipal currentUser, int page, int size) {
		validatePageNumbersAndSize(page, size);
		
		//Retrieve Polls
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Poll> polls = pollRepository.findAll(pageable);
		
		if(polls.getNumberOfElements() == 0){
			return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
					polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
		}
		
		//Map Polls to PollResponse containing vote counts and poll creator details
		List<Long> pollIds = polls.map(Poll::getId).getContent();
		Map<Long, Long> choiceVoteCountMap = getChoiceVoteCountMap(pollIds);
		Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);
		Map<Long, User> creatorMap = getPollCreatorMap(polls.getContent());
		
		List<PollResponse> pollResponses = polls.map(poll -> {
			return ModelMapper.mapPollToPollResponse(poll, 
					choiceVoteCountMap, 
					creatorMap.get(poll.getCreatedBy()), 
					pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null));
		}).getContent();
		
		return new PagedResponse<>(pollResponses, polls.getNumber(),
				polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
	}

	private Map<Long, User> getPollCreatorMap(List<Poll> content) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<Long, Long> getPollUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<Long, Long> getChoiceVoteCountMap(List<Long> pollIds) {
		// TODO Auto-generated method stub
		return null;
	}

	private void validatePageNumbersAndSize(int page, int size) {
		// TODO Auto-generated method stub
		
	}

}
