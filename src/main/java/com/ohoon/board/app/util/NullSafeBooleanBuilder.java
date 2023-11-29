package com.ohoon.board.app.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.function.Supplier;

public class NullSafeBooleanBuilder {

    public static BooleanBuilder builder(Supplier<BooleanExpression> func) {
        try {
            return new BooleanBuilder(func.get());
        } catch (IllegalArgumentException | NullPointerException e) {
            return new BooleanBuilder();
        }
    }
}
