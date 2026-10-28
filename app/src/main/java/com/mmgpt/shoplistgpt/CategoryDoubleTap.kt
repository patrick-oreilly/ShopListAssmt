package com.mmgpt.shoplistgpt

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 * Calls [onRowDoubleTap] with the adapter position of the row double-tapped in this list.
 * The callback returns true if it handled the double tap.
 */
fun RecyclerView.setOnRowDoubleTapListener(onRowDoubleTap: (position: Int) -> Boolean) {
    val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            // The touch listener is on this RecyclerView, so e.x/e.y are already in its coordinates
            val child = findChildViewUnder(e.x, e.y) ?: return false
            val position = getChildAdapterPosition(child)
            return position != RecyclerView.NO_POSITION && onRowDoubleTap(position)
        }
    })

    setOnTouchListener { _, event ->
        gestureDetector.onTouchEvent(event)
        false
    }
}

/** Moves the activity's pager to the page showing category [categoryIndex]. */
fun Fragment.showCategoryPage(categoryIndex: Int, planMode: Boolean) {
    val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
    viewPager.currentItem = CategoryPagerAdapter.pageForCategory(categoryIndex, planMode)
}
