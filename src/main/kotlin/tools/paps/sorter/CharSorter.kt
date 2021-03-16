package tools.paps.sorter

/**
 * Sort a char based on the sorting strategy used by PhraseApp:
 * (0-9) > (A-Z) > (_) > (a-z)
 */
object CharSorter : Comparator<Char> {

    /**
     * @return if the first value is less -> negative. If equal -> 0. If bigger -> positive.
     */
    override fun compare(o1: Char, o2: Char): Int {
        if (o1.isDigit()) {
            return if (o2.isDigit()) {
                o1.digitToInt() - o2.digitToInt();
            } else {
                -1
            }
        }
        if (o1.isUpperCase()) {
            return if (o2.isUpperCase()) {
                o1 - o2
            } else if (o2.isDigit()) {
                1
            } else {
                -1
            }
        }

        if (o1 == '_') {
            return if (o2 == '_') {
                0
            } else if (o2.isUpperCase() || o2.isDigit()) {
                1
            } else {
                -1
            }
        }

        if (o1.isLowerCase()) {
            return if (o2.isLowerCase()) {
                o1 - o2
            } else if (o2.isUpperCase() || o2.isDigit() || o2 == '_') {
                1
            } else {
                -1
            }
        }

        return 0
    }

    /**
     * Copy of kotlin.text.digitToInt to avoid the experimental flag
     */
    private fun Char.digitToInt(): Int {
        if (this in '0'..'9') {
            return this - '0'
        }
        throw IllegalArgumentException("Char $this is not a decimal digit")
    }


}