package br.com.processapi.processapi.domain.process;

import br.com.processapi.processapi.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "process")
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String process;

    @Column(nullable = false)
    private String stick;

    @Column(columnDefinition = "text", nullable = false)
    private String subject;

    @Column(columnDefinition="Decimal(20,2)", nullable = false)
    private Float value;

    @Column(nullable = false)
    private String lawyer;

    @Column(name = "process_type", nullable = false)
    private String processType;

    private String opinion;

    @Column(columnDefinition = "varchar(100) default 'OPEN'")
    @Enumerated(value = EnumType.STRING)
    private ProcessState state;

    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
