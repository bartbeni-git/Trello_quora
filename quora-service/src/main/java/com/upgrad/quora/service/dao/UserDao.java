package com.upgrad.quora.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.upgrad.quora.service.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao {
  @PersistenceContext
  //@Autowired
  private EntityManager entityManager;

  public UserEntity createUser(UserEntity userEntity) {
    entityManager.persist(userEntity);
    return userEntity;
  }

  public UserEntity getUser(final String userUuid) {
    return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
  }

  /**
   * This methods gets the user details based on the username passed.
   *
   * @param userName username of the user whose information is to be fetched.
   * @return null if the user with given username doesn't exist in DB.
   */
  public UserEntity getUserByUserName(String userName) {
    try {
      return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", userName).getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }


  public void updateUserEntity(final UserEntity updatedUserEntity) {
    entityManager.merge(updatedUserEntity);
  }
}
