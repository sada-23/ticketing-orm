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
@EntityListeners(BaseEntityListener.class)
public class BaseEntity {

    /*
     * · @Column(nullable = false,updatable = false) : It will prevent override (update) the values on DB that becomes
     *   null and will keep existing value.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,updatable = false) // to prevent overridden "insertDateTime", "insertUserId" that becomes null
    public LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    public Long insertUserId;
    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime;
    @Column(nullable = false)
    public Long lastUpdateUserId;

    // flag the data if deleted from UI but still available on DB
    private Boolean isDeleted = false; // @Where(clause = "is_deleted=false")



}
