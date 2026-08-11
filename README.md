<h1 align="center">🔧 Ferramenta</h1>

<p align="center">
  Un e-commerce per un negozio di ferramenta.<br/>
  Il mio primo progetto Java "serio", gennaio 2022, prima ancora di finire il corso.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-8-ED8B00?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Jakarta%20EE-JSF%202.3-4E9BCD?style=flat-square&logo=jakartaee&logoColor=white"/>
  <img src="https://img.shields.io/badge/PrimeFaces-UI-20B2AA?style=flat-square"/>
  <img src="https://img.shields.io/badge/MyBatis-persistence-C74634?style=flat-square"/>
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/build-Maven%20(WAR)-C71A36?style=flat-square&logo=apachemaven&logoColor=white"/>
</p>

---

Due parole di contesto, perché sono onesto: questo è il primo progetto che ho scritto quando stavo ancora imparando Java, nel 2022. Con gli occhi di oggi rifarei parecchie cose in modo diverso, ma è proprio per questo che lo tengo online. È la mia riga di partenza, e la storia non si cancella. Lo lascio com'era.

**Ferramenta** è un'applicazione web full-stack che simula il negozio online di una ferramenta: l'utente si registra, sfoglia il catalogo, riempie il carrello e completa l'ordine; l'amministratore gestisce articoli, categorie e ordini da un'area riservata.

## Funzionalità

- Registrazione, login e area riservata utente
- Catalogo articoli organizzato per categorie, con navigazione e dettaglio
- Carrello con aggiunta/rimozione articoli e riepilogo del totale
- Checkout completo: indirizzo di spedizione, metodo di pagamento con carta e conferma ordine
- Back-office per gestire articoli, categorie, utenti e ordini
- Interfaccia bilingue, italiano e inglese (`message_it` / `message_en`)

## Stack tecnico

- **Java 8** su **Jakarta EE**
- **JSF 2.3** con **PrimeFaces** per l'interfaccia (pagine `.xhtml`)
- **CDI** ed **EJB** per bean e logica applicativa
- **MyBatis** come layer di persistenza su **MySQL**
- **Maven** per il build, con artefatto **WAR** da deployare su **JBoss / WildFly**

## Architettura

Il codice, circa 70 classi Java, segue una classica organizzazione a livelli sotto `it.exolab`:

```
src/it/exolab/
├── bean/         managed bean JSF (presentazione)
├── service/      logica di business
├── dao/          accesso ai dati
├── mapper/       mapper MyBatis
├── dto/ pojo/    modelli dati
├── constants/    costanti applicative
└── exception/    eccezioni custom
WebContent/
├── pages/        viste .xhtml (login, articoli, carrello, ordine, area riservata...)
└── WEB-INF/      web.xml, faces-config.xml, beans.xml
```

## Come eseguirlo

Serve un ambiente Java EE classico, quindi non è un progetto da avviare con un solo comando.

1. Crea un database MySQL chiamato `ferramenta`. La connessione è configurata in `sql-map-config.xml`, di default `jdbc:mysql://localhost:3306/ferramenta`.
2. Builda il WAR con Maven:
   ```bash
   mvn clean package
   ```
3. Deploya il `.war` risultante su un server JBoss o WildFly e apri l'app dal browser.

---

<p align="center"><sub>Di <a href="https://github.com/FrancescoRossi-1">Francesco Rossi</a>, dove tutto è cominciato.</sub></p>
