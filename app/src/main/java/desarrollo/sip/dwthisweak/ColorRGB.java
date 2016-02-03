package desarrollo.sip.dwthisweak;

import android.graphics.Color;

/**
 * Created by DESARROLLO on 02/02/16.
 */
public class ColorRGB extends Color {

    int red;
    int green;
    int blue;
    int color;
    int media;
    int binario;

    public ColorRGB(int color) {
        this.color = color;
        this.red = Color.red(color);
        this.green = Color.green(color);
        this.blue = Color.blue(color);
        this.media = ((int) (red + green + blue) / 3);
    }

    public ColorRGB(int red,int green,int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.media = ((int) (red + green + blue) / 3);
    }

    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getMedia() {
        return ((media << 16) | (media << 8) | (media));
    }

    public int getBinario(int umbral) {
        if (media <= umbral) {
            binario = 0;
        } else {
            binario = 255;
        }
        return ((binario << 16) | (binario << 8) | (binario));
    }

    public int getGreen() {
        return green;
    }

    @Override
    public String toString() {
        return "ColorRGB{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
