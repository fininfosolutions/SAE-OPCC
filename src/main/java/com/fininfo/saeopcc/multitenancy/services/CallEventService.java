package com.fininfo.saeopcc.multitenancy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.Event;
import com.fininfo.saeopcc.multitenancy.domains.Issue;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallEventDTO;
import com.fininfo.saeopcc.multitenancy.services.dto.EventDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CallEventService {

  @Autowired private CallEventRepository eventRepository;

  @Autowired private ModelMapper modelMapper;
  @Autowired private CallService appealService;

  @Autowired private IssueRepository issueRepository;

  @Transactional
  public CallEventDTO save(CallEventDTO eventDTO) {
    CallEvent event = modelMapper.map(eventDTO, CallEvent.class);
    event.setEventStatus(EventStatus.PREVALIDATED);
    Optional<Issue> optionalIssue = issueRepository.findById(eventDTO.getIssueId());
    if (optionalIssue.isPresent()) {
      Issue issue = optionalIssue.get();
      // issue.setRemainingAmount(issue.getRemainingAmount().subtract(event.getGlobalAppealAmount()));
      // issue.setRemainingQuantity(
      //     issue.getRemainingQuantity().subtract(event.getGlobalAppealQuantity()));
      // issueRepository.save(issue);
      // event.setRealRate(
      //     event
      //         .getGlobalAppealAmount()
      //         .divide(issue.getAppealAmount(), 2, RoundingMode.UNNECESSARY));

    } else {
      throw new IllegalArgumentException("event without issue");
    }
    event = eventRepository.save(event);

    if (event.getId() != null) {
      event.setReference(createGlobalReference("EVT" + event.getId()));
      event = eventRepository.save(event);
    }

    CallEventDTO eventDTOResult = modelMapper.map(event, CallEventDTO.class);

    return eventDTOResult;
  }

  private String createGlobalReference(String reference) {
    String additionalZeros = "0".repeat(16 - reference.length());
    return reference.replace("EVT", "EVT".concat(additionalZeros));
  }

  @Transactional(readOnly = true)
  public Optional<EventDTO> findOne(Long id) {
    log.debug("Request to get Event : {}", id);
    return eventRepository.findById(id).map(event -> modelMapper.map(event, EventDTO.class));
  }

  // @Transactional
  // public EventDTO updateEvent(EventDTO eventDTO) {
  //   Event event = modelMapper.map(eventDTO, Event.class);
  //   BigDecimal oldAmount =
  //       eventRepository
  //           .findById(event.getId())
  //           .map(Event::getGlobalAppealAmount)
  //           .orElse(BigDecimal.ZERO);
  //   BigDecimal oldQuantity =
  //       eventRepository
  //           .findById(event.getId())
  //           .map(Event::getGlobalAppealQuantity)
  //           .orElse(BigDecimal.ZERO);
  //   Optional<Issue> optionalIssue = issueRepository.findById(event.getIssue().getId());

  //   if (optionalIssue.isPresent()) {
  //     Issue issue = optionalIssue.get();
  //     // issue.setRemainingAmount(
  //     //     issue.getRemainingAmount().subtract(event.getGlobalAppealAmount()).add(oldAmount));
  //     // issue.setRemainingQuantity(
  //     //     issue.getRemainingQuantity().subtract(event.getGlobalAppealQuantity()).add(oldQuantity));
  //     // issueRepository.save(issue);
  //     // event.setRealRate(
  //     //     event
  //     //         .getGlobalAppealAmount()
  //     //         .divide(issue.getAppealAmount(), 2, RoundingMode.UNNECESSARY));
  //   } else {
  //     throw new IllegalArgumentException("event without issue");
  //   }
  //   return modelMapper.map(eventRepository.save(event), EventDTO.class);
  // }

  public Page<EventDTO> getEventsByIssue(Long issueId, Pageable pageable) {
    Optional<Issue> issueOpt = issueRepository.findById(issueId);
    if (issueOpt.isPresent()) {
      Page<CallEvent> page = eventRepository.findByIssue_id(issueId, pageable);
      return page.map(event -> modelMapper.map(event, EventDTO.class));
    } else {
      return new PageImpl<>(new ArrayList<>());
    }
  }

  public long countEventsByIssue(Long issueId) {
    return eventRepository.countByIssue_Id(issueId);
  }

  @Transactional
  public List<CallEventDTO> validateEvents(List<CallEventDTO> eventDTOs) {
    List<CallEventDTO> validatedEvents =
        eventDTOs.stream()
            .map(
                dto -> {
                  CallEvent event =
                      eventRepository
                          .findById(dto.getId())
                          .orElseThrow(
                              () ->
                                  new EntityNotFoundException(
                                      "Event not found for id: " + dto.getId()));
                  event.setEventStatus(EventStatus.VALIDATED);
                  event = eventRepository.save(event);
                  appealService.createFromEvent(event);
                  return modelMapper.map(event, CallEventDTO.class);
                })
            .collect(Collectors.toList());

    return validatedEvents;
  }

  // public Boolean isValid(Long issueId, BigDecimal amount) {
  //   Optional<Issue> optionalIssue = issueRepository.findById(issueId);
  //   return optionalIssue.isPresent()
  //       && optionalIssue.get().getRemainingAmount().compareTo(amount) >= 0;
  // }
}
