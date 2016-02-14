package me.confuser.vaulttag;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class VaultTag extends JavaPlugin implements Listener {

  public static Chat chat = null;

  public void onEnable() {
    if (!this.getServer().getPluginManager().isPluginEnabled("TagAPI")) {
      this.getLogger().severe("TagAPI required. Get it at http://dev.bukkit.org/server-mods/tag/");
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }
    setupChat();
    this.getServer().getPluginManager().registerEvents(this, this);
  }

  public void onDisable() {

  }

  private boolean setupChat() {
    RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager()
                                                              .getRegistration(net.milkbowl.vault.chat.Chat.class);
    if (chatProvider != null) {
      chat = chatProvider.getProvider();
    }

    return (chat != null);
  }

  @EventHandler
  public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
    Player player = event.getNamedPlayer();
    String prefix = chat.getGroupPrefix(player.getWorld(), chat.getPrimaryGroup(player));
    String name = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', prefix)) + player.getName();

    if (name.length() > 16) name = player.getName();

    event.setTag(name);
  }
}
