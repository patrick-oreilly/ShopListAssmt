package com.mmgpt.shoplistgpt

import com.mmgpt.shoplistgpt.CategoryPagerAdapter.Companion.SUMMARY_PAGE_ID
import com.mmgpt.shoplistgpt.CategoryPagerAdapter.Companion.isValidPageId
import com.mmgpt.shoplistgpt.CategoryPagerAdapter.Companion.pageId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CategoryPagerAdapterTest {

    @Test
    fun shopMode_summaryIsLastPage() {
        assertEquals(SUMMARY_PAGE_ID, pageId(3, categoryCount = 3, planMode = false))
        assertEquals(0L, pageId(0, categoryCount = 3, planMode = false))
        assertEquals(2L, pageId(2, categoryCount = 3, planMode = false))
    }

    @Test
    fun planMode_summaryIsFirstPage() {
        assertEquals(SUMMARY_PAGE_ID, pageId(0, categoryCount = 3, planMode = true))
        assertEquals(0L, pageId(1, categoryCount = 3, planMode = true))
        assertEquals(2L, pageId(3, categoryCount = 3, planMode = true))
    }

    /** The original bug: importing into an empty list left the summary at page 0. */
    @Test
    fun shopMode_pageZeroGetsNewIdWhenCategoriesAreAdded() {
        val before = pageId(0, categoryCount = 0, planMode = false)
        val after = pageId(0, categoryCount = 8, planMode = false)
        assertEquals(SUMMARY_PAGE_ID, before)
        assertNotEquals("Page 0 must be rebuilt as a category page", before, after)
    }

    @Test
    fun shopMode_oldLastCategoryPageGetsNewIdWhenCategoriesAreRemoved() {
        val before = pageId(3, categoryCount = 8, planMode = false)
        val after = pageId(3, categoryCount = 3, planMode = false)
        assertNotEquals("Page 3 must be rebuilt as the summary", before, after)
        assertEquals(SUMMARY_PAGE_ID, after)
    }

    @Test
    fun staleCategoryIdsAreDroppedAfterShrinking() {
        assertTrue(isValidPageId(SUMMARY_PAGE_ID, categoryCount = 3, planMode = false))
        assertTrue(isValidPageId(2L, categoryCount = 3, planMode = false))
        assertFalse(isValidPageId(3L, categoryCount = 3, planMode = false))
        assertFalse(isValidPageId(7L, categoryCount = 3, planMode = true))
    }

    /** Double-tapping summary row N navigates to page N; that page must show category N. */
    @Test
    fun shopMode_everySummaryRowMapsToItsCategoryPage() {
        val count = 5
        for (row in 0 until count) {
            assertEquals(row.toLong(), pageId(row, count, planMode = false))
        }
    }

    /** In Plan mode the summary list has an "add" row at 0, so row N maps to page N = category N-1. */
    @Test
    fun planMode_everySummaryRowMapsToItsCategoryPage() {
        val count = 5
        for (row in 1..count) {
            assertEquals((row - 1).toLong(), pageId(row, count, planMode = true))
        }
    }
}
