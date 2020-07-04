/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.language;

import com.zyonicsoftware.maddox.core.main.Maddox;
import de.daschi.javalanguageapi.api.LanguageAPI;
import de.daschi.javalanguageapi.api.LanguageHandler;

import java.io.File;

public class LanguageConfigFileManager {

    public void initLanguageConfig(Maddox maddox) {

        new File("languages/").mkdir();

        LanguageHandler languageHandler = new LanguageHandler(LanguageHandler.LanguageSaveMode.YAML, "languages/", "EN");
        LanguageAPI.setLanguageHandler(languageHandler);
        LanguageAPI.setLanguage("EN");

        //Help
        try {
            File file = new File("languages/EN.yml");

            if (file.createNewFile()) {
                LanguageAPI.setValue("Help-Category", "Information", "EN");
                LanguageAPI.setValue("Help-Desc", "Shows helpful Information", "EN");
                LanguageAPI.setValue("Help-Syntax", "help <Command>", "EN");

                LanguageAPI.setValue("Settings-Category", "Settings", "EN");
                LanguageAPI.setValue("Language-Desc", "Command for switching Language-Settings", "EN");
                LanguageAPI.setValue("Language-Syntax", "lang <Language>", "EN");
                LanguageAPI.setValue("Language-Set-1", "The Language for '", "EN");
                LanguageAPI.setValue("Language-Set-2", "' was set to '", "EN");
                LanguageAPI.setValue("Language-Set-3", "'!", "EN");

                LanguageAPI.setValue("SetPrefix-Desc", "This Commands enables you to choose a server-specific prefix for " + maddox.getName() + ".", "EN");
                LanguageAPI.setValue("SetPrefix-Syntax", "setprefix <new Prefix>", "EN");
                LanguageAPI.setValue("SetPrefix-Set-1", "The Prefix for '", "EN");
                LanguageAPI.setValue("SetPrefix-Set-2", "' was set to '", "EN");
                LanguageAPI.setValue("SetPrefix-Set-3", "' !", "EN");

                LanguageAPI.setValue("AutoRole-Desc", "This Command enables you to set up " + maddox.getName() + " to give new Users that are just now joining your Discord specefied Roles", "EN");
                LanguageAPI.setValue("AutoRole-Syntax", "<PREFIX>autorole add @Role | <PREFIX>autorole remove @Role", "EN");
                LanguageAPI.setValue("AutoRole-Response-1", "The Role <ROLE> was added to Autoroles", "EN");
                LanguageAPI.setValue("AutoRole-Response-2", "The Role <ROLE> was removed from the Autoroles", "EN");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        languageHandler.setLanguage("DE");
        LanguageAPI.setLanguage("DE");

        try {
            File file = new File("languages/DE.yml");

            if (file.createNewFile()) {
                LanguageAPI.setValue("Help-Category", "Informationen", "DE");
                LanguageAPI.setValue("Help-Desc", "Zeigt hilfreiche Informationen.", "DE");
                LanguageAPI.setValue("Help-Syntax", "help <command>", "DE");

                LanguageAPI.setValue("Settings-Category", "Einstellungen", "DE");
                LanguageAPI.setValue("Language-Desc", "Command um die Sprache umzustellen.", "DE");
                LanguageAPI.setValue("Language-Syntax", "lang <Sprache>", "DE");
                LanguageAPI.setValue("Language-Set-1", "Die Sprache von '", "DE");
                LanguageAPI.setValue("Language-Set-2", "' wurde auf '", "DE");
                LanguageAPI.setValue("Language-Set-3", "' gesetzt!", "DE");


                LanguageAPI.setValue("SetPrefix-Desc", "Dieser Command ermöglicht es dir einen Serverspezifischen Prefix für " + maddox.getName() + " zu setzen.", "DE");
                LanguageAPI.setValue("SetPrefix-Syntax", "setprefix <new Prefix>", "DE");
                LanguageAPI.setValue("SetPrefix-Set-1", "Der Prefix für '", "DE");
                LanguageAPI.setValue("SetPrefix-Set-2", "' wurde auf '", "DE");
                LanguageAPI.setValue("SetPrefix-Set-3", "' gesetzt!", "DE");

                LanguageAPI.setValue("AutoRole-Desc", "Dieser Command ermöglicht es dir " + maddox.getName() + " darauf zu programmieren einem neuen Benutzer der deinem Discord Server beitritt bestimmte Rollen zu geben", "DE");
                LanguageAPI.setValue("AutoRole-Syntax", "<PREFIX>autorole add @Rolle | <PREFIX>autorole remove @Rolle", "DE");
                LanguageAPI.setValue("AutoRole-Response-1", "Die Rolle <ROLE> wurde zu den Autorollen hinzugefügt", "DE");
                LanguageAPI.setValue("AutoRole-Response-2", "Die Rolle <ROLE> wurde von den Autorollen entfernt", "DE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        languageHandler.setLanguage(maddox.getDefaultLanguage());
        LanguageAPI.setLanguage(maddox.getDefaultLanguage());
    }
}
