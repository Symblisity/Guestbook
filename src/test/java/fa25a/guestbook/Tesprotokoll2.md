
# Testprotokoll

**Integrationstest des Web Service „Gästebuch“**

## 1. Allgemeine Angaben

| Feld | Inhalt |
|---|---|
| ID des Tests | TL-GB-2026-01 |
| Beschreibung | Integrationstest aller Endpunkte des Web Service „Gästebuch“ (Basis-URL `/guestbook/entries`: Anlegen, Auslesen einzeln und gesamt, Ändern, Löschen) im Zusammenspiel. Die Testfälle bauen aufeinander auf und werden in der angegebenen Reihenfolge ausgeführt. Der Test ist nach jedem Update des Web Service zu wiederholen. |
| Getesteter Service | GitHub-Repository Symblisity/Guestbook (Spring Boot, REST, H2-Datenbank), Branch master |
| Testwerkzeug | Postman |
| Erstellt von | Jan Hundelt |
| Erstellt am | 25.11.2026 |
| Getestet von |  |
| Getestet am |  |

## 2. Vorbereitung

- Der Web Service ist gestartet und unter `http://localhost:8080` erreichbar (Standardport, im Projekt ist kein anderer Port konfiguriert).
- Das Gästebuch ist leer. Die Datenbank wird bei jedem Start neu erzeugt (`spring.jpa.hibernate.ddl-auto=create`). Ein Neustart des Service stellt daher den Ausgangszustand her. Nur dann stimmen die erwarteten IDs (1, 2, 3).
- In Postman ist bei Requests mit Body der Body-Typ „raw / JSON“ gewählt (Header `Content-Type: application/json`). Ausnahme ist TC-11, dort wird bewusst ungültiger Text gesendet.
- Die Testfälle TC-01 bis TC-17 werden der Reihe nach ausgeführt. Weichen IDs ab, sind die Testdaten der folgenden Testfälle entsprechend anzupassen.

## 3. Testfälle

Das erwartete Ergebnis beschreibt das Verhalten des Service laut Quellcode. „Tatsächliches Ergebnis“ und „Status“ werden beim Testlauf in Postman eingetragen.

### TC-01: Auslesen aller Einträge bei leerem Gästebuch

- **Voraussetzung:** Service frisch gestartet, es existiert kein Eintrag.
- **Request:** `GET /guestbook/entries`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 200 OK. Response-Body ist eine leere Liste: []
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-02: Anlegen des ersten Eintrags

- **Voraussetzung:** Gästebuch ist leer (TC-01 ausgeführt).
- **Request:** `POST /guestbook/entries`
- **Testdaten:** `{"title":"Wunderbarer Abend","comment":"Das Essen war hervorragend.","commenter":"Lena Hoffmann"}`
- **Erwartetes Ergebnis:** Status 201 Created. Response: id = 1, title = "Wunderbarer Abend", comment = "Das Essen war hervorragend.", commenter = "Lena Hoffmann".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-03: Anlegen eines zweiten Eintrags

- **Voraussetzung:** Eintrag mit ID 1 existiert (TC-02).
- **Request:** `POST /guestbook/entries`
- **Testdaten:** `{"title":"Gern wieder","comment":"Sehr freundliches Personal.","commenter":"Tim Wagner"}`
- **Erwartetes Ergebnis:** Status 201 Created. Response: id = 2, title = "Gern wieder", comment = "Sehr freundliches Personal.", commenter = "Tim Wagner".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-04: Auslesen eines existierenden Eintrags

- **Voraussetzung:** Eintrag mit ID 1 existiert.
- **Request:** `GET /guestbook/entries/1`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 200 OK. Response: id = 1, title = "Wunderbarer Abend", comment = "Das Essen war hervorragend.", commenter = "Lena Hoffmann".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-05: Auslesen aller Einträge (Reihenfolge)

- **Voraussetzung:** Einträge mit ID 1 und 2 existieren.
- **Request:** `GET /guestbook/entries`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 200 OK. Liste mit genau 2 Einträgen, absteigend nach ID: an erster Stelle ID 2 ("Gern wieder"), an zweiter Stelle ID 1 ("Wunderbarer Abend").
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-06: Ändern eines existierenden Eintrags

- **Voraussetzung:** Eintrag mit ID 2 existiert.
- **Request:** `PUT /guestbook/entries`
- **Testdaten:** `{"id":2,"title":"Gern wieder (Nachtrag)","comment":"Wir kommen im Frühjahr zurück.","commenter":"Tim Wagner"}`
- **Erwartetes Ergebnis:** Status 200 OK. Response: id = 2, title = "Gern wieder (Nachtrag)", comment = "Wir kommen im Frühjahr zurück.", commenter = "Tim Wagner".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-07: Prüfen, ob die Änderung gespeichert wurde

- **Voraussetzung:** TC-06 wurde ausgeführt.
- **Request:** `GET /guestbook/entries/2`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 200 OK. Response enthält die geänderten Daten aus TC-06: title = "Gern wieder (Nachtrag)", comment = "Wir kommen im Frühjahr zurück.".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-08: Ändern eines nicht existierenden Eintrags

