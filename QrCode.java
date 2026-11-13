///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//DEPS com.google.zxing:core:3.5.4

// Génère un code QR en SVG, entièrement en local (ZXing), sans service en ligne.
// Usage (depuis le dossier du projet) : jbang QrCode.java <url> <fichier.svg>

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

void main(String[] args) throws Exception {
    if (args.length != 2) throw new IllegalArgumentException("Usage : jbang QrCode.java <url> <fichier.svg>");
    var hints = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M, EncodeHintType.MARGIN, 4);
    // Taille 0 : une cellule de la matrice = un module du code, marge (quiet zone) de 4 modules comprise.
    var m = new QRCodeWriter().encode(args[0], BarcodeFormat.QR_CODE, 0, 0, hints);
    int w = m.getWidth(), h = m.getHeight();
    var svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 %d %d\" shape-rendering=\"crispEdges\">".formatted(w, h));
    svg.append("<rect width=\"%d\" height=\"%d\" fill=\"#FFFFFF\"/><path fill=\"#17162B\" d=\"".formatted(w, h));
    for (int y = 0; y < h; y++)
        for (int x = 0; x < w; x++)
            if (m.get(x, y)) svg.append("M%d %dh1v1h-1z".formatted(x, y));
    svg.append("\"/></svg>\n");
    Files.writeString(Path.of(args[1]), svg);
    IO.println("OK -> " + args[1] + " (" + w + "x" + h + " modules) : " + args[0]);
}
