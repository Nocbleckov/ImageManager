package desarrollo.sip.dwthisweak;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.NumberPicker;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class PrimeActivity extends AppCompatActivity {

    ImageView imagenSrc;
    NumberPicker numbrePicker;
    Bitmap origin;

    int[] originalPixels;
    int[] array = {1,1,1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime);

        imagenSrc = (ImageView)findViewById(R.id.imagenScr_Prime);

        numbrePicker = (NumberPicker)findViewById(R.id.numberPicker_Prime);
        origin = ((BitmapDrawable)imagenSrc.getDrawable()).getBitmap();

        numbrePicker.setMaxValue(255);
        numbrePicker.setMinValue(0);

        listenerPicker();

    }

    public void listenerPicker(){

        numbrePicker.setValue(120);

        numbrePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                imagenSrc.setImageBitmap(tratamiento(origin, picker.getValue()));
            }
        });

    }


    public Bitmap tratamiento (Bitmap temp,int umbral){
        int imageWidth = temp.getWidth();
        int imageHeight = temp.getHeight();

        //Bitmap bitmapTemp = Bitmap.createBitmap(imageWidth,imageHeight, Bitmap.Config.ARGB_8888);
        Bitmap bitmapTemp = Bitmap.createBitmap(temp);
        originalPixels = new int[imageHeight*imageWidth];
        int[] pixelsBin = new int[imageHeight*imageWidth];


        try{

            temp.getPixels(originalPixels, 0, imageWidth, 1, 1, imageWidth - 1, imageHeight - 1);

            for(int i = 0;i<originalPixels.length;i++){
                ColorRGB colorRGB = new ColorRGB(originalPixels[i]);
                //pixelsBin[i] = colorRGB.getMedia();
            }


            //IntBuffer intBuffer = IntBuffer.allocate(imageHeight*imageWidth);
            //intBuffer.put(pixelsBin);
            //intBuffer.rewind();

            //bitmapTemp.copyPixelsFromBuffer(intBuffer);

        }catch (Exception e){
            e.printStackTrace();
        }


        return  bitmapTemp;
    }


    public void canalMaxMin(ColorRGB pixelColor,int[] max, int[] min){
        if(){
            max = {pixelColor.red, pixelColor.getGreen(), pixelColor.getBlue()};
            min = {pixelColor.red, pixelColor.getGreen(), pixelColor.getBlue()};
        }
            if(max[0]<=pixelColor.getRed()){
                max[0] = pixelColor.getRed();
            }else{
                min[0] = pixelColor.getRed();
            }
            if(max[1]<=pixelColor.getRed()){
                max[1] = pixelColor.getRed();
            }else{
                min[1] = pixelColor.getRed();
            }
            if(max[2]<=pixelColor.getRed()){
                max[2] = pixelColor.getRed();
            }else{
                min[2] = pixelColor.getRed();
            }

        ColorRGB colorMax = new ColorRGB(max[0],max[1],max[2]);
        ColorRGB colorMin = new ColorRGB(min[0],min[1],min[2]);

        Log.wtf("Canales Max",colorMax.toString());
        Log.wtf("Canales Min",colorMin.toString());
    }




}