- **Voraussetzung:** Kein Eintrag mit ID 42 vorhanden.
- **Request:** `PUT /guestbook/entries`
- **Testdaten:** `{"id":42,"title":"Phantom","comment":"Gibt es nicht.","commenter":"Niemand"}`
- **Erwartetes Ergebnis:** Status 500 Internal Server Error (aktuelles Verhalten laut Code: nicht behandelte Ausnahme, weil der Eintrag nicht gefunden wird). Es wird kein Eintrag mit ID 42 angelegt.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-09: Auslesen eines nicht existierenden Eintrags

- **Voraussetzung:** Kein Eintrag mit ID 42 vorhanden (TC-08 hat keinen angelegt).
- **Request:** `GET /guestbook/entries/42`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 500 Internal Server Error (aktuelles Verhalten laut Code). Kein Eintrag im Response-Body.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-10: Auslesen mit ungültiger ID (keine Zahl)

- **Voraussetzung:** Keine besonderen Voraussetzungen.
- **Request:** `GET /guestbook/entries/abc`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 400 Bad Request, da "abc" nicht in eine Zahl umgewandelt werden kann. Kein Eintrag im Response-Body.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-11: Anlegen mit nicht lesbarem Request-Body

- **Voraussetzung:** Keine besonderen Voraussetzungen.
- **Request:** `POST /guestbook/entries`
- **Testdaten:** `das ist kein JSON`
- **Erwartetes Ergebnis:** Status 400 Bad Request. Es wird kein Eintrag angelegt.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-12: Anlegen mit mitgesendeter ID (ID wird ignoriert)

- **Voraussetzung:** Einträge mit ID 1 und 2 existieren, die nächste freie ID ist 3.
- **Request:** `POST /guestbook/entries`
- **Testdaten:** `{"id":77,"title":"Mit ID","comment":"Die ID sollte ignoriert werden.","commenter":"Eva Klein"}`
- **Erwartetes Ergebnis:** Status 201 Created. Response: id = 3 (nicht 77), title = "Mit ID", comment = "Die ID sollte ignoriert werden.", commenter = "Eva Klein".
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-13: Löschen eines existierenden Eintrags

- **Voraussetzung:** Eintrag mit ID 1 existiert.
- **Request:** `DELETE /guestbook/entries/1`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 204 No Content. Response-Body ist leer.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-14: Auslesen des gelöschten Eintrags

- **Voraussetzung:** TC-13 wurde ausgeführt.
- **Request:** `GET /guestbook/entries/1`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 500 Internal Server Error (aktuelles Verhalten laut Code, Eintrag existiert nicht mehr).
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-15: Auslesen aller Einträge nach dem Löschen

- **Voraussetzung:** TC-12 und TC-13 wurden ausgeführt.
- **Request:** `GET /guestbook/entries`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 200 OK. Liste mit genau 2 Einträgen: zuerst ID 3 ("Mit ID"), dann ID 2 ("Gern wieder (Nachtrag)"). ID 1 ist nicht enthalten.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-16: Erneutes Löschen eines bereits gelöschten Eintrags

- **Voraussetzung:** Eintrag mit ID 1 wurde in TC-13 gelöscht.
- **Request:** `DELETE /guestbook/entries/1`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 204 No Content. Das Löschen einer nicht vorhandenen ID führt zu keinem Fehler. Response-Body ist leer.
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

### TC-17: Nicht unterstützte Methode auf der Basis-URL

- **Voraussetzung:** Keine besonderen Voraussetzungen.
- **Request:** `DELETE /guestbook/entries`
- **Testdaten:** keine (ID bzw. Parameter siehe URL)
- **Erwartetes Ergebnis:** Status 405 Method Not Allowed. Es werden keine Einträge gelöscht (GET auf die Basis-URL liefert danach weiterhin die 2 Einträge aus TC-15).
- **Tatsächliches Ergebnis:**
- **Status:** [ ] bestanden  [ ] fehlgeschlagen

## 4. Hinweise zu den erwarteten Ergebnissen

- Bei einer nicht existierenden ID liefern GET (TC-09, TC-14) und PUT (TC-08) laut Code den Status 500, weil im Service `findById(id).get()` ohne Prüfung aufgerufen wird. Nach REST-Konvention wäre 404 Not Found zu erwarten. Eingetragen ist hier bewusst das aktuelle Verhalten.
- DELETE mit nicht vorhandener ID liefert 204 (TC-16), weil `deleteById` unbekannte IDs ignoriert.
- Das Feld `date` wird in der Datenbank gespeichert, aber nicht im Response ausgegeben und daher nicht geprüft.
- Es gibt keine Validierung von Pflichtfeldern. Das Anlegen mit leeren Feldern ist daher nicht als Fehlerfall getestet.

## 5. Gesamtergebnis

- **Gesamtstatus:** [ ] alle Testfälle bestanden  [ ] mindestens ein Testfall fehlgeschlagen
- **Bemerkungen:**