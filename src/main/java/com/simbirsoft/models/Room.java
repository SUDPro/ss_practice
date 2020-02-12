package com.simbirsoft.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simbirsoft.enumTypes.RoomType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;

    @Enumerated(value = EnumType.STRING)
    private RoomType type;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "room")
    @JsonIgnore
    private List<BanInfo> users;

    public Room() {
    }



    public Room(String name, User owner, RoomType type) {
        this.name = name;
        this.owner = owner;
        this.type = type;
    }

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public List<BanInfo> getUsers() {
        return users;
    }

    public void setUsers(List<BanInfo> users) {
        this.users = users;
    }
}