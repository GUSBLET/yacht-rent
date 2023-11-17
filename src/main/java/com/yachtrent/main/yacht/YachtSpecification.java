package com.yachtrent.main.yacht;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class YachtSpecification {

    public Specification<Yacht> setPriceRange(int min, int max) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("pricePerHour"), min, max);
    }

    public Specification<Yacht> setNumberOfPeople(String number) {
        if (!number.isEmpty()) {
            int[] numbers = Arrays.stream(number.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int max = Arrays.stream(numbers).max().orElse(20);
            int min = Arrays.stream(numbers).min().orElse(1);

            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.and(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), min),
                            criteriaBuilder.lessThanOrEqualTo(root.get("capacity"), max)
                    );
        }
        return null;
    }

    public Specification<Yacht> setTypeYacht(String type) {
        if (!type.isEmpty()) {
            String[] types = type.split(" ");
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(
                                    root.get("yachtType").get("type"),
                                    Arrays.stream(types).filter("SMALL_BOAT"::equals).findAny().orElse(null)
                            ),
                            criteriaBuilder.equal(
                                    root.get("yachtType").get("type"),
                                    Arrays.stream(types).filter("BOAT"::equals).findAny().orElse(null)
                            ),
                            criteriaBuilder.equal(
                                    root.get("yachtType").get("type"),
                                    Arrays.stream(types).filter("SHIP"::equals).findAny().orElse(null)
                            )
                    );
        }
        return null;
    }
}
