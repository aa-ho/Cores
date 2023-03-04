package cores.listener

import cores.Main
import cores.Main.Companion.plugin
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.sendDoNotSpamChat
import cores.api.Messages.sendDoNotSpamCommand
import cores.gameStates.GameStates
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class PlayerChatEvent : Listener {
    private val chatCoolDown = arrayListOf<String>()
    private fun addPlayerToChatCoolDown(name: String) {
        chatCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, {
            chatCoolDown.remove(name)
        }, 10)
    }

    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        val p = e.player
        if (chatCoolDown.contains(p.name)) {
            e.isCancelled = true
            sendDoNotSpamChat(p)
        } else {
            addPlayerToChatCoolDown(p.name)
            when (plugin.gameStateManager.getCurrentGameState()) {
                GameStates.LOBBY_STATE -> {
                    e.format = "${plugin.rankHelper.getPlayersRankColor(p)}${p.name}§7: §r${e.message}"
                }
                GameStates.INGAME_STATE -> {
                    if (PLAYERS.containsKey(p)) {
                        val playerColored = "${plugin.teamHelper.getPlayerTeam(p).colorDisplayed}${p.name}"

                        if (e.message.startsWith("@")) {
                            e.format = "§8[§7@a§8] $playerColored§8: §r${e.message.substring(1)}"
                        } else {
                            Bukkit.getOnlinePlayers().forEach {
                                if (PLAYERS.containsKey(it)) {
                                    if (plugin.teamHelper.getPlayerTeam(it) != plugin.teamHelper.getPlayerTeam(p))
                                        e.recipients.remove(it)
                                } else e.recipients.remove(it)
                            }
                            e.format = "$playerColored§8: §r${e.message}"
                        }
                    } else {
                        Bukkit.getOnlinePlayers().forEach {
                            if (PLAYERS.containsKey(it)) e.recipients.remove(it)
                        }
                        e.format = "§7${p.name}§8: §r${e.message}"
                    }
                }
                GameStates.END_STATE -> {
                    if (e.message.contains("gg")) e.message = e.message.replace("gg", "§7§kg§6gg§7§kg")
                    e.format = "${plugin.rankHelper.getPlayersRankColor(p)}${p.name}§8: §r${e.message}"
                }
            }
        }
    }
}