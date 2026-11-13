///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25

// Assemble developpeur-logiciel.html (fichier unique) à partir de template.html,
// de slides.md et des assets reveal.js dans vendor/.
// Les images référencées par src="images/..." sont intégrées en base64 (data: URI).
// Usage (depuis le dossier du projet) : jbang Build.java

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

void main() throws Exception {
    var root = Path.of("").toAbsolutePath();
    if (!Files.exists(root.resolve("template.html")))
        throw new IllegalStateException("template.html introuvable : lancer depuis le dossier du projet (" + root + ")");
    var t = read(root, "template.html");
    var rep = new LinkedHashMap<String, String>();
    rep.put("/*__REVEAL_CSS__*/", read(root, "vendor/reveal.css"));
    rep.put("/*__THEME_CSS__*/", read(root, "vendor/theme/white.css"));
    rep.put("/*__REVEAL_JS__*/", read(root, "vendor/reveal.js"));
    rep.put("/*__MARKDOWN_JS__*/", read(root, "vendor/plugin/markdown/markdown.js"));
    rep.put("/*__NOTES_JS__*/", read(root, "vendor/plugin/notes/notes.js"));
    rep.put("/*__MD__*/", inlineImages(root, read(root, "slides.md")).replace("</script>", "<\\/script>"));
    for (var e : rep.entrySet()) {
        if (!t.contains(e.getKey())) throw new IllegalStateException("Marqueur absent : " + e.getKey());
        t = t.replace(e.getKey(), e.getValue());
    }
    t = t.replaceAll("//# sourceMappingURL=\\S+", "");
    var out = root.resolve("developpeur-logiciel.html");
    Files.writeString(out, t);
    IO.println("OK -> " + out + " " + t.getBytes(java.nio.charset.StandardCharsets.UTF_8).length + " octets");
}

String read(Path root, String p) throws Exception {
    return Files.readString(root.resolve(p));
}

String inlineImages(Path root, String md) throws Exception {
    var m = Pattern.compile("src=\"(images/[^\"]+)\"").matcher(md);
    var sb = new StringBuilder();
    while (m.find()) {
        var p = m.group(1);
        var file = root.resolve(p);
        if (!Files.exists(file)) throw new IllegalStateException("Image introuvable : " + p);
        var ext = p.substring(p.lastIndexOf('.') + 1).toLowerCase();
        var mime = switch (ext) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            case "svg" -> "image/svg+xml";
            default -> throw new IllegalStateException("Format d'image non géré : " + p);
        };
        var data = Base64.getEncoder().encodeToString(Files.readAllBytes(file));
        m.appendReplacement(sb, Matcher.quoteReplacement("src=\"data:" + mime + ";base64," + data + "\""));
    }
    m.appendTail(sb);
    return sb.toString();
}
