package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuestionDao {

  @PersistenceContext
  private EntityManager entityManager;

  public QuestionEntity createQuestion(QuestionEntity questionEntity) {
    entityManager.persist(questionEntity);
    return questionEntity;
  }

  public List<QuestionEntity> getQuestionsByUserId(String userUuid) {
    try {
      return entityManager.createNamedQuery("questionsByUserId", QuestionEntity.class).setParameter("userUuid", userUuid).getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public List<QuestionEntity> getAllQuestions() {
    try {
      return entityManager.createNamedQuery("questions", QuestionEntity.class).getResultList();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public QuestionEntity getQuestionById(String questionUuid) {
    try {
      return entityManager.createNamedQuery("questionById", QuestionEntity.class).setParameter("uuid", questionUuid).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  public void deleteQuestion(String questionUuid) {
      entityManager.createNamedQuery("deleteQuestionById").setParameter("uuid", questionUuid).executeUpdate();
  }

  public void updateQuestion(QuestionEntity questionEntity) {entityManager.merge(questionEntity);}
}
