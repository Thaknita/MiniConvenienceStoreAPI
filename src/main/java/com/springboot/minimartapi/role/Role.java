package com.springboot.minimartapi.role;
import com.springboot.minimartapi.auth.Authority;
import com.springboot.minimartapi.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    private Set<Authority> authorities ;



}
