package ru.neyvan.hm.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

import ru.neyvan.hm.HM;


/**
 * Created by AndyGo on 30.06.2017.
 */
public class MusicManager implements Manager{
    Music music;
    public void init(){
        music = Gdx.audio.newMusic(Gdx.files.internal("sound_music/music0.mp3"));
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(final Music music) {
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if( HM.game.settings.isMusic) music.play();
                    }
                }, 60f);
            }
        });
        //music.setLooping(true);
    }
    public void play(){
        if( HM.game.settings.isMusic) {
            music.setVolume(HM.game.settings.music);
            if (!music.isPlaying()) music.play();
        }
    }
    public void setPitch(){
        if( HM.game.settings.isMusic) {
//            music.setOP(HM.game.settings.music);
//            if (!music.isPlaying()) music.play();
        }
    }
    public void pause(){
        music.pause();
    }
    public void dispose(){
        music.dispose();
    }
}
