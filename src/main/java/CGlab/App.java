package CGlab;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    String version = "0.02";

    public static void main(String[] args) {

        Renderer mainRenderer = new Renderer(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Renderer.LineAlgo.valueOf(args[3]));
        mainRenderer.clear();
        mainRenderer.drawPoint(100, 100);
        mainRenderer.drawLine(296, 164, 332, 61);
        mainRenderer.drawLine(424, 275, 399, 186);
        mainRenderer.drawLine(407, 378, 323, 351);
        mainRenderer.drawLine(591, 208, 469, 235);
        mainRenderer.drawLine(107, 304, 78, 412);
        mainRenderer.drawLine(513, 48, 551, 159);
        mainRenderer.drawLine(494, 279, 591, 296);
        mainRenderer.drawLine(32, 110, 113, 93);

        Vec2f A = new Vec2f(1, 1);
        Vec2f B = new Vec2f(100, 55);
        Vec2f C = new Vec2f(55, 100);

        Vec3i color = new Vec3i(176, 11, 105);

        mainRenderer.drawTriangle(A, B, C, color);

        try {
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
        return this.version;
    }
}