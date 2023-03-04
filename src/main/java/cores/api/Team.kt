package cores.api

import net.kyori.adventure.text.format.NamedTextColor

enum class  Team(val teamName: String, val color: NamedTextColor, val colorDisplayed: String) {
    RED("Rot", NamedTextColor.RED, "§c"), BLUE("Blau", NamedTextColor.DARK_BLUE, "§9")
}