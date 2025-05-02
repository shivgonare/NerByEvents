package com.example.demo.specification;

import com.example.demo.entity.Event;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {

    public static Specification<Event> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Event> hasLocation(String location) {
        return (root, query, cb) ->
                location == null ? null : cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }
}
