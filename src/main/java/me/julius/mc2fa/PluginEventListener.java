package me.julius.mc2fa;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import me.julius.mc2fa.commands.AuthCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static me.julius.mc2fa.commands.AuthCommand.secret;

public class PluginEventListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (AuthCommand.setupMode.contains(e.getPlayer())) {
            e.getPlayer().teleport(e.getFrom());
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (AuthCommand.setupMode.contains(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent e) {
        if (AuthCommand.setupMode.contains(e.getPlayer())) {
            e.setCancelled(true);
            if (e.getMessage().toLowerCase().contains("quit")) {
                AuthCommand.removeFromSetupMode(e.getPlayer());
            }else {
                if (e.getMessage().replaceAll(" ", "").length() == 6) {
                    String Code = e.getMessage().replace(" ", "");
                    try {
                        Integer.parseInt(Code);
                    }catch (Exception exception) {
                        e.getPlayer().sendMessage("Code may only contain Numbers. Type \"quit\" to exit.");
                        return;
                    }
                    GoogleAuthenticator gAuth = new GoogleAuthenticator();
                    if (gAuth.authorize(secret.get(e.getPlayer()), Integer.parseInt(Code))) {
                        e.getPlayer().sendMessage("You are verified.");
                        AuthCommand.removeFromSetupMode(e.getPlayer());
                    }else {
                        e.getPlayer().sendMessage("Wrong code.");
                        return;
                    }
                }else {
                    e.getPlayer().sendMessage("Code must be 6 Characters long");
                }
            }
        }
    }

}
