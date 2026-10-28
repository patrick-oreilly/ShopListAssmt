package com.mmgpt.shoplistgpt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.mmgpt.shoplistgpt.PlanCategoryFragment
import com.mmgpt.shoplistgpt.PlanCategoriesFragment

class CategoryPagerAdapter(
    activity: FragmentActivity,
    private val viewModel: ShoppingListViewModel,
    private val planMode: Boolean = false
) : FragmentStateAdapter(activity) {

    private fun categoryCount(): Int = viewModel.categories.value?.size ?: 0

    override fun getItemCount(): Int = categoryCount() + 1

    override fun createFragment(position: Int): Fragment =
        if (planMode) {
            if (position == 0) {
                PlanCategoriesFragment()
            } else {
                PlanCategoryFragment.newInstance(position - 1)
            }
        } else {
            if (position == categoryCount()) {
                ShopCategoriesFragment()
            } else {
                CategoryFragment.newInstance(position)
            }
        }

    // FragmentStateAdapter reuses a page's fragment while its item ID is unchanged.
    // The default ID is the position, but in Shop mode the summary page's position
    // moves when the category count changes, so a stale summary fragment could stay
    // at a position that should now show a category. IDs must reflect page content.
    override fun getItemId(position: Int): Long = pageId(position, categoryCount(), planMode)

    override fun containsItem(itemId: Long): Boolean =
        isValidPageId(itemId, categoryCount(), planMode)

    companion object {
        const val SUMMARY_PAGE_ID = -1L

        fun pageId(position: Int, categoryCount: Int, planMode: Boolean): Long {
            val summaryPosition = if (planMode) 0 else categoryCount
            return if (position == summaryPosition) {
                SUMMARY_PAGE_ID
            } else {
                // ID is the category index shown on this page
                (if (planMode) position - 1 else position).toLong()
            }
        }

        fun isValidPageId(itemId: Long, categoryCount: Int, planMode: Boolean): Boolean =
            itemId == SUMMARY_PAGE_ID || itemId in 0 until categoryCount
    }
}
