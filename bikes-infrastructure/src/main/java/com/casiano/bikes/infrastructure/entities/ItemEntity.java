package com.casiano.bikes.infrastructure.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ITEMS")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @ManyToOne
    @JoinColumn(name = "TYPE_ID", nullable = false)
    private ItemTypeEntity type;

    @Column(name = "DESCRIPTION")
    private String description;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ItemTypeEntity getType() {
        return type;
    }

    public void setType(ItemTypeEntity type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
            "id=" + id +
            ", model='" + model + '\'' +
            ", type=" + type +
            ", description='" + description + '\'' +
            '}';
    }
}