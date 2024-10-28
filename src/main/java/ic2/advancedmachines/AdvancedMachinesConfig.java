package ic2.advancedmachines;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;

public class AdvancedMachinesConfig {

    public static final String SOUND_CAT = "Machine Sounds";
    public static final String SOUND_CAT_DESC = "Sound files to use on operation. Remember to use '/' instead of backslashes and the Sound directory starts on ic2/sounds. Set empty to disable.";
    public static final String ITEM_IDS_CAT = "Item IDs";
    public static final String BLOCK_IDS_CAT = "Block IDs";

    public static Configuration MAIN_CONFIG;
    public static String[] LANGS;

    public static int ADV_UPGRADE_ID;
    public static int ADV_MACHINE_ID;

    public static String MACERATOR_WORK_SOUND;
    public static String COMPRESSOR_WORK_SOUND;
    public static String EXTRACTOR_WORK_SOUND;
    public static String INDUCTION_WORK_SOUND;
    public static String RECYCLER_WORK_SOUND;
    public static String INTERRUPT_SOUND;

    public static void init() {
        MAIN_CONFIG = new Configuration(new File((File) FMLInjectionData.data()[6], "config/advMachines.cfg"));
        MAIN_CONFIG.load();

        LANGS = getStrings("language Support", null,"localizations", new String[] {"en_US", "ru_RU"}, "Supported localizations.");
        MACERATOR_WORK_SOUND = getString(SOUND_CAT, SOUND_CAT_DESC, "maceratorWorkSound", "Machines/MaceratorOp.ogg", "Macerator Work Sound");
        COMPRESSOR_WORK_SOUND = getString(SOUND_CAT, SOUND_CAT_DESC, "compressorWorkSound", "Machines/CompressorOp.ogg", "Compressor Work Sound");
        EXTRACTOR_WORK_SOUND = getString(SOUND_CAT, SOUND_CAT_DESC, "extractorWorkSound", "Machines/ExtractorOp.ogg", "Extractor Work Sound");
        INDUCTION_WORK_SOUND = getString(SOUND_CAT, SOUND_CAT_DESC, "inductionWorkSound", "Machines/Induction Furnace/InductionLoop.ogg", "Induction Work Sound");
        RECYCLER_WORK_SOUND = getString(SOUND_CAT, SOUND_CAT_DESC, "recyclerWorkSound", "Machines/RecyclerOp.ogg", "Recycler Work Sound");
        INTERRUPT_SOUND = getString(SOUND_CAT, SOUND_CAT_DESC, "interruptSound", "Machines/InterruptOne.ogg", "Interrupt Work Sound");

        ADV_UPGRADE_ID = getId(ITEM_IDS_CAT, null, "advancedUpgradeId", 29776, "Advanced Upgrade ID");
        ADV_MACHINE_ID = getId(BLOCK_IDS_CAT, null, "advancedMachineBlockId", 188, "Advanced Machine Block ID");

        if (MAIN_CONFIG != null) {
            MAIN_CONFIG.save();
        }
    }

    private static String[] getStrings(String cat, @Nullable String tagComment, String tag, String[] defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = MAIN_CONFIG.get(cat, tag, defaultValue);
        prop.comment = comment + (tagComment == null ? "" : tagComment + "\n") + "Default: " + Arrays.toString(defaultValue);
        return prop.valueList;
    }

    private static String getString(String cat, @Nullable String tagComment, String tag, String defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = MAIN_CONFIG.get(cat, tag, defaultValue);
        prop.comment = comment + (tagComment == null ? "" : tagComment + "\n") + "Default: " + defaultValue;
        return prop.value;
    }

    private static int getId(String cat, @Nullable String tagComment, String tag, int defaultValue, String comment) {
        comment = comment.replace("{t}", tag) + "\n";
        Property prop = MAIN_CONFIG.get(cat, tag, defaultValue);
        prop.comment = comment + (tagComment == null ? "" : tagComment + "\n") + "Default: " + defaultValue;
        int value = prop.getInt(defaultValue);
        prop.value = Integer.toString(value);
        return value;
    }
}
