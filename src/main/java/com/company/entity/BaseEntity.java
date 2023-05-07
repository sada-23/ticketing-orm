package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity {

    /*
     * · @Column(nullable = false,updatable = false) : It will prevent override (update) the values on DB that becomes
     *   null and will keep existing value.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,updatable = false) // to prevent overridden "insertDateTime", "insertUserId" that becomes null
    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    private Long lastUpdateUserId;

    // flag the data if deleted from UI but still available on DB
    private Boolean isDeleted = false; // @Where(clause = "is_deleted=false")


    @PrePersist // whenever we save new data on DB this method will be executed
    public void onePrePersist(){ // initializing the fields
        this.insertDateTime =  LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();
        this.insertUserId = 1L;
        this.lastUpdateUserId = 1L;
    }

    @PreUpdate
    public void onePreUpdate(){ // this method will update lastUpdateDateTime and lastUpdateUserId
        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;
    }

}
