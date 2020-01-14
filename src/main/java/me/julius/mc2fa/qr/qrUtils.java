package me.julius.mc2fa.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.bukkit.Location;
import org.bukkit.Material;

public abstract class qrUtils {

    public static void codeToLocation(String text, Location loc) throws WriterException {
        codeToLocation(text, loc, Material.BLACK_WOOL, Material.WHITE_WOOL);
    }

    public static void codeToLocation(String text, Location loc, Material isMat, Material isntMat) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 0, 0);
        int spawnX = loc.getBlockX() - bitMatrix.getWidth() / 2;
        int spawnZ = loc.getBlockZ() - bitMatrix.getHeight() / 2;
        int spawnY = loc.getBlockY();
        Location spawnLoc = new Location(loc.getWorld(), spawnX, spawnY, spawnZ);
        //System.out.println(spawnLoc.toString());
        for (int x = 0; x < bitMatrix.getWidth(); x++) {
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                //System.out.println("Setting Block at: " + loc.getWorld().getBlockAt(spawnX + x, spawnY, spawnY + y).getLocation().toString());
                loc.getWorld().getBlockAt(spawnX + x, spawnY, spawnZ + y).setType(bitMatrix.get(x, y) ? isMat : isntMat);
            }
        }
    }

}
