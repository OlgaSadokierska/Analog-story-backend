package com.olgasadokierska.analogstory.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_type")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_type")
    private String name;
}
