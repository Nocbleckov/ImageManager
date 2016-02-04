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

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.LoginButton;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class PrimeActivity extends AppCompatActivity {

    ImageView imagenSrc;
    NumberPicker numbrePicker;
    LoginButton login;
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

        login = (LoginButton)findViewById(R.id.loginButton);
        login.setReadPermissions("user_friend");

        numbrePicker.setMaxValue(255);
        numbrePicker.setMinValue(0);


        FacebookSdk.sdkInitialize(getApplicationContext());


        new GraphRequest(AccessToken.getCurrentAccessToken(), "me/friends", null, HttpMethod.GET, new GraphRequest.Callback(){

            @Override
            public void onCompleted(GraphResponse response) {
                Log.wtf("RESPONSE",response.toString());
            }
        }).executeAsync();

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
        int[][] histograma = new int[3][256];
        int total = 0;

        try{

            temp.getPixels(originalPixels, 0, imageWidth, 1, 1, imageWidth - 1, imageHeight - 1);

            for(int i = 0;i<originalPixels.length;i++){
                ColorRGB colorRGB = new ColorRGB(originalPixels[i]);
                int rgb[] = canales(colorRGB);
                for(int canal = 0;canal<3;canal++){
                    histograma[canal][rgb[canal]]++;
                }
                total++;
            }

            int maxmin[][] = canalMaxMin(histograma,total,10);

            int max[] = maxmin[0];
            int min[] = maxmin[1];

            for(int i = 0;i<originalPixels.length;i++){
                ColorRGB colorRGB = new ColorRGB(originalPixels[i]);
                int nuevoColor = colorRGB.getEquilibrio(max,min);
                pixelsBin[i] = nuevoColor;
            }

            IntBuffer intBuffer = IntBuffer.allocate(imageHeight*imageWidth);
            intBuffer.put(pixelsBin);
            intBuffer.rewind();

            bitmapTemp.copyPixelsFromBuffer(intBuffer);




        }catch (Exception e){
            e.printStackTrace();
        }


        return  bitmapTemp;
    }


    public int[] canales(ColorRGB pixelColor){
        int rgb[] = {
          pixelColor.red,pixelColor.green,pixelColor.blue
        };
        return rgb;
    }

    public int[][] canalMaxMin(int[][] histograma,int total,int porcentajeRuido){
        int min[]={-1,-1,-1};
        int acumMin[]={0, 0, 0};
        int max[]={-1,-1,-1};
        int acumMax[]={0, 0, 0};

        for (int canal=0;canal < 3;canal++){
            for (int tono=0;tono < 256;tono++){
                if (min[canal]==-1){
                    acumMin[canal]+=histograma[canal][tono];
                    if (acumMin[canal] > total*porcentajeRuido/100){
                        min[canal]=tono;
                    }
                }
                if (max[canal]==-1){
                    acumMax[canal]+=histograma[canal][255-tono];
                    if (acumMax[canal] > total*porcentajeRuido/100){
                        max[canal]=255-tono;
                    }
                }
            }
        }

        System.out.println("Maximo");
        for(int x = 0;x<3;x++){
            System.out.println(max[x]);
        }

        System.out.println("Minimo");
        for(int x = 0;x<3;x++){
            System.out.println(min[x]);
        }

        return new int[][]{max,min};
    }




}
