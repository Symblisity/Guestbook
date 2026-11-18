# Testprotokoll: Integrationstest des Web Service „Gästebuch“

Test aller Endpunkte im Zusammenspiel (Anlegen, Auslesen, Ändern, Löschen)

## 1. Allgemeine Angaben zum Testlauf

| Feld | Inhalt |
|---|---|
| ID des Testlaufs | TL-GB-001 |
| Beschreibung | Integrationstest der REST-Endpunkte des Web Service Gästebuch (Basis-URL `/guestbook/entries`). Die Testfälle bauen aufeinander auf und müssen in der angegebenen Reihenfolge ausgeführt werden. |
| Erstellt von | ______________________ |
| Erstellt am | 18.11.2026 |
| Getestet von | ______________________ |
| Getestet am | ______________________ |
| Testwerkzeug | Postman |
| Getestete Version | GitHub-Repository Symblisity/Guestbook, Branch master |

## 2. Voraussetzungen und Vorbereitung

- Der Web Service ist gestartet und unter `http://localhost:8080` erreichbar (Standardport, im Projekt ist kein anderer Port konfiguriert).
- Die Datenbank ist leer. Das Projekt erzeugt die H2-Datenbank bei jedem Start neu (`spring.jpa.hibernate.ddl-auto=create`), ein Neustart des Services setzt die Daten also zurück. Nur dann stimmen die erwarteten IDs (1, 2, 3).
- In Postman ist bei allen Requests mit Body der Header `Content-Type: application/json` gesetzt (Body-Typ „raw“ / „JSON“).
- Die Testfälle TC-01 bis TC-15 werden der Reihe nach ausgeführt. Weichen die IDs ab, müssen die Testdaten der folgenden Testfälle entsprechend angepasst werden.

## 3. Testfälle

Die Felder „Tatsächliches Ergebnis“ und „Status“ werden beim Testlauf in Postman ausgefüllt. Das erwartete Ergebnis beschreibt das Verhalten des Services laut Quellcode.

### TC-01: Neuen Gästebucheintrag anlegen (Erfolgsfall)

- **Voraussetzung:** Service läuft, Datenbank ist leer.
- **Request:** `POST /guestbook/entries`
- **Body:** `{"title":"Danke für die schöne Zeit","comment":"Wir hatten einen wundervollen Aufenthalt.","commenter":"Anna Beispiel"}`
- **Erwartetes Ergebnis:** Status **201 Created**. Response: id = 1, title = "Danke für die schöne Zeit", comment = "Wir hatten einen wundervollen Aufenthalt.", commenter = "Anna Beispiel".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-02: Zweiten Gästebucheintrag anlegen

- **Voraussetzung:** TC-01 wurde ausgeführt.
- **Request:** `POST /guestbook/entries`
- **Body:** `{"title":"Gerne wieder","comment":"Sehr nette Gastgeber und leckeres Frühstück.","commenter":"Ben Muster"}`
- **Erwartetes Ergebnis:** Status **201 Created**. Response: id = 2, title = "Gerne wieder", comment = "Sehr nette Gastgeber und leckeres Frühstück.", commenter = "Ben Muster".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-03: Alle Gästebucheinträge auslesen (Sortierung)

- **Voraussetzung:** Einträge mit ID 1 und 2 existieren.
- **Request:** `GET /guestbook/entries`
- **Erwartetes Ergebnis:** Status **200 OK**. Liste mit genau 2 Einträgen, absteigend nach ID sortiert: zuerst ID 2 ("Gerne wieder"), danach ID 1 ("Danke für die schöne Zeit").
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-04: Einzelnen Gästebucheintrag auslesen (Eintrag existiert)

- **Voraussetzung:** Der Gästebucheintrag mit ID 1 existiert.
- **Request:** `GET /guestbook/entries/1` (ID = 1)
- **Erwartetes Ergebnis:** Status **200 OK**. Response: id = 1, title = "Danke für die schöne Zeit", comment = "Wir hatten einen wundervollen Aufenthalt.", commenter = "Anna Beispiel".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-05: Einzelnen Gästebucheintrag auslesen (Eintrag existiert nicht)

- **Voraussetzung:** Der Gästebucheintrag mit ID 999 existiert nicht.
- **Request:** `GET /guestbook/entries/999` (ID = 999)
- **Erwartetes Ergebnis:** Status **500 Internal Server Error** (unbehandelte NoSuchElementException im Service). Kein Gästebucheintrag im Response-Body.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-06: Auslesen mit ungültiger ID (keine Zahl)

- **Voraussetzung:** Keine besonderen Voraussetzungen.
- **Request:** `GET /guestbook/entries/abc` (ID = abc)
- **Erwartetes Ergebnis:** Status **400 Bad Request** (ID kann nicht in eine Zahl umgewandelt werden). Kein Gästebucheintrag im Response-Body.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-07: Gästebucheintrag ändern (Eintrag existiert)

