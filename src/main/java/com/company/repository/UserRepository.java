package com.company.repository;

import com.company.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String name);

    /*
     * · when we remove or persist the data in DB we should use @Transactional annotation because we need to provide new transaction
     *      · For derive queries we use @Transactional annotation. We can use this annotation in class level as well. Ex: UserServiceImpl class
     *      · For JPQL and Native queries we use @Modifying annotation
     */
    @Transactional
    void deleteByUserName(String username);


    List<User> findAllByRoleDescriptionIgnoreCase(String description);
}
