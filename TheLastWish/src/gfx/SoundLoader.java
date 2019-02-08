package gfx;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
    
    
    public class SoundLoader {
    	static URL myUrl;
    	static Clip clip;
    	
    	
        public static Clip loadSound(String path) {
        	myUrl = Clip.class.getResource(path);
        	try {
        		clip = AudioSystem.getClip();
        		clip.open(AudioSystem.getAudioInputStream(myUrl));
    		} catch (LineUnavailableException e) {
    			e.printStackTrace();
    		} catch (UnsupportedAudioFileException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
                	return clip;
         
               
        }
       
    }