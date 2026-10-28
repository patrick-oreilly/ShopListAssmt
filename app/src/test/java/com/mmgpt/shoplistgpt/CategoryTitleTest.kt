package com.mmgpt.shoplistgpt

import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryTitleTest {

    @Test
    fun numberedHeadingSplitsIntoRouteNumberAndName() {
        assertEquals(CategoryTitle("4", "Meat"), CategoryTitle.parse("4 Meat"))
        assertEquals(CategoryTitle("12", "Cleaning Products"), CategoryTitle.parse("12 Cleaning Products"))
        assertEquals(CategoryTitle("3", "Pasta, Rice, Tin Tomato"), CategoryTitle.parse("3 Pasta, Rice, Tin Tomato"))
    }

    @Test
    fun unnumberedHeadingKeepsWholeName() {
        assertEquals(CategoryTitle(null, "Frozen Food"), CategoryTitle.parse("Frozen Food"))
    }

    /** A heading that is only a number has no name to show without it. */
    @Test
    fun bareNumberIsTreatedAsTheName() {
        assertEquals(CategoryTitle(null, "7"), CategoryTitle.parse("7"))
    }
}
