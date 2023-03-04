package cores.api

import cores.api.Messages.BLUE_COLOR
import cores.api.Messages.RED_COLOR
import net.kyori.adventure.text.format.NamedTextColor

enum class  Team(val teamName: String, val color: NamedTextColor, val colorDisplayed: String) {
    RED("Rot", NamedTextColor.RED, RED_COLOR), BLUE("Blau", NamedTextColor.DARK_BLUE, BLUE_COLOR)
}