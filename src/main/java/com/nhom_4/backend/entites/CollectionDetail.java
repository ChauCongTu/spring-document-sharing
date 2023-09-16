package com.nhom_4.backend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "collection_details")
public class CollectionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_id")
    @SequenceGenerator(name = "auto_id", sequenceName = "auto_id", allocationSize = 1)
    private Long id;

    private Long collection_id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;
}
