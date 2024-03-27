package com.springboot.minimartapi.auth;

import com.springboot.minimartapi.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    Integer id;
    String authority;
    @ManyToMany(mappedBy = "authorities")

    List<Role> roles;
}