- **Voraussetzung:** Der Gästebucheintrag mit ID 1 existiert.
- **Request:** `PUT /guestbook/entries`
- **Body:** `{"id":1,"title":"Danke für die schöne Zeit (aktualisiert)","comment":"Wir kommen im Sommer wieder.","commenter":"Anna Beispiel"}`
- **Erwartetes Ergebnis:** Status **200 OK**. Response: id = 1, title = "Danke für die schöne Zeit (aktualisiert)", comment = "Wir kommen im Sommer wieder.", commenter = "Anna Beispiel".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-08: Änderung ist dauerhaft gespeichert

- **Voraussetzung:** TC-07 wurde erfolgreich ausgeführt.
- **Request:** `GET /guestbook/entries/1` (ID = 1)
- **Erwartetes Ergebnis:** Status **200 OK**. Response enthält die geänderten Daten aus TC-07 (title = "Danke für die schöne Zeit (aktualisiert)", comment = "Wir kommen im Sommer wieder.").
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-09: Gästebucheintrag ändern (Eintrag existiert nicht)

- **Voraussetzung:** Der Gästebucheintrag mit ID 999 existiert nicht.
- **Request:** `PUT /guestbook/entries`
- **Body:** `{"id":999,"title":"Test","comment":"Test","commenter":"Test"}`
- **Erwartetes Ergebnis:** Status **500 Internal Server Error** (unbehandelte NoSuchElementException). Es wird kein neuer Eintrag angelegt.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-10: Anlegen mit ungültigem JSON

- **Voraussetzung:** Keine besonderen Voraussetzungen.
- **Request:** `POST /guestbook/entries`
- **Body:** `kein json`
- **Erwartetes Ergebnis:** Status **400 Bad Request** (Request-Body nicht lesbar). Es wird kein Eintrag angelegt.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-11: Anlegen mit leerem JSON-Objekt (keine Validierung)

- **Voraussetzung:** Service läuft, ID 3 ist die nächste freie ID.
- **Request:** `POST /guestbook/entries`
- **Body:** `{}`
- **Erwartetes Ergebnis:** Status **201 Created**. Response: id = 3, title = null, comment = null, commenter = null (der Service validiert keine Pflichtfelder).
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-12: Gästebucheintrag löschen (Eintrag existiert)

- **Voraussetzung:** Der Gästebucheintrag mit ID 2 existiert.
- **Request:** `DELETE /guestbook/entries/2` (ID = 2)
- **Erwartetes Ergebnis:** Status **204 No Content**. Leerer Response-Body.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-13: Gelöschten Eintrag auslesen

- **Voraussetzung:** TC-12 wurde erfolgreich ausgeführt.
- **Request:** `GET /guestbook/entries/2` (ID = 2)
- **Erwartetes Ergebnis:** Status **500 Internal Server Error** (Eintrag existiert nicht mehr, unbehandelte NoSuchElementException).
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-14: Alle Einträge nach dem Löschen auslesen

- **Voraussetzung:** TC-11 und TC-12 wurden ausgeführt.
- **Request:** `GET /guestbook/entries`
- **Erwartetes Ergebnis:** Status **200 OK**. Liste mit genau 2 Einträgen: zuerst ID 3 (alle Felder null), danach ID 1 (aktualisierte Daten). Eintrag mit ID 2 ist nicht enthalten.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

### TC-15: Gästebucheintrag löschen (Eintrag existiert nicht)

- **Voraussetzung:** Der Gästebucheintrag mit ID 999 existiert nicht.
- **Request:** `DELETE /guestbook/entries/999` (ID = 999)
- **Erwartetes Ergebnis:** Status **204 No Content** (nicht vorhandene ID wird beim Löschen ohne Fehler ignoriert). Leerer Response-Body.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden [ ] fehlgeschlagen

## 4. Hinweise zum Erwartungswert

- Bei nicht existierender ID geben GET (TC-05, TC-13) und PUT (TC-09) laut Code den Status 500 zurück, weil `findById(id).get()` ohne Prüfung aufgerufen wird. Nach REST-Konvention wäre 404 Not Found sinnvoll. Als erwartetes Ergebnis ist hier bewusst das aktuelle Verhalten des Codes eingetragen.
- Der Service validiert keine Pflichtfelder (TC-11). Das Feld `date` wird gespeichert, aber nicht im Response ausgegeben.
- DELETE auf eine nicht vorhandene ID liefert 204 (TC-15), weil `deleteById` unbekannte IDs ignoriert. Weicht das Ergebnis in Postman ab, ist die Abweichung im Feld „Tatsächliches Ergebnis“ zu notieren.

## 5. Gesamtergebnis

- **Gesamtstatus:** [ ] alle Testfälle bestanden [ ] mindestens ein Testfall fehlgeschlagen
- **Bemerkungen:**