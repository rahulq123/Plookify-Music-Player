package finaltrackplayer;

/**
 *
 * @author Sami
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.util.Timer;
import java.util.TimerTask;




public class PlayerObject {
    FileInputStream fis;
    BufferedInputStream bis;
    public Player player;
    public long pauseLocation;
    public long songTotalLength;
    public String fileLocation;
    public long forwardTime = 4000;
    public long forwardTime2 = 0;
    public Timer timer;
    public int secondsPassed = 1;
    public static int trackLength;
    
    public void stop() {
        if(player != null) {
            player.close();
            pauseLocation = songTotalLength;
            //songTotalLength = 0;
        }
    }
    
    public void pause() {
        if(player != null) {
            try {
                pauseLocation = fis.available();
                player.close();
            } catch(IOException ex) {
                
            }
        }
    }
    
    public void resume() {
        try {
            System.out.println(FXMLDocumentController.secondsPassed);
            if(FXMLDocumentController.secondsPassed == 0) {
                play(fileLocation);
            }
            pause();
            fis = new FileInputStream(fileLocation);
            fis.skip(songTotalLength - pauseLocation);
            bis = new BufferedInputStream(fis);
            
            player = new Player(bis);
        } catch(FileNotFoundException | JavaLayerException ex) {
            
        } catch (IOException ex) {
            Logger.getLogger(PlayerObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch(JavaLayerException ex) {
                    
                }
            }
        }.start();
    }
    
    public void play(String path) {
        try {
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            
            player = new Player(bis);
            
            songTotalLength = fis.available();
            pauseLocation = fis.available();
            fileLocation = path + "";
           
        } catch(FileNotFoundException | JavaLayerException ex) {
            
        } catch(IOException ex) {
            Logger.getLogger(PlayerObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch(JavaLayerException ex) {
                    
                }
            }
        }.start();
    }
    
    public void forward() {
        try {
            pause();
            fis = new FileInputStream(fileLocation);
            fis.skip(songTotalLength - pauseLocation);
            fis.skip(forwardTime + forwardTime2);
            bis = new BufferedInputStream(fis);
            
            
            
            long oneSecond = songTotalLength / trackLength;
            FXMLDocumentController.secondsToBeSet = (forwardTime + forwardTime2) / oneSecond;
            
            player = new Player(bis);
        } catch(FileNotFoundException | JavaLayerException ex) {
            
        } catch (IOException ex) {
            System.out.println(forwardTime2);
            Logger.getLogger(PlayerObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch(JavaLayerException ex) {
                    
                }
            }
        }.start();
    }
    
    public void forwardStart() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if(forwardTime < 0) {
                    forwardTime = 4000;
                }
                if(forwardTime < 500000) {
                    forwardTime *= 2;    
                }
                if(forwardTime > 500000) {
                    forwardTime2 += forwardTime;
                    forwardTime = 64000;   
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 500);
    }
    
    public void forwardStop() {
        try {
            if((forwardTime + forwardTime2) > (fis.available())) {
                forwardTime = fis.available();
                forwardTime2 = 0;
                skip(PlayerObject.trackLength, PlayerObject.trackLength);
                FXMLDocumentController.secondsPassed = PlayerObject.trackLength;
                FXMLDocumentController.secondsToBeSet = 0;
                pauseLocation = fis.available();
                timer.cancel();
                forwardTime = 4000;
                forwardTime2 = 0;
            } else {
                forward();
                timer.cancel();
                forwardTime = 4000;
                forwardTime2 = 0;
            }
        } catch(Exception e) {
            
        }
    }
    
    public void rewindStart() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                if(forwardTime >= 0) {
                    forwardTime = -4000;
                }
                if(forwardTime > -500000) {
                    forwardTime *= 2;    
                }
                if(forwardTime < -500000) {
                    forwardTime2 += forwardTime;
                    forwardTime = -64000;   
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 500);
    }
    
    public void rewindStop() {
        System.out.println(forwardTime*-1 + forwardTime2*-1);
        try {
            if((forwardTime*-1 + forwardTime2*-1) > (songTotalLength - fis.available())) {
                forwardTime = songTotalLength - fis.available();
                forwardTime2 = 0;
                forwardTime *= -1;
                skip(0, PlayerObject.trackLength);
                FXMLDocumentController.secondsPassed = 0;
                FXMLDocumentController.secondsToBeSet = 0;
                pauseLocation = fis.available();
                timer.cancel();
                forwardTime = 4000;
                forwardTime2 = 0;
            } else {
                forward();
                timer.cancel();
                forwardTime = 4000;
                forwardTime2 = 0;
            }
            
        } catch(Exception e) {
            
        }
    }
    
    public void skipMethod(long skipTime) {
        try {
            fis = new FileInputStream(fileLocation);
            fis.skip(skipTime);
            bis = new BufferedInputStream(fis);
            player.close();
            
            
            player = new Player(bis);
        } catch(FileNotFoundException | JavaLayerException ex) {
            
        } catch (IOException ex) {
            Logger.getLogger(PlayerObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                } catch(JavaLayerException ex) {
                    
                }
            }
        }.start();
    }
    
    public void elapsedTimeMethod() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Seconds passed: " + secondsPassed);
                secondsPassed++;
                String sps = Integer.toString(secondsPassed);
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }
    
    public void skip(int seconds, int length) {
        long secondSize = songTotalLength / length;
        long skipTime = secondSize * seconds;
        skipMethod(skipTime);
        if(seconds > trackLength) {
            seconds = trackLength;
        }
        secondsPassed = seconds;
    }
}