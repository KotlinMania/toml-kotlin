// port-lint: source de/parser/dearray.rs
package io.github.kotlinmania.toml.de.parser

/**
 * Type representing a TOML array, payload of the [DeValue.Array] variant.
 */
public class DeArray(
    private val items: MutableList<Spanned<DeValue>> = mutableListOf(),
    private var arrayOfTables: Boolean = false,
) : List<Spanned<DeValue>> by items {

    public constructor(elements: Iterable<Spanned<DeValue>>) : this(elements.toMutableList(), false)

    public fun push(value: Spanned<DeValue>) {
        items.add(value)
    }

    public fun isArrayOfTables(): Boolean = arrayOfTables

    public fun setArrayOfTables(yes: Boolean) {
        arrayOfTables = yes
    }

    override fun toString(): String = items.toString()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeArray) return false
        return items == other.items && arrayOfTables == other.arrayOfTables
    }

    override fun hashCode(): Int = items.hashCode() * 31 + arrayOfTables.hashCode()

    public companion object {
        public fun new(): DeArray = DeArray()
    }
}
