package curseking.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScalingHealthConfigUtil {
    public static boolean isCustomHeartRenderingEnabled(File configDir) {
        File cfg = new File(configDir, "scalinghealth/main.cfg");
        if (!cfg.exists()) return false;
        Pattern pattern = Pattern.compile("B:\"Custom Heart Rendering\"\\s*=\\s*(true|false)", Pattern.CASE_INSENSITIVE);
        try (BufferedReader reader = Files.newBufferedReader(cfg.toPath())) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    return Boolean.parseBoolean(m.group(1));
                }
            }
        } catch (IOException ignored) {}
        return false;
    }
}