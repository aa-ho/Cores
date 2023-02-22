package cores.api

import org.bukkit.Bukkit
import org.bukkit.Sound

object ImportantFunctions {

    fun playSoundForAll(sound: Sound, volume: Float = 1.0F, pitch: Float = 1.0F) {
        Bukkit.getOnlinePlayers().forEach {
            it.playSound(it.location, sound, volume, pitch)
        }
    }
    fun sendTitleForAll(title: String, fadeIn: Int, stay: Int, fadeOut: Int, subTitle: String = "") {
        Bukkit.getOnlinePlayers().forEach {
            it.sendTitle(title, subTitle, fadeIn, stay, fadeOut)
        }
    }
    fun setLevelAll(level: Int) {
        Bukkit.getOnlinePlayers().forEach {
            it.level = level
        }
    }
}