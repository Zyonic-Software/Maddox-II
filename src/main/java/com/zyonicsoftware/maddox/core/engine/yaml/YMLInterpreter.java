/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.core.engine.yaml;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class YMLInterpreter {

    public Map readYML(String filename) {
        try {
            YamlReader yamlReader = new YamlReader(new FileReader(filename));

            Object object = yamlReader.read();

            return (Map) object;

        } catch (Exception e) {
            return null;
        }
    }

    public void createYML(String filename){
        try {
            new File(filename);
            YamlWriter yamlWriter = new YamlWriter(new FileWriter(filename));

            yamlWriter.write("token: \n");
            yamlWriter.write("amountShards: \n");
            yamlWriter.write("defaultPrefix: \n");
            yamlWriter.write("defaultColor: \n");
            yamlWriter.write("defaultBotName: \n");

            yamlWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
