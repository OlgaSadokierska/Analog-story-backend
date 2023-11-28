package com.olgasadokierska.analogstory.user.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;



    @PrePersist
    public void prePersist() {
        if (accountType == null) {

            //Ustawienie domyślnego konta, na klient
            accountType = new AccountType(2L, "Domyślny");
        }
    }
}
