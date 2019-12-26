package fr.opensagres.poi.xwpf.converter.core.registry;

import fr.opensagres.poi.xwpf.converter.core.Color;

public class ColorRegistry
        extends AbstractColorRegistry {

    private static ColorRegistry INSTANCE = new ColorRegistry();

    public static ColorRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    protected Color createColor(String hexColor) {
        if (hexColor != null && !"0xauto".equals(hexColor) && !"0xtransparent".equals(hexColor) && !hexColor.isEmpty() && !"0x".equals(hexColor)) {
            return Color.decode(hexColor);
        }
        return null;
    }

}