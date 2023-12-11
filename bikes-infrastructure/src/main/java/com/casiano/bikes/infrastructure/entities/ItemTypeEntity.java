package com.casiano.bikes.infrastructure.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ITEM_TYPES")
public class ItemTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  // Getters and setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemTypeEntity itemType = (ItemTypeEntity) o;
    return Objects.equals(id, itemType.id) && Objects.equals(name, itemType.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "ItemType{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}