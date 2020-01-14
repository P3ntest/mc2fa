package me.julius.mc2fa.commands;

import com.google.zxing.WriterException;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import me.julius.mc2fa.Main;
import me.julius.mc2fa.qr.qrUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthCommand implements CommandExecutor {

    public static List<Player> setupMode = new ArrayList<>();

    public static HashMap<Player, Location> previosLocation = new HashMap<>();
    public static HashMap<Player, GameMode> previosGameMode = new HashMap<>();

    public static HashMap<Player, String> secret = new HashMap<>();

    public static void removeFromSetupMode(Player p) {
        setupMode.remove(p);
        System.out.println("Removing from setup-mode: " + p.getName());
        System.out.println(previosGameMode.get(p));
        System.out.println(previosLocation.get(p));
        p.teleport(previosLocation.get(p));
        p.setGameMode(previosGameMode.get(p));
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String name, String[] args) {
        if (command.getName().equalsIgnoreCase("2fa")) {

            GoogleAuthenticator authenticator = new GoogleAuthenticator();
            GoogleAuthenticatorKey key = authenticator.createCredentials();

            Player p = (Player) commandSender;

            commandSender.sendMessage("Entered 2FA Setup Mode. Please scan the QR-Code or type in this code manually:");
            commandSender.sendMessage(key.getKey());

            secret.put(p, key.getKey());

            previosGameMode.put(p, p.getGameMode());
            System.out.println(previosGameMode.get(p));
            previosLocation.put(p, p.getLocation());
            System.out.println(previosLocation.get(p));

            p.setGameMode(GameMode.SPECTATOR);
            p.teleport(new Location(Main.getInstance().getQrWorld(), 0, 27, 0, -180f, 90f));

            setupMode.add(p);

            try {
                qrUtils.codeToLocation(createQrText(key.getKey()), new Location(Main.getInstance().getQrWorld(), 0, 0, 0));
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String createQrText(String key) {
        return "otpauth://totp/" + "Minecraft%20Server" + "?secret=" + key;
    }
}
