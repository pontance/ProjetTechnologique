package com.example.traitement_image;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ImageView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Texte crée dans l'application
        final TextView tv =findViewById(R.id.size);

        //Image crée dans l'application
        final ImageView img =findViewById(R.id.image1);

        //Bouton crée dans l'application;
        final Button reset = findViewById(R.id.reset);
        final Button grey = findViewById(R.id.grey);
        final Button unicolor = findViewById(R.id.unicolor);
        final Button colorize = findViewById(R.id.colorize);
        final Button graycontrast = findViewById(R.id.graycontrast);
        final Button colorcontrast = findViewById(R.id.colorcontrast);
        final Button reduce = findViewById(R.id.reduce);
        final Button equalization = findViewById(R.id.equalization);
        final Button equalizationColor = findViewById(R.id.equalizationColor);
        final Button convolution = findViewById(R.id.convolution);
        final Button convolution2 = findViewById(R.id.convolution2);


        //Image importé pour être utilisé dans l'image crée pour l'application;
        final Bitmap bit1 = BitmapFactory.decodeResource(getResources(),R.drawable.beach);
        final Bitmap bit3 = BitmapFactory.decodeResource(getResources(),R.drawable.peppers);
        final Bitmap bit4 = BitmapFactory.decodeResource(getResources(),R.drawable.bureau);
        final Bitmap bit5 = BitmapFactory.decodeResource(getResources(),R.drawable.water);
        final Bitmap bit6 = BitmapFactory.decodeResource(getResources(),R.drawable.woman);


        final SeekBar choose = findViewById(R.id.img);

        final Bitmap[] listImg={bit1,bit3,bit4,bit5,bit6};

        choose.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int height,width;
                final Bitmap bit2=listImg[progress].copy(listImg[progress].getConfig(),true);
                height=bit2.getHeight();
                width=bit2.getWidth();
                tv.setText("Height :"+height+"   Width :"+width);
                final int pixels[]= new int [height*width];
                bit2.getPixels(pixels,0,width,0,0,width,height);
                img.setImageBitmap(bit2);

                //Fonction qui réinitialise l'image;
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int r,g,b;
                        for(int i=0;i<pixels.length;i=i+1){
                            r= Color.red(pixels[i]);
                            g=Color.green(pixels[i]);
                            b=Color.blue(pixels[i]);
                            pixels[i]=Color.rgb(r,g,b);
                        }
                        bit2.setPixels(pixels,0,width,0,0,width,height);
                        img.setImageBitmap(bit2);

                    }
                });
                grey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toGray2(bit2);

                    }
                });
                colorize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        colorize(bit2);

                    }
                });
                unicolor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unicolor(bit2);

                    }
                });
                graycontrast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { grayContrast2(bit2); }
                });
                colorcontrast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { ColourContrast(bit2);
                    }
                });
                reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { reduceContrast(bit2,100,200);

                    }
                });
                equalization.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { histogramEqualization(bit2); }});
                equalizationColor.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {histogramEqualizationColor1(bit2); }});
                convolution.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { convolution(bit2);
                    }
                });
                convolution2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { convolution2(bit2,3);
                    }
                });

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    //Fonction qui retourne une ligne noire au milieu de l'image;
    void blackpix(Bitmap bmp){
        double j = bmp.getHeight() * 0.5;
        for(double i=0; i<bmp.getWidth();i=i+1){
            bmp.setPixel((int) i,(int) j,Color.BLACK);
        }
    }

    //Fonction qui retourne l'image en noir et blanc en utilisant getPixel et setPixel;
    void toGray(Bitmap bmp){
        int i,j;
        for(i=0; i<bmp.getWidth();i=i+1){
            for(j=0;j<bmp.getHeight();j=j+1){
                int p = bmp.getPixel(i,j);
                int gris = (int)(Color.red(p)*0.3)+(int)(Color.green(p)*0.59)+(int)(Color.blue(p)*0.11);
                bmp.setPixel(i,j,Color.rgb(gris,gris,gris));
            }
        }
    }

    //Fonction qui retourne l'image en noir et blanc en utilisant getPixels et setPixels;
    void toGray2(Bitmap bmp){
        int height,width;
        height=bmp.getHeight();
        width=bmp.getWidth();
        int pixels[];
        pixels= new int [height*width];
        bmp.getPixels(pixels,0,width,0,0,width,height);
        for(int i=0;i<height*width;i=i+1){
            int gris = (int)(Color.red(pixels[i])*0.3)+(int)(Color.green(pixels[i])*0.59)+(int)(Color.blue(pixels[i])*0.11);
            pixels[i]=Color.rgb(gris,gris,gris);
        }
        bmp.setPixels(pixels,0,width,0,0,width,height);
    }

    //Fonction qui retourne l'image dans une teinte de couleur unique;
    void colorize(Bitmap bmp){
        float v=(float)Math.random()*360;
        float hsv[];
        int pixels[]= new int [bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());
        for(int i=0;i<bmp.getHeight()*bmp.getWidth();i=i+1){
            //Color.RGBToHSV(Color.red(pixels[i]),Color.green(pixels[i]),Color.blue(pixels[i]),hsv);
            hsv=rgbtohsv(Color.red(pixels[i]),Color.green(pixels[i]),Color.blue(pixels[i]));
            hsv[0]=v;
            //int color=Color.HSVToColor(hsv);
            int color=hsvtorgb(hsv);
            pixels[i]=color;
        }
        bmp.setPixels(pixels,0,bmp.getWidth(),0,0,bmp.getWidth(),bmp.getHeight());
    }

    //Fonction qui converti rgb eh hsv;
    float[] rgbtohsv(int r,int g,int b){
        float hsv[]= new float[3];

        float red=r/255.f;
        float green=g/255.f;
        float blue=b/255.f;


        float max=Math.max(red,Math.max(green,blue));
        float min=Math.min(red,Math.min(green,blue));


        if (max==red){
            hsv[0]=60*(((green-blue)/(max-min))%6);

        }
        else{
            if (max==green){
                hsv[0]=60*(((blue-red)/(max-min))+2);
            }
            else{
                if (max==blue){
                    hsv[0]=60*(((red-green)/(max-min))+4);
                }
                else{
                    hsv[0]=0;
                }
            }
        }

        if (max==0){
            hsv[1]=0;
        }
        else{
            hsv[1]=(max-min)/max;
        }

        hsv[2]=max;

        return hsv;
    }

    //Fonction qui converti hsv en rgb;
    int hsvtorgb(float[] hsv){
        int red,green,blue;
        float r,g,b;
        float c,x,m;

        r=0;
        g=0;
        b=0;

        float hue=hsv[0];
        float saturation=hsv[1];
        float value=hsv[2];

        c=saturation*value;
        x=c*(1-Math.abs(((hue/60)%2)-1));
        m=value-c;

        if(hue>=0 && hue<60){
            r=c;
            g=x;
            b=0;
        }
        else{
            if(hue>=60 && hue<120){
                r=x;
                g=c;
                b=0;
            }
            else{
                if(hue>=120 && hue<180){
                    r=0;
                    g=c;
                    b=x;
                }
                else{
                    if(hue>=180 && hue<240){
                        r=0;
                        g=x;
                        b=c;
                    }
                    else{
                        if(hue>=240 && hue<300){
                            r=x;
                            g=0;
                            b=c;
                        }
                        else{
                            if(hue>=300 && hue<360){
                                r=c;
                                g=0;
                                b=x;
                            }
                        }
                    }
                }
            }
        }
        red=(int)((r+m)*255);
        green=(int)((g+m)*255);
        blue=(int)((b+m)*255)
        ;
        return Color.rgb(red,green,blue);

    }

    //Fonction qui conserve uniquement des teintes de rouge;
    void unicolor(Bitmap bmp){
        int red,green,blue;
        float hsv[];
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int[] pixels=new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        for(int i=0;i<pixels.length;i=i+1){
            red=Color.red(pixels[i]);
            green=Color.green(pixels[i]);
            blue=Color.blue(pixels[i]);
            hsv=rgbtohsv(Color.red(pixels[i]),Color.green(pixels[i]),Color.blue(pixels[i]));
            boolean test=false;
            if (hsv[0]>=325 || hsv[0]<35){
                test=true;
            }
            if (test==true){
                pixels[i]=hsvtorgb(hsv);
            }
            else{
                hsv[1]=0;
                pixels[i]=hsvtorgb(hsv);
            }
        }
        bmp.setPixels(pixels,0,width,0,0,width,height);

    }

    //Fonction qui retourne la valeur maximal de niveau de couleur dans une image en noir et blanc;
    int maxGray(int[] pixels){
        int max=Color.red(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.red(pixels[i]);
            if(tmp>max){
                max=tmp;
            }
        }
        return max;
    }

    //Fonction qui retourne la valeur minimal de niveau de couleur dans une image en noir et blanc;
    int minGray(int[] pixels){
        int min=Color.red(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.red(pixels[i]);
            if(tmp<min){
                min=tmp;
            }
        }
        return min;
    }

    //Fonction qui augmente le contraste dans une image en noir et blanc;
    void grayContrast(Bitmap bmp){
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int gray;
        int[] pixels= new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);
        int max=maxGray(pixels);
        int min=minGray(pixels);
        for(int i=0;i<pixels.length;i=i+1){
            gray=Color.red(pixels[i]);
            gray=(255*(gray-min))/(max-min);
            pixels[i]=Color.rgb(gray,gray,gray);
        }
        bmp.setPixels(pixels,0,width,0,0,width,height);

    }

    //Fonction qui augmente le contraste en utilisant la méthode du "look up table" dans une image en noir et blanc;
    void grayContrast2(Bitmap bmp){
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int gray;
        int[] lut=new int[256];
        int[] pixels= new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);
        int max=maxGray(pixels);
        int min=minGray(pixels);
        for(int i=0;i<256;i=i+1){
            lut[i]=(int)(255.f*(i-min))/(max-min);
        }
        for(int i=0;i<pixels.length;i=i+1){
            int tmp=Color.red(pixels[i]);
            gray=lut[tmp];
            pixels[i]=Color.rgb(gray,gray,gray);
        }
        bmp.setPixels(pixels,0,width,0,0,width,height);
    }

    //Fonction qui réduit le contraste dans une image en noir et blanc;
    void reduceContrast(Bitmap bmp, int min, int max) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int gray;
        int[] lut=new int[256];
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for(int i=0;i<256;i=i+1){
            lut[i]=(int)((i*(max-min))/255.f)+min;
        }
        for(int i=0;i<pixels.length;i=i+1){
            int tmp=Color.red(pixels[i]);
            gray=lut[tmp];
            pixels[i]=Color.rgb(gray,gray,gray);
        }
        bmp.setPixels(pixels,0,width,0,0,width,height);
    }

    //Fonction qui retourne la valeur maximal du rouge dans une image;
    int maxRed(int[] pixels){
        int max=Color.red(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.red(pixels[i]);
            if(tmp>max){
                max=tmp;
            }
        }
        return max;
    }

    //Fonction qui retourne la valeur minimal du rouge dans une image;
    int minRed(int[] pixels){
        int min=Color.red(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.red(pixels[i]);
            if(tmp<min){
                min=tmp;
            }
        }
        return min;
    }

    //Fonction qui retourne la valeur maximal du vert dans une image;
    int maxGreen(int[] pixels){
        int max=Color.green(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.green(pixels[i]);
            if(tmp>max){
                max=tmp;
            }
        }
        return max;
    }

    //Fonction qui retourne la valeur minimal du vert dans une image;
    int minGreen(int[] pixels){
        int min=Color.green(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.green(pixels[i]);
            if(tmp<min){
                min=tmp;
            }
        }
        return min;
    }

    //Fonction qui retourne la valeur maximal du bleu dans une image;
    int maxBlue(int[] pixels){
        int max=Color.blue(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.blue(pixels[i]);
            if(tmp>max){
                max=tmp;
            }
        }
        return max;
    }

    //Fonction qui retourne la valeur minimal du bleu dans une image;
    int minBlue(int[] pixels){
        int min=Color.blue(pixels[0]);
        int tmp;
        for (int i=0;i<pixels.length;i=i+1){
            tmp=Color.blue(pixels[i]);
            if(tmp<min){
                min=tmp;
            }
        }
        return min;
    }

    //Est censé augmenter le contraste d'une image en couleur;
    //Ne marche pas correctement : il faut passer par rgbToHsv et etendre la luminance;
    void ColourContrast(Bitmap bmp){

        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int[] lutRed=new int[256];
        int[] lutGreen=new int[256];
        int[] lutBlue=new int[256];
        int[] pixels= new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);
        int maxRed=maxRed(pixels);
        int minRed=minRed(pixels);
        int maxGreen=maxGreen(pixels);
        int minGreen=minGreen(pixels);
        int maxBlue=maxBlue(pixels);
        int minBlue=minBlue(pixels);
        for(int i=0;i<256;i=i+1){
            lutRed[i]=(int)(255.f*(i-minRed))/(maxRed-minRed);
            lutGreen[i]=(int)(255.f*(i-minGreen))/(maxGreen-minGreen);
            lutBlue[i]=(int)(255.f*(i-minBlue))/(maxBlue-minBlue);
        }
        int red,green,blue;
        for(int i=0;i<pixels.length;i=i+1){
            red=Color.red(pixels[i]);
            green=Color.green(pixels[i]);
            blue=Color.blue(pixels[i]);
            pixels[i]=Color.rgb(lutRed[red],lutGreen[green],lutBlue[blue]);
        }
        bmp.setPixels(pixels,0,width,0,0,width,height);
    }

    //Fonction qui egalise l'histogramme d'une image en noir et blanc;
    void histogramEqualization(Bitmap bmp){
        int grey;
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int[] pixels= new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        int[] histo = new int [256];
        for(int i=0;i<256;i=i+1){
            histo[i]=0;
        }

        for(int i=0;i<pixels.length;i=i+1){
            histo[Color.red(pixels[i])]=histo[Color.red(pixels[i])]+1;
        }

        for(int i=1;i<256;i=i+1){
            histo[i]+=histo[i-1];
        }
        for(int i=0;i<pixels.length;i=i+1){
            grey = (histo[Color.red(pixels[i])]*255)/histo[255];
            pixels[i]= Color.rgb(grey,grey,grey);
        }

        bmp.setPixels(pixels,0,width,0,0,width,height);
    }

    //Est censé égaliser l'histogramme d'une image en couleur;
    //Ne marche pas correctement
    void histogramEqualizationColor1(Bitmap bmp){
        int red,green,blue;
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int[] pixels= new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        int[] histoRed = new int [256];
        int[] histoGreen = new int [256];
        int[] histoBlue = new int [256];
        for(int i=0;i<256;i=i+1){
            histoRed[i]=0;
            histoGreen[i]=0;
            histoBlue[i]=0;
        }

        for(int i=0;i<pixels.length;i=i+1){
            histoRed[Color.red(pixels[i])]++;
            histoGreen[Color.green(pixels[i])]++;
            histoBlue[Color.blue(pixels[i])]++;

        }

        for(int i=1;i<256;i=i+1){
            histoRed[i]+=histoRed[i-1];
            histoGreen[i]+=histoGreen[i-1];
            histoBlue[i]+=histoBlue[i-1];
        }
        for(int i=0;i<pixels.length;i=i+1){
            red = (histoRed[Color.red(pixels[i])]*255)/histoRed[255];
            green = (histoGreen[Color.green(pixels[i])]*255)/histoGreen[255];
            blue = (histoBlue[Color.blue(pixels[i])]*255)/histoBlue[255];
            pixels[i]= Color.rgb(red,green,blue);
        }

        bmp.setPixels(pixels,0,width,0,0,width,height);
    }


    void histogramEqualizationColor2(Bitmap bmp){
        int red,green,blue;
        int width=bmp.getWidth();
        int height=bmp.getHeight();
        int[] pixels= new int[width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        int[] histo = new int [256];

        for(int i=0;i<256;i=i+1){
            histo[i]=0;

        }

        for(int i=0;i<pixels.length;i=i+1){
            histo[1/3*(Color.red(pixels[i])+Color.green(pixels[i])+Color.blue(pixels[i]))]++;
        }

        for(int i=1;i<256;i=i+1){
            histo[i]+=histo[i-1];

        }
        for(int i=0;i<pixels.length;i=i+1){
            red = (histo[Color.red(pixels[i])]*255)/histo[255];
            green = (histo[Color.green(pixels[i])]*255)/histo[255];
            blue = (histo[Color.blue(pixels[i])]*255)/histo[255];
            pixels[i]= Color.rgb(red,green,blue);
        }

        bmp.setPixels(pixels,0,width,0,0,width,height);
    }

    //Fonction qui applique un filtre moyenneur de taille 3*3;
    void convolution(Bitmap bmp){
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int [width*height];
        int[] pixels2 = new int [width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);

        for(int i=0;i<pixels.length;i=i+1){
            if(i>=width && i<=pixels.length-width && i%width!=0 && i%width!=width-1){
                int cvl= (Color.red(pixels[i-width-1])/9 +
                                Color.red(pixels[i-width])/9+
                                Color.red(pixels[i-width+1])/9+
                                Color.red(pixels[i-1])/9+
                                Color.red(pixels[i])/9+
                                Color.red(pixels[i+1])/9+
                                Color.red(pixels[i+width-1])/9+
                                Color.red(pixels[i+width])/9+
                                Color.red(pixels[i+width+1])/9);
                pixels2[i]=Color.rgb(cvl,cvl,cvl);

            }
            else{
                pixels2[i] =Color.rgb(0,0,0);
            }
        }
        bmp.setPixels(pixels2,0,width,0,0,width,height);
    }

    //Fonction qui applique un filtre moyenne de taille size*size; Ne fonctione pas correctement dans l'état actuel;
    void convolution2(Bitmap bmp,int size){
        int n=(size-1)/2;
        int w=size*size;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] pixels = new int [width*height];
        int[] pixels2 = new int [width*height];
        bmp.getPixels(pixels,0,width,0,0,width,height);
        for(int i=0;i<pixels.length;i=i+1){
            if(i>=n*width && i<=pixels.length-n*width && i%width!=0 && i%width!=width-1){//Le problème vient des deux dernières conditions du if mais je ne sais pas comment les corriger;
                int cvl=0;
                for(int j=-n;j<=n;j=j+1){
                    for (int k=-n*width;k<=n*width;k=k+width){
                        cvl=cvl+(Color.red(pixels[i+j+k])/w);
                    }
                }
                pixels2[i]=Color.rgb(cvl,cvl,cvl);
            }
            else{
                pixels2[i] =Color.rgb(0,0,0);
            }
        }
        bmp.setPixels(pixels2,0,width,0,0,width,height);
    }

}
