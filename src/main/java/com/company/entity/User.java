package com.company.entity;

import com.company.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import javax.persistence.*;
/*
 * · Whatever repository created with entity that is annotated with @Where(clause = "is_deleted=false") all queries on that
 *   repository will concatenate will "is_deleted=false" as well.
 *     · Ex: User class annotated with @Where(clause = "is_deleted=false") and UserRepository accepts User entity.
 *  · @Where(clause = "is_deleted=false") => Select * from TableName where is_deleted=false
 *
 *
 */

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
@Where(clause = "is_deleted=false")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;



}
