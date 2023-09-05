package com.bim.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.Size;
import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "userser")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Data
public class User extends DistributedEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Size(max = 30, min = 6)
    @Column(unique = true, nullable = false)
    private String username;

    @Size(max = 60, min = 6)
    @Column(nullable = false)
    private String password;

    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private Set<Role> roles;

    private Boolean active;



}

