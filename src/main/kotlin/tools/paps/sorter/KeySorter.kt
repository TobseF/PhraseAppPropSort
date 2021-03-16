package tools.paps.sorter

/**
 * Sort a String based on the sorting strategy used by PhraseApp:
 * (0-9) > (A-Z) > (_) > (a-z)
 * In addition, a shorter is greater (aa > aaa).
 */
object KeySorter : Comparator<String> {

    /**
     * @return if the first value is less -> negative. If equal -> 0. If bigger -> positive.
     */
    override fun compare(o1: String, o2: String): Int {
        if (o1.isEmpty()) {
            return if (o2.isEmpty()) {
                0
            } else {
                // The longer one is greater
                1
            }
        }

        val chars1 = o1.toCharArray()
        val chars2 = o2.toCharArray()

        chars1.forEachIndexed { i, _ ->
            if (i >= chars2.size) {
                return 1
            } else {
                val comparison = CharSorter.compare(chars1[i], chars2[i])
                if (comparison != 0) {
                    return comparison
                }
            }
        }
        if (chars1.size < chars2.size) {
            // The longer one is greater
            return -1
        }
        return 0
    }
}