package tools.paps

import java.io.File
import java.util.*


class MessagePropertyParser(val lines: List<String>) {

    constructor(file: File) : this(file.readLines())

    fun isComment(line: String): Boolean {
        return line.trim { it <= ' ' }.startsWith("#") && line.trim { it <= ' ' }.length > 1
    }

    fun isProperty(line: String): Boolean {
        return !isComment(line) && line.trim { it <= ' ' }.length > 3 && line.indexOf('=') > 0
    }

    fun parse(): List<Property> {
        return parse(lines)
    }

    fun parseProperty(line: String): Property? {
        val split = line.split("=".toRegex(), 2).toTypedArray()
        return if (split.size == 2) {
            Property(split[0].trim { it <= ' ' }, split[1].trim { it <= ' ' })
        } else {
            null
        }
    }

    private fun parse(lines: List<String>): List<Property> {
        var comment: String? = null
        val keys = HashSet<String>()
        val parsed: MutableList<Property> = ArrayList()
        for (line in lines) {
            if (isProperty(line)) {
                val property = parseProperty(line)
                if (property != null) {

                    property.comment = comment
                    comment = null
                    if (keys.contains(property.key)) {
                        val duplicate = parsed.filter { property == it }.firstOrNull()
                        if (duplicate != null) {
                            println(
                                "Warning: Omitting duplicate key (With the same value): ${property.key}= '${property.value}'"
                            )
                        } else {
                            println("Error: Found duplicate key, with different values: ${property.key}= '${property.value}'")
                            parsed.add(property)
                            keys.add(property.key)
                        }
                    } else {
                        parsed.add(property)
                        keys.add(property.key)
                    }
                }
            } else if (isComment(line)) {
                if (comment == null) {
                    comment = parseComment(line)
                } else {
                    comment += " " + parseComment(line)
                }
            }
        }
        return parsed.filter { it.hasValue() }.sorted().toMutableList()
    }

    private fun parseComment(line: String): String {
        return line.replaceFirst("#".toRegex(), "").trim { it <= ' ' }
    }
}
