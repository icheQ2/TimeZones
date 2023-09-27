package com.timezones.utils;

import com.timezones.entity.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AnswerBase {
    public static List<City> cities = new ArrayList<City>() {
        {
            add(new City("Yekaterinburg", "gmt5"));
            add(new City("Astana", "gmt6"));
            add(new City("Bangkok", "gmt7"));
            add(new City("Hong Kong", "gmt8"));
            add(new City("Tokyo", "gmt9"));
            add(new City("Sydney", "gmt10"));
            add(new City("Wellington", "gmt11"));
            add(new City("London", "gmt12"));
        }
    };

    public static List<Integer> getGameSet() {
        return new Random()
                .ints(0, cities.size())
                .distinct()
                .limit(Math.min(cities.size(), 6))
                .boxed()
                .collect(Collectors.toList());
    }
}