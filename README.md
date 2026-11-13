# Développeur logiciel : un métier de création

Présentation reveal.js pour le Colloque des professions du CSSDM (élèves de secondaire 3 à 5).
Le résultat est un seul fichier HTML autonome, `developpeur-logiciel.html`, qui fonctionne hors ligne.

## Prérequis

- Java 25 et [JBang](https://www.jbang.dev/), par exemple via SDKMAN :

  ```bash
  sdk install java 25-graal   # ou n'importe quel JDK 25
  sdk install jbang
  ```

Il n'y a rien d'autre à installer : JBang télécharge lui-même les dépendances (Playwright pour les captures).

## Modifier la présentation : les étapes

1. **Éditer le bon fichier source** (voir le tableau ci-dessous). Ne jamais modifier `developpeur-logiciel.html` directement : il est écrasé à chaque build.
2. **Reconstruire**, depuis le dossier du projet :

   ```bash
   jbang Build.java
   ```

3. **Vérifier** : ouvrir (ou recharger) `developpeur-logiciel.html` dans le navigateur. L'URL garde le numéro de slide (`#/15`), donc un simple rechargement ramène sur la slide modifiée.
4. **Optionnel : capturer toutes les slides** en PNG dans `shots/` pour tout relire d'un coup d'œil :

   ```bash
   jbang Screenshot.java
   ```

   Au premier lancement, Playwright télécharge ses navigateurs (environ 300 Mo, une seule fois).

## Quel fichier pour quoi ?

| Je veux changer…                                   | Fichier                         |
|----------------------------------------------------|---------------------------------|
| Le contenu des 25 slides (texte, notes, HTML)      | `slides.md`                     |
| Couleurs, polices, mise en page, styles            | `template.html` (bloc `<style>`) |
| Options reveal.js (transitions, numéros de slide…) | `template.html` (`Reveal.initialize`, en bas) |
| Le build lui-même                                  | `Build.java`                    |

## Écrire dans `slides.md`

- **Séparer deux slides** : une ligne `---` entourée de lignes vides.

  ```markdown
  Fin de la slide précédente.

  ---

  # Titre de la slide suivante
  ```

- **Notes présentateur** : une ligne commençant par `Note:`. Tout ce qui suit, jusqu'à la slide suivante, n'apparaît que dans la vue présentateur (touche `S`).
- **Classes et attributs de slide** : un commentaire en tête de slide.

  ```markdown
  <!-- .slide: class="section" data-background-color="#FF7A1A" -->
  <!-- .slide: data-year="1985" -->
  ```

  `class="section"` donne une slide de section (fond plein, texte blanc), `class="title"` la slide de titre, et `data-year` affiche le sticker d'année en haut à droite.
- **Du HTML dans le Markdown** est permis (cartes, chips, encadrés). Évite les lignes vides à l'intérieur d'un bloc HTML : le Markdown reprendrait la main au milieu du bloc et la mise en page casserait.
- Les classes déjà prêtes (`.chip`, `.path`, `.trio`, `.decs`, `.unis`, `.tip`, `.aside`, etc.) sont définies dans le `<style>` de `template.html`. Le plus simple est de copier une slide existante qui ressemble à ce que tu veux.

## `template.html`

C'est la coquille : styles, initialisation de reveal.js, et des marqueurs (`/*__MD__*/`, `/*__REVEAL_JS__*/`, etc.) que le build remplace par le contenu de `slides.md` et de reveal.js. Ne touche pas à ces marqueurs : s'il en manque un, le build s'arrête avec « Marqueur absent ».

## Ajouter une image

1. Déposer l'image dans `images/` (jpg, png, gif, webp ou svg).
2. La référencer dans `slides.md` avec un chemin relatif, et ajouter l'attribution (auteur, licence, source) :

   ```html
   <img src="images/photo.jpg" alt="Description">
   <p class="attrib">Photo : Auteur, Licence · Source</p>
   ```

3. Lancer `jbang Build.java`. Le build remplace chaque `src="images/..."` par l'image encodée en base64 : le HTML final reste un fichier unique qui fonctionne hors ligne. Si le fichier n'existe pas, le build s'arrête avec « Image introuvable ».

Pour des photos côte à côte avec légende, copier le bloc `<div class="photos">` de la slide 1950 (classe `with-photos` sur la slide pour resserrer le texte). Garder des images d'environ 1280 px de large : chacune alourdit le HTML d'un tiers de plus que sa taille.

Pas d'images générées par IA dans cette présentation.

## Présenter

- Ouvrir `developpeur-logiciel.html` dans un navigateur.
- `S` : vue présentateur (notes, minuteur, slide suivante).
- `F` : plein écran. `Esc` : vue d'ensemble de toutes les slides.
- **PDF** : ouvrir dans Chrome avec `?print-pdf` ajouté à l'URL, puis Imprimer → Enregistrer en PDF.

## Avant le jour J

Les cotes R et les détails des programmes (slides 16 à 18, et leurs notes) ont été vérifiés en novembre 2026 sur les sites officiels. Les seuils de cote R changent chaque année : les revérifier avant de présenter.
