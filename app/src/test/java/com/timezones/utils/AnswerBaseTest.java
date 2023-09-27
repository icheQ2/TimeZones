package com.timezones.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class AnswerBaseTest {

    @Test
    public void getGameSet() {
        //when
        List<Integer> result = AnswerBase.getGameSet();

        //then
        assertTrue(result.size() <= 6);
    }
}