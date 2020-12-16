package com.example.miwok;

public class Word {
    private String miwokTranslation;
    private String defaultTranslation;
    private int imageId=-1;
    private int rawId;

    public Word(String miwokTranslation,String defaultTranslation,int rawId){
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.rawId = rawId;
    }

    public Word(String miwokTranslation,String defaultTranslation,int imageId,int rawId){
        this.miwokTranslation = miwokTranslation;
        this.defaultTranslation = defaultTranslation;
        this.imageId=imageId;
        this.rawId = rawId;
    }

    public String getMiwokTranslation(){
        return miwokTranslation;
    }

    public String getDefaultTranslation(){
        return defaultTranslation;
    }

    public int getImageId(){return imageId;}

    public boolean hasImage(){
        if(imageId==-1)
            return false;
        return true;
    }
    public int getRawId(){
        return rawId;
    }

}
