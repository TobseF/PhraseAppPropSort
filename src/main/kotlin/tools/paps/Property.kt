package tools.paps

import tools.paps.sorter.KeySorter


class Property(val key: String, val value: String?) : Comparable<Property> {
    var comment: String? = null

    val keyLength: Int
        get() = key.length

    val group: String
        get() {
            val firstUnderscore = key.indexOf("_")
            return if (firstUnderscore == -1) {
                key
            } else key.substring(0, firstUnderscore)
        }

    override fun compareTo(other: Property): Int {
        return KeySorter.compare(key, other.key)
    }

    override fun toString(): String {
        return "$key = $value #$comment"
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        return result
    }

    fun hasComment(): Boolean {
        return comment != null
    }

    fun hasValue(): Boolean {
        return isNotEmpty(value)
    }

    private fun isNotEmpty(string: String?): Boolean {
        return string != null && string.isNotEmpty()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Property

        if (key != other.key) return false
        if (value != other.value) return false
        if (comment != other.comment) return false

        return true
    }


}
