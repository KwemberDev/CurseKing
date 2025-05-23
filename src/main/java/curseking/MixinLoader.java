package curseking;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("CurseKing")
@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.SortingIndex(99888)
public class MixinLoader implements IFMLLoadingPlugin, IEarlyMixinLoader {

    public MixinLoader() {
        Object initialized = GlobalProperties.get(GlobalProperties.Keys.of("mixin.initialised"));

        if (!(initialized instanceof Boolean) || !((Boolean) initialized)) {
            MixinBootstrap.init();
        }

        Mixins.addConfiguration("mixins.curseking.json");
        Mixins.addConfiguration("mixins.cursekingclient.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return java.util.Arrays.asList(
                "mixins.curseking.json",
                "mixins.cursekingclient.json"
        );
    }
}
