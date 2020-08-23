package br.com.processapi.processapi.domain.user;

import br.com.processapi.processapi.domain.process.Process;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private UserType userType;

//    @OneToMany(targetEntity = Process.class, cascade = CascadeType.ALL)
//    @JoinColumn(referencedColumnName = "id")
//    @OneToMany(targetEntity = Process.class, mappedBy = "users", cascade = CascadeType.ALL)
//    private List<Process> processes;

    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

}
