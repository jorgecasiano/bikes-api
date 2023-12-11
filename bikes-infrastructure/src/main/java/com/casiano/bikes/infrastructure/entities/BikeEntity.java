
package com.casiano.bikes.infrastructure.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "BIKES")
public class BikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRICE", nullable = false)
  private BigDecimal price;

  @Column(name = "CURRENCY", nullable = false)
  private String currency;

  @ManyToOne
  @JoinColumn(name = "MANUFACTURER_ID", nullable = false)
  private ManufacturerEntity manufacturer;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "REL_BIKES_ITEMS", joinColumns = @JoinColumn(name = "BIKE_ID"), inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
  private Set<ItemEntity> items;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public ManufacturerEntity getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(ManufacturerEntity manufacturer) {
    this.manufacturer = manufacturer;
  }

  public Set<ItemEntity> getItems() {
    return items;
  }

  public void setItems(Set<ItemEntity> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BikeEntity bike = (BikeEntity) o;
    return Objects.equals(id, bike.id) && Objects.equals(name, bike.name) && Objects.equals(description, bike.description) && Objects.equals(price, bike.price) && Objects.equals(currency, bike.currency) && Objects.equals(manufacturer, bike.manufacturer) && Objects.equals(items, bike.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, price, currency, manufacturer, items);
  }


  @Override
  public String toString() {
    return "Bike{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", currency='" + currency + '\'' +
        ", manufacturer=" + manufacturer +
        ", items=" + items +
        '}';
  }
}