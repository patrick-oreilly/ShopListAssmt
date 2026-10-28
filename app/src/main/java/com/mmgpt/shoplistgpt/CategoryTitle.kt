package com.mmgpt.shoplistgpt

/** A category heading split into its shop-route number ("4") and its name ("Meat"). */
data class CategoryTitle(val number: String?, val name: String) {
    companion object {
        private val NUMBERED = Regex("""^(\d+)\s+(.+)$""")

        fun parse(heading: String): CategoryTitle {
            val trimmed = heading.trim()
            val match = NUMBERED.matchEntire(trimmed) ?: return CategoryTitle(null, trimmed)
            return CategoryTitle(match.groupValues[1], match.groupValues[2])
        }
    }
}
