package com.company.entity;

import com.company.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class BaseEntityListener extends AuditingEntityListener {

    @PrePersist // whenever we save new data on DB this method will be executed
    public void onePrePersist(BaseEntity baseEntity){ // initializing the fields

        // Capturing which user do actions on the system

        // SecurityContextHolder class holds the login user info in the system
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.insertDateTime =  LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();
//        baseEntity.insertUserId = 1L;
//        baseEntity.lastUpdateUserId = 1L;

        if (authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            baseEntity.insertUserId = ((UserPrincipal) principal).getId();
            baseEntity.lastUpdateUserId = ((UserPrincipal) principal).getId();
        }

    }

    @PreUpdate
    public void onePreUpdate(BaseEntity baseEntity){ // this method will update lastUpdateDateTime and lastUpdateUserId

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();
//        baseEntity.lastUpdateUserId = 1L;


        if (authentication != null && !authentication.getName().equals("anonymousUser")){
            Object principal = authentication.getPrincipal();
            baseEntity.lastUpdateUserId = ((UserPrincipal) principal).getId();
        }
    }

}
