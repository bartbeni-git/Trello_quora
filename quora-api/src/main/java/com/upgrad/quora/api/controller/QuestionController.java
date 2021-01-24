package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.QuestionDeleteResponse;
import com.upgrad.quora.api.model.QuestionDetailsResponse;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.business.QuestionBusinessService;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {
  @Autowired
  private QuestionBusinessService questionBusinessService;

  @RequestMapping(method = RequestMethod.POST, path = "/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<QuestionResponse> create(final QuestionRequest questionRequest, @RequestHeader(value = "authorization", required = false) final String authorization) throws AuthorizationFailedException {
    QuestionEntity questionEntity = new QuestionEntity();
    questionEntity.setContent(questionRequest.getContent());
    questionEntity.setDate(ZonedDateTime.now());
    questionEntity.setUuid(UUID.randomUUID().toString());

    QuestionEntity createdQuestionEntity = questionBusinessService.createQuestion(authorization, questionEntity);
    QuestionResponse questionResponse = new QuestionResponse();
    questionResponse.setId(createdQuestionEntity.getUuid());
    questionResponse.setStatus("QUESTION CREATED");

    return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/all",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<QuestionDetailsResponse>> getAll(@RequestHeader(value = "authorization", required = false) final String authorization) throws AuthorizationFailedException {

    List<QuestionEntity> allQuestions = questionBusinessService.getAllQuestions(authorization);
    List<QuestionDetailsResponse> questionDetailsResponseList = allQuestions.stream().map(questionEntity -> {
      QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse();
      questionDetailsResponse.setId(questionEntity.getUuid());
      questionDetailsResponse.setContent(questionEntity.getContent());
      return questionDetailsResponse;
    }).collect(Collectors.toList());

    return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponseList, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/all/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<QuestionDetailsResponse>> getAllByUserId(@PathVariable("userId") final String userUuid, @RequestHeader(value = "authorization", required = false) final String authorization)
      throws AuthorizationFailedException, UserNotFoundException {

    List<QuestionEntity> allQuestions = questionBusinessService.getAllQuestionsByUserId(authorization, userUuid);
    List<QuestionDetailsResponse> questionDetailsResponseList = allQuestions.stream().map(questionEntity -> {
      QuestionDetailsResponse questionDetailsResponse = new QuestionDetailsResponse();
      questionDetailsResponse.setId(questionEntity.getUuid());
      questionDetailsResponse.setContent(questionEntity.getContent());
      return questionDetailsResponse;
    }).collect(Collectors.toList());

    return new ResponseEntity<List<QuestionDetailsResponse>>(questionDetailsResponseList, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<QuestionResponse> edit(final QuestionRequest questionRequest, @RequestHeader(value = "authorization", required = false) final String authorization) throws AuthorizationFailedException {

    final QuestionResponse questionResponse = new QuestionResponse();
    questionResponse.setId(null);
    return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "delete/{questionId}",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<QuestionDeleteResponse> delete(@PathVariable("questionId") final String questionId, @RequestHeader(value = "authorization", required = false) final String authorization)
      throws AuthorizationFailedException, InvalidQuestionException {
    QuestionEntity questionEntity = questionBusinessService.deleteQuestion(authorization, questionId);
    final QuestionDeleteResponse questionDeleteResponse = new QuestionDeleteResponse();
    questionDeleteResponse.setId(questionEntity.getUuid());
    questionDeleteResponse.setStatus("QUESTION DELETED");
    return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.NO_CONTENT);
  }
}
