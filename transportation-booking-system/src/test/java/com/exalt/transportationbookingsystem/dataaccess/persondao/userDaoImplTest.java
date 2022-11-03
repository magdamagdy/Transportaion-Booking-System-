package com.exalt.transportationbookingsystem.dataaccess.persondao;

import static org.junit.jupiter.api.Assertions.*;

import com.exalt.transportationbookingsystem.models.person.db.UserDB;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class userDaoImplTest {
  UserDao userDao = new userDaoImpl();

  @Test
  void saveAndReadUserById_NewUser_NewSavedRecordIsConsistentWithRetrievedRecord(){
    UserDB newRecord =  new UserDB(4,47,"male","01000371842","testUser@gmail.com");
    userDao.saveUser(newRecord);
    UserDB retrievedRecord = userDao.readUserById(newRecord.getId());
    assertTrue(newRecord.getId() == retrievedRecord.getId() &&
        newRecord.getAge() == retrievedRecord.getAge() &&
        newRecord.getGender().equals(retrievedRecord.getGender()) &&
        newRecord.getPhone() .equals( retrievedRecord.getPhone()) &&
        newRecord.getEmail() .equals(retrievedRecord.getEmail())
    );
    userDao.deleteUserById(newRecord.getId());
  }

  @Test
  void updateUser_ExistingUser_RetrievedRecordIsUpdated() {
    userDao.saveUser(new UserDB(5,36,"female","01519940002","test1User@gmail.com"));
    UserDB ExistingUser =  userDao.readUserById(5);
    UserDB updates = new UserDB(5,39,"male","01233300002","test2User@gmail.com");
    userDao.updateUser(updates);
    UserDB UpdatedUser = userDao.readUserById(5);
    assertTrue(ExistingUser.getId() == UpdatedUser.getId() &&
        ExistingUser.getAge() != UpdatedUser.getAge() &&
        !ExistingUser.getGender().equals(UpdatedUser.getGender()) &&
        !ExistingUser.getPhone().equals(UpdatedUser.getPhone()) &&
        !ExistingUser.getEmail().equals(UpdatedUser.getEmail()) );
    userDao.deleteUserById(ExistingUser.getId());
  }


  @Test
  void readAllUsers_ExistingNoOfRecords_ListOfRecordsNotNull() {
    List<UserDB> allRecords = new ArrayList<>(userDao.readAllUsers());
    assertTrue(allRecords.size() > 0);
  }

  @Test
  void deleteUserById_ExistingUser_GettingNullObjectAfterDelete() {
    userDao.saveUser(new UserDB(6, 40, "male", "01016300002", "test3User@gmail.com"));
    userDao.deleteUserById(6);
    assertNull(userDao.readUserById(6));
  }
}