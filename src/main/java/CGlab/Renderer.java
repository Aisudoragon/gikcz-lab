package CGlab;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Renderer {

    public enum LineAlgo { NAIVE, BRESENHAM, BRESENHAM_INT; }

    private BufferedImage render;
    public int h;
    public int w;

    private String filename;
    private LineAlgo lineAlgo;

    public Renderer(String filename, int width, int height, LineAlgo lineAlgo) {
        render = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.w = width;
        this.h = height;
        this.lineAlgo = lineAlgo;
        this.filename = filename;
    }

    public void drawPoint(int x, int y) {
        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        render.setRGB(x, y, white);
    }

    public void drawLine(int x0, int y0, int x1, int y1) {
        if(lineAlgo == LineAlgo.NAIVE) drawLineNaive(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM) drawLineBresenham(x0, y0, x1, y1);
        if(lineAlgo == LineAlgo.BRESENHAM_INT) drawLineBresenhamInt(x0, y0, x1, y1);
    }

    public void drawLineNaive(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj

        float dx = x1 - x0;
        float dy = y1 - y0;
        for (int x = x0; x <= x1; x++) {
            int y = Math.round(y0 + dy * (x - x0) / dx);
            drawPoint(x, y);
        }
    }

    public void drawLineBresenham(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj

        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = x1-x0;
        int dy = y1-y0;
        float derr = Math.abs(dy/(float)(dx));
        float err = 0;

        int y = y0;

        for (int x=x0; x<=x1; x++) {
            render.setRGB(x, y, white);
            err += derr;
            if (err > 0.5) {
                y += (y1 > y0 ? 1 : -1);
                err -= 1.;
            }
        } // Oktanty: 7, 8
    }

    public void drawLineBresenhamInt(int x0, int y0, int x1, int y1) {
        // TODO: zaimplementuj

        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);

        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;
        int err = dx + dy;

        int e2;

        while (true) {
            drawPoint(x0, y0);
            if (x0 == x1 && y0 == y1) break;
            e2 = 2 * err;
            if (e2 >= dy) {
                if (x0 == x1) break;
                err += dy;
                x0 += sx;
            }
            if (e2 <= dx) {
                if (y0 == y1) break;
                err += dx;
                y0 += sy;
            }
        }
    }

    public void save() throws IOException {
        File outputfile = new File(filename);
        render = Renderer.verticalFlip(render);
        ImageIO.write(render, "png", outputfile);
    }

    public void clear() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int black = 0 | (0 << 8) | (0 << 16) | (255 << 24);
                render.setRGB(x, y, black);
            }
        }
    }

    public static BufferedImage verticalFlip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flippedImage = new BufferedImage(w, h, img.getColorModel().getTransparency());
        Graphics2D g = flippedImage.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return flippedImage;
    }

    public Vec3f barycentric(Vec2f A, Vec2f B, Vec2f C, Vec2f P) {
        Vec3f v1 = new Vec3f(B.x - A.x, C.x - A.x, A.x - P.x);
                // tutaj potrzebujemy wektora składającego się ze współrzędnych
                // x wektorów AB, AC ora PA.

        Vec3f v2 = new Vec3f(B.y - A.y, C.y - A.y, A.y - P.y);
                // tutaj potrzebujemy wektora składającego się ze współrzędnych
                // y wektorów AB, AC ora PA.

        Vec3f cross = cross(v1, v2);
                // iloczyn wektorowy v1 i v2. Wskazówka: zaimplementuj do tego oddzielną metodę

        Vec2f uv = new Vec2f(cross.x / cross.z, cross.y / cross.z);
                // wektor postaci: cross.x / cross.z, cross.y / cross.z

        return new Vec3f(uv.x, uv.y, 1 - uv.x - uv.y);
            // współrzędne barycentryczne, uv.x, uv.y, 1- uv.x - uv.y
    }

    public static Vec3f cross(Vec3f A, Vec3f B) {
        float x, y, z;
        x = (A.y * B.z) - (A.z * B.x);
        y = (A.z * B.x) - (A.x * B.z);
        z = (A.x * B.y) - (A.y * B.x);
        return new Vec3f(x, y, z);
    }

    public void drawTriangle(Vec2f A, Vec2f B, Vec2f C) {
        // dla każdego punktu obrazu this.render:
        //      oblicz współrzędne baryc.
        //      jeśli punkt leży wewnątrz, zamaluj (patrz wykład)

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Vec2f P = new Vec2f(i + 0.5f, j + 0.5f);
                Vec3f barycentric = barycentric(A, B, C, P);
                if (barycentric.x > 0 && barycentric.x < 1 && barycentric.y > 0 && barycentric.y < 1 && barycentric.z > 0 && barycentric.z < 1) {
                    drawPoint(i,j);
                    //render.setRGB(x,y, );
                }
            }
        }
    }
}