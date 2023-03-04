package cores.listener

import cores.Main
import cores.api.Messages.sendDoNotSpamChat
import cores.api.Messages.sendDoNotSpamCommand
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerChatEvent: Listener {
    private val chatCoolDown = arrayListOf<String>()
    private fun addPlayerToChatCoolDown(name: String) {
        chatCoolDown.add(name)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, {
            chatCoolDown.remove(name)
        }, 10)
    }
    @EventHandler
    fun onChat(e: AsyncChatEvent) {
        val p = e.player
        if(chatCoolDown.contains(p.name)) {
            sendDoNotSpamChat(p)
        } else {
            addPlayerToChatCoolDown(p.name)

        }
    }
}