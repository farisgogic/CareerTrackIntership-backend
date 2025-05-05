package com.team5.career_progression_app.specification;

import com.team5.career_progression_app.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> isActive(Boolean active) {
        return (root, query, criteriaBuilder) -> 
            active != null ? criteriaBuilder.equal(root.get("active"), active) : null;
    }

    public static Specification<User> hasNameLike(String name) {
        return (root, query, criteriaBuilder) -> 
            name != null ? criteriaBuilder.like(
                criteriaBuilder.lower(root.get("firstName")),
                "%" + name.toLowerCase() + "%"
            ) : null;
    }

    public static Specification<User> withFilters(Boolean active, String name) {
        return Specification.where(isActive(active))
                          .and(hasNameLike(name));
    }
}