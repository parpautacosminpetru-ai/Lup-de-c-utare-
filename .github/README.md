# Lupă OCR — prototip Android offline

Versiunea 0.1 este gândită ca o **lupă electronică pentru cuvinte-cheie** pe cărți și documente fizice. Camera nu înregistrează și aplicația nu interpretează semantic textul.

## Ce face versiunea aceasta

- Cameră live Android, fără înregistrare.
- OCR pe dispozitiv cu model ML Kit inclus în aplicație.
- Listă fără limită artificială de termeni: un cuvânt sau o expresie pe fiecare linie.
- Verifică toți termenii din listă la fiecare rezultat OCR și marchează simultan toate zonele găsite.
- Trei moduri: **Exact**, **Începe cu**, **Conține**.
- Opțional: folosește doar primele **N caractere** din fiecare termen în modurile „Începe cu” și „Conține”.
- Potrivire fără diferență între litere mari/mici; opțional ignoră diferența de diacritice.
- Highlight vizual pulsatoriu, fără sunet.
- Lanternă ON/OFF.
- Camera poate fi oprită și repornită.
- Dicționar offline local, importabil ca CSV/TSV. Se pot importa mai multe fișiere.
- Când este găsit un termen, aplicația caută exact termenul OCR în dicționarul local și afișează definiția. Un highlight poate fi atins pentru a verifica termenul respectiv.
- Nu există interpretare AI, sinonime inventate sau „sens în context”. Contextul rămâne de verificat de utilizator în sursa fizică.
- Manifestul nu cere permisiune INTERNET; doar CAMERA.

## Formatul dicționarului

Pentru v0.1, formatul recomandat este CSV UTF-8. Coloanele obligatorii sunt termenul și definiția. Sunt acceptate denumiri românești sau englezești pentru antet.

Exemplu:

```csv
term,definition,synonyms,antonyms,source
universitate,"Definiția ta aici","sinonim 1; sinonim 2",,Dicționarul meu
```

Coloane recunoscute:

- termen: `term`, `termen`, `cuvant`, `cuvânt`, `word`
- definiție: `definition`, `definitie`, `definiție`, `sens`, `meaning`
- sinonime: `synonyms`, `sinonime`, `sinonim`
- antonime: `antonyms`, `antonime`, `antonim`
- sursă: `source`, `sursa`, `sursă`

Separatorul poate fi virgulă, punct și virgulă sau TAB. Intrările sunt stocate în SQLite pe telefon pentru căutare rapidă.

Un PDF de dicționar nu este căutat direct în fiecare cadru. Pentru v0.1 este mai stabil să fie convertit o singură dată într-un CSV structurat, apoi importat.

## Observație despre limbi

Modelul OCR inclus aici este modelul ML Kit pentru **scriere latină**, potrivit inclusiv pentru română. Motorul de potrivire nu depinde de limbă, dar OCR-ul pentru alte sisteme de scriere (de exemplu chineză, japoneză, coreeană sau devanagari) cere modele OCR dedicate. Acestea pot fi adăugate într-o versiune ulterioară.

## Structura proiectului

- `MainActivity.kt` — cameră, controale, OCR, toate potrivirile simultane, lanternă și dicționar.
- `OverlayView.kt` — desenează toate highlight-urile și efectul de puls/clipire.
- `KeywordMatcher.kt` — potrivire exactă / prefix / conține, N caractere, normalizare diacritice.
- `DictionaryDatabase.kt` — bază SQLite locală.
- `CsvDictionaryImporter.kt` — import CSV/TSV.
- `sample_dictionary.csv` — fișier minimal de test.
- `core-test/MatcherTest.kt` — teste independente pentru motorul de potrivire.

## Compilare APK

Proiectul este pregătit pentru Android Studio / Gradle, cu `compileSdk 37`, `minSdk 23` și `targetSdk 37`.

1. Deschide folderul `LupaOCR` în Android Studio.
2. Lasă Gradle să descarce dependențele la prima compilare.
3. Selectează `Build > Build APK(s)`.
4. APK-ul rezultat poate fi instalat pe telefon; după instalare funcțiile de cameră/OCR/dicționar sunt proiectate să funcționeze offline.

În mediul în care a fost generat acest proiect nu este instalat Android SDK/Gradle și accesul shell la depozitele Android este blocat, deci APK-ul nu a putut fi compilat aici. Motorul pur de potrivire a fost totuși compilat și testat separat cu Kotlin/JVM.
