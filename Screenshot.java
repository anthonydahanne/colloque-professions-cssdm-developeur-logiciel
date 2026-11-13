///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25
//DEPS com.microsoft.playwright:playwright:1.64.0

// Capture chaque slide en PNG dans shots/ pour vérification visuelle.
// Usage (depuis le dossier du projet) : jbang Screenshot.java
// Au premier lancement, Playwright télécharge Chromium automatiquement.

import com.microsoft.playwright.*;
import java.nio.file.Files;
import java.nio.file.Path;

void main() throws Exception {
    var root = Path.of("").toAbsolutePath();
    var html = root.resolve("developpeur-logiciel.html");
    if (!Files.exists(html))
        throw new IllegalStateException("developpeur-logiciel.html introuvable : lancer « jbang Build.java » depuis le dossier du projet (" + root + ")");
    var shots = Files.createDirectories(root.resolve("shots"));
    try (var pw = Playwright.create(); var b = pw.chromium().launch()) {
        var pg = b.newPage(new Browser.NewPageOptions().setViewportSize(1280, 720));
        pg.navigate(html.toUri().toString());
        pg.waitForTimeout(1500);
        pg.evaluate("Reveal.configure({transition:'none',backgroundTransition:'none'})");
        int total = ((Number) pg.evaluate("Reveal.getTotalSlides()")).intValue();
        for (int i = 0; i < total; i++) {
            pg.evaluate("i => Reveal.slide(i)", i);
            pg.waitForTimeout(600);
            pg.screenshot(new Page.ScreenshotOptions().setPath(shots.resolve("s%02d.png".formatted(i))));
        }
        IO.println("OK -> " + total + " captures dans " + shots);
    }
}
