package tools.paps

fun Property.formatted(): List<String> {
    val formatted: MutableList<String> = arrayListOf()
    if (hasComment()) {
        formatted.add("# ${comment!!}")
    }
    formatted.add("$key = $value")
    return formatted
}

fun List<Property>.formatted(): List<String> {
    return this.flatMap { it.formatted() }
}