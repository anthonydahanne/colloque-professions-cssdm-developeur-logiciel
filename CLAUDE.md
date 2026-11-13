# Présentation « Développeur logiciel : un métier de création »

## Contexte
- Présentateur : Anthony Dahanne, développeur logiciel (HeroDevs, Montréal).
- Événement : Colloque des professions du CSSDM, forum des métiers. Public : élèves de secondaire 3 à 5 (14–17 ans).
- Créneau : bloc PM, section D « La Gestion (inclus l'informatique) ». Le PM comprend la Période 3 (13 h 15–14 h) et la Période 4 (14 h 15–15 h) ; la programmation ne précise pas si Anthony présente une ou deux fois — à confirmer. Viser ~30 min + questions.
- Langue : français (Québec). Ton : « playful », parle à des ados.

## Décisions de design (ne pas revenir dessus sans demander)
- **Aucune image générée par IA.** Anthony a rejeté un essai Canva (images IA, style). Visuels = typographie, couleurs, formes, émojis, SVG dessinés à la main.
- Pas de branding HeroDevs ni de réutilisation d'un deck HeroDevs.
- Outil : reveal.js 5.1.0, **fichier HTML unique autonome** (`developpeur-logiciel.html`), JS/CSS embarqués, fonctionne hors ligne (seules les polices Google Fredoka/Nunito viennent du réseau, avec fallback système).
- Palette : encre `#17162B`, blanc `#FFFFFF`, indigo `#3A2BE0`, tangerine `#FF7A1A`, lime `#C5F23C`, bubblegum `#FF5CA8`, ciel `#39C0F5`, brume `#EEF0FA`.
- Typo : Fredoka (titres, arrondie, bold), Nunito (texte).
- Texte aligné à gauche. Slides « ère » (1950, 1985, 1998, 2010, 2023) portent un sticker d'année via l'attribut `data-year` (CSS `section[data-year]::before`).
- Les cinq personas de la partie 2 (chef de produit, chef de projet / scrum master, développeurs, QA, utilisateurs) sont des **pairs : aucun lien hiérarchique** ne doit apparaître.
- Projet fil rouge de la partie 2 et de la conclusion : ajouter une notification dans Mozaïk pour prévenir, le matin même, d'une journée sans uniforme obligatoire.
- Universités : **2 slides maximum** (francophones / anglophones), jamais une slide par université.

## Structure (25 slides)
1. Titre
2. Salut, moi c'est Anthony! (20 ans de Java, une dizaine de rôles dans des industries variées, carrière surtout technique)
3. Section — Partie 1 : Un métier qui évolue tout le temps!
4. Un dev, c'est quoi? (chips d'apps du quotidien)
5. 1950 — Les dinosaures de l'informatique (mainframes, cartes perforées, banques/transport)
6. 1985 — L'ordi arrive à la maison (PC, Word, Mario/Doom) — Anthony avait dit « début des années 90 » ; sticker mis à 1985, changer `data-year` si souhaité
7. 1998 — Tout le monde sur le web (Amazon, Google)
8. 2010 — Une app dans chaque poche (mobile, réseaux sociaux)
9. 2023 — L'ère de l'IA
10. Section — Partie 2 : Ma place dans l'équipe
11. Une équipe, cinq rôles, un projet (personas côte à côte + encadré projet Mozaïk)
12. Réfléchir et planifier (chef de produit : nuage de canaux ; chef de projet : diagramme de chemin critique en SVG)
13. Du projet au déploiement (cascade : idée → planification → code → test → déploiement, personas associés)
14. Section — Partie 3 : Devenir développeur logiciel
15. Les chemins au Québec (secondaire → cégep → université ; parcours français d'Anthony en aparté)
16. Le cégep : deux portes d'entrée (DEC technique 420.B0 vs préuniversitaire 200.C1 / 200.B1, exemples de cégeps et de cours de session 1)
17. Les universités francophones (UQAM, UdeM ; Polytechnique et ÉTS en encadré)
18. Les universités anglophones (Concordia, McGill)
19. Les compétences qui comptent
20. Section — Conclusion : Un métier du passé ou de l'avenir?
21. Des métiers qui ont changé sans disparaître (bûcheron, agriculteur, ébéniste)
22. L'IA a changé mon métier (autocomplétion → agents IA coordonnés)
23. Si l'IA code, pourquoi apprendre à coder?
24. Retour à notre notification… (sans expert : notif envoyée aux profs, courriels exposés)
25. Questions?

## Fichiers
- `template.html` — coquille HTML : CSS du thème, init reveal. Contient les marqueurs `/*__MD__*/`, etc. remplacés au build.
- `slides.md` — les 25 slides en Markdown reveal (`---` entouré de lignes vides sépare les slides, `Note:` ouvre les notes présentateur, `<!-- .slide: ... -->` pose classes/attributs). La partie 2 (slides 10–13) est surtout du HTML brut (personas, SVG du chemin critique, cascade) : pas de ligne vide à l'intérieur d'un bloc HTML. Les faits des slides 16–18 (codes de DEC, cours, cotes R) ont été vérifiés en novembre 2026 sur les sites officiels ; détails et seuils de cote R dans les notes présentateur. Les cotes R changent chaque année.
- `vendor/` — reveal.js 5.1.0 (reveal.js, reveal.css, theme/white.css, plugin/markdown, plugin/notes) téléchargé depuis cdnjs.
- `Build.java` — assemble `developpeur-logiciel.html` (Java 25, lancé avec JBang, sans dépendance) et intègre les images de `images/` en base64. **Toujours éditer les sources puis relancer `jbang Build.java` depuis le dossier du projet**, jamais le HTML final directement.
- `Screenshot.java` — capture chaque slide dans `shots/` pour vérification visuelle (Playwright Java via `//DEPS`). Lancer `jbang Screenshot.java` depuis le dossier du projet, après le build.
- `README.md` — guide pour modifier la présentation à la main (quel fichier éditer, syntaxe Markdown reveal, build, images). Le garder à jour si la structure ou l'outillage change.
- `images/` — photos référencées dans `slides.md` par `src="images/..."`, intégrées en base64 par `Build.java`. Actuellement : slide 1950 → `ibm704-nasa-1957.jpg` (NASA/NACA, domaine public) et `carte-perforee-fortran.jpg` (Arnold Reinhold, CC BY-SA 2.5) ; slide 1985 → `macintosh-128k.jpg` (Sailko, CC BY 3.0) et `excel3-pub-1990.jpg` (pub Excel 3.0 pour Windows 3.0, Microsoft Sweden, CC BY 2.0 — aucune capture libre d'Excel sous Windows 3.1 n'existe sur Commons). ; slide 1998 → `google-1998.png` (page d'accueil Google déc. 1998, domaine public, recadrée) dans une fenêtre de navigateur dessinée en CSS (`.win`), + une fenêtre d'éditeur CSS avec du HTML 1998 reconstitué (pas de capture libre de Dreamweaver) ; slide 2010 → `steve-jobs-iphone-2007.jpg` (Blake Patterson, CC BY 2.0) et `youtube-2010.png` (capture Wayback Machine du 14 août 2010, **non libre**, choisie par Anthony ; usage en classe seulement — ne pas publier la présentation en ligne avec). Attribution `.attrib` sur chaque slide, liens Commons dans les notes.

## Usage
- Présenter : ouvrir `developpeur-logiciel.html` dans un navigateur ; `S` = vue présentateur (notes, minuteur) ; `F` = plein écran ; `Esc` = vue d'ensemble.
- PDF : ouvrir dans Chrome avec `?print-pdf` ajouté à l'URL, puis Imprimer → Enregistrer en PDF.

## À faire / prochaines étapes
- Images : toujours des photos réelles sous licence libre (Wikimedia Commons…), jamais d'IA ; attribution `.attrib` sur la slide + lien source dans les notes.
- Vérifier le détail du créneau horaire (période 3 et/ou 4).
- Relire le contenu des slides ; notes présentateur à enrichir au goût d'Anthony.
