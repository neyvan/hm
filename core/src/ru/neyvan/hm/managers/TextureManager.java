package ru.neyvan.hm.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import ru.neyvan.hm.Constants;
import ru.neyvan.hm.HM;

/**
 * Created by AndyGo on 30.06.2017.
 */
public class TextureManager implements Manager{

    public TextureAtlas atlas;
    public Skin skin;
    public BitmapFont numberFont;
    private Texture gameBackground;
    private Texture textureFont;
    private Texture shaderTexture;
    private Texture[] episodesImage;

    @Override
    public void init() {

        if(Constants.gwt){
            atlas = new TextureAtlas(Gdx.files.internal("game_atlas.atlas"));
            skin = new Skin(Gdx.files.internal("style/hm_skin.json"));

            for (Texture t : atlas.getTextures()) {
                t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }

            shaderTexture = new Texture("shaders/shader.jpg");
            shaderTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            shaderTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

            textureFont = new Texture(Gdx.files.internal("style/hm_numbers.png"), true);
            textureFont.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
            numberFont = new BitmapFont(Gdx.files.internal("style/hm_numbers.fnt"), new TextureRegion(textureFont), false);

        }else{
            HM.game.manager.load("game_atlas.atlas", TextureAtlas.class);
            HM.game.manager.load("shaders/shader.jpg", Texture.class); 
            
            ParticleEffectLoader.ParticleEffectParameter pep = new ParticleEffectLoader.ParticleEffectParameter();
            pep.atlasFile = "game_atlas.atlas";
            HM.game.manager.load("particles/stream_energy.p", ParticleEffect.class, pep);
            HM.game.manager.load("particles/good_shine.p", ParticleEffect.class, pep);
            HM.game.manager.load("particles/bad_shine.p", ParticleEffect.class, pep);
            HM.game.manager.load("particles/explosion.p", ParticleEffect.class, pep);
            HM.game.manager.load("particles/fire_speed.p", ParticleEffect.class, pep);
            HM.game.manager.load("particles/slow_speed.p", ParticleEffect.class, pep);

            SkinLoader.SkinParameter params = new SkinLoader.SkinParameter("style/hm_skin.atlas");
            HM.game.manager.load("style/hm_skin.json", Skin.class, params);
            HM.game.manager.finishLoading();

            atlas = HM.game.manager.get("game_atlas.atlas");
            skin = HM.game.manager.get("style/hm_skin.json");

            for (Texture t : atlas.getTextures()) {
                t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            }

            shaderTexture = HM.game.manager.get("shaders/shader.jpg");
            
            shaderTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            shaderTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

            textureFont = new Texture(Gdx.files.internal("style/hm_numbers.png"), true);
            textureFont.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
            numberFont = new BitmapFont(Gdx.files.internal("style/hm_numbers.fnt"), new TextureRegion(textureFont), false);
       
        }
        
    }


    public Texture getGameBackground(int i){
        if (gameBackground != null) gameBackground.dispose();
        gameBackground = new Texture("backdrops/backdrop"+i+".jpg");
        return gameBackground;
    }
    public void setEpisodesImages(){
        episodesImage = new Texture[Constants.MAX_EPISODE];
        for (int i=1; i<=Constants.MAX_EPISODE; i++){
            episodesImage[i-1] = new Texture("episodes_images/episode"+i+".jpg");
        }
    }
    public Texture getEpisodesImage(int i){
        if(i <= 0 || i > episodesImage.length){
            Gdx.app.error("TextureManager.getEpisodeImage", "such episode not exist: "+i+", problem solved by install episodeImage0");
            return episodesImage[0];
        }
        return episodesImage[i-1];
    }
    public void disposeEpisodesImages(){
        for (int i=0; i<episodesImage.length; i++){
            episodesImage[i].dispose();
        }
        episodesImage = null;
    }
    public NinePatchDrawable getNinePatchDrawable(String name) {
        return new NinePatchDrawable(new NinePatch(new Texture(name+".9.png"), 10, 10, 10, 10));
    }

    public Texture getTranstionShaderTexture(){
        return shaderTexture;
    }

    @Override
    public void dispose() {
        textureFont.dispose();
        shaderTexture.dispose();
    }
}
