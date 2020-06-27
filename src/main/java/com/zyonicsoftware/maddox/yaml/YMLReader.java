/*
 * Zyonic Software - 2020 - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used freely in compliance with the Apache 2.0 License.
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.maddox.yaml;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileReader;
import java.util.Map;

public class YMLReader {

    public Map readYML(String filename) {
        try {
            YamlReader yamlReader = new YamlReader(new FileReader(filename));

            Object object = yamlReader.read();

            return (Map) object;

        } catch (Exception e) {
            return null;
        }
    }

}
