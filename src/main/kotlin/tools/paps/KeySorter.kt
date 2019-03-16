package tools.paps

import java.text.RuleBasedCollator


private const val underscore = "'\u005f'"
private const val englishRules = "< 0 < 1 < 2 < 3 < 4 < 5 < 6 < 7 < 8 < 9" +

        "< A < B < C < D < E < F " +
        "< G < H < I < J < K < L " +
        "< M < N < O < P < Q < R " +
        "< S < T < U < V < W < X " +
        "< Y < Z" +

        " < $underscore " +

        "< a < b < c < d < e < f " +
        "< g < h < i < j < k < l " +
        "< m < n < o < p < q < r " +
        "< s < t < u < v < w < x " +
        "< y < z"


/**
 * Sorts property keys like PhraseApp.
 * Rule: 0 < 1 < A < B < _ < a < b
 */
object KeySorter : RuleBasedCollator(englishRules)