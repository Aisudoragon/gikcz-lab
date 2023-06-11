package GiKCzK.lab;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class App {
    public static void main(String args[]) throws IOException {
        BufferedImage img = null;
        File f = null;
// wczytaj obraz
        try {
            f = new File("img/all_black.png");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
// pobieramy szerokość i wysokość obrazów
        int width = img.getWidth();
        int height = img.getHeight();
        // pobieramy środkowy piksel
        int p = img.getRGB(width / 2, height / 2);
// Odczytujemy wartosci kanalow przesuwajac o odpowiednia liczbe bitow w prawo, tak aby 
// kanal znalazł się na bitach 7-0, następnie zerujemy pozostałe bity używając bitowego AND z maską 0xFF.

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

// Ustawiamy wartosci poszczegolnych kanalow na przykładowe liczby


        a = 255;
        r = 100;
        g = 150;
        b = 200;

// TODO: ustaw ponownie wartości kanałów dla zmiennej p

        p = (a << 24) | (r << 16) | (g << 8) | b;

        img.setRGB(width / 2, height / 2, p);

        // Wykonanie metody allWhite

        allWhite(img);

// zapis obrazu
        try {
            f = new File("img/modified.png");
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void allWhite(BufferedImage img) {
        // TODO: zaimplementuj
        int wysokosc = img.getHeight();
        int szerokosc = img.getWidth();

        for (int i = 0; i < szerokosc; i++) {
            for (int j = 0; j < wysokosc; j++) {
                int a = 255;
                int r = 255;
                int g = 255;
                int b = 255;
                int p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(i, j, p);
            }
        }

        try {
            File f = new File("img/allWhite.png");
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void imgNegative(BufferedImage img) {
        // TODO: zaimplementuj
        ;

    }
}
