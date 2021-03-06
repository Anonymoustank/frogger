package frogger;
import java.util.ArrayList;
import java.awt.Graphics;
public class Handler{
    ArrayList<GameObject> object = new ArrayList<GameObject>();
    public void tick(){
        for (GameObject i: object){
            if (i.getY() >= 0 - Game.my_height){
                i.tick();
            }
        }
    }
    public void render(Graphics g){
        for (GameObject i: object){
            if (i.getY() >= 0 - Game.my_height){
                i.render(g);
            }
        }
    }
    public void addObject(GameObject object){
        this.object.add(object);
    }
    public void removeObject(GameObject object){
        this.object.remove(object);
    }
}