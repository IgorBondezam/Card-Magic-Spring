package com.desafio.profissional.magic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card implements Serializable {

    @Id
    private UUID id;
    private String name;
    private String imageUrl;
    private Double cmc;
    private String manaCost;
    private String text;
    private String superTypes;
    private String types;
    private String subTypes;
    private String colorIdentity;
    private String colors;

    public List<String> getColorIdentity() {
        if(Objects.isNull(this.colorIdentity)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.colorIdentity.split(","));
    }

    public List<String> getColors() {
        if(Objects.isNull(this.colors)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.colors.split(","));
    }

    public List<String> getSuperTypes() {
        if(Objects.isNull(this.superTypes)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.superTypes.split(","));
    }

    public List<String> getTypes() {
        if(Objects.isNull(this.types)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.types.split(","));
    }

    public List<String> getSubTypes() {
        if(Objects.isNull(this.subTypes)) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.subTypes.split(","));
    }

    public void setColorIdentity(List<String> color_identity) {
        if(Objects.isNull(color_identity) || color_identity.isEmpty()) {
            return;
        }
        this.colorIdentity = String.join(",", color_identity);
    }

    public void setColors(List<String> colors) {
        if(Objects.isNull(colors) || colors.isEmpty()) {
            return;
        }
        this.colors = String.join(",", colors);
    }

    public void setSuperTypes(List<String> superTypes) {
        if(Objects.isNull(superTypes) || superTypes.isEmpty()) {
            return;
        }
        this.superTypes = String.join(",", superTypes);
    }

    public void setTypes(List<String> types) {
        if(Objects.isNull(types) || types.isEmpty()) {
            return;
        }
        this.types = String.join(",", types);
    }

    public void setSubTypes(List<String> subTypes) {
        if(Objects.isNull(subTypes) || subTypes.isEmpty()) {
            return;
        }
        this.subTypes = String.join(",", subTypes);
    }
}
