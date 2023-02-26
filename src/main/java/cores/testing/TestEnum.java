package cores.testing;

import org.bukkit.ChatColor;

public enum TestEnum {
        TEST("test", ChatColor.GRAY),
    TEST2("test2", ChatColor.DARK_AQUA);
        private TestEnum(String playerName, ChatColor chatColor) {
            this.playerName = playerName;
            this.chatColor = chatColor;
        }
        private String playerName;
        private ChatColor chatColor;

    public String getPlayerName() {
        return playerName;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }
}
